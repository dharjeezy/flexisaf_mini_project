package com.example.flexisaf.controller;

import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.payload.DepartmentRequest;
import com.example.flexisaf.payload.Response;
import com.example.flexisaf.service.contracts.DepartmentService;
import org.springframework.data.domain.Page;
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
 * 24/11/2021
 **/
@RestController
@RequestMapping("api/v1/department")
public class DepartmentController {

    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("{name}")
    public ResponseEntity<?> getDepartment(@PathVariable(value = "name") final String name) {
        Department department = departmentService.fetch(name);
        Response response = new Response(true, "Gotten Department successfully", department);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllDepartments(@RequestParam(value = "page", defaultValue = "0") final int page,
                                            @RequestParam(value = "limit", defaultValue = "50") final int limit) {
        Page<Department> departments = departmentService.fetchAll(page, limit);
        Response response = new Response(true,  "Gotten departments successfully", departments.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("{name}")
    public ResponseEntity<?> updateDepartment(@Validated @RequestBody DepartmentRequest departmentRequest,
                                           @PathVariable(value = "name") final String studentId) {
        Department department = departmentService.update(studentId, departmentRequest);
        Response response = new Response(true,  "Updated department successfully", department);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<Response> createDepartment(@Validated @RequestBody DepartmentRequest departmentRequest) {
        Department department = departmentService.create(departmentRequest);
        Response response = new Response(true, "Created department successfully", department);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("{name}")
    public ResponseEntity<?> deleteDepartment(@PathVariable(value = "name") final String name) {
        boolean isDeleted = departmentService.delete(name);
        Response response = new Response(isDeleted,  "Deleted department successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
