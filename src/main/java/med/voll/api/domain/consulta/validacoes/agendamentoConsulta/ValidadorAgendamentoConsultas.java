package med.voll.api.domain.consulta.validacoes.agendamentoConsulta;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public interface ValidadorAgendamentoConsultas {

    void validar(DadosAgendamentoConsulta dados);

}
