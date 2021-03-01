package uwl.senate.coc.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uwl.senate.coc.entities.Department;
import uwl.senate.coc.repositories.DepartmentRepository;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepo;

	public List<Department> getByYear( String year ) {
		return this.departmentRepo.getByYear( year );
	}

	public Department create(Department department) {
		return this.departmentRepo.save( department );
	}

	public void delete(Long id) {
		this.departmentRepo.deleteById( id );
	}

	public Department read( Long id ) {
		return this.departmentRepo.getOne( id );
	}

	public void update( Long pathId, Department department) {
		if( department == null || !pathId.equals(department.getId()) ) throw new IllegalArgumentException();

		Boolean exists = this.departmentRepo.findById( department.getId() ).isPresent();

		if( exists ) {
			Department existing = this.departmentRepo.findById( department.getId() ).get();
			if( department.getYear() == null || !department.getYear().equals( existing.getYear() )) {
				throw new IllegalArgumentException();
			} else {
				this.departmentRepo.updateDepartmentName(department.getName(), pathId);
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

}
