package com.beust.jcommander;

/* JADX INFO: loaded from: classes8.dex */
public class ParameterException extends RuntimeException {
    public ParameterException(Throwable th) {
        super(th);
    }

    public ParameterException(String str) {
        super(str);
    }

    public ParameterException(String str, Throwable th) {
        super(str, th);
    }
}
