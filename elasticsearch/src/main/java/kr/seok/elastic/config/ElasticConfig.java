package kr.seok.elastic.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableElasticsearchRepositories(
        basePackages = "kr.seok.elastic.repository.elastic"
)
@EnableJpaRepositories(
        basePackages = "kr.seok.elastic.repository.jpa"
)
public class ElasticConfig {

    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.clustername")
    private String clusterName;

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration =
                ClientConfiguration.builder()
                        .connectedTo(
                                "localhost:9200"
                                //new HttpHost(esHost, esPort).toHostString()
                        )
                        .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
