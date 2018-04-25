package ebsl.mfms.report.webservices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ebsl.mfms.report.bundles.ReportProperties;
import ebsl.mfms.report.factories.PropertiesFactory;
import ebsl.mfms.report.factories.UtilsFactory;
import ebsl.mfms.report.models.dtos.CompressFileDto;
import ebsl.mfms.report.models.sos.ExportPatrolInspectionRoutineSo;
import ebsl.mfms.report.models.sos.ExportPatrolRoutineSo;
import ebsl.mfms.report.models.vos.ExportPatrolInspectionRoutineVo;
import ebsl.mfms.report.models.vos.ExportPatrolRoutineVo;
import ebsl.mfms.report.services.PatrolExcelMgr;
import ebsl.mfms.report.services.PatrolInspectionExcelMgr;
import ebsl.mfms.report.services.TblPatrolresultMgr;
import ebsl.mfms.report.utils.CommonUtils;
import ebsl.mfms.report.utils.DateUtils;
import ebsl.mfms.report.utils.FileUtils;

//http://localhost:8080/MfmsReport/rest/generateInspectionReportWs/test
//http://localhost:8080/MfmsReport/rest/generateInspectionReportWs/requestInspectionJson
@Path("/generateInspectionReportWs")
public class GenerateInspectionReportWs {
	private final Logger logger = LoggerFactory.getLogger(getClassName());
	private PropertiesFactory propertiesFactory;
	private ReportProperties reportProperties;
	private UtilsFactory utilsFactory;
	private CommonUtils commonUtils;
	private FileUtils fileUtils;
	private DateUtils dateUtils;
	private final String EXCEL_EXT = ".xlsx";
	private final String ZIP_EXT = ".zip";
	
	public GenerateInspectionReportWs() throws Exception {
		try {
			propertiesFactory = PropertiesFactory.getInstanceOfPropertiesFactory();
			reportProperties = propertiesFactory.getInstanceOfReportProperties();
			utilsFactory = UtilsFactory.getInstance();
			commonUtils = utilsFactory.getInstanceOfCommonUtils();
			fileUtils = utilsFactory.getInstanceOfFileUtils();
			dateUtils = utilsFactory.getInstanceOfDateUtils();
		} catch (Exception e) {
			logger.error(getClassName() + ".GenerateInspectionReportWs() - Exception: ", e);
		}
	}	
	private String getClassName(){
		return this.getClass().getName();
	}
	
	@GET
	@Path("/test")
	@Produces("text/plain")
	public String test(){ 
		String returnString = "";
		try{
			returnString = "Test return from GenerateInspectionReportWs()";
		}catch (Exception e){
			logger.error(getClassName() + ".GenerateInspectionReportWs() - Exception: ", e);
		}
		return returnString;
	}
	
