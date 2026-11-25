package com.opencsv.validators;

import com.opencsv.exceptions.CsvValidationException;

/* JADX INFO: loaded from: classes10.dex */
public interface LineValidator {
    boolean isValid(String str);

    void validate(String str) throws CsvValidationException;
}
