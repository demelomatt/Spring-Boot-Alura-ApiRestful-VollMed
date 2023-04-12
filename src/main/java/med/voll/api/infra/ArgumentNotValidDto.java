package med.voll.api.infra;

import org.springframework.validation.FieldError;

public record ArgumentNotValidDto(String field, String msg) {
    public ArgumentNotValidDto(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
