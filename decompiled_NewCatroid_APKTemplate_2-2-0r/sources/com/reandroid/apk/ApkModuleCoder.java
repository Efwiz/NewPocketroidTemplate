package com.reandroid.apk;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ApkModuleCoder {
    public static final String ROOT_DIRECTORY_NAME = "root";
    public static final String SIGNATURE_DIRECTORY_NAME = "signatures";
    private APKLogger apkLogger;

    public abstract ApkModule getApkModule();

    public void setApkLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    APKLogger getApkLogger() {
        return this.apkLogger;
    }

    void logMessage(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(str);
        }
    }

    void logError(String str, Throwable th) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            if (str == null && th == null) {
                return;
            }
            aPKLogger.logError(str, th);
        }
    }

    void logVerbose(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logVerbose(str);
        }
    }
}
