package com.android.tools.r8.utils;

/* JADX INFO: renamed from: com.android.tools.r8.utils.n2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3938n2 extends L {
    public static final AbstractC3938n2 a = new C3922k2();
    public static final AbstractC3938n2 b = new C3928l2();
    public static final AbstractC3938n2 c = new C3933m2();

    AbstractC3938n2() {
    }

    public static AbstractC3938n2 a(boolean z) {
        return z ? a : b;
    }

    public static AbstractC3938n2 g() {
        return c;
    }

    @Override // com.android.tools.r8.utils.L
    public final boolean equals(Object obj) {
        return this == obj;
    }

    public abstract int f();

    @Override // com.android.tools.r8.utils.L
    public final int hashCode() {
        return System.identityHashCode(this);
    }
}
