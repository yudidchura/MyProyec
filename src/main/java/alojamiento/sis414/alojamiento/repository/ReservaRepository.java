package alojamiento.sis414.alojamiento.repository;

import alojamiento.sis414.alojamiento.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
