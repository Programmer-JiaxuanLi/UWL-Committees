package uwl.senate.coc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uwl.senate.coc.entities.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> getByYear(String year);
    @Transactional
    @Modifying
    @Query(value = "UPDATE department d set name =?1 where d.id = ?2",
            nativeQuery = true)
    void updateDepartmentName(String name, Long Id);
}
