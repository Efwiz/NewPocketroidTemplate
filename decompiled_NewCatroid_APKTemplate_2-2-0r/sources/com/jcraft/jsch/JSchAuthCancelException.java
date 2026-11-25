package com.jcraft.jsch;

/* JADX INFO: loaded from: classes10.dex */
class JSchAuthCancelException extends JSchException {
    String method;

    JSchAuthCancelException() {
    }

    JSchAuthCancelException(String str) {
        super(str);
        this.method = str;
    }

    public String getMethod() {
        return this.method;
    }
}
