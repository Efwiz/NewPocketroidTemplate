package com.reandroid.apkeditor.refactor;

import com.reandroid.apk.ApkModule;
import com.reandroid.apkeditor.CommandExecutor;
import com.reandroid.apkeditor.Util;
import com.reandroid.arsc.chunk.TableBlock;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class Refactor extends CommandExecutor<RefactorOptions> {
    public Refactor(RefactorOptions refactorOptions) {
        super(refactorOptions, "[REFACTOR] ");
    }

    @Override // com.reandroid.apkeditor.CommandExecutor
    public void runCommand() throws IOException {
        RefactorOptions options = getOptions();
        delete(options.outputFile);
        logMessage("Loading apk: " + options.inputFile);
        ApkModule apkModuleLoadApkFile = ApkModule.loadApkFile(this, options.inputFile, new File[0]);
        if (!apkModuleLoadApkFile.hasTableBlock()) {
            throw new IOException("Don't have " + TableBlock.FILE_NAME);
        }
        String strIsProtected = Util.isProtected(apkModuleLoadApkFile);
        if (strIsProtected != null) {
            logMessage(options.inputFile.getAbsolutePath());
            logMessage(strIsProtected);
            return;
        }
        if (options.fixTypeNames) {
            TypeNameRefactor typeNameRefactor = new TypeNameRefactor(apkModuleLoadApkFile);
            typeNameRefactor.setApkLogger(this);
            typeNameRefactor.refactor();
        }
        if (options.publicXml != null) {
            logMessage("Renaming from: " + options.publicXml);
            new PublicXmlRefactor(apkModuleLoadApkFile, options.publicXml).refactor();
        } else {
            logMessage("Auto refactoring ...");
            new AutoRefactor(apkModuleLoadApkFile).refactor();
            logMessage("Auto renamed entries");
            new StringValueNameGenerator(apkModuleLoadApkFile.getTableBlock()).refactor();
        }
        if (options.cleanMeta) {
            logMessage("Clearing META-INF ...");
            clearMeta(apkModuleLoadApkFile);
        }
        Util.addApkEditorInfo(apkModuleLoadApkFile, getClass().getSimpleName());
        String strRefreshTable = apkModuleLoadApkFile.refreshTable();
        if (strRefreshTable != null) {
            logMessage(strRefreshTable);
        }
        logMessage("Writing apk ...");
        apkModuleLoadApkFile.writeApk(options.outputFile);
        logMessage("Saved to: " + options.outputFile);
    }
}
