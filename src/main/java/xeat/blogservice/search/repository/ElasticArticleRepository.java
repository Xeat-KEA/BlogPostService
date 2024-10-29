package xeat.blogservice.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import xeat.blogservice.search.entity.ElasticArticle;

public interface ElasticArticleRepository extends ElasticsearchRepository<ElasticArticle, Integer> {
    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}")
    Page<ElasticArticle> findAllByQuery(String query, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}, {\"exists\": {\"field\": \"code_id\"}}]}}")
    Page<ElasticArticle> findCodeArticleByQuery(String query, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}, {\"bool\": {\"must_not\": [{\"exists\": {\"field\": \"code_id\"}}]}}]}}")
    Page<ElasticArticle> findArticleByQuery(String query, Pageable pageable);

}