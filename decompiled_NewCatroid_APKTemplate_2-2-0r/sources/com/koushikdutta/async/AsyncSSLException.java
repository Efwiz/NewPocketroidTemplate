package com.koushikdutta.async;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncSSLException extends Exception {
    private boolean mIgnore;

    public AsyncSSLException(Throwable th) {
        super("Peer not trusted by any of the system trust managers.", th);
        this.mIgnore = false;
    }

    public void setIgnore(boolean z) {
        this.mIgnore = z;
    }

    public boolean getIgnore() {
        return this.mIgnore;
    }
}
