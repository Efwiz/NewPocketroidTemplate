package com.opencsv.bean;

/* JADX INFO: loaded from: classes10.dex */
public class FieldMapByNameEntry<T> {
    private final BeanField<T, String> field;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f216name;
    private final boolean regexPattern;

    public FieldMapByNameEntry(String str, BeanField<T, String> beanField, boolean z) {
        this.f216name = str;
        this.field = beanField;
        this.regexPattern = z;
    }

    public String getName() {
        return this.f216name;
    }

    public BeanField<T, String> getField() {
        return this.field;
    }

    public boolean isRegexPattern() {
        return this.regexPattern;
    }
}
