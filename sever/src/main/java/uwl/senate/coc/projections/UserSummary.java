package uwl.senate.coc.projections;

import uwl.senate.coc.entities.College;
import uwl.senate.coc.entities.Gender;

public interface  UserSummary {
    public Long getId();
    public String getYear();
    public String getLast();
    public String getFirst();
    public College getCollege();
    public Boolean getTenured();
    public Boolean getSoe();
    public Boolean getAdminResponsibility();
    public String getEmail();
    public Gender getGender();
    public String getRank();
}
