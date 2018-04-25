package ebsl.mfms.report.models.vos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ebsl.mfms.report.models.eos.TblPatrolphotoEo;

public class ExportPatrolInspectionRoutineVo extends VoBase{
	
	private Date patrolDate;
	private String patrolDateString;
	private String routeName;
	private String locationName;
	private List<TblPatrolphotoEo> patrolPhotoList;
	
	public ExportPatrolInspectionRoutineVo() {
		
	}
	
	public String getPatrolDateString() {
		return patrolDateString;
	}

	public void setPatrolDateString(String patrolDateString) {
		this.patrolDateString = patrolDateString;
	}

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
	
	public List<TblPatrolphotoEo> getPatrolPhotoList() {
		return patrolPhotoList;
	}
	public void setPatrolPhotoList(List<TblPatrolphotoEo> patrolPhotoList) {
		this.patrolPhotoList = patrolPhotoList;
	}
	
	public void addPatrolPhotoEo(TblPatrolphotoEo tblPatrolphotoEo) {
		if (patrolPhotoList == null) {
			patrolPhotoList = new ArrayList<TblPatrolphotoEo>();
		}
		if (tblPatrolphotoEo != null) {
			patrolPhotoList.add(tblPatrolphotoEo);
		}
	}
	
}
