package com.reandroid.dex.model;

/* JADX INFO: loaded from: classes11.dex */
public abstract class DexCode extends Dex {
    public abstract DexMethod getDexMethod();

    public DexClass getDexClass() {
        return getDexMethod().getDexClass();
    }

    @Override // com.reandroid.dex.model.Dex
    public DexClassRepository getClassRepository() {
        return getDexMethod().getClassRepository();
    }
}
