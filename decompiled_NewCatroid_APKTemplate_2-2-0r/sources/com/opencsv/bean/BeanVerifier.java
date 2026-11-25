package com.opencsv.bean;

import com.opencsv.exceptions.CsvConstraintViolationException;

/* JADX INFO: loaded from: classes10.dex */
public interface BeanVerifier<T> {
    boolean verifyBean(T t) throws CsvConstraintViolationException;
}
