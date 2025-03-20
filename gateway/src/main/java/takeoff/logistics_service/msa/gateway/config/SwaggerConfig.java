package takeoff.logistics_service.msa.gateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 20.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi serviceApi() {
        return GroupedOpenApi.builder()
            .group("all-services")
            .pathsToMatch("/api/**")  // 모든 서비스의 API 경로
            .build();
    }
}
