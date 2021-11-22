package com.example.flexisaf.controller;

import com.example.flexisaf.db.model.Student;
import com.example.flexisaf.db.types.Gender;
import com.example.flexisaf.payload.Response;
import com.example.flexisaf.payload.StudentRequest;
import com.example.flexisaf.service.contracts.StudentService;
import com.example.flexisaf.util.StringUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Damilare
 * 22/11/2021
 **/
@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("{studentId}")
    public ResponseEntity<?> getStudent( @PathVariable(value = "studentId") final String studentId) {
        Student student = studentService.fetch(studentId);
        Response response = new Response(true, "Gotten Student successfully", student);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllStudents(@RequestParam(value = "page", defaultValue = "0") final int page,
                                                        @RequestParam(value = "limit", defaultValue = "50") final int limit) {
        Page<Student> students = studentService.fetchAll(page, limit);
        Response response = new Response(true,  "Gotten students successfully", students.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("{studentId}")
    public ResponseEntity<?> updateStudent(@Validated @RequestBody StudentRequest studentRequest,
                                           @PathVariable(value = "studentId") final String studentId) {
       Student student = studentService.update(studentId, studentRequest);
        Response response = new Response(true,  "Updated student successfully", student);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<Response> createStudent(@Validated @RequestBody StudentRequest studentRequest) {
        Student student = studentService.create(studentRequest);
        Response response = new Response(true, "Created student successfully", student);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "studentId") final String studentId) {
        boolean isDeleted = studentService.delete(studentId);
        Response response = new Response(isDeleted,  "Deleted student successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/filters")
    public ResponseEntity<?> getStudentsByFilters(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "limit", defaultValue = "50") final int limit,
            @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final LocalDateTime from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final LocalDateTime to,
            @RequestParam(value = "firstName", required = false) final String firstName,
            @RequestParam(value = "lastName", required = false) final String lastName,
            @RequestParam(value = "otherNames", required = false) final String otherNames,
            @RequestParam(value = "fullName", required = false) final String fullName,
            @RequestParam(value = "gender", required = false) final Gender gender,
            @RequestParam(value = "departmentName", required = false) final String departmentName,
            @RequestParam(value = "createdBy", required = false) final String createdBy
    ) {
        final HashMap<String, Object> filters = new HashMap<>();
        if (!StringUtil.isBlank(firstName)) {
            filters.put("firstName", firstName);
        }

        if (!StringUtil.isBlank(lastName)) {
            filters.put("lastName", lastName);
        }

        if (!StringUtil.isBlank(otherNames)) {
            filters.put("otherNames", otherNames);
        }

        // NEED TO IMPLEMENT THIS PROPERLY
        if (!StringUtil.isBlank(fullName)) {
            filters.put("fullName", fullName);
        }

        if (gender != null)
            filters.put("gender",gender.name());

        if (!StringUtil.isBlank(createdBy)) {
            filters.put("createdBy", createdBy);
        }

        if (!StringUtil.isBlank(departmentName)) {
            filters.put("departmentName", departmentName);
        }

        final PageRequest pageRequest = PageRequest.of(
                page < 0 ? 0 : page,
                limit,
                Sort.by(Sort.Direction.DESC, "createdDateTime"));


        final Page<Student> students =studentService.fetchStudentByFilters(
                filters,
                from,
                to,
                pageRequest);

        Response response = new Response(true,  "Fetched students by filter", students);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
