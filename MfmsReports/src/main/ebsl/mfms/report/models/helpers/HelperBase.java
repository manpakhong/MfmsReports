package ebsl.mfms.report.models.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ebsl.mfms.report.factories.UtilsFactory;
import ebsl.mfms.report.utils.DateUtils;

public abstract class HelperBase {
	private final Logger logger = LoggerFactory.getLogger(getClassName());
	protected DateUtils dateUtils;
	protected UtilsFactory generalUtilsFactory;
	private String getClassName() {
		String className = this.getClass().getName();
		return className;
	}
	public HelperBase() throws Exception {
		try {
			init();
		} catch (Exception e) {
			logger.error(getClassName() + ".HelperBase() - ", e);
			throw e;
		}
	}


	public void init() throws Exception {
		try {
			generalUtilsFactory = UtilsFactory.getInstance();
			dateUtils = generalUtilsFactory.getInstanceOfDateUtils();

		} catch (Exception e) {
			logger.error(getClassName() + ".init() ", e);
			throw e;
		}
	}	
}
