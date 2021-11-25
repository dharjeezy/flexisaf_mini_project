package com.example.flexisaf.service.implementer;

import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.model.Student;
import com.example.flexisaf.db.repository.DepartmentRepository;
import com.example.flexisaf.exception.NotAcceptableException;
import com.example.flexisaf.exception.NotFoundException;
import com.example.flexisaf.payload.DepartmentRequest;
import com.example.flexisaf.payload.StudentRequest;
import com.example.flexisaf.service.contracts.DepartmentService;
import com.example.flexisaf.util.StringUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Damilare
 * 22/11/2021
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department create(DepartmentRequest request) {
        Optional<Department> departmentOptional = departmentRepository.findByNameAndDeletedAtIsNull(request.getName());

        if(departmentOptional.isPresent())
            throw new NotAcceptableException("Department with name already exists");

        Department department = getDepartmentFromDepartmentRequest(request);
        department.setCreatedDateTime(LocalDateTime.now());
        return departmentRepository.save(department);
    }

    public Department update(String name, DepartmentRequest request) {
        Optional<Department> axistingDepartmentOptional = departmentRepository.findByNameAndDeletedAtIsNull(name);

        if(!axistingDepartmentOptional.isPresent())
            throw new NotFoundException("Department with name does not exist");

        Department department = axistingDepartmentOptional.get();

        if(!StringUtil.isBlank(request.getName())) {
            Optional<Department> departmentOptional = departmentRepository.findByNameAndDeletedAtIsNull(request.getName());

            if(departmentOptional.isPresent())
                throw new NotAcceptableException("Department name already exists in the system, please change it");

            department.setName(request.getName());
        }

        department.setUpdatedDateTime(LocalDateTime.now());

        return departmentRepository.save(department);

    }

    public boolean delete(String name) {
        Optional<Department> departmentOptional = departmentRepository.findByNameAndDeletedAtIsNull(name);

        if(!departmentOptional.isPresent())
            throw new NotFoundException(String.format("Department with name [%s] not found", name));

        Department department = departmentOptional.get();

        department.setDeletedAt(LocalDateTime.now());

        departmentRepository.save(department);
        return true;
    }

    public Department fetch(String name) {
        Optional<Department> department =  departmentRepository.findByNameAndDeletedAtIsNull(name);

        if(!department.isPresent())
            throw new NotFoundException(String.format("Department with name [%s] not found", name));

        return department.get();
    }

    public Page<Department> fetchAll(int limit, int size) {
        PageRequest pageRequest = PageRequest.of(limit, size);
        return departmentRepository.findAllByDeletedAtIsNull(pageRequest);
    }

    private Department getDepartmentFromDepartmentRequest(DepartmentRequest request) {
        Department department = new Department();
        try {
            BeanUtils.copyProperties(department, request);
        } catch (Exception exception) {
            logger.error("Error copying student request properties");
        }
        return department;
    }
}
