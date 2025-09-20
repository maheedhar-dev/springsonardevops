package com.sonar.devops.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonar.devops.model.StudentDTO;
import com.sonar.devops.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllAvailableStudents() throws Exception {
        List<StudentDTO> students = Arrays.asList(new StudentDTO(1L, "Maheedhar", "CSE", "Hyderabad"),
                new StudentDTO(2L, "Sai", "Mech", "Hyderabad"));
        Mockito.when(studentService.findAllStudents()).thenReturn(students);
        mockMvc.perform(get("/students").with(SecurityMockMvcRequestPostProcessors.httpBasic("Maheedhar","password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Maheedhar"));
    }

    @Test
    public void testGetStudentById() throws Exception {
        StudentDTO student = new StudentDTO(1L, "Maheedhar", "CSE", "Hyderabad");
        Mockito.when(studentService.findById(1L)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/1").with(SecurityMockMvcRequestPostProcessors.httpBasic("Maheedhar","password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maheedhar"));
    }

    @Test
    public void testSearchStudentsByIds() throws Exception {
        List<StudentDTO> students = List.of(
                new StudentDTO(1L, "John Doe", "Computer Science", "New York")
        );
        Mockito.when(studentService.getAllStudentsByIds(Arrays.asList(1L, 2L))).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders.post("/students/search")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("Admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(1L, 2L))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

    }

    @Test
    public void testSaveStudent() throws Exception {
        StudentDTO input = new StudentDTO(null, "Maheedhar", "CS", "Hyderabad");
        StudentDTO saved = new StudentDTO(1L, "Maheedhar", "CSE", "Hyderabad");
        Mockito.when(studentService.saveStudent(Mockito.any(StudentDTO.class))).thenReturn(saved);
        mockMvc.perform(post("/students/save")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("Admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testSaveStudents() throws Exception {

    List<StudentDTO> input = List.of(new StudentDTO(null, "Maheedhar", "CSE", "Hyderabad"),
            new StudentDTO(null, "Ravi", "IT", "Banglore"));
    List<StudentDTO> saved = List.of(new StudentDTO(1L, "Maheedhar", "CSE", "Hyderabad"),
            new StudentDTO(2L, "Ravi", "IT", "Banglore"));
    Mockito.when(studentService.saveAllStudents(Mockito.anyList())).thenReturn(saved);
    mockMvc.perform(post("/students/saveall")
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("Admin","admin"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2));
    }
}
