package com.example.flexisaf.service;

import com.example.flexisaf.EntityMocker;
import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.model.Student;
import com.example.flexisaf.db.repository.StudentRepository;
import com.example.flexisaf.payload.StudentRequest;
import com.example.flexisaf.service.contracts.DepartmentService;
import com.example.flexisaf.service.contracts.StudentService;
import com.example.flexisaf.service.implementer.StudentServiceImpl;
import com.example.flexisaf.service.notification.EmailService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Matchers.any;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Damilare
 * 24/11/2021
 **/
@RunWith(SpringRunner.class)
public class StudentServiceTest {
    StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    DepartmentService departmentService;

    @Mock
    EmailService emailService;

    @Before
    public void init(){
        studentService = new StudentServiceImpl(studentRepository,
                departmentService, emailService);
    }

    @Test
    public void create_test() {
        StudentRequest studentRequest = EntityMocker.mock(StudentRequest.class);
        Student student = EntityMocker.mock(Student.class);
        Department department = EntityMocker.mock(Department.class);

        Mockito.when(departmentService.fetch(any(String.class))).thenReturn(department);
        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(student);
        Mockito.when(studentRepository.count()).thenReturn(5L);

        Student result = studentService.create(studentRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(student.getId());
    }

    @Test
    public void update_test() {
        StudentRequest studentRequest = EntityMocker.mock(StudentRequest.class);
        Student student = EntityMocker.mock(Student.class);

        Mockito.when(studentRepository.findByIdAndDeletedAtIsNull(any(String.class))).thenReturn(Optional.of(student));
        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.update(student.getId(), studentRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(student.getId());
    }

    @Test
    public void delete_test() {
        Student student = EntityMocker.mock(Student.class);

        Mockito.when(studentRepository.findByIdAndDeletedAtIsNull(any(String.class))).thenReturn(Optional.of(student));

        boolean result = studentService.delete(student.getId());

        assertThat(result).isEqualTo(true);
    }


    @Test
    public void fetch_test() {
        Student student = EntityMocker.mock(Student.class);

        Mockito.when(studentRepository.findByIdAndDeletedAtIsNull(any(String.class))).thenReturn(Optional.of(student));

        Student result = studentService.fetch(student.getId());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(student.getId());
    }

    @Test
    public void fetchAll_test() {
        final List<Student> studentList = EntityMocker.mockWithoutId(Student.class, 5);
        for(Student student: studentList){
            student.setDeletedAt(null);
            studentRepository.save(student);
        }
        final Page<Student> students = new PageImpl<>(studentList);

        Mockito.when(studentRepository.findAllByDeletedAtIsNull(any(PageRequest.class))).thenReturn(students);

        Page<Student> result = studentService.fetchAll(0, 5);

        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(students.getContent().size());
    }

    @Test
    public void fetchStudentByFilters_test() {
        final List<Student> studentList = EntityMocker.mockWithoutId(Student.class, 5);
        for(Student student: studentList){
            student.setDeletedAt(null);
            studentRepository.save(student);
        }
        final Page<Student> students = new PageImpl<>(studentList);

        Mockito.when(studentRepository.findByFilters(any(HashMap.class), any(LocalDateTime.class), any(LocalDateTime.class), any(PageRequest.class))).thenReturn(students);
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("gender", "M");

        Page<Student> result = studentService.fetchStudentByFilters(objectHashMap, LocalDateTime.MIN, LocalDateTime.now(), PageRequest.of(0,5));

        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(students.getContent().size());
    }


}
