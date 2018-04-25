package ebsl.mfms.report.services;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ebsl.mfms.report.bundles.ReportProperties;
import ebsl.mfms.report.factories.PropertiesFactory;
import ebsl.mfms.report.factories.UtilsFactory;
import ebsl.mfms.report.models.dtos.CompressFileDto;
import ebsl.mfms.report.models.eos.TblPatrolphotoEo;
import ebsl.mfms.report.models.vos.ExportPatrolInspectionRoutineVo;
import ebsl.mfms.report.utils.CommonUtils;
import ebsl.mfms.report.utils.DateUtils;
import ebsl.mfms.report.utils.FileUtils;

public class PatrolInspectionExcelMgr {
	private final Logger logger = LoggerFactory.getLogger(getClassName());
	private PropertiesFactory propertiesFactory;
	private ReportProperties reportProperties;
	private UtilsFactory utilsFactory;
	private CommonUtils commonUtils;
	private FileUtils fileUtils;
	private DateUtils dateUtils;
	private final String EXCEL_EXT = ".xlsx";
	private final String ZIP_EXT = ".zip";
	private Integer noOfRecordsPerFile;
	private String inspectionExcelSheetName;

	public PatrolInspectionExcelMgr() throws Exception {
		try {
			propertiesFactory = PropertiesFactory.getInstanceOfPropertiesFactory();
			reportProperties = propertiesFactory.getInstanceOfReportProperties();
			utilsFactory = UtilsFactory.getInstance();
			commonUtils = utilsFactory.getInstanceOfCommonUtils();
			fileUtils = utilsFactory.getInstanceOfFileUtils();
			dateUtils = utilsFactory.getInstanceOfDateUtils();
			noOfRecordsPerFile = reportProperties.getNoOfRecordsPerFile();
			inspectionExcelSheetName = reportProperties.getInspectionExcelSheetName();
		} catch (Exception e) {
			logger.error(getClassName() + ".PatrolInspectionExcelMgr()", e);
			throw e;
		}

	}

	private String getClassName() {
		return this.getClass().getName();
	}

	public void generateExcelAndSave(List<ExportPatrolInspectionRoutineVo> exportPatrolInspectionRoutineVo)
			throws Exception {
		try {
			generateExcels(exportPatrolInspectionRoutineVo, null);

		} catch (Exception e) {
			logger.error(getClassName() + ".generateExcel() - exportPatrolInspectionRoutineVo="
					+ exportPatrolInspectionRoutineVo, e);
			throw e;
		}
	}

	private void createHeaderRow(XSSFSheet sheet, Integer headerRowIndex) throws Exception {
		try {
			Row headerRow = sheet.createRow(headerRowIndex);
			Cell headerCell0 = headerRow.createCell(0);
			headerCell0.setCellValue("Date");
			Cell headerCell1 = headerRow.createCell(1);
			headerCell1.setCellValue("Route");
			Cell headerCell2 = headerRow.createCell(2);
			headerCell2.setCellValue("Location");
			Cell headerCell3 = headerRow.createCell(3);
			headerCell3.setCellValue("Remark");
			Cell headerCell4 = headerRow.createCell(4);
			headerCell4.setCellValue("Picture");
		} catch (Exception e) {
			logger.error(getClassName() + ".createHeaderRow() - headerRowIndex=" + headerRowIndex, e);
			throw e;
		}
	}

