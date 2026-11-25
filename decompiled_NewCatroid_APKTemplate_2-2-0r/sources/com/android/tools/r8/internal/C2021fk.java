package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2021fk {
    final C3263wA a;
    final C3263wA b;
    final C3263wA c;
    final int d;
    final String e;
    C2021fk f;

    C2021fk(C3263wA c3263wA, C3263wA c3263wA2, C3263wA c3263wA3, int i, String str) {
        this.a = c3263wA;
        this.b = c3263wA2;
        this.c = c3263wA3;
        this.d = i;
        this.e = str;
    }

    static C2021fk a(C2021fk c2021fk, C3263wA c3263wA, C3263wA c3263wA2) {
        if (c2021fk == null) {
            return null;
        }
        C2021fk c2021fkA = a(c2021fk.f, c3263wA, c3263wA2);
        c2021fk.f = c2021fkA;
        C3263wA c3263wA3 = c2021fk.a;
        int i = c3263wA3.e;
        C3263wA c3263wA4 = c2021fk.b;
        int i2 = c3263wA4.e;
        int i3 = c3263wA.e;
        int i4 = c3263wA2 == null ? Integer.MAX_VALUE : c3263wA2.e;
        if (i3 >= i2 || i4 <= i) {
            return c2021fk;
        }
        if (i3 <= i) {
            if (i4 >= i2) {
                return c2021fkA;
            }
            C2021fk c2021fk2 = new C2021fk(c3263wA2, c3263wA4, c2021fk.c, c2021fk.d, c2021fk.e);
            c2021fk2.f = c2021fkA;
            return c2021fk2;
        }
        if (i4 >= i2) {
            C2021fk c2021fk3 = new C2021fk(c3263wA3, c3263wA, c2021fk.c, c2021fk.d, c2021fk.e);
            c2021fk3.f = c2021fkA;
            return c2021fk3;
        }
        C3263wA c3263wA5 = c2021fk.c;
        int i5 = c2021fk.d;
        String str = c2021fk.e;
        C2021fk c2021fk4 = new C2021fk(c3263wA2, c3263wA4, c3263wA5, i5, str);
        c2021fk4.f = c2021fkA;
        c2021fk.f = c2021fk4;
        C2021fk c2021fk5 = new C2021fk(c3263wA3, c3263wA, c3263wA5, i5, str);
        c2021fk5.f = c2021fk4;
        return c2021fk5;
    }
}
