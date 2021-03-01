package uwl.senate.coc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="surveyresponse")
public class SurveyResponse {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="response_id")
	private Long id;
	
	@Column(name="selected")
	private Boolean selected;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinTable( 
			name="survey_surveyresponse", 
			joinColumns={ @JoinColumn(name = "response_id") },
			inverseJoinColumns={ @JoinColumn(name = "survey_id") }
	)
	private Survey survey;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinTable( 
			name="committee_surveyresponse", 
			joinColumns={ @JoinColumn(name = "response_id") },
			inverseJoinColumns={ @JoinColumn(name = "committee_id") }
	)
	private Committee committee;
	
	public SurveyResponse() {		
	}
	
	private SurveyResponse( Builder b ) {
		this.committee = b.committee;
		this.selected = b.selected;
		this.survey = b.survey;
	}

	public Long getId() {
		return this.id;
	}
	
	public void setId( Long id ) {
		this.id = id;		
	}
	
	public Committee getCommittee() {
		return committee;
	}
	
	public void setCommittee(Committee committee) {
		this.committee = committee;
	}
	
	public Boolean getSelected() {
		return selected;
	}
	
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
	public Survey getSurvey( ) {
		return this.survey;
	}
	
	public void setSurvey( Survey survey ) {
		this.survey = survey;
	}
		
	public static class Builder {
		private Committee committee;
		private Boolean selected;
		private Survey survey;
		
		public Builder committee( Committee committee ) {
			this.committee = committee;
			return this;
		}
		
		public Builder selected( Boolean selected ) {
			this.selected = selected;
			return this;
		}
		
		public Builder survey( Survey survey ) {
			this.survey = survey;
			return this;
		}
		
		public SurveyResponse build() {
			return new SurveyResponse(this);
		}
	}
}
