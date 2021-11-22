package com.example.flexisaf.util;

/**
 * Damilare
 * 22/11/2021
 **/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    private static final Random randomGenerator = new Random();
    public static boolean isBlank(final String string) {
        return string == null || string.trim().isEmpty();
    }
}

