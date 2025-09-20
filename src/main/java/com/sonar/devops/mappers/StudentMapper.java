package com.sonar.devops.mappers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonar.devops.entity.Student;
import com.sonar.devops.model.StudentDTO;

public class StudentMapper {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static StudentDTO studentToStudentDTO(Student student){
        return objectMapper.convertValue(student,StudentDTO.class);
    }

    public static Student studentDTOToStudent(StudentDTO studentDTO){
        return objectMapper.convertValue(studentDTO,Student.class);
    }
}
