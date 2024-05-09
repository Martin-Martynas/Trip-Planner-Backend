package ca.javau9.tripplanner.security;

import ca.javau9.tripplanner.exception.JwtAuthenticationException;
import ca.javau9.tripplanner.models.UserDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${javau9.app.jwtSecret}")
    private String jwtSecret;

    @Value("${javau9.app.jwtExpirationMs}")
    private int jwtExpirationMs;



    public void setSecretKey(String key) {
        jwtSecret = key;
    }

    public void setExpirationMs(int ms) {
        jwtExpirationMs = ms;
    }

    public String generateJwtToken(Authentication authentication) {

        UserDto userPrincipal = (UserDto) authentication.getPrincipal();

        logger.info("Generating JWT token for user: {}", authentication.getPrincipal());

        String token = Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();

        logger.info("JWT token generated successfully: {}", token);

        return token;
    }
    public Key toKey(String secret) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    private Key key() {
        return toKey(jwtSecret);
    }


    public String extractUsernameFromToken(HttpServletRequest request) {
        // Get the authorization header from the request
        String headerAuth = request.getHeader("Authorization");

        // Check if the header is null or doesn't start with "Bearer "
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            // Extract the token from the header
            String token = headerAuth.substring(7);

            try {
                // Use JwtUtils to extract the username from the token
                return getUserNameFromJwtToken(token);
            } catch (JwtAuthenticationException e) {
                // Log any errors and return null
                logger.error("Failed to extract username from JWT token: {}", e.getMessage());
                return null;
            }
        }

        // If the header is invalid, return null
        return null;
    }

    public String getUserNameFromJwtToken(String token) throws JwtAuthenticationException {

        try{
            String username = Jwts
                    .parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

            logger.info("Successfully parsed JWT token. Username: {}", username);

            return username;

        } catch (Exception e) {
            logger.error("Failed to parse JWT token: {}", e.getMessage());
            throw new JwtAuthenticationException("Failed to parse JWT token", e);
        }

    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith((SecretKey)key()).build().parse(authToken);

            logger.info("JWT token validation successful.");

            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


}
