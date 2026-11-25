package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ek, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1944ek {
    private final int a;
    private final String b;
    private final String c;
    private final String d;
    private final boolean e;

    public C1944ek(int i, String str, String str2, String str3, boolean z) {
        this.a = i;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = z;
    }

    public final String a() {
        return this.d;
    }

    public final String b() {
        return this.c;
    }

    public final String c() {
        return this.b;
    }

    public final int d() {
        return this.a;
    }

    public final boolean e() {
        return this.e;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C1944ek)) {
            return false;
        }
        C1944ek c1944ek = (C1944ek) obj;
        return this.a == c1944ek.a && this.e == c1944ek.e && this.b.equals(c1944ek.b) && this.c.equals(c1944ek.c) && this.d.equals(c1944ek.d);
    }

    public final int hashCode() {
        return (this.d.hashCode() * this.c.hashCode() * this.b.hashCode()) + this.a + (this.e ? 64 : 0);
    }

    public final String toString() {
        return this.b + '.' + this.c + this.d + " (" + this.a + (this.e ? " itf" : "") + ')';
    }
}
