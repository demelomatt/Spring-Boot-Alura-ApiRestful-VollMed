package med.voll.api.service.autenticacao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import med.voll.api.domain.usuario.Usuario;
import med.voll.api.repository.UsuarioRepository;

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    String secret;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            String token = JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    private DecodedJWT getDecodedJWT(String token) {

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

    public Authentication getAuthentication(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            String token = authHeader.split("Bearer ")[1];
            String login = getDecodedJWT(token).getSubject();
            UserDetails usuario = this.usuarioRepository.findByLogin(login);
            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            return authentication;
        }

        return null;
    }
}
