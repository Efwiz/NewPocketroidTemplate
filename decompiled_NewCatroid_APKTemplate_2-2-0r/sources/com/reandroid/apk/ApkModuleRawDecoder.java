package com.reandroid.apk;

import com.reandroid.archive.InputSource;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class ApkModuleRawDecoder extends ApkModuleDecoder {
    public ApkModuleRawDecoder(ApkModule apkModule) {
        super(apkModule);
        apkModule.setLoadDefaultFramework(false);
    }

    @Override // com.reandroid.apk.ApkModuleDecoder
    public void decodeResourceTable(File file) throws IOException {
        ApkModule apkModule = getApkModule();
        apkModule.discardTableBlockChanges();
        InputSource tableOriginalSource = apkModule.getTableOriginalSource();
        if (tableOriginalSource == null) {
            logMessage("File NOT found: " + TableBlock.FILE_NAME);
        } else {
            tableOriginalSource.write(new File(file, TableBlock.FILE_NAME));
            addDecodedPath(TableBlock.FILE_NAME);
        }
    }

    @Override // com.reandroid.apk.ApkModuleDecoder
    void decodeAndroidManifest(File file) throws IOException {
        ApkModule apkModule = getApkModule();
        apkModule.discardManifestChanges();
        InputSource manifestOriginalSource = apkModule.getManifestOriginalSource();
        if (manifestOriginalSource == null) {
            logMessage("File NOT found: " + AndroidManifestBlock.FILE_NAME);
        } else {
            manifestOriginalSource.write(new File(file, AndroidManifestBlock.FILE_NAME_BIN));
            addDecodedPath(AndroidManifestBlock.FILE_NAME);
        }
    }
}
