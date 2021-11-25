package com.example.flexisaf.service;

import com.example.flexisaf.EntityMocker;
import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.model.Student;
import com.example.flexisaf.db.repository.DepartmentRepository;
import com.example.flexisaf.db.repository.StudentRepository;
import com.example.flexisaf.payload.DepartmentRequest;
import com.example.flexisaf.payload.StudentRequest;
import com.example.flexisaf.service.implementer.DepartmentServiceImpl;
import com.example.flexisaf.service.implementer.StudentServiceImpl;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Damilare
 * 25/11/2021
 **/
@RunWith(SpringRunner.class)
public class DepartmentServiceTest {
    DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Before
    public void init(){
        departmentService = new DepartmentServiceImpl(departmentRepository);
    }

    @Test
    public void create_test() {
        DepartmentRequest departmentRequest = EntityMocker.mock(DepartmentRequest.class);
        Department department = EntityMocker.mock(Department.class);

        Mockito.when(departmentRepository.findByNameAndDeletedAtIsNull(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department result = departmentService.create(departmentRequest);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(department.getName());
    }

    @Test
    public void update_test() {
        DepartmentRequest departmentRequest = EntityMocker.mock(DepartmentRequest.class);
        Department department = EntityMocker.mock(Department.class);

        Mockito.when(departmentRepository.findByNameAndDeletedAtIsNull(department.getName())).thenReturn(Optional.of(department));
        Mockito.when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department result = departmentService.update(department.getName(), departmentRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(department.getId());
    }

    @Test
    public void delete_test() {
        Department department = EntityMocker.mock(Department.class);

        Mockito.when(departmentRepository.findByNameAndDeletedAtIsNull(any(String.class))).thenReturn(Optional.of(department));

        boolean result = departmentService.delete(department.getId());

        assertThat(result).isEqualTo(true);
    }


    @Test
    public void fetch_test() {
        Department department = EntityMocker.mock(Department.class);

        Mockito.when(departmentRepository.findByNameAndDeletedAtIsNull(any(String.class))).thenReturn(Optional.of(department));

        Department result = departmentService.fetch(department.getId());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(department.getId());
    }

    @Test
    public void fetchAll_test() {
        final List<Department> departmentList = EntityMocker.mockWithoutId(Department.class, 5);
        for(Department department: departmentList){
            department.setDeletedAt(null);
            departmentRepository.save(department);
        }
        final Page<Department> departments = new PageImpl<>(departmentList);

        Mockito.when(departmentRepository.findAllByDeletedAtIsNull(any(PageRequest.class))).thenReturn(departments);

        Page<Department> result = departmentService.fetchAll(0, 5);

        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(departments.getContent().size());
    }
}
