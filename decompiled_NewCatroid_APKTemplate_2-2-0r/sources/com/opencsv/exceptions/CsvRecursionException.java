package com.opencsv.exceptions;

/* JADX INFO: loaded from: classes10.dex */
public class CsvRecursionException extends CsvRuntimeException {
    private static final long serialVersionUID = 1;
    private final Class<?> offendingType;

    public CsvRecursionException(String str, Class<?> cls) {
        super(str);
        this.offendingType = cls;
    }

    public Class<?> getOffendingType() {
        return this.offendingType;
    }
}
