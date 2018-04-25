package ebsl.mfms.report.services;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ebsl.mfms.report.daos.TblPatrolresultDao;
import ebsl.mfms.report.models.eos.TblPatrolphotoEo;
import ebsl.mfms.report.models.eos.TblPatrolresultEo;
import ebsl.mfms.report.models.helpers.TblPatrolresultMgrHelper;
import ebsl.mfms.report.models.sos.ExportPatrolInspectionRoutineSo;
import ebsl.mfms.report.models.sos.TblPatrolphotoSo;
import ebsl.mfms.report.models.sos.TblPatrolresultSo;
import ebsl.mfms.report.models.vos.ExportPatrolInspectionRoutineVo;
import ebsl.mfms.report.models.vos.ExportPatrolRoutineVo;
import ebsl.mfms.report.models.vos.PatrolResultVo;
public class TblPatrolresultMgr extends ServiceBase{
	private final Logger logger = LoggerFactory.getLogger(getClassName());
	private TblPatrolresultDao dao;
	private TblPatrolphotoMgr patrolphotoMgr;
	private TblPatrolresultMgrHelper helper;
	private String getClassName(){
		return this.getClass().getName();
	}
	public TblPatrolresultMgr() throws Exception{
		try{
			init(null);
		} catch (Exception e){
			logger.error(getClassName() + ".TblPatrolresultMgr() - connectionType=", e);
			throw e;
		}
	} // end constructor
	public TblPatrolresultMgr(String connectionType) throws Exception{
		try{
			init(connectionType);
		} catch (Exception e){
			logger.error(getClassName() + ".TblPatrolresultMgr() - connectionType=" + connectionType, e);
			throw e;
		}
	} // end constructor
	public void init(String connectionType) throws Exception{
		try{
			dao = new TblPatrolresultDao(connectionType);
			helper = new TblPatrolresultMgrHelper();
			patrolphotoMgr = new TblPatrolphotoMgr(connectionType);
		} catch (Exception e){
			logger.error(getClassName() + "init() - connectionType=" + connectionType, e);
			throw e;
		}
	} // end constructor
	public List<ExportPatrolInspectionRoutineVo> readByExportPatrolInspectionRoutineSo(Object so) throws Exception{
		List<ExportPatrolInspectionRoutineVo> exportPatrolInspectionRoutineVoList = null;
		List<PatrolResultVo> patrolResultVoList = null;
		try{

			patrolResultVoList = dao.readPatrolResultVoList(so);
			List<Integer> patrolresultKeyList = helper.retrieveTblPatrolResultVoKeyList(patrolResultVoList);
			TblPatrolphotoSo patrolPhotoSo = new TblPatrolphotoSo();
			patrolPhotoSo.setPatrolResultKeyList(patrolresultKeyList);
			List<TblPatrolphotoEo> tblPatrolphotoEoList = patrolphotoMgr.read(patrolPhotoSo);
			exportPatrolInspectionRoutineVoList = helper.mergePatrolResultVoListWithPatrolPhotoEoList(patrolResultVoList, tblPatrolphotoEoList);
		}
		catch (Exception e){
			logger.error(getClassName() + ".readByExportPatrolInspectionRoutineSo() - so=" + so, e);
			throw e;
		} // end try ... catch
		return exportPatrolInspectionRoutineVoList;
	}
	public List<ExportPatrolRoutineVo> readByExportPatrolRoutineSo(Object so) throws Exception{
		List<ExportPatrolRoutineVo> exportPatrolRoutineVoList = null;
		try{
			exportPatrolRoutineVoList = dao.readByExportPatrolRoutineSo(so);
		}
		catch (Exception e){
			logger.error(getClassName() + ".readByExportPatrolRoutineSo() - so=" + so, e);
			throw e;
		} // end try ... catch
		return exportPatrolRoutineVoList;
	}
	public List<TblPatrolresultEo> read(Object so) throws Exception{
		List<TblPatrolresultEo> tblPatrolresultEoList = null;
		try{
			tblPatrolresultEoList = dao.read(so);
		}
		catch (Exception e){
			logger.error(getClassName() + ".read() - so=" + so, e);
			throw e;
		} // end try ... catch
		return tblPatrolresultEoList;
	} // end select function
	public Integer create(TblPatrolresultEo eo) throws Exception{
		Integer noOfAffectedRow = null;
		try{
			noOfAffectedRow = dao.create(eo);
		}
		catch (Exception e){
			logger.error(getClassName() + ".create() - eo=" + eo, e);
			throw e;
		} // end try ... catch
		return noOfAffectedRow;
	} // end create function
	public Integer update(TblPatrolresultEo eo) throws Exception{
		Integer noOfAffectedRow = null;
		try{
			noOfAffectedRow = dao.update(eo);
		}
		catch (Exception e){
			logger.error(getClassName() + ".update() - eo=" + eo, e);
			throw e;
		} // end try ... catch
		return noOfAffectedRow;
	} // end create function
	public Integer delete(TblPatrolresultEo eo) throws Exception{
		Integer noOfAffectedRow = null;
		try{
			noOfAffectedRow = dao.delete(eo);
		}
		catch (Exception e){
			logger.error(getClassName() + ".delete() - eo=" + eo, e);
			throw e;
		} // end try ... catch
		return noOfAffectedRow;
	} // end create function
} //end class
