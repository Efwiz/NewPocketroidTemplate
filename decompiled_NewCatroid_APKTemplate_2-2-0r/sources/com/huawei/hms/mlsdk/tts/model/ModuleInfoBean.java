package com.huawei.hms.mlsdk.tts.model;

import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ModuleInfoBean {
    private String creatorClassName;
    private String framework;
    private List<LanguageInfo> languages;
    private int minApkVersion;
    private String moduleName;

    public String getCreatorClassName() {
        return this.creatorClassName;
    }

    public String getFramework() {
        return this.framework;
    }

    public List<LanguageInfo> getLanguages() {
        return this.languages;
    }

    public int getMinApkVersion() {
        return this.minApkVersion;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setCreatorClassName(String str) {
        this.creatorClassName = str;
    }

    public void setFramework(String str) {
        this.framework = str;
    }

    public void setLanguages(List<LanguageInfo> list) {
        this.languages = list;
    }

    public void setMinApkVersion(int i) {
        this.minApkVersion = i;
    }

    public void setModuleName(String str) {
        this.moduleName = str;
    }
}
