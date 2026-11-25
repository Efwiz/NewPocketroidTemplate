package com.huawei.hms.mlsdk.internal.client.rest;

import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: RestClientRequestHeaders.java */
/* JADX INFO: loaded from: classes10.dex */
public class f {
    private Map<String, String> a;

    /* JADX INFO: compiled from: RestClientRequestHeaders.java */
    public static class b {
        private Map<String, String> a;
        private d b;

        public b() {
            this.a = new HashMap();
            this.b = null;
            d dVar = new d(MLApplication.getInstance());
            this.b = dVar;
            this.a = dVar.a(this);
        }

        public b a(String str, String str2) {
            this.a.put(str, str2);
            return this;
        }

        public f a() {
            return new f(this, null);
        }
    }

    /* synthetic */ f(b bVar, a aVar) {
        this.a = null;
        this.a = bVar.a;
    }

    public Map<String, String> a() {
        return this.a;
    }
}
