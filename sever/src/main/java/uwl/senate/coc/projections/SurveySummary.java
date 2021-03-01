package uwl.senate.coc.projections;

import java.util.List;

import uwl.senate.coc.utils.EncryptionUtils;

public interface SurveySummary {
	public Long getId();
	public String getComment();
	public Boolean getIsEnabled();
	public String getUrlkey();
	public default String getDecodedKey() {
		return EncryptionUtils.decrypt( this.getUrlkey() );
	}
	public List<SurveyResponseSummary> getResponses();
}