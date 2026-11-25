package com.opencsv.bean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes10.dex */
@Target({ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvDate {
    String chronology() default "ISO";

    String value() default "yyyyMMdd'T'HHmmss";

    String writeChronology() default "ISO";

    boolean writeChronologyEqualsReadChronology() default true;

    String writeFormat() default "yyyyMMdd'T'HHmmss";

    boolean writeFormatEqualsReadFormat() default true;
}
