package com.example.flexisaf.service.implementer;

import com.example.flexisaf.db.model.Student;
import com.example.flexisaf.db.repository.StudentRepository;
import com.example.flexisaf.exception.NotFoundException;
import com.example.flexisaf.payload.StudentRequest;
import com.example.flexisaf.service.contracts.DepartmentService;
import com.example.flexisaf.service.contracts.StudentService;
import com.example.flexisaf.util.StringUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Damilare
 * 22/11/2021
 **/
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    StudentRepository studentRepository;
    DepartmentService departmentService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, DepartmentService departmentService) {
        this.studentRepository = studentRepository;
        this.departmentService = departmentService;
    }

    public Student create(StudentRequest request) {
        Student student = studentRepository.save(getCustomerFromCustomerRequest(request));

        // check department service if department name exists

        // check if age is valid

        // generate matric number

        student.setCreatedDateTime(LocalDateTime.now());
        return student;
    }


    public Student update(String id, StudentRequest request) {
        Optional<Student> studentOptional = studentRepository.findByIdAndDeletedAtIsNull(id);

        if(!studentOptional.isPresent())
            throw new NotFoundException(String.format("Student with id [%s] not found", id));

        Student student = studentOptional.get();

        if(!StringUtil.isBlank(request.getFirstName()))
            student.setFirstName(request.getFirstName());

        if(!StringUtil.isBlank(request.getLastName()))
            student.setLastName(request.getLastName());

        if(!StringUtil.isBlank(request.getOtherNames()))
            student.setOtherNames(request.getOtherNames());

        if(request.getGender()!=null)
            student.setGender(request.getGender());

        if(!StringUtil.isBlank(request.getDepartmentName()))
            student.setDepartmentName(request.getDepartmentName());

        if(request.getDateOfBirth()!=null)
            student.setDateOfBirth(request.getDateOfBirth());

        if(!StringUtil.isBlank(request.getCreatedBy()))
            student.setCreatedBy(request.getCreatedBy());

        // generate matric number in case

        student.setUpdatedDateTime(LocalDateTime.now());

        return studentRepository.save(student);
    }

    public boolean delete(String id) {
        Optional<Student> studentOptional = studentRepository.findByIdAndDeletedAtIsNull(id);

        if(!studentOptional.isPresent())
            throw new NotFoundException(String.format("Student with id [%s] not found", id));

        Student student = studentOptional.get();

        student.setDeletedAt(LocalDateTime.now());
        return true;
    }

    public Student fetch(String id) {
        Optional<Student> studentOptional =  studentRepository.findByIdAndDeletedAtIsNull(id);

        if(!studentOptional.isPresent())
            throw new NotFoundException(String.format("Student with id [%s] not found", id));

        return studentOptional.get();
    }

    public Page<Student> fetchAll(int limit, int size) {
        PageRequest pageRequest = PageRequest.of(limit, size);
        return studentRepository.findAllByIdAndDeletedAtIsNull(pageRequest);
    }

    public Page<Student> fetchStudentByFilters(HashMap<String, Object> filters, LocalDateTime from, LocalDateTime to, PageRequest pageRequest){
        return studentRepository.findByFilters(filters,from,to,pageRequest);
    }


    private Student getCustomerFromCustomerRequest(StudentRequest request) {
        Student customer = new Student();
        try {
            BeanUtils.copyProperties(customer, request);
        } catch (Exception exception) {
            logger.error("Error copying student request properties");
        }
        return customer;
    }
}
