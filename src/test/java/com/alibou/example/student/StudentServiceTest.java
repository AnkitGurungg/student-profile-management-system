package com.alibou.example.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StudentServiceTest {

    // which service we want to test

    @InjectMocks
    private StudentService studentService;

    // declare the dependencies
    @Mock
    StudentRepository repository;
    @Mock
    StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void  should_successfully_save_a_student(){
        // Given
        StudentDto dto = new StudentDto(
                "John",
                "Doe",
                "john@email.com",
                1
        );
        Student student = new Student(
                "John",
                "Doe",
                "john@mail.com",
                20
        );
        Student savedStudent = new Student(
                "John",
                "Doe",
                "john@mail.com",
                20
        );
        savedStudent.setId(1);

       // Mock the calls
        when(studentMapper.toStudent(dto))
                 .thenReturn(student);
        when(repository.save(student))
                .thenReturn(savedStudent);
        when(studentMapper.toStudentResponseDto(savedStudent))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "john@mail.com"));

        // When
        StudentResponseDto responseDto = studentService.saveStudent(dto);

        // What
        assertEquals(dto.firstname(), responseDto.firstname());
        assertEquals(dto.lastname(), responseDto.lastname());
        assertEquals(dto.email(), responseDto.email());

    }
}