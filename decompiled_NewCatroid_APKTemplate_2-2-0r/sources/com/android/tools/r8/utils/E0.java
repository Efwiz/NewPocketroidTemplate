package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.internal.AbstractC1915eM;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class E0 {
    static final /* synthetic */ boolean a = true;

    public static boolean a(com.android.tools.r8.graph.J0 j0) {
        return j0 == null || j0.z0() || a(j0.t0().g);
    }

    public static boolean a(com.android.tools.r8.graph.B0... b0Arr) {
        for (int length = b0Arr.length - 1; length >= 0; length--) {
            com.android.tools.r8.graph.B0 b0 = b0Arr[length];
            b0.getClass();
            if (b0 instanceof com.android.tools.r8.graph.B0.b) {
                return true;
            }
            if (!a) {
                com.android.tools.r8.graph.B0 b1 = b0Arr[length];
                b1.getClass();
                if (b1 instanceof com.android.tools.r8.graph.F0) {
                    throw new AssertionError();
                }
            }
        }
        return true;
    }

    public static D0 a(C0684j2 c0684j2, com.android.tools.r8.graph.J0.a aVar) {
        int iA = -1;
        if (aVar == null) {
            return new D0(-1, null);
        }
        N n = new N();
        C0 c0 = new C0(aVar.e, c0684j2, n);
        AbstractC1915eM abstractC1915eM = null;
        for (com.android.tools.r8.graph.B0 b0 : aVar.g) {
            b0.a(c0);
            if (c0.b() > 0) {
                break;
            }
            if (b0 instanceof com.android.tools.r8.graph.B0.b) {
                iA = c0.a();
                abstractC1915eM = (AbstractC1915eM) n.a();
            }
        }
        return new D0(iA, abstractC1915eM);
    }
}
