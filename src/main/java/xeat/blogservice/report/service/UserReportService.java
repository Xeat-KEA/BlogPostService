package xeat.blogservice.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.report.dto.BlogReportRequestDto;
import xeat.blogservice.report.dto.BlogReportResponseDto;
import xeat.blogservice.report.entity.UserReport;
import xeat.blogservice.report.repository.UserReportRepository;

@Service
@RequiredArgsConstructor
public class UserReportService {


    private final UserReportRepository userReportRepository;
    private final BlogRepository blogRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public Response<BlogReportResponseDto> reportBlog(Long blogId, BlogReportRequestDto blogReportRequestDto) {

        UserReport userReport = UserReport.builder()
                .blog(blogRepository.findById(blogId).get())
                .reportCategory(blogReportRequestDto.getReportCategory())
                .directCategory(blogReportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);
        return Response.success(BlogReportResponseDto.toDto(userReport));
    }
}
