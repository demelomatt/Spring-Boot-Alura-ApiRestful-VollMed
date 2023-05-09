package med.voll.api.adapter.web.dto.admin;

import med.voll.api.application.dto.admin.AdminDto;
import med.voll.api.domain.entity.admin.Admin;

public record AdminCadastrarResponse(
        Long id
) {
    public AdminCadastrarResponse(AdminDto admin) {
        this(admin.id());
    }
}
