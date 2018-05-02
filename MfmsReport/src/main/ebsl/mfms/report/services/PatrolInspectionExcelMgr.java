package ebsl.mfms.report.services;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ebsl.mfms.report.bundles.ReportProperties;
import ebsl.mfms.report.factories.PropertiesFactory;
import ebsl.mfms.report.factories.UtilsFactory;
import ebsl.mfms.report.models.vos.ExportPatrolInspectionRoutineVo;
import ebsl.mfms.report.utils.CommonUtils;
import ebsl.mfms.report.utils.DateUtils;
import ebsl.mfms.report.utils.FileUtils;

import ebsl.mfms.report.models.eos.TblPatrolphotoEo;

public class PatrolInspectionExcelMgr {
	private final Logger logger = LoggerFactory.getLogger(getClassName());
	private PropertiesFactory propertiesFactory;
	private ReportProperties reportProperties;
	private UtilsFactory utilsFactory;
	private CommonUtils commonUtils;
	private FileUtils fileUtils;
	private DateUtils dateUtils;
	private final String EXCEL_EXT = ".xlsx";
	public PatrolInspectionExcelMgr() throws Exception{
		try {
			propertiesFactory = PropertiesFactory.getInstanceOfPropertiesFactory();
			reportProperties = propertiesFactory.getInstanceOfReportProperties();
			utilsFactory = UtilsFactory.getInstance();
			commonUtils = utilsFactory.getInstanceOfCommonUtils();
			fileUtils = utilsFactory.getInstanceOfFileUtils();
			dateUtils = utilsFactory.getInstanceOfDateUtils();
		} catch (Exception e) {
			logger.error(getClassName() + ".PatrolExcelMgr()" , e);
			throw e;
		}

	}
	private String getClassName(){
		return this.getClass().getName();
	}
	
	public void generateExcelAndSave(List<ExportPatrolInspectionRoutineVo> exportPatrolInspectionRoutineVoList) throws Exception {
		try{
			generateExcel(exportPatrolInspectionRoutineVoList, null);
			
		} catch (Exception e){
			logger.error(getClassName() + ".generateExcel() - exportPatrolInspectionRoutineVoList=" + exportPatrolInspectionRoutineVoList, e);
			throw e;
		}
	}
	public void generateExcel(List<ExportPatrolInspectionRoutineVo> exportPatrolInspectionRoutineVoList, ByteArrayOutputStream  out) throws Exception {
		String rootDir = null;
		String fileName = null;
		String fileNamePrefix = null;
		String fileNameSuffix = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		FileOutputStream outputStream = null;
		final int HEADER_ROW = 0;
		try{
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("PatrolInspectionRoutine");
			rootDir = reportProperties.getReportDirectory();
			fileNamePrefix = reportProperties.getInspectionExcelPrefix();
			fileNameSuffix = reportProperties.getInspectionExcelSuffix();
			fileName =rootDir + "/" + fileNamePrefix + "_" + fileNameSuffix +  commonUtils.genTimestampString() + EXCEL_EXT;
			fileUtils.createDirectoryIfNotExisted(rootDir);
			// header row
			
			Row headerRow = sheet.createRow(HEADER_ROW);
			Cell headerCell0 = headerRow.createCell(0); 
			headerCell0.setCellValue("Date");
			Cell headerCell1 = headerRow.createCell(1);
			headerCell1.setCellValue("Route");
			Cell headerCell2 = headerRow.createCell(2);
			headerCell2.setCellValue("Location");
			Cell headerCell3 = headerRow.createCell(3);
			headerCell3.setCellValue("Remark");
			Cell headerCell4 = headerRow.createCell(4);
			headerCell4.setCellValue("Photos");	
		
			if (exportPatrolInspectionRoutineVoList != null ) {
				// body data rows
				for (int r =1; r < exportPatrolInspectionRoutineVoList.size(); r++) {
					ExportPatrolInspectionRoutineVo vo = exportPatrolInspectionRoutineVoList.get(r);
					Row row = sheet.createRow(r);
					Cell cell0 = row.createCell(0); 
					cell0.setCellValue(dateUtils.convertDateTimeToDisplayDateTimeString(vo.getPatrolDate()));
					Cell cell1 = row.createCell(1);
					cell1.setCellValue(vo.getRouteName());
					Cell cell2 = row.createCell(2);
					cell2.setCellValue(vo.getLocationName());
					
					List<TblPatrolphotoEo> tblPatrolPhotoEoList = vo.getPatrolPhotoLists();
					// Get the patrol photo list - looping the list to list out (results , photo paths)
					String photoRemarks = "";
					List<String> photoPaths = null;
					if(tblPatrolPhotoEoList != null){
						for(int patrolPhotoIndex = 0; patrolPhotoIndex < tblPatrolPhotoEoList.size(); patrolPhotoIndex++){
							TblPatrolphotoEo patrolPhotoEo = (TblPatrolphotoEo) tblPatrolPhotoEoList.get(patrolPhotoIndex);
							photoRemarks = photoRemarks + patrolPhotoEo.getPpRemark() + "\n\r";
							photoPaths.add(patrolPhotoEo.getPpPhotoPath());
						}
					}
					Cell cell3 = row.createCell(3);
					cell3.setCellValue(photoRemarks);
					
					InputStream inputStream = null;
					int cellIndex = 4;
					for(int photoPathIndex = 0; photoPathIndex < photoPaths.size(); photoPathIndex++){
						String filePath = photoPaths.get(photoPathIndex);
						File file = new File(filePath);
						if(file.exists()){
							inputStream = new FileInputStream(filePath);
							byte[] bytes = IOUtils.toByteArray(inputStream); 
							int pictureIdx = workbook.addPicture(bytes, workbook.PICTURE_TYPE_JPEG);
							inputStream.close();
							
							CreationHelper helper = workbook.getCreationHelper();
							Drawing drawing = sheet.createDrawingPatriarch();
							ClientAnchor anchor = helper.createClientAnchor();
							anchor.setCol1(cellIndex);
							anchor.setRow1(row.getRowNum());
							Picture pict = drawing.createPicture(anchor,  pictureIdx);
							pict.resize();
						}else{
							row.createCell(cellIndex).setCellValue(filePath);
						}
						cellIndex++;
					}
				}
			}
			if (out == null) { // write to file Directly
				outputStream = new FileOutputStream(fileName);
				workbook.write(outputStream);
			} else {
				workbook.write(out);
			}
	
//			workbook.close();
			
		} catch (Exception e){
			logger.error(getClassName() + ".generateExcel() - exportPatrolInspectionRoutineVoList=" + exportPatrolInspectionRoutineVoList, e);
			throw e;
		} finally {
			if (workbook != null) {
				workbook = null;
			}
			if (outputStream != null) {
				outputStream.close();
				outputStream = null;
			}
		}
	}

}
