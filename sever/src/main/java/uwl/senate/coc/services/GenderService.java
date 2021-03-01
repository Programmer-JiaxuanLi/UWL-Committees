package uwl.senate.coc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uwl.senate.coc.entities.Gender;
import uwl.senate.coc.repositories.GenderRepository;

@Service
public class GenderService {
	@Autowired
	private GenderRepository genderRepo;

	public List<Gender> getByYear( String year ) {
		return this.genderRepo.getByYear( year );
	}

	public Gender read(Long id) {
		return this.genderRepo.getOne( id );
	}

	public Gender create(Gender gender) {
		return this.genderRepo.save( gender );
	}

	public void delete(Long id) {
		this.genderRepo.deleteById( id );
	}

	public void update(Long id, Gender gender) {
		if( id == null || gender == null || !id.equals(gender.getId()) ) {
			throw new IllegalArgumentException();
		}

		Boolean exists = this.genderRepo.findById( id ).isPresent();
		if( exists ) {
			Gender existing = this.genderRepo.findById( id ).get();
			if( !existing.getYear().equals( gender.getYear() ) ) {
				throw new IllegalArgumentException(); 
			}
			this.genderRepo.updateGenderName( gender.getName(), id );
		} else {
			throw new IllegalArgumentException();
		}

	}

}
