package com.reandroid.apkeditor.protect;

import com.reandroid.apk.APKLogger;
import com.reandroid.apk.ApkModule;
import com.reandroid.archive.InputSource;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Confuser implements APKLogger {
    private Set<String> filePaths;
    private final String logTag;
    private final Protector protector;

    public abstract void confuse();

    public Confuser(Protector protector, String str) {
        this.protector = protector;
        this.logTag = str;
    }

    public boolean containsFilePath(String str) {
        return getFilePaths().contains(str);
    }

    public void onPathChanged(String str, String str2) {
        getFilePaths().add(str2);
        logVerbose(str + " -> " + str2);
    }

    public Set<String> getFilePaths() {
        if (this.filePaths == null) {
            ZipEntryMap zipEntryMap = getApkModule().getZipEntryMap();
            HashSet hashSet = CollectionUtil.toHashSet(ComputeIterator.of(zipEntryMap.iterator(), new Function() { // from class: com.reandroid.apkeditor.protect.Confuser$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((InputSource) obj).getAlias();
                }
            }));
            this.filePaths = hashSet;
            hashSet.addAll(CollectionUtil.toHashSet(ComputeIterator.of(zipEntryMap.iterator(), new Function() { // from class: com.reandroid.apkeditor.protect.Confuser$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((InputSource) obj).getName();
                }
            })));
        }
        return this.filePaths;
    }

    public boolean isKeepType(String str) {
        return getOptions().keepTypes.contains(str);
    }

    public Protector getProtector() {
        return this.protector;
    }

    public ProtectorOptions getOptions() {
        return getProtector().getOptions();
    }

    public ApkModule getApkModule() {
        return getProtector().getApkModule();
    }

    @Override // com.reandroid.apk.APKLogger
    public void logMessage(String str) {
        this.protector.logMessage(this.logTag + str);
    }

    @Override // com.reandroid.apk.APKLogger
    public void logError(String str, Throwable th) {
        this.protector.logError(str, th);
    }

    @Override // com.reandroid.apk.APKLogger
    public void logVerbose(String str) {
        this.protector.logVerbose(str);
    }
}
