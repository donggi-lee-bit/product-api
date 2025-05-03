package donggi.lee.catalog.common.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
            .info(new Info()
                .title("상품 관리 및 인증 API")
                .version("v1")
                .description("회원가입, 로그인, 상품, 상품 옵션 관리용 API 문서"));
    }
}
