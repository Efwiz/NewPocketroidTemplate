package org.xml.sax;

/* JADX INFO: loaded from: classes3.dex */
public class SAXException extends Exception {
    static final long serialVersionUID = 583241635256073760L;
    private Exception exception;

    public SAXException() {
        this.exception = null;
    }

    public SAXException(Exception exc) {
        this.exception = exc;
    }

    public SAXException(String str) {
        super(str);
        this.exception = null;
    }

    public SAXException(String str, Exception exc) {
        super(str);
        this.exception = exc;
    }

    public Exception getException() {
        return this.exception;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        Exception exc;
        String message = super.getMessage();
        return (message != null || (exc = this.exception) == null) ? message : exc.getMessage();
    }

    @Override // java.lang.Throwable
    public String toString() {
        Exception exc = this.exception;
        return exc != null ? exc.toString() : super.toString();
    }
}
