package kr.seok.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * async 에 문제가 있는지 관련내용만 안됨
 */
@Slf4j
public class 문서추가_BULK_Optimize_REST {

    public static void main(String[] args) throws IOException, InterruptedException {
        RestHighLevelClient client = getRestHighLevelClient();

        //인덱스 명
        String INDEX_NAME = "movie_auto_java";

        //타입 명
        String TYPE_NAME="_doc";

        String FIELD_NAME = "movieNm";

        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                log.debug("Executing bulk [{}] with {} requests",
                        executionId, numberOfActions);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  BulkResponse response) {
                if (response.hasFailures()) {
                    log.warn("Bulk [{}] executed with failures", executionId);
                } else {
                    log.debug("Bulk [{}] completed in {} milliseconds",
                            executionId, response.getTook().getMillis());
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                log.error("Failed to execute bulk", failure);
            }
        };
        BulkProcessor bulkProcessor = BulkProcessor.builder(
                (request, bulkListener) ->
                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener
        ).build();

        BulkProcessor.Builder builder = BulkProcessor.builder(
                (request, bulkListener) ->
                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                listener);

        // bulkProcessor 설정
        extracted(builder);

        BulkRequest request = new BulkRequest(INDEX_NAME);
        String _id = "7";
        IndexRequest one = new IndexRequest(INDEX_NAME).id(_id).source(XContentType.JSON, FIELD_NAME, "살아남은 아이");
        _id = "8";
        IndexRequest two = new IndexRequest(INDEX_NAME).id(_id).source(XContentType.JSON, FIELD_NAME, "프렌즈: 몬스터섬의비밀");
        _id = "9";
        IndexRequest three = new IndexRequest(INDEX_NAME).id(_id).source(XContentType.JSON, FIELD_NAME, "캡틴아메리카 시빌워");

        builder.build().add(one);
        builder.build().add(two);
        builder.build().add(three);

        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);

        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            switch (bulkItemResponse.getOpType()) {
                case INDEX:

                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
            }
        }


        boolean terminated = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);

        bulkProcessor.close();
    }

    private static void extracted(BulkProcessor.Builder builder) {
        builder.setBulkActions(500);
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
        builder.setConcurrentRequests(0);
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
        builder.setBackoffPolicy(BackoffPolicy
                .constantBackoff(TimeValue.timeValueSeconds(1L), 3));

        //500개의 문서가 저장되면 Elasticsearch에 저장 요청한다.
        builder.setBulkActions(3);
        //1MB이상의 문서가 쌓이면 Elasticsearch에 저장 요청한다.
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
        //10초가 되면 문서를 Elasticsearch에 저장 요청한다.
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
    }

    private static RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
    }
}
