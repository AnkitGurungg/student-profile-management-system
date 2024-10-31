package com.alibou.example.student;

import com.alibou.example.school.School;
import com.alibou.example.studentprofile.StudentProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    public void shouldMapStudentDtoToStudent() {
        StudentDto dto = new StudentDto("John",
                "Doe",
                "john@email.com",
                1);

        Student student = mapper.toStudent(dto);

        assertEquals(dto.firstname(), student.getFirstname());
        assertEquals(dto.lastname(), student.getLastname());
        assertEquals(dto.email(), student.getEmail());
        assertNotNull(dto.schoolId());
        assertEquals(dto.schoolId(), student.getSchool().getId());
    }

    @Test
    public void should_throw_null_pointer_exception_when_studentDto_is_null() {
        var exp = assertThrows(NullPointerException.class, () -> mapper.toStudent(null));
        assertEquals("The student Dto should not be null", exp.getMessage());

//        Student student = mapper.toStudent(null);
    }

    @Test
    public void shouldMapStudentToStudentResponseDto() {
        // Given
        StudentProfile studentProfileObj = new StudentProfile("science");
        School schoolObj = new School("AnkitGurung");
        Student studentObj = new Student("Jane",
                "Smith",
                "jane@email.com",
                20);
        studentObj.setStudentProfile(studentProfileObj);
        studentObj.setSchool(schoolObj);

        // When
        StudentResponseDto response = mapper.toStudentResponseDto(studentObj);

        // Then
        assertEquals(response.firstname(), studentObj.getFirstname());
        assertEquals(response.lastname(), studentObj.getLastname());
        assertEquals(response.email(), studentObj.getEmail());

//        assertEquals(response.firstname(), studentObj.getStudentProfile().getStudent().getFirstname());
//        assertEquals(response.lastname(), studentObj.getStudentProfile().getStudent().getLastname());
//        assertEquals(response.email(), studentObj.getStudentProfile().getStudent().getEmail());

    }
}
