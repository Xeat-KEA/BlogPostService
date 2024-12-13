package xeat.blogservice.search.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import xeat.blogservice.search.entity.ElasticArticle;

public interface ElasticArticleRepository extends ElasticsearchRepository<ElasticArticle, Integer> {
    @Highlight(fields = {@HighlightField(name = "content"), @HighlightField(name = "title")}, parameters = @HighlightParameters(preTags = "<b>", postTags = "</b>"))
    @Query("{\"bool\": {\"should\": [" +
            "{\"match\": {\"title\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            "{\"match\": {\"content\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            "{\"term\": {\"code_id\": \"?0\"}}" +
            "]}}")
    SearchPage<ElasticArticle> findAllByQuery(String query, Pageable pageable);


    @Highlight(fields = {@HighlightField(name = "content")}, parameters = @HighlightParameters(preTags = "<b>", postTags = "</b>"))
    @Query("{\"bool\": {\"must\": [{\"bool\": {\"should\": [{\"match\": {\"title\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            " {\"match\": {\"content\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}]}}, {\"exists\": {\"field\": \"code_id\"}}]}}")
    SearchPage<ElasticArticle> findCodeArticleByQuery(String query, Pageable pageable);

    @Highlight(fields = {@HighlightField(name = "content")}, parameters = @HighlightParameters(preTags = "<b>", postTags = "</b>"))
    @Query("{\"bool\": {\"must\": [{\"bool\": {\"should\": [{\"match\": {\"title\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            " {\"match\": {\"content\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            "{\"term\": {\"code_id\": \"?0\"}}" +
            " ]}}, {\"bool\": {\"must_not\": [{\"exists\": {\"field\": \"code_id\"}}]}}]}}")
    SearchPage<ElasticArticle> findArticleByQuery(String query, Pageable pageable);

    /**이하 블로그 내부 검색 쿼리**/
    @Highlight(fields = {@HighlightField(name = "content")}, parameters = @HighlightParameters(preTags = "<b>", postTags = "</b>"))
    @Query("{\"bool\": {\"must\": [{\"term\": {\"blog_id\": ?1}}, {\"bool\": {\"should\": [{\"match\": {\"title\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}, " +
            "{\"match\": {\"content\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}]}}]}}")
    SearchPage<ElasticArticle> findAllByQuery(String query, Long blogId, Pageable pageable);

    @Highlight(fields = {@HighlightField(name = "content")}, parameters = @HighlightParameters(preTags = "<b>", postTags = "</b>"))
    @Query("""
            {
              "bool": {
                "must": [
                  {
                    "term": {
                      "blog_id": ?1
                    }
                  },
                  {
                    "term": {
                      "child_category_id": ?2
                    }
                  },
                  {
                    "bool": {
                      "should": [
                        {
                          "match": {
                            "title": {
                              "query": "?0",
                              "fuzziness": "AUTO"
                            }
                          }
                        },
                        {
                          "match": {
                            "content": {
                              "query": "?0",
                              "fuzziness": "AUTO"
                            }
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            }
            """)
    SearchPage<ElasticArticle> findChildByQuery(String query, Long blogId, Long childId, Pageable pageable);

    @Highlight(fields = {@HighlightField(name = "content")}, parameters = @HighlightParameters(preTags = "<b>", postTags = "</b>"))
    @Query("""
            {
              "bool": {
                "must": [
                  {
                    "term": {
                      "blog_id": ?1
                    }
                  },
                  {
                    "term": {
                      "parent_category_id": ?2
                    }
                  },
                  {
                    "bool": {
                      "should": [
                        {
                          "match": {
                            "title": {
                              "query": "?0",
                              "fuzziness": "AUTO"
                            }
                          }
                        },
                        {
                          "match": {
                            "content": {
                              "query": "?0",
                              "fuzziness": "AUTO"
                            }
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            }
            """)
    SearchPage<ElasticArticle> findParentByQuery(String query, Long blogId, Long parentId, Pageable pageable);

}
