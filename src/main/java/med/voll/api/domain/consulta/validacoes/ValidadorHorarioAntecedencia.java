package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioAntecedencia {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var horarioAtual = LocalDateTime.now();
        var diferencaMinutos = Duration.between(horarioAtual, dataConsulta).toMinutes();

        if(diferencaMinutos < 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }

}
