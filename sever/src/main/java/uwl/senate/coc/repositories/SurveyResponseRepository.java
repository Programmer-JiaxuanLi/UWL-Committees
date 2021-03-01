package uwl.senate.coc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uwl.senate.coc.entities.SurveyResponse;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
	List<SurveyResponse> findBySurveyId(Long sid);
}