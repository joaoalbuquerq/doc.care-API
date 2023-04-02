package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

import java.time.DayOfWeek;

public class ValidadorHorarioFuncionamentoClinica {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesAberturaClinica = dataConsulta.getHour() < 7;
        var depoisEncerramentoClinica = dataConsulta.getHour() > 18;

        if(domingo || antesAberturaClinica || depoisEncerramentoClinica){
            throw new ValidacaoException("Consulta fora do horario de funcionamento da clinica");
        }
    }

}
