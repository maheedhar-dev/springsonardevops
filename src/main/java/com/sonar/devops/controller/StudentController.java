package com.sonar.devops.controller;

import com.sonar.devops.aop.TimeDuration;
import com.sonar.devops.entity.Student;
import com.sonar.devops.model.StudentDTO;
import com.sonar.devops.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    @TimeDuration
    public ResponseEntity<List<StudentDTO>> getAllAvailableStudents(){
       List<StudentDTO> studentDTOS = studentService.findAllStudents();
       return new ResponseEntity<>(studentDTOS, HttpStatus.OK);
    }

    @GetMapping("/students/{studentId}")
    @TimeDuration
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("studentId") Long studentId){
        StudentDTO studentDTO= studentService.findById(studentId);
        return new ResponseEntity<>(studentDTO,HttpStatus.OK);
    }

    @PostMapping("/students/search")
    @TimeDuration
    public ResponseEntity<List<StudentDTO>> searchStudentsByIds(@RequestBody List<Long> studentIds){
        List<StudentDTO> studentDTOS = studentService.getAllStudentsByIds(studentIds);
        return new ResponseEntity<>(studentDTOS,HttpStatus.OK);
    }

    @PostMapping("/students/save")
    @TimeDuration
    public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO studentDTO){
        StudentDTO savedStudentDTO =  studentService.saveStudent(studentDTO);
        return new ResponseEntity<>(savedStudentDTO,HttpStatus.OK);
    }

    @PostMapping("/students/saveall")
    @TimeDuration
    public ResponseEntity<List<StudentDTO>> saveStudents(@RequestBody List<StudentDTO> studentDTOSList){
        List<StudentDTO> savedStudentDTOS =  studentService.saveAllStudents(studentDTOSList);
        return new ResponseEntity<>(savedStudentDTOS,HttpStatus.OK);
    }

}
