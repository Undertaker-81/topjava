package ru.javawebinar.topjava.util.formater;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmitriy Panfilov
 * 24.11.2020
 */
public class MyFormatAnnotationFormatterTimeFactory implements AnnotationFormatterFactory<MyTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> setTypes = new HashSet<Class<?>>();
        setTypes.add(LocalTime.class);
        return setTypes;
    }

    @Override
    public Printer<?> getPrinter(MyTimeFormat myTimeFormat, Class<?> aClass) {
        return new MyTimeFormatter();
    }

    @Override
    public Parser<?> getParser(MyTimeFormat myTimeFormat, Class<?> aClass) {
        return new MyTimeFormatter();
    }
}
