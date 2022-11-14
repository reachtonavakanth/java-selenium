package com.poc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Utils {
	
	/*
	 * @author:Navakanth
	 * Description: To read data from properties files
	 */
	public String getConfigValue(String sFile, String sKey) {
		Properties prop = new Properties();
		String sValue = null;
		try {
			InputStream input = new FileInputStream(sFile);
			prop.load(input);
			sValue = prop.getProperty(sKey);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sValue;
	}

	/*
	 * @author:Navakanth
	 * Description: To set / write data to properties files
	 */
	public void setCongigValue(String sFile, String sKey, String sValue) {
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(new File(sFile));
			prop.load(fis);
			fis.close();

			FileOutputStream fos = new FileOutputStream(new File(sFile));
			prop.setProperty(sKey, sValue);
			prop.store(fos, "Updating folder path");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @author:Navakanth
	 * Description: To read test data from excel sheet
	 */
	public String[] toReadExcelData(String filePath, String sheetName, String sTestCaseID) {

		String sData[] = null;
		try {

			FileInputStream fis = new FileInputStream(filePath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sheetName);
			int iRowNum = sht.getLastRowNum();
			for (int i = 1; i <= iRowNum; i++) {
				if (sht.getRow(i).getCell(0).toString().equals(sTestCaseID)) {
					int iCellNum = sht.getRow(i).getLastCellNum();
					sData = new String[iCellNum];
					for (int j = 0; j < iCellNum; j++) {
						sData[j] = sht.getRow(i).getCell(j).getStringCellValue();
					}
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sData;
	}

	/*
	 * @author:Navakanth
	 * Description: To Convert a string value to Integer
	 */
	public Integer getNum(String str) {
		return Integer.valueOf(str);
	}

	/*
	 * @author:Navakanth
	 * Description: To write logs to a external file
	 */
	public void log(String txt) {

		String msg = getDateTime()+ " "+Thread.currentThread().getStackTrace()[2].getClassName() + ":  " + txt;

		String strFile = "logs";
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(logFile + File.separator+" log.txt", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(msg);
		printWriter.close();

	}
	
	/*
	 * @author:Navakanth
	 * Description: To generate current time stamp 
	 */
	  public String getDateTime() {
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	        Date date = new Date();
	        return dateFormat.format(date);
	    }
}
