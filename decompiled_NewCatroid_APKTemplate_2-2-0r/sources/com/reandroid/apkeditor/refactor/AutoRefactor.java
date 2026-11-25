package com.reandroid.apkeditor.refactor;

import com.reandroid.apk.APKLogger;
import com.reandroid.apk.ApkModule;
import com.reandroid.apk.ResFile;
import com.reandroid.identifiers.PackageIdentifier;
import com.reandroid.identifiers.TableIdentifier;
import com.reandroid.identifiers.TypeIdentifier;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class AutoRefactor {
    private APKLogger apkLogger;
    private final ApkModule mApkModule;

    public AutoRefactor(ApkModule apkModule) {
        this.mApkModule = apkModule;
        this.apkLogger = apkModule.getApkLogger();
    }

    public void refactor() throws IOException {
        refactorResourceNames();
        refactorFilePaths();
    }

    public int refactorFilePaths() {
        logMessage("Validating file paths ...");
        int i = 0;
        for (ResFile resFile : this.mApkModule.listResFiles()) {
            String str = "res/" + resFile.buildPath();
            if (!str.equals(resFile.getFilePath())) {
                resFile.setFilePath(str);
                i++;
            }
        }
        return i;
    }

    private void refactorResourceNames() {
        logMessage("Validating resource names ...");
        TableIdentifier tableIdentifier = new TableIdentifier();
        tableIdentifier.load(this.mApkModule.getTableBlock());
        String strValidateSpecNames = tableIdentifier.validateSpecNames();
        if (strValidateSpecNames == null) {
            logMessage("All resource names are valid");
            return;
        }
        Iterator<PackageIdentifier> it = tableIdentifier.getPackages().iterator();
        while (it.hasNext()) {
            Iterator it2 = it.next().list().iterator();
            while (it2.hasNext()) {
                new EntryRefactor((TypeIdentifier) it2.next()).refactorAll();
            }
        }
        logMessage(strValidateSpecNames);
    }

    public void setApkLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
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
