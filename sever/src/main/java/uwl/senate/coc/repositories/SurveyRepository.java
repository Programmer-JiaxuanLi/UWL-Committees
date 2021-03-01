package uwl.senate.coc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import uwl.senate.coc.entities.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
	public <T> T findByUserId(Long uid, Class<T> c);
}
