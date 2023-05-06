package med.voll.api.component;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import med.voll.api.service.autenticacao.AuthService;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        this.authService.setAuthentication(request);

        filterChain.doFilter(request, response);
    }

}
