package com.reandroid.graph;

import com.reandroid.apk.ApkModule;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.dex.model.DexClassRepository;

/* JADX INFO: loaded from: classes11.dex */
public abstract class BaseApkModuleProcessor extends BaseDexClassProcessor {
    private final ApkModule apkModule;

    public BaseApkModuleProcessor(ApkModule apkModule, DexClassRepository dexClassRepository) {
        super(dexClassRepository);
        this.apkModule = apkModule;
    }

    public ZipEntryMap getZipEntryMap() {
        return getApkModule().getZipEntryMap();
    }

    public ApkModule getApkModule() {
        return this.apkModule;
    }
}
