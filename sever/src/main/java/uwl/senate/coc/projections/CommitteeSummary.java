package uwl.senate.coc.projections;

import java.util.List;

import uwl.senate.coc.entities.Criteria;

public interface CommitteeSummary {
    public Long getId();
    public String getIntroduction();
    public String getName();
    public String getYear();
    public Integer getSize();
    
    public Integer getNumMembers();
    public Integer getNumVolunteers();
    
    public List<Criteria> getCriteria();
    public List<Duty> getDuties();
}