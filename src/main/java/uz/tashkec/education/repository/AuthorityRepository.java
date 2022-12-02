package uz.tashkec.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tashkec.education.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
