package ru.javawebinar.topjava.service;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.TimeUnit;

/**
 * @author Dmitriy Panfilov
 * 25.10.2020
 */
public class MyTestWatcher extends Stopwatch {

    private static final Logger log = LoggerFactory.getLogger(MyTestWatcher.class);

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
//        System.out.println(String.format("Test %s %s, spent %d microseconds",
//                testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
        log.info(String.format("Test %s %s, spent %d milliseconds",
                testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
    }
}
