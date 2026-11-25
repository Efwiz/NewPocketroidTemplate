package com.reandroid.apk;

import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class DexFileRawDecoder implements DexDecoder {
    private APKLogger apkLogger;

    @Override // com.reandroid.apk.DexDecoder
    public void decodeDex(DexFileInputSource dexFileInputSource, File file) throws IOException {
        logVerbose(dexFileInputSource.getAlias());
        dexFileInputSource.write(dexFileInputSource.toFile(new File(file, DEX_DIRECTORY_NAME)));
    }

    public void setApkLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    private void logVerbose(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logVerbose(str);
        }
    }
}
