package com.opencsv.validators;

import com.opencsv.exceptions.CsvValidationException;

/* JADX INFO: loaded from: classes10.dex */
public interface RowValidator {
    boolean isValid(String[] strArr);

    void validate(String[] strArr) throws CsvValidationException;
}
