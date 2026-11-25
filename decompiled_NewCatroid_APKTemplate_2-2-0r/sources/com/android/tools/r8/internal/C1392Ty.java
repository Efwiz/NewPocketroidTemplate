package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ty, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1392Ty {
    private final int a;
    private final int b;
    private final int c;

    public C1392Ty(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    public final int a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public final int c() {
        return this.c;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1392Ty)) {
            return false;
        }
        C1392Ty c1392Ty = (C1392Ty) obj;
        return this.a == c1392Ty.a && this.b == c1392Ty.b && this.c == c1392Ty.c;
    }

    public final int hashCode() {
        return Integer.hashCode(this.c) + ((Integer.hashCode(this.b) + (Integer.hashCode(this.a) * 31)) * 31);
    }

    public final String toString() {
        return new StringBuilder().append(this.a).append('.').append(this.b).append('.').append(this.c).toString();
    }
}
