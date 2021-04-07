package uwl.senate.coc.entities;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.JoinColumn;


@Entity
public class Committee {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="committee_id")
	private Long id;

	@Column(name="introduction")		
    private String introduction;
	
	@Column(name="name")
    private String name;

	@Column(name="year")
    private String year;

    @ManyToMany
    @JoinTable(name = "committee_members",joinColumns = { @JoinColumn(name = "committee_id")},inverseJoinColumns = {@JoinColumn(name = "members_id")})
    private Set<User> members;

    @OneToMany(mappedBy = "committee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Criteria> criteria;

    @OneToMany(mappedBy = "committee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Duty> duties;

    @ManyToMany
    @JoinTable(name = "committee_volunteers",joinColumns = { @JoinColumn(name = "committee_id")},inverseJoinColumns = {@JoinColumn(name = "volunteers_id")})
    private Set<User> volunteers;
    
    @Transient
    @JsonInclude
    private Integer size;
    
    @Transient
    @JsonInclude
    private Integer numMembers;
    
    @Transient
    @JsonInclude
    private Integer numVolunteers;
    

    public Committee(){
    }
    
    public Integer getSize() {
    	Optional<Criteria> criteria = this.criteria.stream().filter( c -> c.getCriteria().startsWith("(size") ).findFirst();
    	
    	if( criteria.isPresent() ) {
    		String txt = criteria.get().getCriteria();
    		return Integer.valueOf( txt.substring(6, txt.length()-1));
    	} else {
    		return 0;
    	}
    }
    
    public void setSize(Integer size) {
    	// this should never be called.  It is only here
    	// to support potential problems with Springs IOC
    }
    
    public Integer getNumMembers() {
    	return this.members.size();
    }
    
    public void setNumMembers(Integer size) {
    	// this should never be called.  It is only here
    	// to support potential problems with Springs IOC
    }
    
    public Integer getNumVolunteers() {
    	return this.volunteers.size();
    }
    
    public void setNumVolunteers(Integer size) {
    	// this should never be called.  It is only here
    	// to support potential problems with Springs IOC
    }

    private Committee(Builder builder) {
        setId(builder.id);
        setIntroduction(builder.introduction);
        setName(builder.name);
        setYear(builder.year);
        setMembers(builder.members);
        setCriteria(builder.criteria);
        setDuties(builder.duties);
        setVolunteers(builder.volunteers);
    }

    public static Builder build() {
        return new Builder();
    }

    public static Builder Builder(Committee copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.introduction = copy.getIntroduction();
        builder.name = copy.getName();
        builder.year = copy.getYear();
        builder.members = copy.getMembers();
        builder.criteria = copy.getCriteria();
        builder.duties = copy.getDuties();
        builder.volunteers = copy.getVolunteers();
        return builder;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Committee))
            return false;
        if (obj == this)
            return true;
        return this.id == ((Committee) obj).id;
    }

    public int hashCode() {
        return id.intValue();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        if (year!=null) {
            this.year = year.toString().substring(0, 4);
        }
    }

    public Set<User> getVolunteers() {
        return volunteers;
    }

    public Set<User> getMembers() {
        return members;
    }

    public List<Criteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Criteria> criteria) {
        this.criteria = criteria;
    }

    public void setDuties(List<Duty> duties) {
        this.duties = duties;
    }

    public List<Duty> getDuties() {
        return duties;
    }

    public void setMembers(Set<User> users) {
        this.members = users;
    }

    public void setVolunteers(Set<User> volunteers) {
        this.volunteers = volunteers;
    }

    public void removeMember(User a){
        this.members.remove(a);
        a.getCommittees().remove(this);
    }

    public static final class Builder {
        private Long id;
        private String introduction;
        private String name;
        private String year;
        private Set<User> members;
        private List<Criteria> criteria;
        private List<Duty> duties;
        private Set<User> volunteers;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder introduction(String introduction) {
            this.introduction = introduction;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder year(String year) {
            this.year = year;
            return this;
        }

        public Builder members(Set<User> members) {
            this.members = members;
            return this;
        }

        public Builder criteria(List<Criteria> criteria) {
            this.criteria = criteria;
            return this;
        }

        public Builder duties(List<Duty> duties) {
            this.duties = duties;
            return this;
        }

        public Builder volunteers(Set<User> volunteers) {
            this.volunteers = volunteers;
            return this;
        }

        public Committee build() {
            return new Committee(this);
        }
    }
}

