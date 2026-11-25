package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1046Hk {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C0702m c0702m, com.android.tools.r8.graph.T0 t0) {
        com.android.tools.r8.graph.Y0 y0 = c0702m.a().g4.a;
        if (t0.N0() && t0.getType().R0()) {
            com.android.tools.r8.graph.Y0 y0A = c0702m.E().a(t0.getReference());
            y0A.getClass();
            return y0.f == y0A.f && y0.h == y0A.h;
        }
        if (!a) {
            com.android.tools.r8.graph.Y0 y0A2 = c0702m.E().a(t0.getReference());
            y0A2.getClass();
            if (y0.f == y0A2.f && y0.h == y0A2.h) {
                throw new AssertionError();
            }
        }
        return false;
    }
}
