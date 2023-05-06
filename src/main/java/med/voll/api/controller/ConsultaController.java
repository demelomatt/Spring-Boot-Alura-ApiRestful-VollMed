package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import med.voll.api.dto.consulta.ConsultaDetalheDto;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.service.consulta.ConsultaService;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<ConsultaDetalheDto> agendar(@RequestBody ConsultaDto dados) {
        ConsultaDetalheDto consulta = this.consultaService.agendar(dados);
        return ResponseEntity.ok(consulta);
    }
}
