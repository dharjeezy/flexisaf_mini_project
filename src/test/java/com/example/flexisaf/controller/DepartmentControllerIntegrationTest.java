package com.example.flexisaf.controller;

/**
 * Damilare
 * 25/11/2021
 **/
import com.example.flexisaf.EntityMocker;
import com.example.flexisaf.FlexisafApplication;
import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.model.Student;
import com.example.flexisaf.payload.DepartmentRequest;
import com.example.flexisaf.payload.StudentRequest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


@SpringBootTest(classes = FlexisafApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
public class DepartmentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void test_getDepartment() {
        Department department = EntityMocker.mock(Department.class);

        HttpEntity entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/department", HttpMethod.GET, entity, String.class);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void test_getDepartments() {
        Department department = EntityMocker.mock(Department.class);

        HttpEntity entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/department/random-name", HttpMethod.GET, entity, String.class);

        assertNotNull(responseEntity);
    }

    @Test
    public void test_updateDepartment() {
        DepartmentRequest departmentRequest = EntityMocker.mock(DepartmentRequest.class);

        HttpEntity entity = new HttpEntity<>(departmentRequest, new HttpHeaders());

        ResponseEntity responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/student/random-name", HttpMethod.PUT, entity, String.class);

        assertNotNull(responseEntity);
    }

    @Test
    public void test_deleteDepartment() {
        Department department = EntityMocker.mock(Department.class);

        HttpEntity entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/department/random-id", HttpMethod.DELETE, entity, String.class);

        assertNotNull(responseEntity);
    }


    @Test
    public void test_createStudent() {
        DepartmentRequest departmentRequest = EntityMocker.mock(DepartmentRequest.class);


        ResponseEntity<Department> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/department", departmentRequest, Department.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
}
