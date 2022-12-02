package uz.tashkec.education.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.tashkec.education.domain.Groups;

/**
 * Spring Data JPA repository for the Groups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {
    @Query("select groups from Groups groups where groups.user.login = ?#{principal.username}")
    List<Groups> findByUserIsCurrentUser();
}
