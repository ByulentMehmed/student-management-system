package com.bmehmed.app.service.impl;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.bmehmed.app.dto.StudentDto;
import com.bmehmed.app.entity.StudentEntity;
import com.bmehmed.app.mapper.StudentMapper;
import com.bmehmed.app.repository.StudentRepository;
import com.bmehmed.app.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepo;

	@Override
	public List<StudentDto> getAllStudents() {
		List<StudentEntity> students = studentRepo.findAll();
		return StudentMapper.convertStudentEntitiesToStudentDtos(students);
	}

	@Override
	public ByteArrayInputStream loadStudentsExcelFile() {
		List<StudentEntity> students = studentRepo.findAll();
		return ExcelUtils.studentsToExcel(students);
	}

	@Override
	public void processStudentsExcelFile(Resource excelFile) {
		List<StudentEntity> students = ExcelUtils.parseStudentsExcelFile(excelFile);
		studentRepo.saveAll(students);
	}

}
