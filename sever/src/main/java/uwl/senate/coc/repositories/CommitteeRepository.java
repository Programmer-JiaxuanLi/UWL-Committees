package uwl.senate.coc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uwl.senate.coc.entities.Committee;
import uwl.senate.coc.entities.User;
import uwl.senate.coc.projections.*;

import java.util.List;

@Repository
public interface CommitteeRepository extends JpaRepository<Committee, Long> {
    <T> T findByNameEqualsAndYearEquals(String name, String year, Class<T> clazz);
    <T> List<T> findByNameEquals( String name, Class<T> clazz );
    <T> List<T> findByYear(String year, Class<T> type);
    <T> T findByIdEquals( Long id, Class<T> type);

    List<CommitteeWithUserSummaries> findByYearBetween(String startYear, String endYear);
    List<CommitteeSummary> findByYearBetweenAndIdNotNull(String startYear, String endYear);
    List<CommitteeYear> findDistinctByYearNotNullOrderByYearAsc();
    List<CommitteeYear> findDistinctByNameEquals(String name);
    List<CommitteeSummary> findByMembers(User u);
    List<CommitteeSummary> findByVolunteers(User v);
    
    CommitteeWithUserSummaries findByIdEqualsAndIdNotNull(Long id);
}