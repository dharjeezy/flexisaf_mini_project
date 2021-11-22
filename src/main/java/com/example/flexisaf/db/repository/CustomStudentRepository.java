package com.example.flexisaf.db.repository;

import com.example.flexisaf.db.model.Student;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomStudentRepository {
    Page<Student> findByFilters(HashMap<String, Object> filters, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
