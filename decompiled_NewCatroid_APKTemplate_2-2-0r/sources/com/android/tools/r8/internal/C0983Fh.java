package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0983Fh {
    public static void a(AbstractC1182Mh abstractC1182Mh, com.android.tools.r8.graph.T0 t0, com.android.tools.r8.ir.optimize.info.n nVar) {
        if (t0.getReference().h.R0()) {
            int i = -1;
            if (t0.X0().h() != -1 && abstractC1182Mh.J1()) {
                S10 s10B = abstractC1182Mh.b();
                if (s10B.a0() <= 0) {
                    int iN2 = 0;
                    for (AbstractC2412ko abstractC2412ko : s10B.h0()) {
                        if (!a(t0, abstractC2412ko)) {
                            if (abstractC2412ko instanceof X1) {
                                X1 x1P = abstractC2412ko.p();
                                S10 s10M = ((S10) ((ArrayList) x1P.h1()).get(1 - ((ArrayList) x1P.h1()).indexOf(s10B))).m();
                                if (!s10M.i() && s10M.b.B1()) {
                                    iN2 |= s10M.b.E().N2();
                                }
                            }
                        }
                    }
                    i = iN2;
                }
                nVar.a(t0, i);
            }
        }
    }

    private static boolean a(com.android.tools.r8.graph.T0 t0, AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.i2()) {
            return abstractC2412ko.L1() && abstractC2412ko.N().getField() == t0.getReference();
        }
        S10 s10B = abstractC2412ko.l0().b();
        if (s10B.a0() > 0) {
            return false;
        }
        Iterator<AbstractC2412ko> it = s10B.h0().iterator();
        while (it.hasNext()) {
            if (!a(t0, it.next())) {
                return false;
            }
        }
        return true;
    }
}
