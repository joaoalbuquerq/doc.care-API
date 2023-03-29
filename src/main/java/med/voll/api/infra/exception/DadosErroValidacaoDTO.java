package med.voll.api.infra.exception;

import org.springframework.validation.FieldError;

public record DadosErroValidacaoDTO(String campo, String mensagem) {

    public DadosErroValidacaoDTO(FieldError err){
        this(err.getField(), err.getDefaultMessage());
    }

}
