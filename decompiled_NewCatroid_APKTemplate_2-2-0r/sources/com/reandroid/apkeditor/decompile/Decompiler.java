package com.reandroid.apkeditor.decompile;

import com.reandroid.apk.ApkModule;
import com.reandroid.apk.ApkModuleDecoder;
import com.reandroid.apk.ApkModuleJsonDecoder;
import com.reandroid.apk.ApkModuleRawDecoder;
import com.reandroid.apk.ApkModuleXmlDecoder;
import com.reandroid.apkeditor.CommandExecutor;
import com.reandroid.apkeditor.Options;
import com.reandroid.apkeditor.Util;
import com.reandroid.apkeditor.smali.SmaliDecompiler;
import com.reandroid.archive.ArchiveFile;
import com.reandroid.archive.block.ApkSignatureBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.coder.xml.XmlCoder;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class Decompiler extends CommandExecutor<DecompileOptions> {
    public Decompiler(DecompileOptions decompileOptions) {
        super(decompileOptions, "[DECOMPILE] ");
    }

    @Override // com.reandroid.apkeditor.CommandExecutor
    public void runCommand() throws IOException {
        DecompileOptions options = getOptions();
        delete(options.outputFile);
        logMessage("Loading ...");
        ApkModule apkModuleLoadApkFile = ApkModule.loadApkFile(this, options.inputFile, options.getFrameworks());
        apkModuleLoadApkFile.setPreferredFramework(options.frameworkVersion);
        if (options.signaturesDirectory != null) {
            dumpSignatureBlock();
            return;
        }
        String strIsProtected = Util.isProtected(apkModuleLoadApkFile);
        if (strIsProtected != null) {
            logMessage(options.inputFile.getAbsolutePath());
            logMessage(strIsProtected);
            return;
        }
        if (options.resDirName != null) {
            logMessage("Renaming resources root dir: " + options.resDirName);
            apkModuleLoadApkFile.setResourcesRootDir(options.resDirName);
        }
        if (options.validateResDir) {
            logMessage("Validating resources dir ...");
            apkModuleLoadApkFile.validateResourcesDir();
        }
        logMessage("Decompiling to " + options.type + " ...");
        getApkModuleDecoder(apkModuleLoadApkFile).decode(options.outputFile);
        logMessage("Saved to: " + options.outputFile);
    }

    private ApkModuleDecoder getApkModuleDecoder(ApkModule apkModule) {
        ApkModuleDecoder apkModuleDecoder;
        DecompileOptions options = getOptions();
        if (Options.TYPE_JSON.equals(options.type)) {
            ApkModuleJsonDecoder apkModuleJsonDecoder = new ApkModuleJsonDecoder(apkModule, options.splitJson);
            apkModuleJsonDecoder.setDexDecoder(getSmaliDecompiler(apkModule.getTableBlock()));
            apkModuleDecoder = apkModuleJsonDecoder;
        } else if (Options.TYPE_RAW.equals(options.type)) {
            ApkModuleRawDecoder apkModuleRawDecoder = new ApkModuleRawDecoder(apkModule);
            apkModuleRawDecoder.setDexDecoder(getSmaliDecompiler(apkModule.getTableBlock()));
            apkModuleDecoder = apkModuleRawDecoder;
        } else {
            ApkModuleXmlDecoder apkModuleXmlDecoder = new ApkModuleXmlDecoder(apkModule);
            apkModuleXmlDecoder.setKeepResPath(options.keepResPath);
            XmlCoder.getInstance().getSetting().setLogger(this);
            apkModuleDecoder = apkModuleXmlDecoder;
        }
        apkModuleDecoder.sanitizeFilePaths();
        apkModuleDecoder.setDexDecoder(getSmaliDecompiler(apkModule.getTableBlock()));
        return apkModuleDecoder;
    }

    private SmaliDecompiler getSmaliDecompiler(TableBlock tableBlock) {
        if (getOptions().dex) {
            return null;
        }
        SmaliDecompiler smaliDecompiler = new SmaliDecompiler(tableBlock, getOptions());
        smaliDecompiler.setApkLogger(this);
        return smaliDecompiler;
    }

    private void dumpSignatureBlock() throws IOException {
        logMessage("Dumping signature blocks ...");
        DecompileOptions options = getOptions();
        ApkSignatureBlock apkSignatureBlock = new ArchiveFile(options.inputFile).getApkSignatureBlock();
        if (apkSignatureBlock == null) {
            logMessage("Don't have signature block");
        } else {
            apkSignatureBlock.writeSplitRawToDirectory(options.signaturesDirectory);
            logMessage("Signatures dumped to: " + options.signaturesDirectory);
        }
    }
}
