package com.example.flexisaf.db.model;

import com.example.flexisaf.db.types.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

/**
 * Damilare
 * 22/11/2021
 **/
@Getter
@Setter
@Document(collection = "flexisaf_student")
public class Student {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String otherNames;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String departmentName;
    private String matricNumber;
    private String createdBy;

    private String email;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    private LocalDateTime deletedAt;
}
