package com.reandroid.apkeditor.protect;

import com.reandroid.apk.ApkModule;
import com.reandroid.apkeditor.CommandExecutor;
import com.reandroid.apkeditor.Util;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class Protector extends CommandExecutor<ProtectorOptions> {
    private ApkModule mApkModule;

    public Protector(ProtectorOptions protectorOptions) {
        super(protectorOptions, "[PROTECT] ");
    }

    public ApkModule getApkModule() {
        return this.mApkModule;
    }

    public void setApkModule(ApkModule apkModule) {
        this.mApkModule = apkModule;
    }

    @Override // com.reandroid.apkeditor.CommandExecutor
    public ProtectorOptions getOptions() {
        return (ProtectorOptions) super.getOptions();
    }

    @Override // com.reandroid.apkeditor.CommandExecutor
    public void runCommand() throws IOException {
        ProtectorOptions options = getOptions();
        delete(options.outputFile);
        ApkModule apkModuleLoadApkFile = ApkModule.loadApkFile(this, options.inputFile, new File[0]);
        apkModuleLoadApkFile.setLoadDefaultFramework(false);
        String strIsProtected = Util.isProtected(apkModuleLoadApkFile);
        if (strIsProtected != null) {
            logMessage(options.inputFile.getAbsolutePath());
            logMessage(strIsProtected);
            return;
        }
        setApkModule(apkModuleLoadApkFile);
        new ManifestConfuser(this).confuse();
        new DirectoryConfuser(this).confuse();
        new FileNameConfuser(this).confuse();
        new TableConfuser(this).confuse();
        apkModuleLoadApkFile.getTableBlock().refresh();
        logMessage("Writing apk ...");
        apkModuleLoadApkFile.writeApk(options.outputFile);
        apkModuleLoadApkFile.close();
        logMessage("Saved to: " + options.outputFile);
        apkModuleLoadApkFile.close();
    }
}
