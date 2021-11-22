package com.example.flexisaf.service.contracts;

import com.example.flexisaf.db.model.Student;
import com.example.flexisaf.payload.StudentRequest;
import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Damilare
 * 22/11/2021
 **/
@Service
public interface StudentService {

    Student create(StudentRequest request);

    Student update(String id, StudentRequest request);

    boolean delete(String id);

    Student fetch(String id);

    Page<Student> fetchAll(int limit, int size);

    Page<Student> fetchStudentByFilters(HashMap<String, Object> filters, LocalDateTime from, LocalDateTime to, PageRequest pageRequest);

}
