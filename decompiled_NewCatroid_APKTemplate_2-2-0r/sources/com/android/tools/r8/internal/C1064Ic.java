package com.android.tools.r8.internal;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ic, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1064Ic {
    private final String a;
    private final String b;
    private final C1944ek c;
    private final Object[] d;

    public C1064Ic(String str, String str2, C1944ek c1944ek, Object... objArr) {
        this.a = str;
        this.b = str2;
        this.c = c1944ek;
        this.d = objArr;
    }

    public final C1944ek a() {
        return this.c;
    }

    public final int b() {
        return this.d.length;
    }

    final Object[] c() {
        return this.d;
    }

    public final String d() {
        return this.b;
    }

    public final String e() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C1064Ic)) {
            return false;
        }
        C1064Ic c1064Ic = (C1064Ic) obj;
        return this.a.equals(c1064Ic.a) && this.b.equals(c1064Ic.b) && this.c.equals(c1064Ic.c) && Arrays.equals(this.d, c1064Ic.d);
    }

    public final int hashCode() {
        return ((this.a.hashCode() ^ Integer.rotateLeft(this.b.hashCode(), 8)) ^ Integer.rotateLeft(this.c.hashCode(), 16)) ^ Integer.rotateLeft(Arrays.hashCode(this.d), 24);
    }

    public final String toString() {
        return this.a + " : " + this.b + StringUtil.SPACE + this.c + StringUtil.SPACE + Arrays.toString(this.d);
    }

    public final Object a(int i) {
        return this.d[i];
    }
}
