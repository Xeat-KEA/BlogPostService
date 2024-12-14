package xeat.blogservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import xeat.blogservice.search.repository.ElasticArticleRepository;
import xeat.blogservice.search.repository.ElasticUserRepository;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = {ElasticArticleRepository.class, ElasticUserRepository.class})
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