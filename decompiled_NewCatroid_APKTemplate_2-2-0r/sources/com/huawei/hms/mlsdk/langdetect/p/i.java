package com.huawei.hms.mlsdk.langdetect.p;

import retrofit2.Retrofit;

/* JADX INFO: compiled from: RestClient.java */
/* JADX INFO: loaded from: classes10.dex */
public class i {
    private Retrofit a;

    public i(Retrofit retrofit) {
        this.a = retrofit;
    }

    public <T> T a(Class<T> cls) {
        return (T) this.a.create(cls);
    }
}
