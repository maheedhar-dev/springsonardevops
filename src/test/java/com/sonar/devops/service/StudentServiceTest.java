package com.sonar.devops.service;

import com.sonar.devops.entity.Student;
import com.sonar.devops.exception.BusinessException;
import com.sonar.devops.model.StudentDTO;
import com.sonar.devops.repo.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        student1 = new Student();
        student1.setId(1L);
        student1.setName("Maheedhar");
        student1.setCourse("CSE");
        student1.setCity("Hyderabad");

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Sai");
        student2.setCourse("Mech");
        student2.setCity("Hyderabad");
    }

    @Test
    public void testFindAllStudents_success() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));
        List<StudentDTO> students = studentService.findAllStudents();
        assertThat(students).hasSize(2);
        assertThat(students.get(0).getName()).isEqualTo("Maheedhar");
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testFindAllStudents_noStudentsFound() {
        when(studentRepository.findAll()).thenReturn(List.of());
        assertThrows(BusinessException.class, () -> studentService.findAllStudents());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testFindById_success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        StudentDTO result = studentService.findById(1L);
        assertThat(result.getName()).isEqualTo("Maheedhar");
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_studentNotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> studentService.findById(99L));
        verify(studentRepository, times(1)).findById(99L);
    }

    @Test
    void testSaveStudent_success() {
        when(studentRepository.save(any(Student.class))).thenReturn(student1);
        StudentDTO studentDTO = new StudentDTO(null, "Maheedhar", "CSE", "Hyderabad");
        StudentDTO savedStudent = studentService.saveStudent(studentDTO);
        assertThat(savedStudent.getName()).isEqualTo("Maheedhar");
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testGetAllStudentsByIds_success() {
        when(studentRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(student1, student2));
        List<StudentDTO> studentDTOS = studentService.getAllStudentsByIds(Arrays.asList(1L, 2L));
        assertThat(studentDTOS).hasSize(2);
        verify(studentRepository, times(1)).findAllById(Arrays.asList(1L, 2L));
    }

    @Test
    void testGetAllStudentsByIds_noStudentsFound() {
        when(studentRepository.findAllById(Arrays.asList(100L, 200L))).thenReturn(List.of());
        assertThrows(BusinessException.class, () -> studentService.getAllStudentsByIds(Arrays.asList(100L, 200L)));
        verify(studentRepository, times(1)).findAllById(Arrays.asList(100L, 200L));
    }

    @Test
    void saveAllStudents_success(){
        when(studentRepository.saveAll(anyList())).thenReturn(Arrays.asList(student1,student2));
        List<StudentDTO> studentDTOS = studentService.saveAllStudents(Arrays.asList(new StudentDTO(null,"Maheedhar","CSE","Hyderabad"),
                new StudentDTO(null,"Sai","Mech","Hyderabad")));
        assertThat(studentDTOS).hasSize(2);
        verify(studentRepository,times(1)).saveAll(anyList());
    }

    @Test
    void saveAllStudents_notSaved(){
        when(studentRepository.saveAll(anyList())).thenReturn(Collections.singletonList(student1));
        assertThrows(BusinessException.class,()->studentService.saveAllStudents(Arrays.asList(new StudentDTO(null,"Maheedhar","CSE","Hyderabad"),
                new StudentDTO(null,"Sai","Mech","Hyderabad"))));
        verify(studentRepository,times(1)).saveAll(anyList());
    }
}
