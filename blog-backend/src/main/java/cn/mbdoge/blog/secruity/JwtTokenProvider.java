package cn.mbdoge.blog.secruity;


import cn.mbdoge.blog.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jyx
 */
@Component
public class JwtTokenProvider {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CHECK_KEY = "c";
    private static final String CHECK_VALUE = "u";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ROLES = "roles";
    private static final String AUTHORITY_KEY = "authority";

    @Value("${jwt.secret}")
    private String secret = "secret";

    @Value("${jwt.expiration}")
    private long expiration = 3600L;



    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(UserDetails userDetails) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put(CLAIM_KEY_ROLES, userDetails.getAuthorities());
        claims.put(CLAIM_KEY_CREATED, now);
        claims.put(CHECK_KEY, CHECK_VALUE);

        Date validity = new Date(now.getTime() + expiration * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * // UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
     * @param token token
     * @return 身份
     */
    @SuppressWarnings("unchecked")
    Authentication getAuthentication(String token) {

        Jws<Claims> claims = getClaimsFromToken(token);
        String userName = claims.getBody().getSubject();

        List<Map<String, String>> a = (List<Map<String, String>>) claims.getBody().get(CLAIM_KEY_ROLES);

        return new UsernamePasswordAuthenticationToken(userName, "", a.stream().map((map)-> new SimpleGrantedAuthority(map.get(AUTHORITY_KEY))).collect(Collectors.toList()));
    }

    private Jws<Claims> getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .require(CHECK_KEY, CHECK_VALUE)
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtAuthenticationException("AccountStatusUserDetailsChecker.expired" , e);
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("AbstractAccessDecisionManager.accessDenied", e);
        }
// ExpiredJwtException
// UnsupportedJwtException
// MalformedJwtException
// SignatureException


    }

    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = getClaimsFromToken(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // 签名错误，
            throw new InvalidJwtAuthenticationException("Expired or invalid token", e);
        }
    }
}
