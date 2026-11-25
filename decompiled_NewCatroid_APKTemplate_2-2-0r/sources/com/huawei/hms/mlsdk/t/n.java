package com.huawei.hms.mlsdk.t;

import okhttp3.Response;

/* JADX INFO: compiled from: SynthesisResponse.java */
/* JADX INFO: loaded from: classes10.dex */
public class n {
    private Response a = null;
    private int b;
    private String c;

    public n a(Response response) {
        this.a = response;
        return this;
    }

    public String b() {
        return this.c;
    }

    public Response c() {
        return this.a;
    }

    public int a() {
        return this.b;
    }

    public n a(int i) {
        this.b = i;
        return this;
    }

    public n a(String str) {
        this.c = str;
        return this;
    }
}
