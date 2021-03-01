package uwl.senate.coc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uwl.senate.coc.entities.Committee;
import uwl.senate.coc.entities.Survey;
import uwl.senate.coc.entities.SurveyResponse;
import uwl.senate.coc.entities.User;
import uwl.senate.coc.repositories.SurveyRepository;
import uwl.senate.coc.repositories.SurveyResponseRepository;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository surveyRepo;

    @Autowired 
    private SurveyResponseRepository surveyResponseRepo;
    
    @Autowired
    private CommitteeService committeeService;
    
    public Survey create( User user ) {
    	List<Committee> committees = committeeService.getCommitteesByYear( user.getYear() );
	
		Survey survey = new Survey.Builder()
				.comment("")
				.userId( user.getId() )
				.year( user.getYear() )
				.build();
		
		surveyRepo.save( survey );
		
		List<SurveyResponse> responses = committees
				.stream()
				.map( c -> new SurveyResponse.Builder()
						.committee( c )
						.selected( false )
						.build() )
				.collect( Collectors.toList() );
		
		surveyResponseRepo.saveAll( responses );		
		return survey;
    }
    
    public <T> T getByUserId( Long uid, Class<T> clazz) {
    	return surveyRepo.findByUserId(uid, clazz );
    }
    
    public List<SurveyResponse> getResponsesBySurveyId( Long sid ) {
    	return surveyResponseRepo.findBySurveyId(sid);
    }
    
    public Survey update( Survey survey ) {
    	Survey oldSurvey = this.surveyRepo.getOne( survey.getId() );
    	oldSurvey.setComment( survey.getComment() );

    	survey.getResponses().forEach( rNew -> {
    		Optional<SurveyResponse> response = oldSurvey
    				.getResponses()
    				.stream()
    				.filter( rOld -> rOld.getId() == rNew.getId() )
    				.findFirst();

    		
    		if( response.isPresent() ) {
    			response.get().setSelected( rNew.getSelected() );
    		}
    	});
    	
    	this.surveyResponseRepo.saveAll( oldSurvey.getResponses() );
    	return this.surveyRepo.save( survey );
    }
    
    public SurveyResponse updateResponse( SurveyResponse surveyResponse ) { 
    	SurveyResponse oldResponse = this.surveyResponseRepo.getOne( surveyResponse.getId() );
    	oldResponse.setSelected( surveyResponse.getSelected() );
    	return this.surveyResponseRepo.save( oldResponse );
    }

	public Survey updateComment(Survey survey) {
		Survey oldSurvey = this.surveyRepo.getOne( survey.getId() );
		oldSurvey.setComment( survey.getComment() );
		return this.surveyRepo.save( oldSurvey );
	}
}
