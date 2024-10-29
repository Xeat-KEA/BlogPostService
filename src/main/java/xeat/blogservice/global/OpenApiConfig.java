package xeat.blogservice.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private final String devUrl;

    public OpenApiConfig(
            @Value("${XeatBlog.openapi.dev-url}") final String devUrl) {
        this.devUrl = devUrl;
    }

    @Bean
    public OpenAPI openAPI() {
        final Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.description("개발 환경 서버 URL");


        final Info info = new Info()
                .title("XEAT_BLOG API")
                .version("v1.0.0")
                .description("XEAT 블로그 서비스 API");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }

}