	@GET
	@Path("/requestInspectionJson")
	@Produces(MediaType.APPLICATION_JSON)
	public Response requestInspectionJson(
			@QueryParam("siteKey") String siteKeyString,
			@QueryParam("resultStartDate") String resultStartDateString,
			@QueryParam("resultEndDate") String resultEndDateString,
			@QueryParam("routeKeyList") String routeKeyListString,
			@QueryParam("routeLocationList") String routeLocationListString
			){ 
		String returnString = "";
		Response response = null;
		try{
			
			Integer siteKey = null;
			Date resultStartDate = null;
			Date resultEndDate = null;
			List<String> routeKeyList = null;
			List<String> routeLocationKeyList = null;
			

			
			
			if (siteKeyString != null && commonUtils.isInteger(siteKeyString)) {
				siteKey = Integer.parseInt(siteKeyString);
			}
			
			if (resultStartDateString != null) {
				resultStartDate = dateUtils.convertParamDateTimeString2Date(resultStartDateString);
			}
			if (resultEndDateString != null) {
				resultEndDate = dateUtils.convertParamDateTimeString2Date(resultEndDateString);
			}
			
			if (routeKeyListString != null) {
				routeKeyList = commonUtils.splitByDelimited(routeKeyListString, ",");
			}
			if (routeLocationListString != null) {
				routeLocationKeyList = commonUtils.splitByDelimited(routeLocationListString, ",");
			}
			
			
			ExportPatrolInspectionRoutineSo so = new ExportPatrolInspectionRoutineSo();
			if (siteKey != null) {
				so.setSiteKey(siteKey);
			}
			
			if (resultStartDate != null) {
				so.setResultStartDate(resultStartDate);
			}
			
			if (resultEndDate != null) {
				so.setResultEndDate(resultEndDate);
			}
			
			if (routeKeyList != null) {
				so.setRouteKeyList(routeKeyList);
			}
			if (routeLocationKeyList != null) {
				so.setRouteLocationKeyList(routeLocationKeyList);
			}
			
			
			TblPatrolresultMgr manager = new TblPatrolresultMgr();
			 List<ExportPatrolInspectionRoutineVo> voList =  manager.readByExportPatrolInspectionRoutineSo(so);
			Gson gson = new GsonBuilder().create();
			returnString = gson.toJson(voList);
			
			ResponseBuilder builder = Response.status(201).entity(returnString);
			response = builder.build();
			 
		}catch (Exception e){
			logger.error(getClassName() + ".requestInspectionJson() - Exception: ", e);
		}
		return response;
	}
	@GET
	@Path("/requestInspectionExcel")
//    @Produces("application/vnd.ms-excel")
    @Produces(MediaType.TEXT_PLAIN)
//	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response requestInspectionExcel(
			@QueryParam("siteKey") String siteKeyString,
			@QueryParam("resultStartDate") String resultStartDateString,
			@QueryParam("resultEndDate") String resultEndDateString,
			@QueryParam("routeKeyList") String routeKeyListString,
			@QueryParam("routeLocationList") String routeLocationListString
			){
		String rootDir = null;
		String fileName = null;
		String fileNamePrefix = null;
		String fileNameSuffix = null;
		Response response = null;
		StreamingOutput  stream = null;
		try{
			
			Integer siteKey = null;
			Date resultStartDate = null;
			Date resultEndDate = null;
			List<String> routeKeyList = null;
			List<String> routeLocationKeyList = null;
			

			
			
			if (siteKeyString != null && commonUtils.isInteger(siteKeyString)) {
				siteKey = Integer.parseInt(siteKeyString);
			}
			
			if (resultStartDateString != null) {
				resultStartDate = dateUtils.convertParamDateTimeString2Date(resultStartDateString);
			}
			if (resultEndDateString != null) {
				resultEndDate = dateUtils.convertParamDateTimeString2Date(resultEndDateString);
			}
			
			if (routeKeyListString != null) {
				routeKeyList = commonUtils.splitByDelimited(routeKeyListString, ",");
			}
			if (routeLocationListString != null) {
				routeLocationKeyList = commonUtils.splitByDelimited(routeLocationListString, ",");
			}
			
			
			ExportPatrolInspectionRoutineSo so = new ExportPatrolInspectionRoutineSo();
			if (siteKey != null) {
				so.setSiteKey(siteKey);
			}
			
			if (resultStartDate != null) {
				so.setResultStartDate(resultStartDate);
			}
			
			if (resultEndDate != null) {
				so.setResultEndDate(resultEndDate);
			}
			
			if (routeKeyList != null) {
				so.setRouteKeyList(routeKeyList);
			}
			if (routeLocationKeyList != null) {
				so.setRouteLocationKeyList(routeLocationKeyList);
			}
			
			
			TblPatrolresultMgr manager = new TblPatrolresultMgr();
			List<ExportPatrolInspectionRoutineVo> voList =  manager.readByExportPatrolInspectionRoutineSo(so);
			PatrolInspectionExcelMgr mgr = new PatrolInspectionExcelMgr();

//			List<ByteArrayOutputStream> byteArrayOutputStreamList = new ArrayList<ByteArrayOutputStream>();
//			mgr.generateExcelAndSave(voList);	
		
			List<CompressFileDto> compressFileDtoList = new ArrayList<CompressFileDto>();
			
			mgr.generateExcels(voList, compressFileDtoList);
			Boolean saveExcelsToServer = reportProperties.getSaveExcelsToServer();
			if (saveExcelsToServer) {
				mgr.generateExcelAndSave(voList);
			}
			
			final ByteArrayOutputStream byteArrayOutputStream = mgr.compressByteArrayOutputStreamList(compressFileDtoList);
			
			Boolean saveCompressedFileToServer = reportProperties.getSaveCompressedFilesToServer();
			if (saveCompressedFileToServer && compressFileDtoList != null && compressFileDtoList.size() > 0) {
				mgr.compressByteArrayOutputStreamListAndSave(compressFileDtoList);
			}
			
//			mgr.generateExcel(voList, oStream);			
			

	        stream = new StreamingOutput() {
	            @Override
	            public void write(OutputStream os) throws IOException, WebApplicationException {
	                os.write(byteArrayOutputStream.toByteArray());
	                os.flush();
	            }
	        };
			
	        
			propertiesFactory = PropertiesFactory.getInstanceOfPropertiesFactory();
			reportProperties = propertiesFactory.getInstanceOfReportProperties();
			utilsFactory = UtilsFactory.getInstance();
			commonUtils = utilsFactory.getInstanceOfCommonUtils();
			fileUtils = utilsFactory.getInstanceOfFileUtils();
			dateUtils = utilsFactory.getInstanceOfDateUtils();
	        
			rootDir = reportProperties.getReportDirectory();
			fileNamePrefix = reportProperties.getInspectionExcelPrefix();
			fileNameSuffix = reportProperties.getInspectionExcelSuffix();
			fileName = fileNamePrefix + "_" + fileNameSuffix +  commonUtils.genTimestampString() + ZIP_EXT;

	        
	
			ResponseBuilder builder = 
					Response.ok(stream, MediaType.APPLICATION_OCTET_STREAM)
				    .header("content-disposition",
				      "attachment; filename = \"" + fileName + "\"");

			response = builder.build();

		}catch (Exception e){
			logger.error(getClassName() + ".requestInspectionExcel() - siteKey=" + siteKeyString + 
					",resultStartDate=" + resultStartDateString + ",resultEndDate=" + resultEndDateString + 
					",routeKeyList=" + routeKeyListString + ",routeLocationList=" + routeLocationListString, e);
		}
		return response;
	}
}
