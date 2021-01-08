package kr.seok.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 	이건 왜 안될까? 그냥 통신만 되네
 */
@Slf4j
public class 인덱스생성_ASYNC_REST {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = getRestHighLevelClient();

        log.info("Client 연결");
        // Index명
        String INDEX_NAME = "movie_rest";

        // 타입명
        String TYPE_NAME = "_doc";

        // 매핑정보
        XContentBuilder indexBuilder = jsonBuilder()
                .startObject()
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
                .endObject();

        // 매핑 설정
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
        request.settings(
                Settings.builder()
                        .put("index.number_of_shards", 3)
                        .put("index.number_of_replicas", 0)
        );
        request.mapping(indexBuilder);

        // Alias 설정
        String ALIAS_NAME = "movie_auto_alias";
        request.alias(new Alias(ALIAS_NAME));

        client.indices().createAsync(request, RequestOptions.DEFAULT, new ActionListener<CreateIndexResponse>() {
            @Override
            public void onResponse(CreateIndexResponse createIndexResponse) {
                boolean acknowledged = createIndexResponse.isAcknowledged();
                boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
                if (acknowledged) log.info("성공");
                if (shardsAcknowledged) log.info("shardsAcknowledged");
            }

            @Override
            public void onFailure(Exception e) {
                log.info("실패 : {}", e.getMessage());
            }
        });
        log.info("정상 종료");
        client.close();
    }

    private static RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
    }
}
