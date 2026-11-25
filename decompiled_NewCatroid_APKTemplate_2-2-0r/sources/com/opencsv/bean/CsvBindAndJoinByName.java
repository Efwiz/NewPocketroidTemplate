package com.opencsv.bean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.commons.collections4.MultiValuedMap;

/* JADX INFO: loaded from: classes10.dex */
@Target({ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvBindAndJoinByName {
    String capture() default "";

    String column() default "";

    Class<? extends AbstractCsvConverter> converter() default AbstractCsvConverter.class;

    Class<?> elementType();

    String format() default "";

    String locale() default "";

    Class<? extends MultiValuedMap> mapType() default MultiValuedMap.class;

    boolean required() default false;

    String writeLocale() default "";

    boolean writeLocaleEqualsReadLocale() default true;
}
