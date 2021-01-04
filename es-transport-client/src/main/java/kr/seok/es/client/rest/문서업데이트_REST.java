package kr.seok.es.client.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * script
 * 문서의 부분 update
 * update or insert
 *
 */
@Slf4j
public class 문서업데이트_REST {

    /**
     * 스크립트를 통한 필드 값 업데이트
     */
    public void updateScript() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        // 인덱스명
        String INDEX_NAME = "movie_auto_java";

        // 타입명
        String TYPE_NAME="_doc";

        // 문서 키값
        String _id = "1";


        /**
         * [업데이트 요청1]
         *
         * 스크립트를 이용한 업데이트 방식
         */
        UpdateRequest request1 = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id);

        Map<String, Object> parameters = singletonMap("count", 10);
        Script inline =
                new Script(ScriptType.INLINE, "painless", "ctx._source.prdtYear += params.count", parameters);

        request1.script(inline);

        try {
            UpdateResponse updateResponse = client.update(request1, RequestOptions.DEFAULT);

            if(updateResponse.status().getStatus() == 200) {
                GetResult getResult = updateResponse.getGetResult();
                log.info("수정 응답 확인");
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("업데이트 대상이 존재하지 않습니다.");
            }
        }
        client.close();
    }

    /**
     * 부분적인 필드 값 업데이트
     * 기존 문서를 검색해 새로 생성한 문서로 overriding
     */
    public void partUpdate() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        // 인덱스명
        String INDEX_NAME = "movie_auto_java";

        // 타입명
        String TYPE_NAME = "_doc";

        // 문서 키값
        String _id = "1";


        /**
         * [업데이트 요청2]
         *
         * 문서의 부분을 업데이트 방식
         */
        XContentBuilder builder = jsonBuilder();
        builder.startObject();
        builder.field("createdAt", new Date());
        builder.field("prdtYear", "2019");
        builder.field("typeNm", "장편");
        builder.endObject();

        UpdateRequest request2 = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id).doc(builder);

        try {
            UpdateResponse updateResponse = client.update(request2, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("업데이트 대상이 존재하지 않습니다.");
            }
        }


        client.close();
    }

    /**
     * merge 같은 기능
     * 문서를 검색해 문서가 존재하면 업데이트, 없으면 새로 생성
     */
    public void upsert() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        // 인덱스명
        String INDEX_NAME = "movie_auto_java";

        // 타입명
        String TYPE_NAME="_doc";

        // 문서 키값
        String _id = "1";


        /**
         * [업데이트 요청3]
         *
         * UPSERT를 이용한 업데이트 방식
         */
        _id = "2";
        /*
            문서를 생성 INSERT
            문서가 존재하지 않는다면 여기 설정한 내용만 생성됨

            밑에 설정한 upsertBuilder는 문서가 존재할 때만 적용됨
         */
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME, TYPE_NAME, _id)
                .source(jsonBuilder()
                        .startObject()
                            .field("movieCd", "20173732")
                            .field("movieNm", "살아남은 아이")
                            .field("movieNmEn", "Last Child")
                            .field("openDt", "")
                            .field("typeNm", "장편")
                        .endObject());

        /*
            문서를 수정 UPDATE
            문서가 존재한다면 여기 설정한 내용이 추가됨,

            문서가 존재하지 않는다면? 여기다 설정한 필드는 적용되지 않음
         */
        XContentBuilder upsertBuilder = jsonBuilder()
                .startObject()
                    .field("createdAt", new Date())
                .endObject();

        UpdateRequest upsertRequest = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id)

                .doc(upsertBuilder)

                .upsert(indexRequest);

        try {
            UpdateResponse updateResponse = client.update(upsertRequest,RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("업데이트 대상이 존재하지 않습니다.");
            }
        }


        client.close();
    }
    public static void main(String[] args) throws IOException {
        new 문서업데이트_REST()
                /* 특정 값에 추가적으로 값을 더하거나 빼는 스크립트를 작성하여 업데이트 */
//                .updateScript()
                /* 부분적으로 필드 값을 업데이트 */
//                .partUpdate()
                /* 문서를 검색해서 없으면 Insert 적용, 문서가 존재하면 update 적용 */
                .upsert()
        ;
    }
}
