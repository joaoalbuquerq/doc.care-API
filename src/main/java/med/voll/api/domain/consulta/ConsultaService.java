package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadoresDeConsultas;

    public ResponseEntity agendar(DadosAgendamentoConsulta dadosConsulta){

        if(dadosConsulta.idPaciente() != null && !pacienteRepository.existsById(dadosConsulta.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        if(dadosConsulta.idMedico() != null && !medicoRepository.existsById(dadosConsulta.idMedico())){
            throw new ValidacaoException("Id do medico informado não existe");
        }

        validadoresDeConsultas.forEach( v -> v.validar(dadosConsulta));

        var medico = escolherMedico(dadosConsulta);
        var paciente = pacienteRepository.getReferenceById(dadosConsulta.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dadosConsulta.data());
        consultaRepository.save(consulta);
        return null;
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dadosConsulta) {
        if(dadosConsulta.idMedico() != null){
            return medicoRepository.getReferenceById(dadosConsulta.idMedico());
        }

        if(dadosConsulta.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido");
        }

        return medicoRepository.escolherMedicoLivreNaData(dadosConsulta.especialidade(), dadosConsulta.data());

    }

}
