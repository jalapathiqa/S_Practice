package Excel_Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Read_Excel {

	public String filelocation;
	public FileInputStream ipstr = null;
	public FileOutputStream opstr = null;
	public XSSFWorkbook wb = null;
	public XSSFSheet ws = null;

	public Read_Excel(String filelocation) {
		this.filelocation = filelocation;
		try {
			ipstr = new FileInputStream(filelocation);
			wb = new XSSFWorkbook(ipstr);
			ws = wb.getSheetAt(0);
			ipstr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// To retrieve No Of Rows from .xlsx file's sheets.
	public int retrieveNoOfRows(String wsName) {
		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return 0;
		else {
			ws = wb.getSheetAt(sheetIndex);
			int rowCount = ws.getLastRowNum() + 1;
			return rowCount;
		}
	}

	// To retrieve No Of Columns from .xlsx file's sheets.
	public int retrieveNoOfCols(String wsName) {
		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return 0;
		else {
			ws = wb.getSheetAt(sheetIndex);
			int colCount = ws.getRow(0).getLastCellNum();
			return colCount;
		}
	}

	// To retrieve No Of Columns from .xlsx file's sheets.
	public int retrieveNoOfCols(String wsName, int rowNumb) {
		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return 0;
		else {
			ws = wb.getSheetAt(sheetIndex);
			int colCount = ws.getRow(rowNumb).getLastCellNum();
			return colCount;
		}
	}

	// To retrieve test data from test case data sheets.
	public Object[][] retrieveTestData(String wsName, String testCaseName) {

		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return null;
		else {
			ArrayList<Integer> retrieveTestCaseData = retrieveTestCaseRows(wsName, testCaseName);
			int rowNum = retrieveTestCaseData.size();
			// int colNum = retrieveNoOfCols(wsName);
			int colNum = retrieveNoOfCols(wsName, retrieveTestCaseData.get(0) - 1);

			Object data[][] = new Object[rowNum][colNum - 2];

			for (int i = 0; i < rowNum; i++) {

				XSSFRow row = ws.getRow(retrieveTestCaseData.get(i));
				for (int j = 1; j < colNum - 1; j++) {
					if (row == null) {
						data[i][j] = "";
					} else {
						XSSFCell cell = row.getCell(j);

						if (cell == null) {
							data[i][j] = "";
						} else {

							DataFormatter formatter = new DataFormatter();
							String value = formatter.formatCellValue(cell);
							// System.out.println(data[i][j - 1] = value);
							data[i][j - 1] = value;
						}
					}
				}
			}
			return data;
		}

	}

	// To retrieve DataToRun flag of test data.
	public ArrayList<Integer> retrieveTestCaseRows(String wsName, String testCaseName) {
		String testCaseColName = "Testcase";
		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return null;
		else {
			int rowNum = retrieveNoOfRows(wsName);
			int colNum = retrieveNoOfCols(wsName);
			int colNumber = -1;

			XSSFRow Suiterow = ws.getRow(0);
			String data[] = new String[rowNum - 1];
			ArrayList<Integer> testCaseRowList = new ArrayList<>();
			for (int i = 0; i < colNum; i++) {
				if (Suiterow.getCell(i).getStringCellValue().equals(testCaseColName.trim())) {
					colNumber = i;
				}
			}

			if (colNumber == -1) {
				return null;
			}

			for (int j = 0; j < rowNum - 1; j++) {
				XSSFRow Row = ws.getRow(j + 1);
				if (Row == null) {
					data[j] = "";
				} else {
					XSSFCell cell = Row.getCell(colNumber);
					if (cell == null) {
						data[j] = "";
					} else {
						DataFormatter formatter = new DataFormatter();
						String value = formatter.formatCellValue(cell);

						if (value.equalsIgnoreCase(testCaseName)) {
							testCaseRowList.add(j + 1);
						}

					}
				}
			}
			// System.out.println("rowList:" + testCaseRowList.size());
			// System.out.println("rowList:" + testCaseRowList);
			return testCaseRowList;
		}
	}

	// To retrieve DataToRun flag of test data.
	public String[] retrieveToRunFlagTestData(String wsName, String testCaseName, String colName) {

		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return null;
		else {

			ArrayList<Integer> retrieveTestCaseData = retrieveTestCaseRows(wsName, testCaseName);
			int rowNum = retrieveTestCaseData.size();
			int colNum = retrieveNoOfCols(wsName, retrieveTestCaseData.get(0));
			int colNumber = -1;
			int row = retrieveTestCaseData.get(0);

			XSSFRow Suiterow = ws.getRow(row - 1);
			String data[] = new String[rowNum];
			for (int i = 0; i < colNum; i++) {
				if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
					colNumber = i;
				}
			}

			if (colNumber == -1) {
				return null;
			}

			for (int j = 0; j < rowNum; j++) {
				XSSFRow Row = ws.getRow(retrieveTestCaseData.get(j));
				if (Row == null) {
					data[j] = "";
				} else {
					XSSFCell cell = Row.getCell(colNumber);
					if (cell == null) {
						data[j] = "";
					} else {
						DataFormatter formatter = new DataFormatter();
						String value = formatter.formatCellValue(cell);
						System.out.println(data[j] = value);
					}
				}
			}

			return data;
		}
	}

	// To retrieve SuiteToRun and CaseToRun flag of test suite and test case.
	public String retrieveToRunFlag(String wsName, String colName, String rowName) {

		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return null;
		else {
			int rowNum = retrieveNoOfRows(wsName);
			int colNum = retrieveNoOfCols(wsName);
			int colNumber = -1;
			int rowNumber = -1;

			XSSFRow Suiterow = ws.getRow(0);

			for (int i = 0; i < colNum; i++) {
				if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
					colNumber = i;
				}
			}

			if (colNumber == -1) {
				return "";
			}

			for (int j = 0; j < rowNum; j++) {
				XSSFRow Suitecol = ws.getRow(j);
				if (Suitecol.getCell(0).getStringCellValue().equals(rowName.trim())) {
					rowNumber = j;
				}
			}

			if (rowNumber == -1) {
				return "";
			}

			XSSFRow row = ws.getRow(rowNumber);
			XSSFCell cell = row.getCell(colNumber);
			if (cell == null) {
				return "";
			}
			DataFormatter formatter = new DataFormatter();
			String value = formatter.formatCellValue(cell);
			return value;
		}
	}

	// To write result In test data and test case list sheet.
	public boolean writeResult(String wsName, String colName, int rowNumber, String Result) {
		try {
			int sheetIndex = wb.getSheetIndex(wsName);
			if (sheetIndex == -1)
				return false;
			int colNum = retrieveNoOfCols(wsName);
			int colNumber = -1;

			XSSFRow Suiterow = ws.getRow(0);
			for (int i = 0; i < colNum; i++) {
				if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
					colNumber = i;
				}
			}

			if (colNumber == -1) {
				return false;
			}

			XSSFRow Row = ws.getRow(rowNumber);
			XSSFCell cell = Row.getCell(colNumber);
			if (cell == null)
				cell = Row.createCell(colNumber);

			cell.setCellValue(Result);

			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// To write result In test suite list sheet.
	public boolean writeResult(String wsName, String colName, String rowName, String Result) {
		try {
			int rowNum = retrieveNoOfRows(wsName);
			int rowNumber = -1;
			int sheetIndex = wb.getSheetIndex(wsName);
			if (sheetIndex == -1)
				return false;
			int colNum = retrieveNoOfCols(wsName);
			int colNumber = -1;

			XSSFRow Suiterow = ws.getRow(0);
			for (int i = 0; i < colNum; i++) {
				if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
					colNumber = i;
				}
			}

			if (colNumber == -1) {
				return false;
			}

			for (int i = 0; i < rowNum - 1; i++) {
				XSSFRow row = ws.getRow(i + 1);
				XSSFCell cell = row.getCell(0);
				DataFormatter formatter = new DataFormatter();
				String value = formatter.formatCellValue(cell);
				if (value.equals(rowName)) {
					rowNumber = i + 1;
					break;
				}
			}

			XSSFRow Row = ws.getRow(rowNumber);
			XSSFCell cell = Row.getCell(colNumber);
			if (cell == null)
				cell = Row.createCell(colNumber);

			cell.setCellValue(Result);

			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// To write result In test data and test case list sheet.
	public void writeData(String wsName, int colNumber, int rowNumber, String Result) {
		try {
			int sheetIndex = wb.getSheetIndex(wsName);
			if (sheetIndex == -1)
				return;

			XSSFRow row = ws.getRow(rowNumber);
			XSSFCell cell = row.getCell(colNumber);
			if (cell == null)
				cell = row.createCell(colNumber);

			cell.setCellValue(Result);

			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// To write result In test data and test case list sheet.
	public void updateTestCaseResult(String wsName, int rowNumber, String result) {
		try {
			int sheetIndex = wb.getSheetIndex(wsName);
			if (sheetIndex == -1)
				return;

			int colNumber = retrieveNoOfCols(wsName, rowNumber);

			XSSFRow row = ws.getRow(rowNumber);
			XSSFCell cell;
			cell = row.getCell(colNumber - 1);
			/*
			 * if (cell == null) { cell = row.createCell(colNumber);
			 * 
			 * } else { cell = row.getCell(colNumber); }
			 */

			cell.setCellValue(result);

			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// To write result In test data and test case list sheet.
	public void updateTestCaseResult(String wsName, int rowNumber, int colNumber, String result) {
		try {
			int sheetIndex = wb.getSheetIndex(wsName);
			if (sheetIndex == -1)
				return;

			XSSFRow row = ws.getRow(rowNumber);
			XSSFCell cell = row.getCell(colNumber - 1);
			cell.setCellValue(result);

			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// To write result In test data and test case list sheet.
	public void writeData(String wsName, String colName, int rowNumber, String Result) {
		try {
			int sheetIndex = wb.getSheetIndex(wsName);
			if (sheetIndex == -1)
				return;
			int colNum = retrieveNoOfCols(wsName);
			int colNumber = -1;

			XSSFRow Suiterow = ws.getRow(0);
			for (int i = 0; i < colNum; i++) {
				if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
					colNumber = i;
				}
			}

			if (colNumber == -1) {
				return;
			}

			XSSFRow Row = ws.getRow(rowNumber);
			XSSFCell cell = Row.getCell(colNumber);
			if (cell == null)
				cell = Row.createCell(colNumber);

			cell.setCellValue(Result);

			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		// String filelocation = System.getProperty("user.dir") +
		// "\\TestData\\DataDrivenExcel.xlsx";
		String filelocation = "C:\\Users\\Rishi\\Desktop\\Jp_Practice_Final\\TestData\\DataDrivenExcel.xlsx";
		Read_Excel read_XLS = new Read_Excel(filelocation);

		// ArrayList<Integer> retrieveTestCaseRows =
		// read_XLS.retrieveTestCaseRows("TestSheet", "Test_4");

		// read_XLS.updateTeastcaseResult("TestSheet", retrieveTestCaseRows.get(0),
		// "Passed");

		Object[][] retrieveTestData = read_XLS.retrieveTestData("TestSheet", "HRMLogin");
		// System.out.println("TestData: " + retrieveTestData("name", "word"));
		// read_XLS.retrieveToRunFlagTestData("TestSheet", "DataDriven", "runData");

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time: " + totalTime);

	}

}
