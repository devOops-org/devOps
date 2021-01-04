package kr.seok.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    /* scroll 방식 개선 */
    public void scrollSearch2() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));


        String INDEX_NAME = "movie_auto_java";
        String FIELD_NAME = "movieNm";
        String QUERY_TEXT = "캡틴아메리카";

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(
                matchQuery(FIELD_NAME, QUERY_TEXT));

        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest
                .source(searchSourceBuilder)
                .scroll(TimeValue.timeValueMinutes(1L));

        SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();

        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId(scrollId);

        //scroll ID가 여러개일 경우는 setScrollIds를 사용하여 조회 가능
        //request.setScrollIds(scrollIds);
        ClearScrollResponse response = client.clearScroll(request, RequestOptions.DEFAULT);

        //해당 scrollId가 정상적으로 release 되었는지 확인 가능
        boolean success = response.isSucceeded();
        int released = response.getNumFreed();

        client.close();
    }

    /* 스크립트 기반 스크롤 요청 */
    public void scrollSearch3() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        String INDEX_NAME = "movie_auto_java";
        String FIELD_NAME = "movieNm";
        String QUERY_TEXT = "캡틴아메리카";

        SearchTemplateRequest searchRequest = new SearchTemplateRequest();
        searchRequest.setRequest(new SearchRequest(INDEX_NAME));

        searchRequest.setScriptType(ScriptType.INLINE);
        searchRequest.setScript(
                        "{"
                        + "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } },"
                        + "  \"size\" : \"{{size}}\""
                        + "}"
        );

        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("field", FIELD_NAME);
        scriptParams.put("value", QUERY_TEXT);
        scriptParams.put("size", 10);

        searchRequest.setScriptParams(scriptParams);

        SearchTemplateResponse searchTemplateResponse = client.searchTemplate(searchRequest, RequestOptions.DEFAULT);
        SearchResponse response = searchTemplateResponse.getResponse();

        if(response.status().getStatus() == 200) {
            log.info("데이터 정상 호출");
        }

        client.close();
    }

    public static void main(String[] args) throws IOException {
        new 문서검색_스크롤_REST().scrollSearch3();
    }
}
