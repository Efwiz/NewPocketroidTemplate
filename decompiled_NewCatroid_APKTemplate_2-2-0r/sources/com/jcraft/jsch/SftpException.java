package com.jcraft.jsch;

/* JADX INFO: loaded from: classes10.dex */
public class SftpException extends Exception {
    private Throwable cause;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public int f207id;

    public SftpException(int i, String str) {
        super(str);
        this.cause = null;
        this.f207id = i;
    }

    public SftpException(int i, String str, Throwable th) {
        super(str);
        this.f207id = i;
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return this.f207id + ": " + getMessage();
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
