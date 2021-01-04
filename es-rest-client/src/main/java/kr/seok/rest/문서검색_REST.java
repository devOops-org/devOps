package kr.seok.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Map;

/**
 * SEARCH API
 * SearchRequest를 이용한 검색 요청
 * QueryDSL 클래스를 사용하여 검색
 *
 * Exception in thread "main" ElasticsearchStatusException[Elasticsearch exception
 * [type=search_phase_execution_exception, reason=all shards failed]]; nested: ElasticsearchException[Elasticsearch exception [
 * type=illegal_argument_exception,
 * reason=Fielddata is disabled on text fields by default.
 *          Set fielddata=true on [movieNm] in order to load fielddata in memory by uninverting the inverted index.
 *          Note that this can however use significant memory. Alternatively use a keyword field instead.]];
 *
 *          nested: ElasticsearchException[Elasticsearch exception [type=illegal_argument_exception, reason=Fielddata is disabled on text fields by default.
 *          Set fielddata=true on [movieNm] in order to load fielddata in memory by uninverting the inverted index. Note that this can however use significant memory. Alternatively use a keyword field instead.]];
 *
 */
@Slf4j
public class 문서검색_REST {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        // 인덱스명
        String INDEX_NAME = "movie_auto_java";

        // 타입명
        String TYPE_NAME="_doc";

        // 필드명
        String FIELD_NAME = "movieNm";

        // 검색 쿼리 설정
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder
                .query(QueryBuilders.matchAllQuery())
                .from(0)
                .size(5)
                .sort(
                        new FieldSortBuilder(FIELD_NAME).order(SortOrder.DESC)
                );

        // 요청
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.types(TYPE_NAME);
        searchRequest.source(searchSourceBuilder);

        // 응답
        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits searchHits = searchResponse.getHits();

        /* hits 된 데이터 출력 */
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            log.info("data : {}", sourceAsMap);
        }

        client.close();

    }
}
