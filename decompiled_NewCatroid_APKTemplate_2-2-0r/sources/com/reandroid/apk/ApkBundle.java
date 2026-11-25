package com.reandroid.apk;

import com.reandroid.archive.ZipEntryMap;
import com.reandroid.archive.block.ApkSignatureBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.utils.collection.ArrayCollection;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class ApkBundle implements Closeable {
    private APKLogger apkLogger;
    private final Map<String, ApkModule> mModulesMap = new HashMap();

    public ApkModule mergeModules() throws IOException {
        return mergeModules(false);
    }

    public ApkModule mergeModules(boolean z) throws IOException {
        List<ApkModule> apkModuleList = getApkModuleList();
        if (apkModuleList.size() == 0) {
            throw new FileNotFoundException("Nothing to merge, empty modules");
        }
        ApkModule apkModule = new ApkModule(generateMergedModuleName(), new ZipEntryMap());
        apkModule.setAPKLogger(this.apkLogger);
        apkModule.setLoadDefaultFramework(false);
        ApkModule baseModule = getBaseModule();
        if (baseModule == null) {
            baseModule = getLargestTableModule();
        }
        apkModule.merge(baseModule, z);
        ApkSignatureBlock apkSignatureBlock = null;
        for (ApkModule apkModule2 : apkModuleList) {
            ApkSignatureBlock apkSignatureBlock2 = apkModule2.getApkSignatureBlock();
            if (apkModule2 != baseModule) {
                if (apkSignatureBlock == null) {
                    apkSignatureBlock = apkSignatureBlock2;
                }
                apkModule.merge(apkModule2, z);
            } else if (apkSignatureBlock2 != null) {
                apkSignatureBlock = apkSignatureBlock2;
            }
        }
        apkModule.setApkSignatureBlock(apkSignatureBlock);
        if (apkModule.hasTableBlock()) {
            TableBlock tableBlock = apkModule.getTableBlock();
            tableBlock.sortPackages();
            tableBlock.refresh();
        }
        apkModule.getZipEntryMap().autoSortApkFiles();
        return apkModule;
    }

    private String generateMergedModuleName() {
        String str = "merged";
        int i = 1;
        while (this.mModulesMap.keySet().contains(str)) {
            str = "merged_" + i;
            i++;
        }
        return str;
    }

    private ApkModule getLargestTableModule() {
        ApkModule apkModule = null;
        int i = 0;
        for (ApkModule apkModule2 : getApkModuleList()) {
            if (apkModule2.hasTableBlock()) {
                int chunkSize = apkModule2.getTableBlock().getHeaderBlock().getChunkSize();
                if (apkModule == null || chunkSize > i) {
                    apkModule = apkModule2;
                    i = chunkSize;
                }
            }
        }
        return apkModule;
    }

    public ApkModule getBaseModule() {
        for (ApkModule apkModule : getApkModuleList()) {
            if (apkModule.isBaseModule()) {
                return apkModule;
            }
        }
        return null;
    }

    public List<ApkModule> getApkModuleList() {
        return new ArrayCollection(this.mModulesMap.values());
    }

    public void loadApkDirectory(File file) throws IOException {
        loadApkDirectory(file, false);
    }

    public void loadApkDirectory(File file, boolean z) throws IOException {
        List<File> listListFiles;
        if (!file.isDirectory()) {
            throw new FileNotFoundException("No such directory: " + file);
        }
        if (z) {
            listListFiles = ApkUtil.recursiveFiles(file, ".apk");
        } else {
            listListFiles = ApkUtil.listFiles(file, ".apk");
        }
        if (listListFiles.size() == 0) {
            throw new FileNotFoundException("No '*.apk' files in directory: " + file);
        }
        logMessage("Found apk files: " + listListFiles.size());
        for (File file2 : listListFiles) {
            logVerbose("Loading: " + file2.getName());
            ApkModule apkModuleLoadApkFile = ApkModule.loadApkFile(file2, ApkUtil.toModuleName(file2));
            apkModuleLoadApkFile.setAPKLogger(this.apkLogger);
            addModule(apkModuleLoadApkFile);
        }
    }

    public void addModule(ApkModule apkModule) {
        apkModule.setLoadDefaultFramework(false);
        String moduleName = apkModule.getModuleName();
        this.mModulesMap.remove(moduleName);
        this.mModulesMap.put(moduleName, apkModule);
    }

    public boolean containsApkModule(String str) {
        return this.mModulesMap.containsKey(str);
    }

    public ApkModule removeApkModule(String str) {
        return this.mModulesMap.remove(str);
    }

    public ApkModule getApkModule(String str) {
        return this.mModulesMap.get(str);
    }

    public List<String> listModuleNames() {
        return new ArrayList(this.mModulesMap.keySet());
    }

    public int countModules() {
        return this.mModulesMap.size();
    }

    public Collection<ApkModule> getModules() {
        return this.mModulesMap.values();
    }

    private boolean hasOneTableBlock() {
        Iterator<ApkModule> it = getModules().iterator();
        while (it.hasNext()) {
            if (it.next().hasTableBlock()) {
                return true;
            }
        }
        return false;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Iterator<ApkModule> it = this.mModulesMap.values().iterator();
        while (it.hasNext()) {
            it.next().close();
        }
        this.mModulesMap.clear();
    }

    public void setAPKLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    private void logMessage(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(str);
        }
    }

    private void logError(String str, Throwable th) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logError(str, th);
        }
    }

    private void logVerbose(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logVerbose(str);
        }
    }
}
