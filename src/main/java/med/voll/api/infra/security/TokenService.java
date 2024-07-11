package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    public String generarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return
                    JWT.create()
                    .withIssuer("API voll med")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(getFechaExpiracion())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("error al generar token jwt");
        }
    }

    private Instant getFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        DecodedJWT verifier = null;
        try{
           var algoritmo = Algorithm.HMAC256(secret);
            verifier = JWT.require(algoritmo)
                   .withIssuer("API voll med")
                   .build()
                   .verify(tokenJWT);
                   verifier.getSubject();
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT invalido o expirado!");
        }

        if(verifier==null){
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();

    }

}
