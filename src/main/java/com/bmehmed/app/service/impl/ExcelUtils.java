package com.bmehmed.app.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;

import com.bmehmed.app.entity.Gender;
import com.bmehmed.app.entity.StudentEntity;

public class ExcelUtils {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERS = { "Id", "First name", "Last name", "Gender", "Date of birth" };
	static String SHEET = "Students";

	public static ByteArrayInputStream studentsToExcel(List<StudentEntity> students) {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERS.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERS[col]);
			}

			int rowIdx = 1;
			for (StudentEntity student : students) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(student.getId());
				row.createCell(1).setCellValue(student.getFirstName());
				row.createCell(2).setCellValue(student.getLastName());
				row.createCell(3).setCellValue(student.getGender().toString());
				row.createCell(4).setCellValue(student.getDateOfBirth().toString());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
		}
	}
	
	public static List<StudentEntity> parseStudentsExcelFile(Resource excelFile) {
		try (InputStream inputStream = excelFile.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
			List<StudentEntity> students = new ArrayList<>();
			Sheet sheet = workbook.getSheetAt(0);

			DataFormatter formatter = new DataFormatter();
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			for (Row row : sheet) {
				if (row.getRowNum() != 0) {
					StudentEntity student = new StudentEntity();
					if (row.getCell(0) != null) {
						student.setId(Integer.valueOf(formatter.formatCellValue(row.getCell(0))));
					}
					student.setFirstName(row.getCell(1).getStringCellValue());
					student.setLastName(row.getCell(2).getStringCellValue());
					student.setGender(Gender.valueOf(row.getCell(3).getStringCellValue()));
					student.setDateOfBirth(LocalDate.parse(dateFormatter.format(row.getCell(4).getDateCellValue())));
					students.add(student);
				}
			}
			return students;
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse data from Excel file: " + e.getMessage());
		}
	}
}
