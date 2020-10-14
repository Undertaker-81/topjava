package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

//    public static boolean isBetweenHalfOpenTime(LocalTime lt, LocalTime startTime, LocalTime endTime) {
//
//        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
//    }
//    public static boolean isBetweenHalfOpenDate(LocalDate lt, LocalDate startTime, LocalDate endTime) {
//
//        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
//    }
    public static <E extends Comparable<E>, T extends E> boolean isBetweenHalfOpenDateTime(T lt, T startTime, T endTime) {

        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;

    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

