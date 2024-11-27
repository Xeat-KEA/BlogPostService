package xeat.blogservice.search.dto;

import lombok.Data;

@Data
public class ArticleSearchDto {
    private String query;
    private String type;
    private String sort;
    private Integer page;
}
