package kr.seok.es.client.rest;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class 인덱스삭제_REST {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        // Index명
        String INDEX_NAME = "movie_auto_java";


        // 인덱스 삭제
        DeleteIndexRequest request = new DeleteIndexRequest(INDEX_NAME);

        DeleteIndexResponse deleteIndexResponse =
                client.indices().delete(request, RequestOptions.DEFAULT);

        boolean acknowledged = deleteIndexResponse.isAcknowledged();

        client.close();
    }
}
