package com.example.flexisaf.service.contracts;

import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.model.Student;
import com.example.flexisaf.payload.DepartmentRequest;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    Department create(DepartmentRequest request);

    Department update(String name, DepartmentRequest request);

    boolean delete(String name);

    Department fetch(String name);

    Page<Department> fetchAll(int limit, int size);
}
