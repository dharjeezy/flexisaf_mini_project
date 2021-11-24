package com.example.flexisaf.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * Damilare
 * 22/11/2021
 **/
public class DateUtil {

    public static boolean isBirthDate(LocalDate dateOfBirth) {
        int birthDate = dateOfBirth.getDayOfMonth();
        Month birthMonth = dateOfBirth.getMonth();

        // get current date
        LocalDate currentDate = LocalDate.now();

        // get current date and month
        int date = currentDate.getDayOfMonth();
        Month month = currentDate.getMonth();

        if(date == birthDate && month == birthMonth)
            return true;
        else
            return false;

    }

    public static long age(LocalDate date) {
        return LocalDate.from(date).until(LocalDate.now(), ChronoUnit.YEARS);
    }
}
