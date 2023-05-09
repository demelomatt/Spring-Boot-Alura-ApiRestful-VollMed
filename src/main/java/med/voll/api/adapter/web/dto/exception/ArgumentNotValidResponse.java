package med.voll.api.adapter.web.dto.exception;

import org.springframework.validation.FieldError;

public record ArgumentNotValidResponse(String field, String msg) {
    public ArgumentNotValidResponse(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
