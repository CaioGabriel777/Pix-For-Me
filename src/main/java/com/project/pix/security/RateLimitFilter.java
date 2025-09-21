package com.project.pix.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket newBucket() {
        // Limite de 6 req/minuto
        Refill refillMinute = Refill.greedy(6, Duration.ofMinutes(1));
        Bandwidth limitMinute = Bandwidth.classic(6, refillMinute);

        // Limite de 10 req/dia
        Refill refillDay = Refill.greedy(10, Duration.ofDays(1));
        Bandwidth limitDay = Bandwidth.classic(10, refillDay);

        return Bucket.builder()
                .addLimit(limitMinute)
                .addLimit(limitDay)
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String key = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(key, k -> newBucket());

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("""
                        {
                          "status": 429,
                          "error": "too_many_requests",
                          "message": "Você atingiu o limite de requisições. Tente novamente mais tarde."
                        }
                    """);
        }
    }
}
