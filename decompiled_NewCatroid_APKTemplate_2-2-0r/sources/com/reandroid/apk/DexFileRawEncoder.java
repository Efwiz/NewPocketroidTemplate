package com.reandroid.apk;

import com.reandroid.archive.FileInputSource;
import com.reandroid.archive.InputSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class DexFileRawEncoder implements DexEncoder {
    private APKLogger apkLogger;

    @Override // com.reandroid.apk.DexEncoder
    public List<InputSource> buildDexFiles(ApkModuleEncoder apkModuleEncoder, File file) throws IOException {
        File file2 = new File(file, DexFileInputSource.DEX_DIRECTORY_NAME);
        if (!file2.isDirectory()) {
            return new ArrayList();
        }
        List<File> listListDexFiles = DexFileInputSource.listDexFiles(file2);
        ArrayList arrayList = new ArrayList(listListDexFiles.size());
        if (listListDexFiles.size() == 0) {
            logMessage("WARN: No dex files found on: " + file2);
            return arrayList;
        }
        for (File file3 : listListDexFiles) {
            String name2 = file3.getName();
            FileInputSource fileInputSource = new FileInputSource(file3, name2);
            fileInputSource.setMethod(0);
            DexFileInputSource dexFileInputSource = new DexFileInputSource(name2, fileInputSource);
            dexFileInputSource.setMethod(0);
            arrayList.add(dexFileInputSource);
            logVerbose(name2);
        }
        return arrayList;
    }

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
