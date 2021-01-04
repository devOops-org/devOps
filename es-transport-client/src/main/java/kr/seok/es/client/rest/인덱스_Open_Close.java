package kr.seok.es.client.rest;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * 엘라스틱서치 클러스터 내부의 인덱스는 운영 중에는 설정 정보를 수정할 수 없다.
 * 만약 운영 중에 설정 정보를 변경하려면 인덱스를 닫고 수정한 후 다시 열어야 하는데
 * 이때 Open API와 Close API를 사용한다.
 *
 * Close API를 사용하기 위해서는 client.indeces().close() 메서드를 사용
 * Open API를 사용하기 위해서는 client.indices().open() 메서드를 사용
 *
 */
public class 인덱스_Open_Close {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        // Index명
        String INDEX_NAME = "moive_auto_alias";

        // Index Close
        CloseIndexRequest requestClose = new CloseIndexRequest(INDEX_NAME);

        CloseIndexResponse closeIndexResponse =
                client.indices().close(requestClose, RequestOptions.DEFAULT);

        boolean ackClose = closeIndexResponse.isAcknowledged();

        // Index Open
        OpenIndexRequest requestOpen = new OpenIndexRequest(INDEX_NAME);

        OpenIndexResponse openIndexResponse =
                client.indices().open(requestOpen, RequestOptions.DEFAULT);

        boolean ackOpen = openIndexResponse.isAcknowledged();

        client.close();
    }
}
