package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.cancelamentoConsulta.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.agendamentoConsulta.ValidadorAgendamentoConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dadosConsulta){

        if(dadosConsulta.idPaciente() != null && !pacienteRepository.existsById(dadosConsulta.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        if(dadosConsulta.idMedico() != null && !medicoRepository.existsById(dadosConsulta.idMedico())){
            throw new ValidacaoException("Id do medico informado não existe");
        }

        validadoresDeConsultas.forEach( v -> v.validar(dadosConsulta));

        var medico = escolherMedico(dadosConsulta);

        if(medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }

        var paciente = pacienteRepository.getReferenceById(dadosConsulta.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dadosConsulta.data(), null);
        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
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
