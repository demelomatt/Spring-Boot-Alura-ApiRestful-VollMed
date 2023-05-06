package med.voll.api.dto.admin;

import med.voll.api.domain.admin.Admin;

public record AdminCadastrarResponse() {
    public AdminCadastrarResponse(Admin admin) {
        this();
    }
}
