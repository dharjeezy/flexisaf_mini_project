package com.example.flexisaf.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Damilare
 * 22/11/2021
 **/
public class DateUtil {

    public static boolean isBirthDate(final Date dob) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar dobCalendar = Calendar.getInstance();

        currentCalendar.setTime(new Date());
        dobCalendar.setTime(dob);

        if( currentCalendar.get(Calendar.MONTH) == dobCalendar.get(Calendar.MONTH)
                &&currentCalendar.get(Calendar.DAY_OF_MONTH) == dobCalendar.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }

        return false;
    }
}