	private void generateExcel(List<ExportPatrolInspectionRoutineVo> exportPatrolInspectionRoutineVoList,
			Integer fromIndex, Integer fileIndex, CompressFileDto compressFileDto) throws Exception {
		String rootDir = null;
		String fileName = null;
		String fileNamePath = null;
		String fileNamePrefix = null;
		String fileNameSuffix = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		FileOutputStream outputStream = null;
		final int HEADER_ROW = 0;
		Integer toIndex = null;
		String imageRootPath = null;
		CreationHelper helper = null;
		Drawing drawing = null;
		try {
			toIndex = this.noOfRecordsPerFile + fromIndex;
			if (toIndex > exportPatrolInspectionRoutineVoList.size()) {
				toIndex = exportPatrolInspectionRoutineVoList.size();
			}

			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet(this.inspectionExcelSheetName + fileIndex);
			rootDir = reportProperties.getReportDirectory();
			fileNamePrefix = reportProperties.getInspectionExcelPrefix();
			fileNameSuffix = reportProperties.getInspectionExcelSuffix();
			fileName = fileNamePrefix + "_" + fileNameSuffix + commonUtils.genTimestampString() + "_" + fileIndex
					+ EXCEL_EXT;
			fileNamePath = rootDir + "/" + fileName;
			imageRootPath = reportProperties.getImageRoot();
			fileUtils.createDirectoryIfNotExisted(rootDir);
			// header row
			if (exportPatrolInspectionRoutineVoList != null) {
				// body data rows
				for (int r = fromIndex; r < toIndex; r++) {
					int thisFileIndexList = r % noOfRecordsPerFile;
					int excelRowIndex = thisFileIndexList + 1;

					if (thisFileIndexList == 0) {
						createHeaderRow(sheet, HEADER_ROW);
					}

					ExportPatrolInspectionRoutineVo vo = exportPatrolInspectionRoutineVoList.get(r);
					Row row = sheet.createRow(excelRowIndex);
					Cell cell0 = row.createCell(0);
					cell0.setCellValue(dateUtils.convertDateTimeToDisplayDateTimeString(vo.getPatrolDate()));
					Cell cell1 = row.createCell(1);
					cell1.setCellValue(vo.getRouteName());
					Cell cell2 = row.createCell(2);
					cell2.setCellValue(vo.getLocationName());

					
					helper =workbook.getCreationHelper();
					drawing = sheet.createDrawingPatriarch();
					
					List<TblPatrolphotoEo> patrolPhotoEoList = vo.getPatrolPhotoList();

					if (patrolPhotoEoList != null) {
						for (TblPatrolphotoEo patrolphotoEo : patrolPhotoEoList) {
							String remark = patrolphotoEo.getPpRemark();
							Cell cell3 = row.createCell(3);
							cell3.setCellValue(remark);

							
							String photoPath = patrolphotoEo.getPpPhotoPath();
							photoPath = imageRootPath + '/' + photoPath;
							if (fileUtils.isFileExisted(photoPath)) {
								byte[] byteArray = fileUtils.readFileToByteArray(photoPath);		
								
								int pictureIdx = workbook.addPicture(byteArray, Workbook.PICTURE_TYPE_JPEG);
//								helper =workbook.getCreationHelper();
//								drawing = sheet.createDrawingPatriarch();
								ClientAnchor anchor = helper.createClientAnchor();
								
								anchor.setCol1(4);
								anchor.setRow1(excelRowIndex);
								anchor.setCol2(4);
								anchor.setRow2(excelRowIndex);
								Picture pict = drawing.createPicture(anchor, pictureIdx);
//								Cell cell4 = row.createCell(4);
								pict.resize();
//								cell4.setCellValue(photoPath);	

							}

						}

					}
				}
			}
			if (compressFileDto == null) { // write to file Directly
				outputStream = new FileOutputStream(fileNamePath);
				workbook.write(outputStream);

			} else {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				workbook.write(byteArrayOutputStream);
				compressFileDto.setByteArrayOutputStream(byteArrayOutputStream);
				compressFileDto.setFileName(fileName);
			}

			// workbook.close();

		} catch (Exception e) {
			logger.error(getClassName() + ".generateExcel() - exportPatrolInspectionRoutineVoList="
					+ exportPatrolInspectionRoutineVoList, e);
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
				workbook = null;
			}
			if (outputStream != null) {
				outputStream.close();
				outputStream = null;
			}
			if (helper != null) {
				helper = null;
			}
			if (drawing != null) {
				drawing = null;
			}
		}
	}

	public void generateExcels(List<ExportPatrolInspectionRoutineVo> exportPatrolInspectionRoutineVoList,
			List<CompressFileDto> compressFileDtoList) throws Exception {
		try {

			if (exportPatrolInspectionRoutineVoList != null) {
				// body data rows
				int fileIndexCount = 1;
				for (int r = 0; r < exportPatrolInspectionRoutineVoList.size(); r++) {
					int voListIndex = r % noOfRecordsPerFile;

					if (voListIndex == 0) {
						if (compressFileDtoList != null) { // generate byteArrayOutputStreamList
							CompressFileDto compressFileDto = new CompressFileDto();
							generateExcel(exportPatrolInspectionRoutineVoList, r, fileIndexCount, compressFileDto);
							compressFileDtoList.add(compressFileDto);
						} else { // output to files
							generateExcel(exportPatrolInspectionRoutineVoList, r, fileIndexCount, null);
						}
						fileIndexCount++;
					}
				}
			}

			// workbook.close();

		} catch (Exception e) {
			logger.error(getClassName() + ".generateExcel() - exportPatrolInspectionRoutineVoList="
					+ exportPatrolInspectionRoutineVoList, e);
			throw e;
		}
	}

	public void compressByteArrayOutputStreamListAndSave(List<CompressFileDto> compressFileDtoList) throws Exception {
		ByteArrayOutputStream outByteArrayOutputStream = null;
		OutputStream outputStream = null;
		String rootDir = null;
		String fileName = null;
		String fileNamePath = null;
		String fileNamePrefix = null;
		String fileNameSuffix = null;
		try {
			rootDir = reportProperties.getReportDirectory();
			fileNamePrefix = reportProperties.getInspectionExcelPrefix();
			fileNameSuffix = reportProperties.getInspectionExcelSuffix();
			fileName = fileNamePrefix + "_" + fileNameSuffix + commonUtils.genTimestampString() + ZIP_EXT;
			fileNamePath = rootDir + "/" + fileName;
			fileUtils.createDirectoryIfNotExisted(rootDir);

			outByteArrayOutputStream = new ByteArrayOutputStream();
			commonUtils.compressBytes(outByteArrayOutputStream, compressFileDtoList);

			outputStream = new FileOutputStream(fileNamePath);
			outByteArrayOutputStream.writeTo(outputStream);

		} catch (Exception e) {
			logger.error(getClassName() + ".compressByteArrayOutputStreamList()");
			throw e;
		} finally {
			if (outputStream != null) {
				outputStream.close();
				outputStream = null;
			}
		}

	}

	public ByteArrayOutputStream compressByteArrayOutputStreamList(List<CompressFileDto> compressFileDtoList)
			throws Exception {
		ByteArrayOutputStream outByteArrayOutputStream = null;
		try {
			outByteArrayOutputStream = new ByteArrayOutputStream();
			commonUtils.compressBytes(outByteArrayOutputStream, compressFileDtoList);
		} catch (Exception e) {
			logger.error(getClassName() + ".compressByteArrayOutputStreamList()");
			throw e;
		}
		return outByteArrayOutputStream;
	}

}
