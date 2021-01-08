package kr.seok.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * ID를 이용해 문서 추가
 *
 */
@Slf4j
public class 문서추가_REST {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = getRestHighLevelClient();

        // 인덱스 명
        String INDEX_NAME = "movie_auto_java";

        // 타입 명
        String TYPE_NAME="_doc";

        // 문서 키값
        String _id = "1";


        // 데이터 추가
        IndexRequest request = new IndexRequest(INDEX_NAME)
                .id(_id);

        request.source(jsonBuilder()
                .startObject()
                    .field("movieCd", "20173732")
                    .field("movieNm", "살아남은 아이")
                    .field("movieNmEn", "Last Child")
                    .field("prdtYear", 1)
                .endObject()
        );

        // 결과 조회
        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            if(response.status().getStatus() == 201) log.info("정상 생성");
            /*ID 충돌로 인한 오류 발생 시 예외처리라고 되어 있는데 문서 내용이 변하기만 함 */
        } catch(ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("문서 생성에 실패하였습니다.");
            }
        }

        client.close();
    }

    private static RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
    }
}
