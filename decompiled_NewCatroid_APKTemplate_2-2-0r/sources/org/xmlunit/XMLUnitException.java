package org.xmlunit;

/* JADX INFO: loaded from: classes3.dex */
public class XMLUnitException extends RuntimeException {
    private static final long serialVersionUID = 673558045568231955L;

    public XMLUnitException(String str, Throwable th) {
        super(str, th);
    }

    public XMLUnitException(String str) {
        this(str, null);
    }

    public XMLUnitException(Throwable th) {
        this(th != null ? th.getMessage() : null, th);
    }
}
