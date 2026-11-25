package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1545Yw {
    public static AbstractC1545Yw a(AbstractC3024sx abstractC3024sx) {
        return abstractC3024sx instanceof C2886qx ? C1492Ww.b : new C1492Ww(abstractC3024sx);
    }

    public static AbstractC1545Yw e() {
        return C1518Xw.a;
    }

    public AbstractC3024sx b() {
        return null;
    }

    public final boolean c() {
        if (b() != null) {
            AbstractC3024sx abstractC3024sxB = b();
            abstractC3024sxB.getClass();
            if (abstractC3024sxB instanceof C2886qx) {
                return true;
            }
        }
        return false;
    }

    public boolean d() {
        return this instanceof C1518Xw;
    }

    public static AbstractC1545Yw a() {
        return C1492Ww.b;
    }
}
