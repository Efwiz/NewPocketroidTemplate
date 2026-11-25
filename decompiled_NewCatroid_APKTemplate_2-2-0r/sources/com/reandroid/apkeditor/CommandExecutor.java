package com.reandroid.apkeditor;

import com.reandroid.apk.APKLogger;
import com.reandroid.apk.ApkModule;
import com.reandroid.apkeditor.Options;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.arsc.ARSCLib;
import com.reandroid.arsc.coder.xml.XmlCoderLogger;
import com.reandroid.commons.utils.log.Logger;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes10.dex */
public class CommandExecutor<T extends Options> implements APKLogger, XmlCoderLogger {
    private boolean mEnableLog = true;
    private String mLogTag;
    private final T options;

    public CommandExecutor(T t, String str) {
        this.options = t;
        this.mLogTag = str;
    }

    @Deprecated
    public void run() throws IOException {
        runCommand();
    }

    public void runCommand() throws IOException {
        throw new RuntimeException("Method not implemented");
    }

    protected void delete(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        logMessage("Delete: " + file);
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            Util.deleteDir(file);
        }
    }

    protected T getOptions() {
        return this.options;
    }

    protected void setLogTag(String str) {
        if (str == null) {
            str = "";
        }
        this.mLogTag = str;
    }

    public void setEnableLog(boolean z) {
        this.mEnableLog = z;
    }

    @Override // com.reandroid.apk.APKLogger
    public void logMessage(String str) {
        if (this.mEnableLog) {
            Logger.i(this.mLogTag + str);
        }
    }

    @Override // com.reandroid.apk.APKLogger
    public void logError(String str, Throwable th) {
        if (this.mEnableLog) {
            Logger.e(this.mLogTag + str, th);
        }
    }

    @Override // com.reandroid.apk.APKLogger
    public void logVerbose(String str) {
        if (this.mEnableLog) {
            Logger.sameLine(this.mLogTag + str);
        }
    }

    @Override // com.reandroid.arsc.coder.xml.XmlCoderLogger
    public void logMessage(String str, String str2) {
        if (this.mEnableLog) {
            Logger.sameLine(this.mLogTag + str2);
        }
    }

    @Override // com.reandroid.arsc.coder.xml.XmlCoderLogger
    public void logVerbose(String str, String str2) {
        if (this.mEnableLog) {
            Logger.sameLine(this.mLogTag + str2);
        }
    }

    public void logWarn(String str) {
        Logger.e(this.mLogTag + str);
    }

    public void logVersion() {
        logMessage("Using: " + APKEditor.getName() + " version " + APKEditor.getVersion() + ", " + ARSCLib.getName() + " version " + ARSCLib.getVersion());
    }

    protected static void clearMeta(ApkModule apkModule) {
        removeSignature(apkModule);
        apkModule.setApkSignatureBlock(null);
    }

    protected static void removeSignature(ApkModule apkModule) {
        ZipEntryMap zipEntryMap = apkModule.getZipEntryMap();
        zipEntryMap.removeIf(Pattern.compile("^META-INF/.+\\.(([MS]F)|(RSA))"));
        zipEntryMap.remove("stamp-cert-sha256");
    }
}
