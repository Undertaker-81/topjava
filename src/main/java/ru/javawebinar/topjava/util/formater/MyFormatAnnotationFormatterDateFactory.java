package ru.javawebinar.topjava.util.formater;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmitriy Panfilov
 * 24.11.2020
 */
public class MyFormatAnnotationFormatterDateFactory implements AnnotationFormatterFactory<MyDateFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> setTypes = new HashSet<Class<?>>();
        setTypes.add(LocalDate.class);
        return setTypes;
    }

    @Override
    public Printer<?> getPrinter(MyDateFormat myDateFormat, Class<?> aClass) {
        return new MyDateFormatter();
    }

    @Override
    public Parser<?> getParser(MyDateFormat myDateFormat, Class<?> aClass) {
        return new MyDateFormatter();
    }
}
