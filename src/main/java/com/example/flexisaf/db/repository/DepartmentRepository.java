package com.example.flexisaf.db.repository;

import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.model.Student;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Damilare
 * 22/11/2021
 **/
public interface DepartmentRepository extends MongoRepository<Department, String> {
    Optional<Department> findByNameAndDeletedAtIsNull(String departmentName);
    Page<Department> findAllByDeletedAtIsNull(PageRequest pageRequest);
}
