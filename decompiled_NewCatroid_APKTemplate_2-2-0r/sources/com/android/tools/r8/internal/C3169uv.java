package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3169uv {
    private final String a;
    static final /* synthetic */ boolean c = true;
    private static final C3169uv b = new C3169uv("");

    public C3169uv(String str) {
        if (!c && str == null) {
            throw new AssertionError();
        }
        this.a = str;
    }

    public static C3169uv a() {
        return b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3169uv.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C3169uv) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a;
    }
}
