package org.apache.commons.beanutils;

/* JADX INFO: loaded from: classes12.dex */
public class ConversionException extends RuntimeException {
    protected Throwable cause;

    public ConversionException(String str) {
        super(str);
        this.cause = null;
    }

    public ConversionException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    public ConversionException(Throwable th) {
        super(th.getMessage());
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
