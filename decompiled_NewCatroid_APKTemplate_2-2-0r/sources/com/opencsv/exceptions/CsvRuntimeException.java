package com.opencsv.exceptions;

/* JADX INFO: loaded from: classes10.dex */
public class CsvRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public CsvRuntimeException() {
    }

    public CsvRuntimeException(String str) {
        super(str);
    }

    public CsvRuntimeException(String str, Throwable th) {
        super(str, th);
    }
}
