package med.voll.api.application.service.autenticacao;

import jakarta.servlet.http.HttpServletRequest;
import med.voll.api.infra.repository.jpa.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return this.adminRepository.findByLogin(login);
    }

    private Authentication getAuthentication(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            String token = authHeader.split("Bearer ")[1];
            String login = this.tokenService.getDecodedJWT(token).getSubject();
            UserDetails usuario = this.loadUserByUsername(login);
            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            return authentication;
        }

        return null;
    }

    public void setAuthentication(HttpServletRequest request) {
        Authentication authentication = this.getAuthentication(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
