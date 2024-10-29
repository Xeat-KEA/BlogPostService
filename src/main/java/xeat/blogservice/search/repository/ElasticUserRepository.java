package xeat.blogservice.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import xeat.blogservice.search.entity.ElasticUser;

public interface ElasticUserRepository extends ElasticsearchRepository<ElasticUser, String> {
    @Query("{\"match\": { \"nickname\": \"?0\" } }")
    Page<ElasticUser> findAllByQuery(String query, Pageable pageable);

}
