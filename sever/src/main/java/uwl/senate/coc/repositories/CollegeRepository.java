package uwl.senate.coc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uwl.senate.coc.entities.College;
import java.util.List;

public interface CollegeRepository extends JpaRepository<College, Long> {
    List<College> findByYear(String year);
    List<College> findAllByYearBetween(String startYear, String endYear);
    @Transactional
    @Modifying
    @Query(value = "UPDATE college c set name =?1 where c.id = ?2",
           nativeQuery = true)
    void updateCollegeName(String name, Long Id);
}
