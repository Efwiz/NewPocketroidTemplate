package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2953rv {
    private C3092tv a = C3092tv.a;
    private C3169uv b = C3169uv.a();

    public final void a(String str) {
        this.a = new C3022sv(str);
    }

    public final void b(String str) {
        this.b = new C3169uv(str);
    }

    public final void a(String str, String str2, String str3) {
        this.a = new C3243vv(str, str2, str3);
    }

    public final void b(String str, String str2, String str3) {
        this.a = new C3318wv(str, str2, str3);
    }

    public final C3395xv a() {
        if (this.a.equals(C3092tv.a) && this.b.equals(C3169uv.b)) {
            return C3395xv.c;
        }
        return new C3395xv(this.a, this.b);
    }
}
