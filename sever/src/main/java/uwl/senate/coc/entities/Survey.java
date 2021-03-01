package uwl.senate.coc.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="survey")
public class Survey {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="survey_id")
	private Long id;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column( name="year" )
	private String year;
	
	@Column( name="comment" )
	private String comment;
	
	@Column( name="urlkey", length=2048 )
	private String urlkey;
	
	@Column( name="enabled" )
	private Boolean isEnabled;

	@OneToMany( fetch=FetchType.LAZY )
	private List<SurveyResponse> responses;
	
	public Survey() {		
	}
	
	private Survey( Builder b ) {
		this.id = b.id;
		this.userId = b.userId;
		this.year = b.year;
		this.comment = b.comment;
		this.responses = b.responses;
		this.urlkey = b.urlkey;
		this.isEnabled = b.isEnabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<SurveyResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<SurveyResponse> responses) {
		this.responses = responses;
	}

	public String getUrlkey() {
		return this.urlkey;
	}

	public void setUrlkey(String urlkey) {
		this.urlkey = urlkey;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public static class Builder {
		private Long id;
		private Long userId;
		private String year;
		private String comment;
		private String urlkey;
		private Boolean isEnabled;
		private List<SurveyResponse> responses;
		
		public Builder id( Long id ) {
			this.id = id;
			return this;
		}
		
		public Builder userId( Long userId ) {
			this.userId = userId;
			return this;
		}
		
		public Builder urlkey( String urlkey ) {
			this.urlkey = urlkey;
			return this;
		}
		
		public Builder year(String year ) {
			this.year = year;
			return this;
		}
		
		public Builder comment( String comment ) {
			this.comment = comment;
			return this;
		}
		
		public Builder isEnabled( Boolean isEnabled) {
			this.isEnabled = isEnabled;
			return this;
		}
		
		public Builder responses( List<SurveyResponse> responses ) {
			this.responses = responses;
			return this;
		}
		
		public Survey build() {
			return new Survey( this );
		}
	}
}
