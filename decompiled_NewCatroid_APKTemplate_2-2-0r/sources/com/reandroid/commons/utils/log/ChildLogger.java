package com.reandroid.commons.utils.log;

/* JADX INFO: loaded from: classes10.dex */
public interface ChildLogger {
    void log(String str);

    void log(String str, String str2);

    void log(String str, Throwable th);

    void log(Throwable th);

    void logSameLine(String str);

    void logSameLineTimely(String str);
}
