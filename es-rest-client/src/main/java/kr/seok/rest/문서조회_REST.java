package kr.seok.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Map;

/**
 * 특정 ID에 해당하는 문서를 조회
 * 문서를 Map 형태로 반환
 */
@Slf4j
public class 문서조회_REST {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        // 인덱스명
        String INDEX_NAME = "movie_auto_java";

        // 타입명
        String TYPE_NAME = "_doc";

        // 문서 키값
        String _id = "1";

        // 요청
        GetRequest request =
                new GetRequest(INDEX_NAME, TYPE_NAME, _id);

        // 응답
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        // 응답의 결과를 Map 형태로 제공받는다.
        if (response.isExists()) {
            long version = response.getVersion();
            Map<String, Object> sourceAsMap = response.getSourceAsMap();
            log.info("response String : {}", response.getSourceAsString());
            log.info("version :: {}", version);
            for(Map.Entry<String, Object> entity : sourceAsMap.entrySet()) {
                log.info("response entity : {}", entity);
            }
        } else {
            System.out.println("결과가 존재하지 않습니다.");
        }

        client.close();
    }
}
