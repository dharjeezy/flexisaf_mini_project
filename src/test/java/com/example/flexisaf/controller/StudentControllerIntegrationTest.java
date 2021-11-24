package com.example.flexisaf.controller;

/**
 * Damilare
 * 24/11/2021
 **/

import com.example.flexisaf.EntityMocker;
import com.example.flexisaf.FlexisafApplication;
import com.example.flexisaf.db.model.Student;
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
public class StudentControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_getStudent() {
       Student student = EntityMocker.mock(Student.class);

        HttpEntity entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/student", HttpMethod.GET, entity, String.class);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void test_getStudents() {
        Student student = EntityMocker.mock(Student.class);

        HttpEntity entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/student/random-id", HttpMethod.GET, entity, String.class);

        assertNotNull(responseEntity);
    }

    @Test
    public void test_updateStudent() {
        StudentRequest studentRequest = EntityMocker.mock(StudentRequest.class);

        HttpEntity entity = new HttpEntity<>(studentRequest, new HttpHeaders());

        ResponseEntity responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/student/random-id", HttpMethod.PUT, entity, String.class);

        assertNotNull(responseEntity);
    }

    @Test
    public void test_deleteStudents() {
        Student student = EntityMocker.mock(Student.class);

        HttpEntity entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/student/random-id", HttpMethod.DELETE, entity, String.class);

        assertNotNull(responseEntity);
    }


    @Test
    public void test_createStudent() {
        StudentRequest studentRequest = EntityMocker.mock(StudentRequest.class);

        ResponseEntity<Student> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/v1/student", studentRequest, Student.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void test_filters() {
        Student student = EntityMocker.mock(Student.class);

        HttpEntity entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/student/filters", HttpMethod.GET, entity, String.class);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
