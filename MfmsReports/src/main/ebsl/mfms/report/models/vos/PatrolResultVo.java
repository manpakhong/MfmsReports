package ebsl.mfms.report.models.vos;

import ebsl.mfms.report.models.eos.TblPatrolresultEo;

public class PatrolResultVo extends TblPatrolresultEo{
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	protected String routeName;
	protected String locationName;
}
