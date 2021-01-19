package com.bmehmed.app.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.core.io.Resource;

import com.bmehmed.app.dto.StudentDto;

public interface StudentService {

	List<StudentDto> getAllStudents();
	
	ByteArrayInputStream loadStudentsExcelFile();
	
	void processStudentsExcelFile(Resource excelFile);
}
