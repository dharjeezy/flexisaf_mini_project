package com.example.flexisaf.util;

import java.time.LocalDate;
import java.time.Month;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Damilare
 * 24/11/2021
 **/
public class StringUtilTest {

    @Test
    public void test_isBlank() {
        assertEquals(true, StringUtil.isBlank(""));
        assertEquals(false, StringUtil.isBlank("no"));
    }

    @Test
    public void test_age() {
        assertEquals(21, DateUtil.age(LocalDate.of(2000, Month.JANUARY, 10)));
        assertEquals(61, DateUtil.age(LocalDate.of(1960, Month.JANUARY, 10)));
    }

}
