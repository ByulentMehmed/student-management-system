package com.bmehmed.app.mapper;

import java.util.ArrayList;
import java.util.List;

import com.bmehmed.app.dto.StudentDto;
import com.bmehmed.app.entity.StudentEntity;

import model.Student;

public class StudentMapper {

	public static List<StudentDto> convertStudentEntitiesToStudentDtos(List<StudentEntity> studentEntities) {
		List<StudentDto> studentDtos = new ArrayList<>();
		for (StudentEntity studentEntity : studentEntities) {
			studentDtos.add(convertStudentEntityToStudentDto(studentEntity));
		}
		return studentDtos;
	}

	public static StudentDto convertStudentEntityToStudentDto(StudentEntity studentEntity) {
		StudentDto studentDto = new StudentDto();
		studentDto.setId(studentEntity.getId());
		studentDto.setFirstName(studentEntity.getFirstName());
		studentDto.setLastName(studentEntity.getLastName());
		studentDto.setGender(studentEntity.getGender().toString());
		studentDto.setDateOfBirth(studentEntity.getDateOfBirth().toString());
		return studentDto;
	}
	
	public static List<Student> convertStudentDtosToStudentModels(List<StudentDto> studentDtos) {
		List<Student> students = new ArrayList<>();
		for (StudentDto studentDto : studentDtos) {
			students.add(convertStudentDtoToStudentModel(studentDto));
		}
		return students;
	}

	public static Student convertStudentDtoToStudentModel(StudentDto studentDto) {
		Student student = new Student();
		student.setId(studentDto.getId());
		student.setFirstName(studentDto.getFirstName());
		student.setLastName(studentDto.getLastName());
		student.setGender(studentDto.getGender());
		student.setDateOfBirth(studentDto.getDateOfBirth());
		return student;
	}
}
