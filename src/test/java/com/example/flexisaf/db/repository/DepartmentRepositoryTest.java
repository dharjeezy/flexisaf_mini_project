package com.example.flexisaf.db.repository;

import com.example.flexisaf.EntityMocker;
import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.model.Student;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * Damilare
 * 25/11/2021
 **/
public class DepartmentRepositoryTest extends BaseRepositoryTest {
    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    public void findByNameAndDeletedAtIsNull_should_return_student() {
        Department department = EntityMocker.mock(Department.class);
        department.setDeletedAt(null);
        departmentRepository.save(department);

        final Optional<Department> result = departmentRepository.findByNameAndDeletedAtIsNull(department.getName());
        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(department.getName());
    }

    @Test
    public void findByNameAndDeletedAtIsNull_should_return_no_student() {
        Department department = EntityMocker.mock(Department.class);
        departmentRepository.save(department);

        final Optional<Department> result = departmentRepository.findByNameAndDeletedAtIsNull("random");
        assertThat(result).isEmpty();
    }

    @Test
    public void findByNameAndDeletedAtIsNull_should_return_actual_content_size() {
        final List<Department> departmentList = EntityMocker.mockWithoutId(Department.class, 5);
        for(Department department: departmentList){
            department.setDeletedAt(null);
            departmentRepository.save(department);
        }


        final Page<Department> resultInDB= departmentRepository.findAllByDeletedAtIsNull(PageRequest.of(0, 5));
        final Page<Department> actualResult = new PageImpl<>(departmentList);

        assertThat(resultInDB.getContent().size()).isEqualTo(actualResult.getContent().size());
    }
}
