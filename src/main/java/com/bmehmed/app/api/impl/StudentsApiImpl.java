package com.bmehmed.app.api.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.bmehmed.app.dto.StudentDto;
import com.bmehmed.app.mapper.StudentMapper;
import com.bmehmed.app.service.StudentService;

import api.StudentsApi;
import model.Student;

@CrossOrigin(origins = "*")
@RestController
public class StudentsApiImpl implements StudentsApi {
	
	@Autowired
	private StudentService studentService;

	@Override
	public ResponseEntity<List<Student>> getAllStudents() {
		List<StudentDto> students = studentService.getAllStudents();
		return new ResponseEntity<>(StudentMapper.convertStudentDtosToStudentModels(students), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Resource> returnStudentsExcelFile() {
		String filename = "students.xlsx";
	    InputStreamResource file = new InputStreamResource(studentService.loadStudentsExcelFile());

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	}
	
	@Override
	public ResponseEntity<Void> uploadStudentsExcelFile(@Valid Resource body) {
		studentService.processStudentsExcelFile(body);		
		return ResponseEntity.noContent().build();
	}
}
