package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import med.voll.api.dto.consulta.ConsultaDetalheResponse;
import med.voll.api.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.service.consulta.ConsultaService;

@Tag(name = "Consulta")
@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Operation(summary = "Agendar nova consulta")
    @PostMapping
    public ResponseEntity<ConsultaDetalheResponse> cadastrar(@RequestBody ConsultaAgendarRequest dados) {
        ConsultaDetalheResponse consulta = this.consultaService.agendar(dados);
        return ResponseEntity.ok(consulta);
    }
}
