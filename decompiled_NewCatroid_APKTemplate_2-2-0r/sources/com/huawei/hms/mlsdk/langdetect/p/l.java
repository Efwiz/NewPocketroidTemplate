package com.huawei.hms.mlsdk.langdetect.p;

import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: RestClientRequestHeaders.java */
/* JADX INFO: loaded from: classes10.dex */
public class l {
    private Map<String, String> a;

    /* JADX INFO: compiled from: RestClientRequestHeaders.java */
    public static class b {
        private Map<String, String> a;
        private j b;

        public b() {
            j jVar = new j(MLApplication.getInstance());
            this.a = new HashMap();
            this.b = jVar;
            this.a = this.b.a(this);
        }

        public b a(String str, String str2) {
            this.a.put(str, str2);
            return this;
        }

        public l a() {
            return new l(this, null);
        }
    }

    /* synthetic */ l(b bVar, a aVar) {
        this.a = null;
        this.a = bVar.a;
    }

    public Map<String, String> a() {
        return this.a;
    }
}
