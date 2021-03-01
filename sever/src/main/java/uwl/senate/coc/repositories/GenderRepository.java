package uwl.senate.coc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uwl.senate.coc.entities.Gender;

import java.util.List;

public interface GenderRepository extends JpaRepository<Gender, Long> {
    List<Gender> getByYear(String year);

   @Transactional
   @Modifying
   @Query(value = "UPDATE gender g set name =?1 where g.id = ?2",
           nativeQuery = true)
   void updateGenderName(String name, Long Id);
}