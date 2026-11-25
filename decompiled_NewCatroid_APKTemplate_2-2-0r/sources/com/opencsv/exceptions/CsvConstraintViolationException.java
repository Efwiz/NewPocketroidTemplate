package com.opencsv.exceptions;

/* JADX INFO: loaded from: classes10.dex */
public class CsvConstraintViolationException extends CsvException {
    private static final long serialVersionUID = 1;
    private final transient Object sourceObject;

    public CsvConstraintViolationException() {
        this.sourceObject = null;
    }

    public CsvConstraintViolationException(Object obj) {
        this.sourceObject = obj;
    }

    public CsvConstraintViolationException(String str) {
        super(str);
        this.sourceObject = null;
    }

    public CsvConstraintViolationException(Object obj, String str) {
        super(str);
        this.sourceObject = obj;
    }

    public Object getSourceObject() {
        return this.sourceObject;
    }
}
