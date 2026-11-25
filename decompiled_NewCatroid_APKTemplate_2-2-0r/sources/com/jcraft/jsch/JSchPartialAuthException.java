package com.jcraft.jsch;

/* JADX INFO: loaded from: classes10.dex */
class JSchPartialAuthException extends JSchException {
    String methods;

    public JSchPartialAuthException() {
    }

    public JSchPartialAuthException(String str) {
        super(str);
        this.methods = str;
    }

    public String getMethods() {
        return this.methods;
    }
}
