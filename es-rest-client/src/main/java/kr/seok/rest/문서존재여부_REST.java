package kr.seok.rest;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * 검색쿼리를 이용하여 문서의 존재여부 확인하는 메서드
 */
@Slf4j
public class 문서존재여부_REST {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = getRestHighLevelClient();

        //인덱스 명
        String INDEX_NAME = "movie_auto_java";

        //타입 명
        String TYPE_NAME = "_doc";

        //문서 키값
        String _id = "1";

        GetRequest getRequest = new GetRequest(INDEX_NAME, _id);
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        if (exists)
            log.info("문서가 존재합니다.");
        else
            log.info("문서가 존재하지 않습니다.");

        client.close();
    }

    private static RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
    }
}
