package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC2251ih;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class W0 extends AbstractC2251ih {
    private static final W0 b = new W0();
    private final boolean a = false;

    private W0() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final boolean a(Object obj, Object obj2) {
        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) obj;
        com.android.tools.r8.graph.Y0 y1 = (com.android.tools.r8.graph.Y0) obj2;
        if (this.a) {
            return y0.h.equals(y1.h);
        }
        return y0.f.equals(y1.f) && y0.h.equals(y1.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) obj;
        if (this.a) {
            return y0.h.hashCode();
        }
        return y0.h.hashCode() + (y0.f.hashCode() * 31);
    }

    public static W0 a() {
        return b;
    }
}
