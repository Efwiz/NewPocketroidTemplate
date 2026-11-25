package com.android.tools.r8.internal;

import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class E4 implements R10 {
    public static final E4 b = new E4(null);
    public static final E4 c = new E4(CZ.j);
    public static final E4 d = new E4(CZ.k);
    public static final E4 e = new E4(CZ.l);
    public static final E4 f = new E4(CZ.m);
    public static final E4 g = new E4(CZ.d(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME));
    public static final E4 h = new E4(CZ.e);
    private final CZ a;

    public E4(CZ cz) {
        this.a = cz;
    }

    public final int a() {
        CZ cz = this.a;
        return (cz == CZ.l || cz == CZ.m) ? 2 : 1;
    }

    public final boolean b() {
        CZ cz = this.a;
        return cz != null && (cz.e() == 10 || this.a.e() == 9);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof E4)) {
            return false;
        }
        CZ cz = this.a;
        if (cz == null) {
            return ((E4) obj).a == null;
        }
        return cz.equals(((E4) obj).a);
    }

    public final int hashCode() {
        CZ cz = this.a;
        if (cz == null) {
            return 0;
        }
        return cz.hashCode();
    }

    public final String toString() {
        if (this == b) {
            return ".";
        }
        if (this == h) {
            return "A";
        }
        return this == g ? "R" : this.a.b();
    }
}
