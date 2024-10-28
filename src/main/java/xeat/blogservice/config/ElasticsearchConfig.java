package xeat.blogservice.config;

import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import xeat.blogservice.search.repository.ElasticArticleRepository;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = {ElasticArticleRepository.class,ElasticArticleRepository.class})
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {

        return ClientConfiguration.builder()
                .connectedTo("172.16.211.61:9200")
                .withConnectTimeout(30000)
                .withSocketTimeout(30000)
                .build();
    }

}