package med.voll.api.application.service.autenticacao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import med.voll.api.domain.entity.admin.Admin;

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    String secret;

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String gerarToken(Admin admin) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            String token = JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(admin.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public DecodedJWT getDecodedJWT(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.require(algorithm)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(token);

        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido.");
        }

    }
}
