package ebsl.mfms.report.models.vos;

import java.util.Date;
import java.util.List; 
import ebsl.mfms.report.models.eos.TblPatrolphotoEo;

public class ExportPatrolInspectionRoutineVo extends VoBase{
	
	private Date patrolDate;
	private String routeName;
	private String locationName;
	private List<TblPatrolphotoEo> patrolPhotoList;
	
	
	public Date getPatrolDate() {
		return patrolDate;
	}
	public void setPatrolDate(Date patrolDate) {
		this.patrolDate= patrolDate;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName= routeName;
	}

	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public List<TblPatrolphotoEo> getPatrolPhotoLists() {
		return patrolPhotoList;
	}
	public void setPatrolPhotoLists(List<TblPatrolphotoEo> patrolPhotoList) {
		this.patrolPhotoList = patrolPhotoList;
	}
	
	
	
}
