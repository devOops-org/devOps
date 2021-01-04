package kr.seok.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Exception in thread "main" java.lang.NoSuchMethodError: org.elasticsearch.client.RestClient.performRequestAsync(Lorg/elasticsearch/client/Request;Lorg/elasticsearch/client/ResponseListener;)V
 * 	at org.elasticsearch.client.RestHighLevelClient.performRequestAsync(RestHighLevelClient.java:1325)
 * 	at org.elasticsearch.client.RestHighLevelClient.performRequestAsync(RestHighLevelClient.java:1302)
 * 	at org.elasticsearch.client.RestHighLevelClient.performRequestAsyncAndParseEntity(RestHighLevelClient.java:1284)
 * 	at org.elasticsearch.client.IndicesClient.createAsync(IndicesClient.java:190)
 * 	at kr.seok.rest.인덱스생성_ASYNC_REST.main(인덱스생성_ASYNC_REST.java:64)
 */
@Slf4j
public class 인덱스생성_ASYNC_REST {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        log.info("Client 연결");
        // Index명
        String INDEX_NAME = "movie_rest";

        // 타입명
        String TYPE_NAME = "_doc";

        // 매핑정보
        XContentBuilder indexBuilder = jsonBuilder()
                .startObject()
                    .startObject(TYPE_NAME)
                        .startObject("properties")
                            .startObject("movieCd")
                                .field("type", "keyword")
                                .field("store", "true")
                                .field("index_options", "docs")
                            .endObject()
                            .startObject("movieNm")
                                .field("type", "text")
                                .field("store", "true")
                                .field("index_options", "docs")
                            .endObject()
                            .startObject("movieNmEn")
                                .field("type", "text")
                                .field("store", "true")
                                .field("index_options", "docs")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();

        // 매핑 설정
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
        request.mapping(TYPE_NAME, indexBuilder);

        // Alias 설정
        String ALIAS_NAME = "moive_auto_alias";
        request.alias(new Alias(ALIAS_NAME));

        client.indices().createAsync(request, new ActionListener<CreateIndexResponse>() {
            @Override
            public void onResponse(CreateIndexResponse createIndexResponse) {
                boolean acknowledged = createIndexResponse.isAcknowledged();
                if(acknowledged) log.info("성공");
            }

            @Override
            public void onFailure(Exception e) {
                log.info("실패 : {}", e.getMessage());
            }
        });
        client.close();
    }
}
