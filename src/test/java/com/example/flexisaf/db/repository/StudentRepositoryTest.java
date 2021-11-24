package com.example.flexisaf.db.repository;

import com.example.flexisaf.EntityMocker;
import com.example.flexisaf.db.model.Student;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * Damilare
 * 24/11/2021
 **/
public class StudentRepositoryTest extends BaseRepositoryTest {
    @Autowired
    StudentRepository studentRepository;

    @Test
    public void findByIdAndDeletedAtIsNull_should_return_student() {
        Student student = EntityMocker.mock(Student.class);
        student.setDeletedAt(null);
        studentRepository.save(student);

        final Optional<Student> result = studentRepository.findByIdAndDeletedAtIsNull(student.getId());
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(student.getId());
    }

    @Test
    public void findByIdAndDeletedAtIsNull_should_return_no_student() {
        Student student = EntityMocker.mock(Student.class);
        studentRepository.save(student);

        final Optional<Student> result = studentRepository.findByIdAndDeletedAtIsNull("random");
        assertThat(result).isEmpty();
    }

    @Test
    public void findAllByDeletedAtIsNull_should_return_actual_content_size() {
        final List<Student> studentList = EntityMocker.mockWithoutId(Student.class, 5);
        for(Student student: studentList){
            student.setDeletedAt(null);
            studentRepository.save(student);
        }


        final Page<Student> resultInDB= studentRepository.findAllByDeletedAtIsNull(PageRequest.of(0, 5));
        final Page<Student> actualResult = new PageImpl<>(studentList);

        assertThat(resultInDB.getContent().size()).isEqualTo(actualResult.getContent().size());
    }
}
