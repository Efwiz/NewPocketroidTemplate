package com.reandroid.apkeditor.refactor;

import com.reandroid.apk.APKLogger;
import com.reandroid.apk.ApkModule;
import com.reandroid.identifiers.TableIdentifier;
import java.io.File;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes10.dex */
public class PublicXmlRefactor {
    private APKLogger apkLogger;
    private final ApkModule apkModule;
    private final File pubXmlFile;

    public PublicXmlRefactor(ApkModule apkModule, File file) {
        this.apkModule = apkModule;
        this.pubXmlFile = file;
        this.apkLogger = apkModule.getApkLogger();
    }

    public void refactor() throws IOException {
        logMessage("Loading: " + this.pubXmlFile);
        TableIdentifier tableIdentifier = new TableIdentifier();
        try {
            tableIdentifier.loadPublicXml(this.pubXmlFile);
            logMessage("Applying from public xml ...");
            tableIdentifier.setTableBlock(this.apkModule.getTableBlock());
            int iRenameSpecs = tableIdentifier.renameSpecs();
            if (iRenameSpecs == 0) {
                logMessage("Nothing renamed !");
            }
            logMessage("Renamed: " + iRenameSpecs);
            this.apkModule.getTableBlock().removeUnusedSpecs();
        } catch (XmlPullParserException e) {
            throw new IOException(e);
        }
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
