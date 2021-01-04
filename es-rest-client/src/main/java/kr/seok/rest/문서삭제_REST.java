package kr.seok.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * 문서 삭제
 */
@Slf4j
public class 문서삭제_REST {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        //인덱스 명
        String INDEX_NAME = "movie_auto_java";

        //타입 명
        String TYPE_NAME="_doc";

        //문서 키값
        String _id = "6";

        DeleteRequest request = new DeleteRequest(INDEX_NAME, TYPE_NAME, _id);
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);

        if(deleteResponse.status().getStatus() == 200) {
            log.info("삭제 성공");
        } else {
            log.info("삭제 실패");
        }
        client.close();
    }
}
