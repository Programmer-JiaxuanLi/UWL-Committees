package uwl.senate.coc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uwl.senate.coc.entities.Committee;
import uwl.senate.coc.entities.Duty;

import java.util.List;

@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {
    List<Duty> findByCommitteeEquals(Committee c);
}
