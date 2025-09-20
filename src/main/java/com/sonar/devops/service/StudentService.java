package com.sonar.devops.service;

import com.sonar.devops.entity.Student;
import com.sonar.devops.exception.BusinessException;
import com.sonar.devops.mappers.StudentMapper;
import com.sonar.devops.model.StudentDTO;
import com.sonar.devops.repo.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDTO> findAllStudents(){
        List<Student> students= studentRepository.findAll();
        if(students.isEmpty()){
            throw new BusinessException("No Students are found");
        }
        return students.stream().map(StudentMapper::studentToStudentDTO).toList();
    }

    public StudentDTO findById(Long studentId){
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = optionalStudent.orElseThrow(()->new BusinessException("No student found with that id"));
        return StudentMapper.studentToStudentDTO(student);
    }

    public StudentDTO saveStudent(StudentDTO studentDTO){
        Student student = StudentMapper.studentDTOToStudent(studentDTO);
        Student savedStudent=  studentRepository.save(student);
        return StudentMapper.studentToStudentDTO(savedStudent);
    }

    public List<StudentDTO> getAllStudentsByIds(List<Long> studentIds){
        List<Student> students = studentRepository.findAllById(studentIds);
        if(students.isEmpty()){
            throw new BusinessException("No Students are found for the provided student ids");
        }

        return students.stream().map(StudentMapper::studentToStudentDTO).toList();
    }

    public List<StudentDTO> saveAllStudents(List<StudentDTO> studentDTOS){
        List<Student> students = studentDTOS.stream().map(StudentMapper::studentDTOToStudent).toList();
        List<Student> savedStudents = studentRepository.saveAll(students);
        List<Student> notSavedStudents = students.stream().filter(student -> savedStudents.stream().map(Student::getName).noneMatch(s -> s.equalsIgnoreCase(student.getName()))).toList();
        if(!notSavedStudents.isEmpty()){
            throw new BusinessException("Not able to save the fallowing students:"+notSavedStudents);
        }
        return savedStudents.stream().map(StudentMapper::studentToStudentDTO).toList();
    }
}
