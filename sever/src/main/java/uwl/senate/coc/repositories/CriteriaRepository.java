package uwl.senate.coc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uwl.senate.coc.entities.Committee;
import uwl.senate.coc.entities.Criteria;

import java.util.List;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
    List<Criteria> findByCommitteeEquals(Committee c);
}