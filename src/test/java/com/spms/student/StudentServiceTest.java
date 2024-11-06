package com.spms.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
                "john@mail.com",
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
//        savedStudent.setId(1);

       // Mock the calls
//        (which means StudentService will run in isolation mode and does not 100% depend on real implementation but the real instance of StudentMapper. In case forget to mock a step, test will fail)
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
        // the result is null (when running without mocking calls) because repository is calling save method but here we
        // are using mock and don't have real instance running for test and is
        // because the test is running in isolation mode, so needed to mock every call that uses another service or dependency in StudentService which is studentMapper and repository in this case

        // Then
        assertEquals(dto.firstname(), responseDto.firstname());
        assertEquals(dto.lastname(), responseDto.lastname());
        assertEquals(dto.email(), responseDto.email());

        verify(studentMapper, times(1))
                .toStudent(dto);
        verify(repository, times(1))
                .save(student);
        verify(studentMapper, times(1))
                .toStudentResponseDto(savedStudent);
    }

    @Test
    public void should_return_all_students() {
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student(
                "John",
                "Doe",
                "john@mail.com",
                20
        ));

        // Mock the calls
        when(repository.findAll())
                .thenReturn(students);
        when(studentMapper.toStudentResponseDto(any(Student.class))).thenReturn(new StudentResponseDto(
                "John",
                "Doe",
                "john@mail.com"
        ));

//        When
        List<StudentResponseDto> studentResponseDtos = studentService.findAllStudent();

//        Then
        assertEquals(students.size(), studentResponseDtos.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void should_return_student_by_id() {
        // Given
        Integer studentId = 1;
        Student student = new Student(
                "John",
                "Doe",
                "john@mail.com",
                20
        );
        student.setId(studentId);

//        Mock the calls
        when(repository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                    "John",
                    "Doe",
                    "john@mail.com"
                ));

//        When
        StudentResponseDto dto = studentService.findStudentById(studentId);

//        Then
        assertEquals(dto.firstname(), student.getFirstname());
        assertEquals(dto.lastname(), student.getLastname());
        assertEquals(dto.email(), student.getEmail());

        verify(repository, times(1)).findById(studentId);
    }

    @Test
    public void should_find_student_by_name() {
//        Given
        String studentName = "John";
        Student student = new Student(
                "John",
                "Doe",
                "john@mail.com",
                20
        );
        List<Student> students = new ArrayList<>();
        students.add(student);

//        Mock the calls
        when(repository.findByFirstnameContaining(studentName))
                .thenReturn(students);
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "john@mail.com"
                ));

//        When
        var responseDto = studentService.findStudentsByName(studentName);

//        Then
        assertEquals(students.size(), responseDto.size());
        verify(repository, times(1))
                .findByFirstnameContaining(studentName);
    }
}