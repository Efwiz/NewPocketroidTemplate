package com.reandroid.apk;

/* JADX INFO: loaded from: classes10.dex */
public interface APKLogger {
    void logError(String str, Throwable th);

    void logMessage(String str);

    void logVerbose(String str);
}
