package uwl.senate.coc.projections;

import java.util.List;

public interface CommitteeMembers {
   public Long getId();
   public List<UserSummary> getMembers();
}
