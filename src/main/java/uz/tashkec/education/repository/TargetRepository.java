package uz.tashkec.education.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.tashkec.education.domain.Target;

/**
 * Spring Data JPA repository for the Target entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {}
