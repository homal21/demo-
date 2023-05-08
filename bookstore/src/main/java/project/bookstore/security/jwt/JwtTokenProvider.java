package project.bookstore.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project.bookstore.entity.User;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final String JWT_SECRET = "blahbleh";
    private final long JWT_EXPIRATION = 30* 1000 * 10000;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claim = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claim.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new JwtException(ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new JwtException(ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new JwtException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new JwtException(ex.getMessage());
        }
//        return false;
    }
}