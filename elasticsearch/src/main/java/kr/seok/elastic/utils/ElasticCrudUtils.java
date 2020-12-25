package kr.seok.elastic.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://www.baeldung.com/tag/httpclient/
 * https://www.baeldung.com/java-http-response-body-as-string
 * https://www.baeldung.com/spring-data-elasticsearch-tutorial
 *
 */
@Slf4j
public class ElasticCrudUtils {

    public static final String ELASTIC_SEARCH_HOME = "http://localhost:9200";

    /* 1. 엘라스틱서치 healthCheck */
    public static void basicGet() throws IOException {
        /* ElasticSearch 연결 확인 */
        getBody(ELASTIC_SEARCH_HOME);
    }

    /* 2. 엘라스틱서치 노드 체크 요청 URL */
    public static void basicGet1() throws IOException {
        getBody(ELASTIC_SEARCH_HOME, "/_cat/nodes?v&pretty");
    }

    /* 3. 엘라스틱서치 index 생성 */
    public static void basicGet2() {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut();

    }

    public static String getBody(String... url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(Arrays.toString(url));
        CloseableHttpResponse execute = client.execute(httpGet);

        if(execute.getStatusLine().getStatusCode() != 200) {
            System.out.println("요청 실패");
            return "";
        }
        HttpEntity entity = execute.getEntity();
        System.out.println(entity.getContentType());
        String body = EntityUtils.toString(entity);
        System.out.println(body);
        return body;
    }

    public static void basicPost() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:9200");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", "june"));
        params.add(new BasicNameValuePair("password", "pass"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response.getStatusLine());
        client.close();
    }

    public void jsonPost(String json) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:9200/hospital_index?pretty");

        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
//        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response.getEntity().getContent());
        client.close();
    }

    public void jsonPut(String url, String json) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);

        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
//        httpPost.setHeader("Accept", "application/json");

        httpPut.setHeader("Content-type", "application/json; charset=utf-8;");

        CloseableHttpResponse response = client.execute(httpPut);
        System.out.println(response.toString());
        client.close();
    }
}
