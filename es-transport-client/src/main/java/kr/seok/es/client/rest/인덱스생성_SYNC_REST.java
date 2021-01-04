package kr.seok.es.client.rest;

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

@Slf4j
public class 인덱스생성_SYNC_REST {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        log.info("Client 연결");
        // Index명
        String INDEX_NAME = "movie_auto_java";

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
                                /* Search API 사용시 text 타입이라고 오류가 남 keyword로 수정 테스트*/
                                .field("type", "text")
                                .field("fielddata", true)
//                                .field("type", "keyword")
                                .field("store", "true")
                                .field("index_options", "docs")
                            .endObject()
                            .startObject("movieNmEn")
                                .field("type", "text")
                                .field("fielddata", true)
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

        // 인덱스 생성 (동기화 방식)
        CreateIndexResponse createIndexResponse =
                client.indices().create(request, RequestOptions.DEFAULT);

        boolean acknowledged = createIndexResponse.isAcknowledged();

        if(acknowledged) {
            log.info("acknowledged {}", true);
        }
        client.close();
    }
}
