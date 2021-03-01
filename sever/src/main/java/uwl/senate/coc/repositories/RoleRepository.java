package uwl.senate.coc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uwl.senate.coc.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}