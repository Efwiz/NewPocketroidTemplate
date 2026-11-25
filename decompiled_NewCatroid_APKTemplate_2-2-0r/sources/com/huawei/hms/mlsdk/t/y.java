package com.huawei.hms.mlsdk.t;

/* JADX INFO: compiled from: AudioSegment.java */
/* JADX INFO: loaded from: classes10.dex */
public class y {
    private String a;
    private byte[] b;
    private int c;
    private boolean d;
    private int e;
    private boolean f;
    private int g;
    private int h;
    private int i;
    private boolean j;
    private boolean k;

    /* JADX INFO: compiled from: AudioSegment.java */
    public static class b {
        private String a;
        private byte[] b;
        private int c;
        private boolean d;
        private int e;
        private boolean f;
        private int g;
        private int h;
        private int i;
        private boolean j;
        private boolean k;

        public b a(String str) {
            this.a = str;
            return this;
        }

        public b b(int i) {
            this.e = i;
            return this;
        }

        public b c(int i) {
            this.i = i;
            return this;
        }

        public b d(boolean z) {
            this.f = z;
            return this;
        }

        public b e(int i) {
            this.g = i;
            return this;
        }

        public b a(byte[] bArr) {
            this.b = (byte[]) bArr.clone();
            return this;
        }

        public b b(boolean z) {
            this.j = z;
            return this;
        }

        public b c(boolean z) {
            this.k = z;
            return this;
        }

        public b d(int i) {
            this.h = i;
            return this;
        }

        public b a(int i) {
            this.c = i;
            return this;
        }

        public b a(boolean z) {
            this.d = z;
            return this;
        }

        public b a(y yVar) {
            this.a = yVar.a;
            this.b = yVar.b;
            this.c = yVar.c;
            this.d = yVar.d;
            this.e = yVar.e;
            this.f = yVar.f;
            this.h = yVar.g;
            this.g = yVar.h;
            this.i = yVar.i;
            this.j = yVar.j;
            this.k = yVar.k;
            return this;
        }

        public y a() {
            return new y(this.a, this.b, this.c, this.d, this.e, this.f, this.h, this.g, this.i, this.j, this.k, null);
        }
    }

    /* synthetic */ y(String str, byte[] bArr, int i, boolean z, int i2, boolean z2, int i3, int i4, int i5, boolean z3, boolean z4, a aVar) {
        this.a = str;
        this.b = bArr;
        this.c = i;
        this.d = z;
        this.e = i2;
        this.f = z2;
        this.g = i3;
        this.h = i4;
        this.i = i5;
        this.j = z3;
        this.k = z4;
    }

    public boolean l() {
        return this.k;
    }

    public int a() {
        return this.c;
    }

    public int b() {
        return this.i;
    }

    public int c() {
        return this.g;
    }

    public int d() {
        return this.h;
    }

    public byte[] e() {
        return (byte[]) this.b.clone();
    }

    public String f() {
        return this.a;
    }

    public boolean g() {
        int i = this.e;
        return i == 1 || i == -1;
    }

    public boolean h() {
        return this.e == -1;
    }

    public boolean i() {
        return this.d;
    }

    public boolean j() {
        return this.f;
    }

    public boolean k() {
        return this.j;
    }
}
