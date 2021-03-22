package commonLibraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Excel {	

	
	public static String get_String_CellValue(String fullPath, int sheet_no, int row_no, int column_no) {
		
		String StringCellValue = null;
		
		try {
			
			File ExcelFile = getExcelFile(fullPath);
			XSSFWorkbook workbook = getWorkbook(ExcelFile);
			XSSFSheet sheet = getSheet(workbook, sheet_no);		
			StringCellValue = sheet.getRow(row_no).getCell(column_no).getStringCellValue();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return StringCellValue;
		
	}

	public static Double get_Double_CellValue(String fullPath, int sheet_no, int row_no, int column_no) {
		
		Double DoubleCellValue = null;
				
		try {
			
			File ExcelFile = getExcelFile(fullPath);
			XSSFWorkbook workbook = getWorkbook(ExcelFile);
			XSSFSheet sheet = getSheet(workbook, sheet_no);		
			DoubleCellValue = sheet.getRow(row_no).getCell(column_no).getNumericCellValue();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return DoubleCellValue;	
		
	}
	
	public static Integer get_Integer_CellValue(String fullPath, int sheet_no, int row_no, int column_no) {
		
		Integer integerCellValue = null;
		try {
			Double doubleValue = get_Double_CellValue(fullPath, sheet_no, row_no, column_no);
			Long longValue = Math.round(doubleValue);
			integerCellValue = longValue.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return integerCellValue;
		
	}

	public static File getExcelFile(String fullPath) {
		
		File excelFile = null;
		String fileExtensionName = fullPath.substring(fullPath.indexOf("."));
		excelFile = new File(fullPath);
		
		if (!excelFile.exists()) {
			System.out.println("File does not exist: " + fullPath);
			return null;
		}
		
		if (!fileExtensionName.equals(".xlsx") && !fileExtensionName.equals(".xls")) {
			System.out.println("Unsupported file extension: " + fileExtensionName);
			return null;
		}
		
		return excelFile;
		
	}

	public static XSSFWorkbook getWorkbook(File file) {
		
		FileInputStream fis = null;
		XSSFWorkbook wb = null;		
		
		try {
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		return wb;
		
		
	}

	public static XSSFSheet getSheet(XSSFWorkbook wb, int sheet_no) {
		return wb.getSheetAt(sheet_no);
	}


}
