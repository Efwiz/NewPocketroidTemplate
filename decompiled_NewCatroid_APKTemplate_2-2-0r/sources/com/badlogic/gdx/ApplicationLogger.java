package com.badlogic.gdx;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/ApplicationLogger.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface ApplicationLogger {
    void debug(String str, String str2);

    void debug(String str, String str2, Throwable th);

    void error(String str, String str2);

    void error(String str, String str2, Throwable th);

    void log(String str, String str2);

    void log(String str, String str2, Throwable th);
}
