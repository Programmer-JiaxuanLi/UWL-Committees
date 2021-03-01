package uwl.senate.coc.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String role;

	public Role() {
	}
	
	private Role( Builder b ) {
		this.id = b.id;
		this.role = b.role;
	}
	
	public void setRole( String role ) {
		this.role = role;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public static class Builder {
		public String role;
		public Long id;
		
		public Builder id( Long id ) {
			this.id = id;
			return this;
		}
		
		public Builder role( String role ) {
			this.role = role;
			return this;
		}
		
		public Role build() {
			return new Role( this );
		}		
	}
}
