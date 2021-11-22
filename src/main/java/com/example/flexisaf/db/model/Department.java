package com.example.flexisaf.db.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Damilare
 * 22/11/2021
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "flexisaf_department")
public class Department {
    @Id
    private String id;
    private String name;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    private LocalDateTime deletedAt;
}
