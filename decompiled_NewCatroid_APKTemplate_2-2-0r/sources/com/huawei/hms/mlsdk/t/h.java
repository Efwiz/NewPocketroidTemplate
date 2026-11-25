package com.huawei.hms.mlsdk.t;

/* JADX INFO: compiled from: TtsEvent.java */
/* JADX INFO: loaded from: classes10.dex */
public class h {
    private String a;
    private String b;
    private String c;
    private String d;
    private boolean e;
    private boolean f;
    private boolean g;

    /* JADX INFO: compiled from: TtsEvent.java */
    public static class a {
        private String a = "";
        private String b = "";
        private String c = null;
        private String d = null;
        private boolean e = false;
        private boolean f = false;
        private boolean g = false;

        public a a(String str) {
            this.c = str;
            return this;
        }

        public a b(String str) {
            this.d = str;
            return this;
        }

        public a c(String str) {
            this.b = str;
            return this;
        }

        public a d(String str) {
            this.a = str;
            return this;
        }

        public a a(boolean z) {
            this.e = z;
            return this;
        }

        public a b(boolean z) {
            this.g = z;
            return this;
        }

        public a c(boolean z) {
            this.f = z;
            return this;
        }

        public h a() {
            return new h(this.a, this.c, this.d, this.b, this.e, this.f, this.g);
        }
    }

    public h(String str, String str2, String str3, String str4, boolean z, boolean z2, boolean z3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = z;
        this.f = z2;
        this.g = z3;
    }

    public String a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.d;
    }

    public String d() {
        return this.a;
    }

    public boolean e() {
        return this.g;
    }

    public boolean f() {
        return this.e;
    }

    public boolean g() {
        return this.f;
    }

    public String toString() {
        return com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a("TtsEvent{text='"), this.a, '\'', ", locale='"), this.b, '\'', ", speaker='"), this.c, '\'', ", taskId='"), this.d, '\'', ", externalPlayback=").append(this.e).append(", printStream=").append(this.f).append(", isFlushMode=").append(this.g).append('}').toString();
    }
}
