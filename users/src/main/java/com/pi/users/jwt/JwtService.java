package com.pi.users.jwt;

import com.pi.users.controllers.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.AclFileAttributeView;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService {
    private final static String SECRET_KEY ="Z4h5/H39t6gdMbJ0pAfce1EUn0JANtsYJWL9NLxjnGBzh0UZoh5BszZNPoKQoWS2";
    //private final static byte[] SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    //getSubject refers to the user email
    public String extractUserEmail(String token) {
        return extractClaim(token,Claims::getSubject) ;

    }

    public <T> T extractClaim(String token , Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String,Object> extraClaims ,
                                UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token) ;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);
    }


    //get all the claims of jwt (exp , isa , nbf ..)
    //SigninKey is the signature of the token which is used to verify
    // the identity of the sender of the jwt and that the message wasn't changed
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigninKey())
                .build().parseClaimsJws(token)
                .getBody();

    }

    private Key getSigninKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
       // return Keys.hmacShaKeyFor(SECRET_KEY);


    }




}
