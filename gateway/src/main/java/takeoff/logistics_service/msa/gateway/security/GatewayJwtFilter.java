package takeoff.logistics_service.msa.gateway.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GatewayJwtFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String token = request.getHeaders().getFirst("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            log.error("JWT 토큰이 존재하지 않음");
            return chain.filter(exchange);
        }
        token = token.substring(7);

        if (!jwtUtil.validateToken(token)) {
            log.error("유효하지 않은 JWT 토큰");
            return exchange.getResponse().setComplete();
        }

        String userId = jwtUtil.getUserIdFromToken(token);
        String role = jwtUtil.getUserRoleFromToken(token);

        log.info("API Gateway - 인증 완료: userId={}, role={}", userId, role);

        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Id", userId)
                .header("X-User-Role", role)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }
}
