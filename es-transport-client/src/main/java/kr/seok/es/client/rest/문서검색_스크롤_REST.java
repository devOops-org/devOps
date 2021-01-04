package kr.seok.es.client.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * 검색 시 필요에 의해 한 번에 많은 양의 검색 결과를 요청할 때가 있다.
 * 엘라스틱 서치는 검색 결과를 한 번에 제공하지 않고, 페이지 단위로 나눠서 순차적으로 제공
 *
 * 요청할 때마다 순차적으로 다음 페이지를 제공하는 API
 *
 */
@Slf4j
public class 문서검색_스크롤_REST {

    public void scrollSearch() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));


        String INDEX_NAME = "movie_auto_java";
        String FIELD_NAME = "movieNm";
        String QUERY_TEXT = "캡틴아메리카";


        // 검색 쿼리 설정
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(
                /* 쿼리 만들기 */
                matchQuery(FIELD_NAME, QUERY_TEXT)
        );

        // 스크롤 생성
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));


        // 최초 요청
        SearchRequest searchRequest =
                /* 검색 요청시 인덱스명 설정 */
                new SearchRequest(INDEX_NAME);

        searchRequest
                /* 검색 쿼리 설정 */
                .source(searchSourceBuilder)
                /* 스크롤 기준 생성 */
                .scroll(scroll);

        // 최초 응답
        SearchResponse searchResponse =
                client.search(
                        /* 검색 요청 (인덱스, 검색 쿼리, 스크롤) */
                        searchRequest, RequestOptions.DEFAULT);
        /* 최초 응답의 스크롤 아이디를 얻어옴 */
        String scrollId = searchResponse.getScrollId();
        /* hits 된 데이터 저장 */
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        /* 첫 데이터 호출 후 남은 데이터가 존재하면 지속적으로 호출 (페이징 처리) */
        while (searchHits != null && searchHits.length > 0) {

            // 다음 요청 ( 스크롤 아이디를 기준으로 스크롤 요청 값 생성 )
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);

            // 다음 응답
            searchResponse = client.scroll(
                    /* 스크롤 요청 값 기준 유지 */
                    scrollRequest, RequestOptions.DEFAULT);
            /* 위 내용 반복 */
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();

        }

        client.close();
    }

    public static void main(String[] args) throws IOException {
        new 문서검색_스크롤_REST().scrollSearch();
    }
}
