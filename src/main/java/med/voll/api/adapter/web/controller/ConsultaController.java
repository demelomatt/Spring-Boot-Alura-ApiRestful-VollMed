package med.voll.api.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import med.voll.api.adapter.web.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.adapter.web.dto.consulta.ConsultaAtualizarRequest;
import med.voll.api.adapter.web.dto.consulta.ConsultaDetalheResponse;
import med.voll.api.adapter.web.dto.consulta.ConsultaListarResponse;
import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.application.service.consulta.ConsultaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Consulta")
@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Operation(summary = "Agendar nova consulta")
    @PostMapping
    public ResponseEntity<ConsultaDetalheResponse> cadastrar(@Valid @RequestBody ConsultaAgendarRequest dados, UriComponentsBuilder uriBuilder) {
        ConsultaDetalheResponse consulta =
                new ConsultaDetalheResponse(
                        this.consultaService.agendar(
                                new ConsultaIdDto(dados)));

        URI uri = uriBuilder.path("/consultas/{id}").buildAndExpand(consulta.id()).toUri();
        return ResponseEntity.created(uri).body(consulta);
    }

    @Operation(summary = "Atualizar consulta")
    @PutMapping
    public ResponseEntity<ConsultaDetalheResponse> atualizar(@Valid @RequestBody ConsultaAtualizarRequest dados) {
        ConsultaDetalheResponse consulta =
                new ConsultaDetalheResponse(
                        this.consultaService.atualizar(
                                new ConsultaIdDto(dados)));

        return ResponseEntity.ok(consulta);
    }

    @Operation(summary = "Cancelar consulta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar consultas")
    @GetMapping
    public ResponseEntity<Page<ConsultaListarResponse>> listar(@PageableDefault(size = 30, sort = {"data", "medico", "paciente"}) Pageable paginacao) {

        Page<ConsultaListarResponse> consultas = this.consultaService.listar(paginacao).map(ConsultaListarResponse::new);
        return ResponseEntity.ok(consultas);
    }

    @Operation(summary = "Detalhar consulta")
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDetalheResponse> detalhar(@PathVariable Long id) {
        ConsultaDetalheResponse consulta =
                new ConsultaDetalheResponse(
                        this.consultaService.detalhar(
                                id));
        return ResponseEntity.ok(consulta);
    }
}
