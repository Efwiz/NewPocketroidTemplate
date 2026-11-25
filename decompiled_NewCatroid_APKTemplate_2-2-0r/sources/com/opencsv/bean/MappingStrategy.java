package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import org.apache.commons.collections4.MultiValuedMap;

/* JADX INFO: loaded from: classes10.dex */
public interface MappingStrategy<T> {
    void captureHeader(CSVReader cSVReader) throws CsvRequiredFieldEmptyException, IOException;

    String[] generateHeader(T t) throws CsvRequiredFieldEmptyException;

    @Deprecated
    default boolean isAnnotationDriven() {
        return false;
    }

    T populateNewBean(String[] strArr) throws CsvBeanIntrospectionException, CsvValidationException, CsvRequiredFieldEmptyException, CsvConstraintViolationException, CsvDataTypeMismatchException;

    default void setErrorLocale(Locale locale) {
    }

    void setType(Class<? extends T> cls) throws CsvBadConverterException;

    String[] transmuteBean(T t) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

    default void ignoreFields(MultiValuedMap<Class<?>, Field> multiValuedMap) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }
}
