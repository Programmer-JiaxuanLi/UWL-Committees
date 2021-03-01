package uwl.senate.coc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uwl.senate.coc.entities.Committee;
import uwl.senate.coc.entities.User;
import uwl.senate.coc.projections.UserSummary;
import uwl.senate.coc.projections.UserYearsOnly;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<UserYearsOnly> findDistinctByEmailOrderByYearAsc(String email);
    List<User> findByEmailEquals(String email);
    User findByEmailEqualsAndYearEquals(String email, String year);
    List<User> findByYear(String year);

    <T> T findById( Long id, Class<T> c);
}
