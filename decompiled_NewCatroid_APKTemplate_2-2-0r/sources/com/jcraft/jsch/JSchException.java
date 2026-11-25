package com.jcraft.jsch;

/* JADX INFO: loaded from: classes10.dex */
public class JSchException extends Exception {
    private Throwable cause;

    public JSchException() {
        this.cause = null;
    }

    public JSchException(String str) {
        super(str);
        this.cause = null;
    }

    public JSchException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
