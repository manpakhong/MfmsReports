package ebsl.mfms.report.models.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ebsl.mfms.report.models.eos.TblPatrolphotoEo;
import ebsl.mfms.report.models.eos.TblPatrolresultEo;
import ebsl.mfms.report.models.sos.TblPatrolphotoSo;
import ebsl.mfms.report.models.vos.ExportPatrolInspectionRoutineVo;
import ebsl.mfms.report.models.vos.PatrolResultVo;

public class TblPatrolresultMgrHelper extends HelperBase {
	private final Logger logger = LoggerFactory.getLogger(getClassName());
	public TblPatrolresultMgrHelper() throws Exception {
		super();
	}
	private String getClassName() {
		return this.getClass().getName();
	}

	public List<Integer> retrieveTblPatrolResultVoKeyList(List<PatrolResultVo> patrolResultVoList)
			throws Exception {
		List<Integer> patrolresultKeyList = null;
		try {
			if (patrolResultVoList != null && patrolResultVoList.size() > 0) {
				patrolresultKeyList = new ArrayList<Integer>();
				for (PatrolResultVo vo : patrolResultVoList) {
					Integer key = vo.getPrKey();
					patrolresultKeyList.add(key);
				}
			}
		} catch (Exception e) {
			logger.error(getClassName() + ".retrieveTblPatrolresultEoKeyList() - patrolResultVoList="
					+ patrolResultVoList, e);
			throw e;
		}
		return patrolresultKeyList;
	}
	public List<ExportPatrolInspectionRoutineVo> mergePatrolResultVoListWithPatrolPhotoEoList(List<PatrolResultVo> patrolResultVoList, List<TblPatrolphotoEo> tblPatrolphotoEoList) throws Exception{
		List<ExportPatrolInspectionRoutineVo> exportPatrolInspectionRoutineVoList = null;
		try {
			if (patrolResultVoList != null && patrolResultVoList.size() > 0 
					) {
				exportPatrolInspectionRoutineVoList = new ArrayList<ExportPatrolInspectionRoutineVo>();
				

				for (PatrolResultVo patrolResultVo: patrolResultVoList) {
					ExportPatrolInspectionRoutineVo vo = new ExportPatrolInspectionRoutineVo();
					vo.setLocationName(patrolResultVo.getLocationName());
					vo.setRouteName(patrolResultVo.getRouteName());
					Date prTimeAttended = patrolResultVo.getPrTimeAttended();
					vo.setPatrolDate(prTimeAttended);
					String timeAttendedString = dateUtils.convertDate2ParamDateString(prTimeAttended);
					vo.setPatrolDateString(timeAttendedString);
					
					if (tblPatrolphotoEoList != null && tblPatrolphotoEoList.size() > 0) {
						for (TblPatrolphotoEo tblPatrolphotoEo: tblPatrolphotoEoList) {
							Integer patrolResultKey = tblPatrolphotoEo.getPpPatrolResultKey();
							if (patrolResultKey.intValue() == patrolResultVo.getPrKey().intValue()) {
								vo.addPatrolPhotoEo(tblPatrolphotoEo);
							}
						}
					}
					exportPatrolInspectionRoutineVoList.add(vo);
				}
			}
		} catch (Exception e) {
			logger.error(getClassName() + ".mergePatrolresultKeyListWithPatrolPhotoEoList() - patrolResultVo="
					+ patrolResultVoList + ",tblPatrolphotoEoList=" + tblPatrolphotoEoList, e);
			throw e;
		}
		return exportPatrolInspectionRoutineVoList;
	}
}
