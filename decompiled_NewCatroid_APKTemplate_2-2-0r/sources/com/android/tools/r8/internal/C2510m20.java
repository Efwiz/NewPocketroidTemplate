package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.m20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2510m20 {
    public static final C2510m20 d = new C2510m20(256, 256, 256);
    private final int a;
    private final int b;
    private final int c;

    public C2510m20(int i, int i2, int i3) {
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
        if (!(obj instanceof C2510m20)) {
            return false;
        }
        C2510m20 c2510m20 = (C2510m20) obj;
        return this.a == c2510m20.a && this.b == c2510m20.b && this.c == c2510m20.c;
    }

    public final int hashCode() {
        return Integer.hashCode(this.c) + ((Integer.hashCode(this.b) + (Integer.hashCode(this.a) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sbAppend;
        int i;
        if (this.c == 0) {
            sbAppend = new StringBuilder().append(this.a).append('.');
            i = this.b;
        } else {
            sbAppend = new StringBuilder().append(this.a).append('.').append(this.b).append('.');
            i = this.c;
        }
        return sbAppend.append(i).toString();
    }

    public final void a(InterfaceC1475Wi interfaceC1475Wi, InterfaceC1475Wi interfaceC1475Wi2) {
        int i;
        int i2;
        if (AbstractC0936Ds.a(this, d)) {
            return;
        }
        int i3 = this.a;
        if (i3 > 7 || (i = this.b) > 15 || (i2 = this.c) > 127) {
            interfaceC1475Wi2.a(Integer.valueOf((this.b << 8) | i3 | (this.c << 16)));
        } else {
            interfaceC1475Wi.a(Integer.valueOf((i << 3) | i3 | (i2 << 7)));
        }
    }
}
