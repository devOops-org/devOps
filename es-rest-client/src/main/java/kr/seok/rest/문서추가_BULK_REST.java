package kr.seok.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Slf4j
public class 문서추가_BULK_REST {
    public void basicBulk() throws IOException {
        RestHighLevelClient client = getRestHighLevelClient();

        // 인덱스명
        String INDEX_NAME = "movie_auto_java";

        // 타입명
        String TYPE_NAME="_doc";

        // 필드명
        String FIELD_NAME = "movieNm";


        // 데이터 생성
        BulkRequest request = new BulkRequest();

        request
                .add(new IndexRequest(INDEX_NAME)
                        .id("4")
                        .source(XContentType.JSON,FIELD_NAME, "살아남은 아이"));

        request
                .add(new IndexRequest(INDEX_NAME)
                        .id("5")
                        .source(XContentType.JSON,FIELD_NAME, "프렌즈: 몬스터섬의비밀"));

        request
                .add(new IndexRequest(INDEX_NAME)
                        .id("6")
                        .source(XContentType.JSON,FIELD_NAME, "캡틴아메리카 시빌워"));

        // 결과 조회
        getBulkResponse(client, request);

        client.close();
    }

    public void customBulkMerge() throws IOException {
        RestHighLevelClient client = getRestHighLevelClient();


        // 인덱스명
        String INDEX_NAME = "movie_auto_java";

        // 타입명
        String TYPE_NAME="_doc";

        // 필드명
        String FIELD_NAME = "movieNm";


        // 데이터 생성
        BulkRequest request = new BulkRequest();

        request
                .add(getMergeRequest(INDEX_NAME, "4", jsonBuilder()
                        .startObject()
                        .field("movieCd", "20173732")
                        .field("movieNm", "살아남은 아이")
                        .field("movieNmEn", "Last Child")
                        .field("openDt", "")
                        .field("typeNm", "장편")
                        .endObject()));

        request
                .add(getMergeRequest(INDEX_NAME, "5", jsonBuilder()
                        .startObject()
                        .field("movieCd", "20173732")
                        .field("movieNm", "프렌즈: 몬스터섬의비밀")
                        .field("movieNmEn", "Friends: Mononoke Shima no Naki")
                        .field("openDt", "")
                        .field("typeNm", "장편")
                        .endObject()));

        request
                .add(getMergeRequest(INDEX_NAME, "6", jsonBuilder()
                        .startObject()
                        .field("movieCd", "20173732")
                        .field("movieNm", "캡틴아메리카 시빌워")
                        .field("movieNmEn", "Captain America: Civil War")
                        .field("openDt", "")
                        .field("typeNm", "장편")
                        .endObject()));

        // 결과 조회
        getBulkResponse(client, request);

        client.close();
    }

    private RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
    }

    private void getBulkResponse(RestHighLevelClient client, BulkRequest request) throws IOException {
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            switch (bulkItemResponse.getOpType()) {
                case INDEX:

                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    if (indexResponse.status().getStatus() >= 200) {
                        log.info("문서 생성");
                    }
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    if (updateResponse.status().getStatus() >= 200) {
                        log.info("문서 수정");
                    }
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    if (deleteResponse.status().getStatus() == 200) {
                        log.info("문서 삭제");
                    }
            }
        }
    }

    /*
        기존 데이터를 bulk 업데이트 한다고 했을 때, 값이 있으면 수정, 없으면 생성 할 수 있도록 하는 방법? 이 아닐까
        없는 건 새로 생성되서 편해 보이긴 하는데 수정되는 경우에서 기존에 있는 데이터가 필요없는데 그대로 존재할 수 있는 문제가 있음
    */
    private UpdateRequest getMergeRequest(String INDEX_NAME, String id, XContentBuilder jsonData) {
        IndexRequest indexRequest =
                new IndexRequest(INDEX_NAME)
                        .id(id)
                        .source(jsonData);
        return new UpdateRequest(INDEX_NAME, id)
                .doc(jsonData)
                .upsert(indexRequest);
    }

    public static void main(String[] args) throws IOException {
        new 문서추가_BULK_REST().customBulkMerge();
    }
}
