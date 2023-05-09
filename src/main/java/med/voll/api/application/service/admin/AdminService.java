package med.voll.api.application.service.admin;

import jakarta.transaction.Transactional;

import med.voll.api.application.dto.admin.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import med.voll.api.domain.entity.admin.Admin;
import med.voll.api.infra.repository.jpa.AdminRepository;
import med.voll.api.application.service.autenticacao.TokenService;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Transactional
    public AdminDto cadastrar(AdminDto dados) {
        Admin admin = new Admin(dados);
        admin.setLogin(dados.login());
        admin.setSenha(this.passwordEncoder.encode(dados.senha()));
        this.adminRepository.save(admin);

        return new AdminDto(admin);
    }

    public String getCredentials(AdminDto dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        Authentication auth = this.manager.authenticate(authToken);
        String tokenJwt = this.tokenService.gerarToken((Admin) auth.getPrincipal());

        return tokenJwt;
    }
}
