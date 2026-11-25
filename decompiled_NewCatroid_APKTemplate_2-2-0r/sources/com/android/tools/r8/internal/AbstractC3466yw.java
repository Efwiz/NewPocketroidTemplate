package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3466yw {
    public static AbstractC3466yw a(AbstractC3024sx abstractC3024sx) {
        return abstractC3024sx instanceof C2886qx ? C3397xw.b : new C3397xw(abstractC3024sx);
    }

    public abstract AbstractC3024sx b();

    public final boolean c() {
        AbstractC3024sx abstractC3024sx = ((C3397xw) this).a;
        if (abstractC3024sx != null) {
            abstractC3024sx.getClass();
            if (abstractC3024sx instanceof C2886qx) {
                return true;
            }
        }
        return false;
    }

    public static AbstractC3466yw a() {
        return C3397xw.b;
    }
}
