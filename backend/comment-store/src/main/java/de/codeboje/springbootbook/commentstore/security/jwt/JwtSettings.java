package de.codeboje.springbootbook.commentstore.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "sbb.security.jwt")
public class JwtSettings {

    private Integer tokenExpirationTime;

    private String tokenIssuer;
    
    private String tokenSigningKey;

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }
    
    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }
    
    public String getTokenIssuer() {
        return tokenIssuer;
    }
    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }
    
    public String getTokenSigningKey() {
        return tokenSigningKey;
    }
    
    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }

}
