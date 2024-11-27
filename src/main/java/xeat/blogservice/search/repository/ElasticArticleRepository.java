package xeat.blogservice.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import xeat.blogservice.search.entity.ElasticArticle;

public interface ElasticArticleRepository extends ElasticsearchRepository<ElasticArticle, Integer> {
    @Highlight(fields = {@HighlightField(name = "content")}, parameters = @HighlightParameters(preTags = "<b>", postTags = "</b>"))
    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}")
    SearchPage<ElasticArticle> findAllByQuery(String query, Pageable pageable);

    @Highlight(fields = {@HighlightField(name = "content")}, parameters = @HighlightParameters(preTags = "<b>", postTags = "</b>"))
    @Query("{\"bool\": {\"must\": [{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}, {\"exists\": {\"field\": \"code_id\"}}]}}")
    SearchPage<ElasticArticle> findCodeArticleByQuery(String query, Pageable pageable);

    @Highlight(fields = {@HighlightField(name = "content")}, parameters = @HighlightParameters(preTags = "<b>", postTags = "</b>"))
    @Query("{\"bool\": {\"must\": [{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}, {\"bool\": {\"must_not\": [{\"exists\": {\"field\": \"code_id\"}}]}}]}}")
    SearchPage<ElasticArticle> findArticleByQuery(String query, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"term\": {\"nickname\": \"?1\"}}, {\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}]}}")
    Page<ElasticArticle> findAllByQueryAndNickname(String query, String nickname, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"term\": {\"nickname\": \"?1\"}}, {\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}, {\"exists\": {\"field\": \"code_id\"}}]}}")
    Page<ElasticArticle> findCodeArticleByQueryAndNickname(String query, String nickname, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"term\": {\"nickname\": \"?1\"}}, {\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}, {\"bool\": {\"must_not\": [{\"exists\": {\"field\": \"code_id\"}}]}}]}}")
    Page<ElasticArticle> findArticleByQueryAndNickname(String query, String nickname, Pageable pageable);
}
