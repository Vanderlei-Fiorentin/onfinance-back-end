package com.onfinance.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.onfinance.entities.UsuarioEntity;
import com.onfinance.utils.PropertyUtil;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class JwtController {

    public String gerarJwt(UsuarioEntity usuario, List<String> roles) throws NoSuchAlgorithmException {

        int minutos = Integer.parseInt(PropertyUtil.get("com.onfinance.expiracao.token"));
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic(); //Get the key instance
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();//Get the key instance
        String token = null;

        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withExpiresAt(new Date(System.currentTimeMillis() + (minutos * 60 * 1000)))
                    .withClaim("Usuario", usuario.getUsuario())
                    .withArrayClaim("Roles", roles.toArray(new String[0]))
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            System.err.println(ex);
        }

        return token;
    }

    public DecodedJWT decodeToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt;
        } catch (JWTDecodeException ex) {
            System.err.println(ex);
            return null;
        }        
        
    }
    
    public boolean isExpired(String token) {
        
        Date now = Date.from(Instant.now());
        try {
            DecodedJWT jwt = JWT.decode(token);
            return now.after(jwt.getExpiresAt());
        } catch (JWTDecodeException ex) {
            System.err.println(ex);
            return false;
        }        
        
    }

}