package med.voll.api.domain.consulta.validacoes.agendamentoConsulta;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoEscolhidoAtivo implements ValidadorAgendamentoConsultas{

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if(dados.idMedico() == null){
            return;
        }

        var medicoAtivo = repository.findAtivoById(dados.idMedico());
        if(!medicoAtivo)
            throw new ValidacaoException("Consulta não pode ser agendada com médico inativo!");

    }

}
