package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultasService {

    @Autowired
    ConsultaRepository consultaRepository;
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    PacienteRepository pacienteRepository;
    public void agendar(DatosAgendaConsulta datosAgendaConsulta){

        //var medico = medicoRepository.getReferenceById(datosAgendaConsulta.idMedico());
        //var paciente = pacienteRepository.getReferenceById(datosAgendaConsulta.idPaciente());
        if(!pacienteRepository.findById(datosAgendaConsulta.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }
        if( datosAgendaConsulta.idMedico()!=null && !medicoRepository.existsById(datosAgendaConsulta.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }

        var paciente = pacienteRepository.getReferenceById(datosAgendaConsulta.idPaciente());
        var medico = seleccionarMedico(datosAgendaConsulta);

        var consulta = new Consulta(null,null,paciente,datosAgendaConsulta.fecha());

        consultaRepository.save(consulta);

    }

    private Medico seleccionarMedico(DatosAgendaConsulta datosAgendaConsulta) {
        if(datosAgendaConsulta.idMedico()!=null){
            return medicoRepository.getReferenceById(datosAgendaConsulta.idMedico());
        }
        if(datosAgendaConsulta.especialidad()==null){
            throw new ValidacionDeIntegridad("Debe escoger una especialidad para el medico.");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendaConsulta.especialidad(),datosAgendaConsulta.fecha());

    }
}
