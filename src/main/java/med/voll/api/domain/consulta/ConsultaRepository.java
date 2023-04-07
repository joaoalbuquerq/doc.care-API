package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    @Query("""
            select c 
            from Consulta c
            where c.paciente.id = :idPaciente
            and c.data between :horarioInicial and :horarioFinal
            """)
    Consulta existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime horarioInicial, LocalDateTime horarioFinal);
}
