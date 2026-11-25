package com.huawei.hms.mlsdk.tts.client;

/* JADX INFO: loaded from: classes10.dex */
public class ModuleInfo {
    private String creatorName;
    private boolean liteFlag;
    private int minApkVersion;
    private String moduleName;

    public ModuleInfo(String str, String str2, int i) {
        this.moduleName = str;
        this.creatorName = str2;
        this.minApkVersion = i;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public int getMinApkVersion() {
        return this.minApkVersion;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public boolean isLiteFlag() {
        return this.liteFlag;
    }

    public void setLiteFlag(boolean z) {
        this.liteFlag = z;
    }

    public ModuleInfo(String str, String str2, int i, boolean z) {
        this.moduleName = str;
        this.creatorName = str2;
        this.minApkVersion = i;
        this.liteFlag = z;
    }
}
