package com.example.flexisaf.service.notification.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * Damilare
 * 24/11/2021
 **/
@Getter
@Setter
public class EmailModel {
    private String[] recipients;
    private String subject;
    private Map model = new HashMap();
    private String template;
}
