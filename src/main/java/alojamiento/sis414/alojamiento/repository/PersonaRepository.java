package alojamiento.sis414.alojamiento.repository;

import alojamiento.sis414.alojamiento.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
