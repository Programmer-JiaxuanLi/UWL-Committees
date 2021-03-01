package uwl.senate.coc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Duty {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String duty;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "committee_id", nullable = false)
	private Committee committee;

	private Duty( Builder b ) {
		this.id = b.id;
		this.duty = b.duty;
		this.committee = b.committee;
	}
	
	public Duty() {		
	}
	
	public Committee getCommitee() {
		return this.committee;
	}
	
	public Committee getCommittee() {
		return committee;
	}

	public void setCommittee( Committee c ) {
		this.committee = c;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public static class Builder {
		public Long id;
		public String duty;
		public Committee committee;
		
		public Builder id( Long id ) {
			this.id = id;
			return this;
		}
		
		public Builder duty( String duty ) {
			this.duty = duty;
			return this;
		}

		public Builder committee( Committee c ) {
			this.committee = c;
			return this;
		}
		
		public Duty build() {
			return new Duty(this);
		}
	}

}
