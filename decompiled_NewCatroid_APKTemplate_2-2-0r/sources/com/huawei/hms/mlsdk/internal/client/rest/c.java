package com.huawei.hms.mlsdk.internal.client.rest;

import retrofit2.Retrofit;

/* JADX INFO: compiled from: RestClient.java */
/* JADX INFO: loaded from: classes10.dex */
public class c {
    private Retrofit a;

    public c(Retrofit retrofit) {
        this.a = retrofit;
    }

    public <T> T a(Class<T> cls) {
        return (T) this.a.create(cls);
    }
}
