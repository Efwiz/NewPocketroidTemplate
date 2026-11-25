package com.reandroid.apkeditor.compile;

import com.reandroid.apk.ApkModule;
import com.reandroid.apk.ApkModuleJsonEncoder;
import com.reandroid.apk.ApkModuleRawEncoder;
import com.reandroid.apk.ApkModuleXmlEncoder;
import com.reandroid.apkeditor.CommandExecutor;
import com.reandroid.apkeditor.Options;
import com.reandroid.apkeditor.smali.SmaliCompiler;
import com.reandroid.archive.ArchiveFile;
import com.reandroid.archive.block.ApkSignatureBlock;
import com.reandroid.archive.writer.ApkFileWriter;
import com.reandroid.arsc.coder.xml.XmlCoder;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class Builder extends CommandExecutor<BuildOptions> {
    public Builder(BuildOptions buildOptions) {
        super(buildOptions, "[BUILD] ");
    }

    @Override // com.reandroid.apkeditor.CommandExecutor
    public void runCommand() throws IOException {
        BuildOptions options = getOptions();
        delete(options.outputFile);
        String str = options.type;
        if (Options.TYPE_SIG.equals(str)) {
            restoreSignatures();
            return;
        }
        if (Options.TYPE_RAW.equals(str)) {
            buildRaw();
        } else if ("xml".equals(str)) {
            buildXml();
        } else if (Options.TYPE_JSON.equals(str)) {
            buildJson();
        }
    }

    private void restoreSignatures() throws IOException {
        logMessage("Restoring signatures ...");
        BuildOptions options = getOptions();
        ApkFileWriter apkFileWriter = new ApkFileWriter(options.outputFile, new ArchiveFile(options.inputFile).getInputSources());
        apkFileWriter.setAPKLogger(this);
        ApkSignatureBlock apkSignatureBlock = new ApkSignatureBlock();
        apkSignatureBlock.scanSplitFiles(options.signaturesDirectory);
        apkFileWriter.setApkSignatureBlock(apkSignatureBlock);
        logMessage("Writing apk...");
        apkFileWriter.write();
        logMessage("Saved to: " + options.outputFile);
        apkFileWriter.close();
    }

    public void buildJson() throws IOException {
        logMessage("Scanning JSON directory ...");
        ApkModuleJsonEncoder apkModuleJsonEncoder = new ApkModuleJsonEncoder();
        apkModuleJsonEncoder.setApkLogger(this);
        BuildOptions options = getOptions();
        SmaliCompiler smaliCompiler = new SmaliCompiler(options.noCache);
        smaliCompiler.setApkLogger(this);
        apkModuleJsonEncoder.setDexEncoder(smaliCompiler);
        apkModuleJsonEncoder.scanDirectory(options.inputFile);
        ApkModule apkModule = apkModuleJsonEncoder.getApkModule();
        apkModule.setAPKLogger(this);
        if (options.resDirName != null) {
            logMessage("Renaming resources root dir: " + options.resDirName);
            apkModule.setResourcesRootDir(options.resDirName);
        }
        if (options.validateResDir) {
            logMessage("Validating resources dir ...");
            apkModule.validateResourcesDir();
        }
        logMessage("Writing apk...");
        apkModule.getZipEntryMap().autoSortApkFiles();
        apkModule.writeApk(options.outputFile, null);
        apkModule.close();
        logMessage("Saved to: " + options.outputFile);
    }

    public void buildXml() throws IOException {
        logMessage("Scanning XML directory ...");
        XmlCoder.getInstance().getSetting().setLogger(this);
        ApkModuleXmlEncoder apkModuleXmlEncoder = new ApkModuleXmlEncoder();
        apkModuleXmlEncoder.setApkLogger(this);
        BuildOptions options = getOptions();
        SmaliCompiler smaliCompiler = new SmaliCompiler(options.noCache);
        smaliCompiler.setApkLogger(this);
        apkModuleXmlEncoder.setDexEncoder(smaliCompiler);
        ApkModule apkModule = apkModuleXmlEncoder.getApkModule();
        apkModule.setAPKLogger(this);
        apkModule.setPreferredFramework(options.frameworkVersion);
        Iterator<File> it = options.frameworks.iterator();
        while (it.hasNext()) {
            apkModule.addExternalFramework(it.next());
        }
        apkModuleXmlEncoder.scanDirectory(options.inputFile);
        ApkModule apkModule2 = apkModuleXmlEncoder.getApkModule();
        logMessage("Writing apk...");
        apkModule2.writeApk(options.outputFile, null);
        apkModule2.close();
        logMessage("Saved to: " + options.outputFile);
    }

    public void buildRaw() throws IOException {
        logMessage("Scanning Raw directory ...");
        ApkModuleRawEncoder apkModuleRawEncoder = new ApkModuleRawEncoder();
        apkModuleRawEncoder.setApkLogger(this);
        BuildOptions options = getOptions();
        if (!options.validateResDir && options.resDirName == null) {
            apkModuleRawEncoder.setKeepOriginal(true);
            logMessage("Keep original binaries");
        }
        SmaliCompiler smaliCompiler = new SmaliCompiler(options.noCache);
        smaliCompiler.setApkLogger(this);
        apkModuleRawEncoder.setDexEncoder(smaliCompiler);
        ApkModule apkModule = apkModuleRawEncoder.getApkModule();
        apkModule.setAPKLogger(this);
        apkModule.setPreferredFramework(options.frameworkVersion);
        Iterator<File> it = options.frameworks.iterator();
        while (it.hasNext()) {
            apkModule.addExternalFramework(it.next());
        }
        apkModuleRawEncoder.scanDirectory(options.inputFile);
        ApkModule apkModule2 = apkModuleRawEncoder.getApkModule();
        logMessage("Writing apk...");
        apkModule2.writeApk(options.outputFile, null);
        apkModule2.close();
        logMessage("Saved to: " + options.outputFile);
    }
}
