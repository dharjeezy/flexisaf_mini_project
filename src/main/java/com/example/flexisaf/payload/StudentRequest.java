package com.example.flexisaf.payload;

import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.types.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Damilare
 * 22/11/2021
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String otherNames;
    private Gender gender;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String departmentName;
    private String matricNumber;
    private String createdBy;

    private String email;
}
