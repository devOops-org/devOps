package kr.seok.trans;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class 인덱스_분석기_추가_생성 {
    @SuppressWarnings({"resource"})
    public static void main(String[] args) throws IOException {


        /**
         * 클라이언트 생성
         */
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));

        /**
         * 인덱스 생성
         */

        // Index명
        String INDEX_NAME="moive_nori_java";

        // 타입명
        String TYPE_NAME = "_doc";

        // 매핑정보
        XContentBuilder indexBuilder = jsonBuilder()
                .startObject()
                    .startObject(TYPE_NAME)
                        .startObject("properties")
                            .startObject("movieCd")
                                .field("type","keyword")
                                .field("store","true")
                                .field("index_options","docs")
                            .endObject()
                            .startObject("movieNm")
                                .field("type","text")
                                .field("store","true")
                                .field("index_options","docs")
                            .endObject()
                            .startObject("movieNmEn")
                                .field("type","text")
                                .field("store","true")
                                .field("index_options","docs")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();

        // 세팅정보
        XContentBuilder settingBuilder = jsonBuilder()
                .startObject()
                    .startObject("index")
                        .startObject("analysis")
                            .startObject("tokenizer")
                                .startObject("nori_user_dict_tokenizer")
                                    .field("type","nori_tokenizer")
                                    .field("decompound_mode","mixed")
                                    .field("user_dictionary","userdict_ko.txt")
                                .endObject()
                            .endObject()
                            .startObject("analyzer")
                                .startObject("nori_token_analyzer")
                                    .field("tokenizer","nori_user_dict_tokenizer")
                                    .array("filter","lowercase")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();

        // 인덱스 생성
        client.admin().indices()
                .prepareCreate(INDEX_NAME)
                .setSettings(settingBuilder)
                .addMapping(TYPE_NAME, indexBuilder)
                .get();


        /**
         * 클라이언트 종료
         */
        client.close();


    }
}
