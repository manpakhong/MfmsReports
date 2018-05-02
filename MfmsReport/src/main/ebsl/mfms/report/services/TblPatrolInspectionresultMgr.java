package ebsl.mfms.report.services;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ebsl.mfms.report.daos.TblPatrolInspectionresultDao;
import ebsl.mfms.report.models.eos.TblPatrolInspectionresultEo;
import ebsl.mfms.report.models.vos.ExportPatrolInspectionRoutineVo;
public class TblPatrolInspectionresultMgr extends ServiceBase{
	private final Logger logger = LoggerFactory.getLogger(getClassName());
	private TblPatrolInspectionresultDao dao;
	private String getClassName(){
		return this.getClass().getName();
	}
	public TblPatrolInspectionresultMgr() throws Exception{
		try{
			init(null);
		} catch (Exception e){
			logger.error(getClassName() + ".TblPatrolInspectionresultMgr() - connectionType=", e);
			throw e;
		}
	} // end constructor
	public TblPatrolInspectionresultMgr(String connectionType) throws Exception{
		try{
			init(connectionType);
		} catch (Exception e){
			logger.error(getClassName() + ".TblPatrolInspectionresultMgr() - connectionType=" + connectionType, e);
			throw e;
		}
	} // end constructor
	public void init(String connectionType) throws Exception{
		try{
			dao = new TblPatrolInspectionresultDao(connectionType);
		} catch (Exception e){
			logger.error(getClassName() + "init() - connectionType=" + connectionType, e);
			throw e;
		}
	} // end constructor
	public List<ExportPatrolInspectionRoutineVo> readByExportPatrolInspectionRoutineSo(Object so) throws Exception{
		List<ExportPatrolInspectionRoutineVo> exportPatrolInspectionRoutineVo = null;
		try{
			exportPatrolInspectionRoutineVo = dao.readByExportPatrolInspectionRoutineSo(so);
		}
		catch (Exception e){
			logger.error(getClassName() + ".readByExportPatrolInspectionRoutineSo() - so=" + so, e);
			throw e;
		} // end try ... catch
		return exportPatrolInspectionRoutineVo;
	}
	public List<TblPatrolInspectionresultEo> read(Object so) throws Exception{
		List<TblPatrolInspectionresultEo> tblPatrolInspectionresultEoList = null;
		try{
			tblPatrolInspectionresultEoList = dao.read(so);
		}
		catch (Exception e){
			logger.error(getClassName() + ".read() - so=" + so, e);
			throw e;
		} // end try ... catch
		return tblPatrolInspectionresultEoList;
	} // end select function
	public Integer create(TblPatrolInspectionresultEo eo) throws Exception{
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
	public Integer update(TblPatrolInspectionresultEo eo) throws Exception{
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
	public Integer delete(TblPatrolInspectionresultEo eo) throws Exception{
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
