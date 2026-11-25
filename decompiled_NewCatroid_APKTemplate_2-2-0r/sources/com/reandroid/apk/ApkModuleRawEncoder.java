package com.reandroid.apk;

import com.reandroid.archive.FileInputSource;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import java.io.File;

/* JADX INFO: loaded from: classes10.dex */
public class ApkModuleRawEncoder extends ApkModuleEncoder {
    private final ApkModule apkModule;
    private boolean mKeepOriginal;

    public ApkModuleRawEncoder() {
        ZipEntryMap zipEntryMap = new ZipEntryMap();
        this.apkModule = new ApkModule("encoded_raw" + String.valueOf(zipEntryMap.hashCode()).substring(1), zipEntryMap);
    }

    public void setKeepOriginal(boolean z) {
        this.mKeepOriginal = z;
    }

    public boolean isKeepOriginal() {
        return this.mKeepOriginal;
    }

    @Override // com.reandroid.apk.ApkModuleEncoder
    public void buildResources(File file) {
        addTableBlock(file);
    }

    @Override // com.reandroid.apk.ApkModuleEncoder, com.reandroid.apk.ApkModuleCoder
    public ApkModule getApkModule() {
        return this.apkModule;
    }

    @Override // com.reandroid.apk.ApkModuleEncoder
    public void encodeBinaryManifest(File file) {
        File file2 = new File(file, AndroidManifestBlock.FILE_NAME_BIN);
        if (!file2.isFile()) {
            file2 = new File(file, AndroidManifestBlock.FILE_NAME);
            if (!file2.isFile() || !AndroidManifestBlock.isResXmlBlock(file2)) {
                logMessage("WARN: Missing file " + AndroidManifestBlock.FILE_NAME_BIN);
                return;
            }
        }
        logMessage("Loaded binary manifest: " + file2.getName());
        FileInputSource fileInputSource = new FileInputSource(file2, AndroidManifestBlock.FILE_NAME);
        ApkModule apkModule = getApkModule();
        apkModule.add(fileInputSource);
        if (isKeepOriginal()) {
            apkModule.discardManifestChanges();
        }
    }

    private void addTableBlock(File file) {
        File file2 = new File(file, TableBlock.FILE_NAME);
        if (!file2.isFile()) {
            logMessage("Warn: File not found: " + TableBlock.FILE_NAME);
            return;
        }
        getApkModule().setLoadDefaultFramework(false);
        FileInputSource fileInputSource = new FileInputSource(file2, TableBlock.FILE_NAME);
        ApkModule apkModule = getApkModule();
        apkModule.add(fileInputSource);
        if (isKeepOriginal()) {
            apkModule.discardManifestChanges();
        }
    }

    @Override // com.reandroid.apk.ApkModuleEncoder
    void refreshTable() {
        if (isKeepOriginal() || getApkModule().getLoadedTableBlock() == null) {
            return;
        }
        super.refreshTable();
    }

    @Override // com.reandroid.apk.ApkModuleEncoder
    void onScanDirectoryComplete() {
        if (isKeepOriginal()) {
            ApkModule apkModule = getApkModule();
            apkModule.discardTableBlockChanges();
            apkModule.discardManifestChanges();
        }
    }
}
