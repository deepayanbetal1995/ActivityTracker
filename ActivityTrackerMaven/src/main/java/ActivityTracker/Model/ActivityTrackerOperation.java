package ActivityTracker.Model;

import java.util.Date;
import java.util.Scanner;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ActivityTrackerOperation implements OperationMethods {

	String leftTimeMsg="";
	String Path = "G:\\excel\\TestExcel.xlsx";
	ActivityTrackerModel ATM = new ActivityTrackerModel();
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

	public String DataRead() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter UID");
		ATM.UID = sc.next();
		System.out.println("Enter WorkingHours");
		ATM.workingHours = sc.nextDouble();
		System.out.println("Enter Work Area");
		ATM.workArea = sc.next();
		System.out.println("Enter Project");
		ATM.Project = sc.next();
		System.out.println("Enter pendingWorkItem");
		ATM.pendingWorkItem = sc.next();
		sc.close();

		return null;
	}

	public String DataDisp() {
		System.out.println(ATM.UID);
		System.out.println(ATM.workingHours);
		System.out.println(ATM.workArea);
		System.out.println(ATM.Project);
		System.out.println(ATM.pendingWorkItem);
		return null;

	}

	public void ExcelUpdate(ActivityTrackerModel ATM) throws IOException, InvalidFormatException {

		File f = new File(Path);
		System.out.println("Inside ExcelUpdate");
		
		if (!f.exists()) {

			UpdateInNewExcel(ATM);
			System.out.println("File is not present");

		} else {
			System.out.println("File is present");
			UpdateInOldExcel(ATM);

		}
	}

	public void UpdateInNewExcel(ActivityTrackerModel ATModel) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Test Sheet");
		
		double leftTime = ATModel.toDoTime - ATModel.workingHours;
		if(leftTime >= 0.0)
		{
			leftTimeMsg = Double.toString(leftTime);
		}
		else
			leftTimeMsg = " Great !You have done"+" "+Math.abs(leftTime)+" "+"hours more job.";
		
		Object[][] bookData = { { "Date","UID", "WorkingHours", "WorlArea", "Project", "PendingWorkItem","TO DO Time","Time Remain" },
				{df.format(date),ATModel.UID, ATModel.workingHours, ATModel.workArea, ATModel.Project, ATModel.pendingWorkItem,ATModel.toDoTime, leftTimeMsg }

		};

		int rowCount = 0;

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;

			Cell cell = row.createCell(columnCount);
			cell.setCellValue(rowCount);

			for (Object field : aBook) {
				Cell cell1 = row.createCell(++columnCount);

				if (field instanceof String) {
					cell1.setCellValue((String) field);
				} else if (field instanceof Integer || field instanceof Double) {
					cell1.setCellValue((Double) field);

				}
			}

		}

		try (FileOutputStream outputStream = new FileOutputStream("G:\\excel\\TestExcel.xlsx")) {
			try {
				workbook.write(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		workbook.close();
		// inputStream.close();
	}

	public void UpdateInOldExcel(ActivityTrackerModel ATModel) throws InvalidFormatException {
		System.out.println("In old File");
		try {
			FileInputStream inputStream = new FileInputStream(new File(Path));
			Workbook workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(0);
			System.out.println(sheet);
			double leftTime = ATModel.toDoTime - ATModel.workingHours;
			if(leftTime >= 0.0)
				leftTimeMsg = Double.toString(leftTime);
			else
				leftTimeMsg = " Great !You have done"+" "+Math.abs(leftTime)+" "+"hours more job.";
			
			Object[][] bookData = { { df.format(date),ATModel.UID, ATModel.workingHours, ATModel.workArea, ATModel.Project, ATModel.pendingWorkItem, ATModel.toDoTime, leftTimeMsg }

			};

			int rowCount = sheet.getLastRowNum();
			System.out.println(rowCount);

			for (Object[] aBook : bookData) {
				Row row = sheet.createRow(++rowCount);

				int columnCount = 0;

				Cell cell = row.createCell(columnCount);
				cell.setCellValue(rowCount+1);

				for (Object field : aBook) {
					cell = row.createCell(++columnCount);
					if (field instanceof String) {
						cell.setCellValue((String) field);
					} else if (field instanceof Integer || field instanceof Double) {
						cell.setCellValue((Double) field);

					}
				}

			}

			inputStream.close();

			FileOutputStream outputStream = new FileOutputStream(Path);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();

		} catch (IOException | EncryptedDocumentException ex) {
			ex.printStackTrace();
		}
	}
}
