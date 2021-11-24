package com.example.flexisaf.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * Damilare
 * 24/11/2021
 **/
public class DateUtilTest {

    @Test
    public void test_isBirthDate() {
        assertEquals(true, DateUtil.isBirthDay(LocalDate.now()));
        assertEquals(false, DateUtil.isBirthDay(LocalDate.MAX));
    }

    @Test
    public void test_age() {
        assertEquals(true, DateUtil.isBirthDay(LocalDate.now()));
        assertEquals(false, DateUtil.isBirthDay(LocalDate.MAX));
    }
}
