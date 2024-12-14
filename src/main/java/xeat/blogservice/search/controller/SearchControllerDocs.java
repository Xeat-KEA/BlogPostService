package xeat.blogservice.search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.search.dto.ArticleSearchDto;
import xeat.blogservice.search.dto.ArticleSearchResultDto;
import xeat.blogservice.search.entity.ElasticUser;

@Tag(name = "검색 API")
public interface SearchControllerDocs {

    @Operation(summary = "게시글 검색", description = "ex) /blog/article/search?query=자바&type=all&sort=score&page=0")
    @Parameters(value = {@Parameter(name = "query", description = "사용자가 입력한 검색어, 입력 안하면 검색 결과 비어있음", in = ParameterIn.QUERY),
            @Parameter(name = "type", description = "검색할 게시글 유형 all, normal(일반게시글만), code(코테게시글만)", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "sort", description = "score=연관도순, latest=최신순", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "page", description = "페이지번호, 0부터 시작", required = true, in = ParameterIn.QUERY)}
    )
    Response<Page<ArticleSearchResultDto>> allArticleSearch(@ModelAttribute @Parameter() ArticleSearchDto articleSearchDto);

    @Operation(summary = "사용자(블로그) 검색", description = "query는 사용자가 입력한 검색어")
    Response<Page<ElasticUser>> blogSearch(@RequestParam String query, @PageableDefault(size = 20) Pageable pageable);
}
