package uz.tashkec.education.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.tashkec.education.domain.Participiant;

/**
 * Spring Data JPA repository for the Participiant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipiantRepository extends JpaRepository<Participiant, Long> {}
