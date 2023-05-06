package med.voll.api.dto.admin;

import med.voll.api.domain.admin.Admin;

public record AdminDetalheDto() {
    public AdminDetalheDto(Admin admin) {
        this();
    }
}
