package ebsl.mfms.report.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ebsl.mfms.report.utils.DbUtils;
import ebsl.mfms.report.utils.MySqlDbUtils;
public class DbUtilsFactory {
	private final static Logger logger = LoggerFactory.getLogger(getClassName());

	private static DbUtils mySqlDbUtils;
	private static DbUtilsFactory dbUtilsFactory;
//	private static DbUtils db2DbUtils;
//	private static DbUtils msSqlDbUtils;
	
	private DbUtilsFactory(){
		
	}
	public static DbUtilsFactory getInstanceOfDbUtilsFactory() {
		if (dbUtilsFactory == null) {
			dbUtilsFactory = new DbUtilsFactory();
		}
		return dbUtilsFactory;
	}
	public DbUtils getInstanceOfMySqlDbUtils() throws Exception{
		try {
			if (mySqlDbUtils == null){
				mySqlDbUtils = new MySqlDbUtils();
			}
		} catch (Exception e) {
			logger.error(getClassName() + ".getInstanceOfMySqlDbUtils()", e);
			throw e;
		}
		return mySqlDbUtils;
	}
	public static String getClassName() {
		String className = DbUtilsFactory.class.getName();
		return className;
	}
//	public static DbUtils getInstanceOfDb2DbUtils() throws Exception{
//		if (db2DbUtils == null){
//			db2DbUtils = new Db2DbUtils();
//		}
//		return db2DbUtils;
//	}
//	
//	public static DbUtils getInstanceOfMsSqlDbUtils() throws Exception{
//		if (msSqlDbUtils == null){
//			msSqlDbUtils = new MsSqlDbUtils();
//		}
//		return msSqlDbUtils;
//	}
}
