package com.swp.hr_backend.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.swp.hr_backend.entity.Account;
import com.swp.hr_backend.model.TokenPayLoad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {
    private static final  long serialVersionUID = -2550185165626007488L;
    public static final long ACCESS_TOKEN_EXPIRED = 2 * 60 * 60; //2 giờ
    public static  final long REFRESH_TOKEN_EXPIRED = 2 * 24 * 60 * 60; //2 ngày
    private final String secret = "HR_SECRET" ;
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public  TokenPayLoad getTokenPayLoad(String token) {
        return getClaimFromToken(token , (Claims claim) -> {
             Map<String,Object> mapResult = (Map<String,Object>)claim.get("payload");
             return TokenPayLoad.builder().roleName((String)mapResult.get("roleName")).build();
        });
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject, long expiredDate) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredDate * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    public String generateToken(String username, long expiredDate,String rolename) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("payload", TokenPayLoad.builder().roleName(rolename).build());
        return doGenerateToken(claims, username, expiredDate);
    }
    public Boolean validateToken(String token, Account account) {
        final String username = getUsernameFromToken(token);
        return (username.equals(account.getUsername()) && account.isStatus() && !isTokenExpired(token));
    }

}
