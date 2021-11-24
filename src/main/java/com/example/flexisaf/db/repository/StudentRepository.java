package com.example.flexisaf.db.repository;

import com.example.flexisaf.db.model.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Damilare
 * 22/11/2021
 **/
public interface StudentRepository extends MongoRepository<Student, String>, CustomStudentRepository {
    Optional<Student> findByMatricNumberAndDeletedAtIsNull(String matricNumber);
    Optional<Student> findByIdAndDeletedAtIsNull(String id);
    Page<Student> findAllByIdAndDeletedAtIsNull(PageRequest pageRequest);
}
