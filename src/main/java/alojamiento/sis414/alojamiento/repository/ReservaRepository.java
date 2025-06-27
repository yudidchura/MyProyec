package alojamiento.sis414.alojamiento.repository;

import alojamiento.sis414.alojamiento.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.habitacion.id = :habitacionId " +
            "AND (:fechaEntrada < r.fechaSalida AND :fechaSalida > r.fechaEntrada)")
    boolean existeConflictoReserva(@Param("habitacionId") Long habitacionId,
                                   @Param("fechaEntrada") LocalDate fechaEntrada,
                                   @Param("fechaSalida") LocalDate fechaSalida);

}
