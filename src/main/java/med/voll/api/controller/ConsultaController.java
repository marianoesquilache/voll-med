package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultasService;
import med.voll.api.domain.consulta.DatosAgendaConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.flywaydb.core.internal.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/consultas")

public class ConsultaController{

    @Autowired
    AgendaDeConsultasService agendaDeConsultasService;

    @PostMapping
    @Transactional
    public ResponseEntity agendarConsulta(@RequestBody @Valid DatosAgendaConsulta datosAgendaConsulta){
        agendaDeConsultasService.agendar(datosAgendaConsulta);
            return ResponseEntity.ok(new DatosDetalleConsulta(null,null,null,null));
    }

}
