package uwl.senate.coc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import uwl.senate.coc.utils.CriteriaPredicateFactory;

import javax.persistence.*;

@Entity
public class Criteria {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String criteria;
	
	@Transient
	@JsonInclude
	private Boolean isSatisfied;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "committee_id", nullable = false)
	private Committee committee;

	private Criteria( Builder b ) {
		this.id = b.id;
		this.criteria = b.criteria;
		this.committee = b.committee;
	}
	
	public Criteria() {		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Committee getCommittee() {
		return this.committee;
	}
	
	public void setCommittee( Committee committee ) {
		this.committee = committee;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public Boolean getIsSatisfied( ) {
		return CriteriaPredicateFactory.build( this ).test( this.committee );
	}

	public void setIsSatisfied( Boolean f ) {
		// this function only exists to
		// support Springs IOC and to prevent 
		// potential errors at the controller level.
		// Not sure if this is needed or not.
	}
	
	public static class Builder {
		private Long id;
		private String criteria;
		private Committee committee;
		
		public Builder id( Long id ) {
			this.id = id;
			return this;
		}
		
		public Builder criteria( String criteria ) {
			this.criteria = criteria;
			return this;
		}
		
		public Builder committee( Committee committee ) {
			this.committee = committee;
			return this;
		}

		public Criteria build( ) {
			return new Criteria( this );
		}
		
	}
}
