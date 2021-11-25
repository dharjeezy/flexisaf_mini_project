package com.example.flexisaf.service.implementer;

import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.model.Student;
import com.example.flexisaf.db.repository.StudentRepository;
import com.example.flexisaf.exception.NotAcceptableException;
import com.example.flexisaf.exception.NotFoundException;
import com.example.flexisaf.payload.StudentRequest;
import com.example.flexisaf.service.contracts.DepartmentService;
import com.example.flexisaf.service.contracts.StudentService;
import com.example.flexisaf.service.notification.EmailService;
import com.example.flexisaf.service.notification.model.EmailModel;
import com.example.flexisaf.util.DateUtil;
import com.example.flexisaf.util.StringUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Damilare
 * 22/11/2021
 **/
@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    StudentRepository studentRepository;
    DepartmentService departmentService;
    EmailService emailService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, DepartmentService departmentService,
                              EmailService emailService) {
        this.studentRepository = studentRepository;
        this.departmentService = departmentService;
        this.emailService = emailService;
    }

    public Student create(StudentRequest request) throws NotFoundException{
        // check department service if department name exists, will thow an exception if department name not found
       departmentService.fetch(request.getDepartmentName());


        // check if age is valid
        if(DateUtil.age(request.getDateOfBirth() ) < 18 && DateUtil.age(request.getDateOfBirth() ) > 25)
            throw new NotAcceptableException("Student must be 18 years and below 25 years of age");

        Student student = getStudentFromStudentRequest(request);

        // generate and set matric number
        student.setMatricNumber(generateMatricNumber());

        student.setCreatedDateTime(LocalDateTime.now());

        student = studentRepository.save(student);


        return student;
    }

    private String generateMatricNumber() {
        long currentNum = studentRepository.count() + 1;

        String prepend = "FLEXISAF/00";

        return String.format("%s%s", prepend, currentNum);
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

        student.setUpdatedDateTime(LocalDateTime.now());

        return studentRepository.save(student);
    }

    public boolean delete(String id) {
        Optional<Student> studentOptional = studentRepository.findByIdAndDeletedAtIsNull(id);

        if(!studentOptional.isPresent())
            throw new NotFoundException(String.format("Student with id [%s] not found", id));

        Student student = studentOptional.get();

        student.setDeletedAt(LocalDateTime.now());

        studentRepository.save(student);
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
        return studentRepository.findAllByDeletedAtIsNull(pageRequest);
    }

    public Page<Student> fetchStudentByFilters(HashMap<String, Object> filters, LocalDateTime from, LocalDateTime to, PageRequest pageRequest){
        return studentRepository.findByFilters(filters,from,to,pageRequest);
    }

    // Should run 6 AM everyday
    @Scheduled(cron = "0 6 * * * *", zone = "Africa/Lagos")
    public void sendBirthdayMessage() {
        List<Student> students = studentRepository.findAll();
        for(Student student:students){
            if(!DateUtil.isBirthDay(student.getDateOfBirth()))
                continue;

            logger.info("Sending birthday message to email for student [{}]", student.getMatricNumber());

            try {
                EmailModel emailModel = new EmailModel();
                emailModel.setRecipients(new String[]{student.getEmail()});
                emailModel.setSubject("Happy Birthday!");
                emailModel.setTemplate("birthday-email.ftl");

                Map emailTemplateMap = new HashMap();
                emailTemplateMap.put("firstName", student.getFirstName());
                emailService.sendEmail(emailModel);
            } catch (Exception exception){
                logger.info("An error occured while sending birthday email", exception.getMessage());
            }
        }
    }


    private Student getStudentFromStudentRequest(StudentRequest request) {
        Student department = new Student();
        try {
            BeanUtils.copyProperties(department, request);
        } catch (Exception exception) {
            logger.error("Error copying department request properties");
        }
        return department;
    }
}
