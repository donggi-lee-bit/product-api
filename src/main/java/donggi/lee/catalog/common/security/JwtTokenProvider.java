package donggi.lee.catalog.common.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKeyBase64;

    @Value("${jwt.expiration-ms}")
    private long accessTokenExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        // Base64 인코딩된 비밀키를 디코딩해 HMAC-SHA 키 객체로 생성 (단방향 해싱)
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyBase64);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /** 주어진 subject(이메일)로만 액세스 토큰을 생성 */
    public String createAccessToken(final String subject) {
        final Date now = new Date();
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenExpirationMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    /** HTTP 요청의 Authorization 헤더에서 Bearer 토큰을 추출 */
    public String resolveToken(final HttpServletRequest request) {
        final var bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    /** 토큰의 유효성을 검사 (서명 및 만료) */
    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    /** 토큰에서 Authentication 객체를 생성 */
    public Authentication getAuthentication(final String token) {
        final var claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        final var email = claims.getSubject();

        // 권한 정보가 필요하다면 claims.get("roles", List.class) 등을 활용
        return new UsernamePasswordAuthenticationToken(
            new UserPrincipal(email),
            null,
            List.of()
        );
    }

    public record UserPrincipal(String email) {}
}
