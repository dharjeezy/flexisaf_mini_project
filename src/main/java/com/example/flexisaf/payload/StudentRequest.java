package com.example.flexisaf.payload;

import com.example.flexisaf.db.model.Department;
import com.example.flexisaf.db.types.Gender;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private LocalDateTime dateOfBirth;
    private String departmentName;
    private String matricNumber;
    private String createdBy;
}
