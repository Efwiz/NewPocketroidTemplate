package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xe, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1500Xe {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C0702m<C3733i> c0702m, C2409kl c2409kl) {
        for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
            if (abstractC2412ko.b() != null && abstractC2412ko.b().S()) {
                if (!abstractC2412ko.w2() && !abstractC2412ko.Q1() && !abstractC2412ko.p1() && !abstractC2412ko.q1()) {
                    if (abstractC2412ko.Y1()) {
                        AbstractC0748t0 abstractC0748t0H = abstractC2412ko.b0().h(c0702m, c2409kl.h());
                        if (abstractC0748t0H == null || !abstractC0748t0H.e().i1().D()) {
                            return false;
                        }
                    } else {
                        if (abstractC2412ko.V1() || abstractC2412ko.c2() || abstractC2412ko.E1() || abstractC2412ko.m2()) {
                            return false;
                        }
                        if (!a && !abstractC2412ko.m1() && !abstractC2412ko.s1() && !abstractC2412ko.v1() && !abstractC2412ko.O1() && !abstractC2412ko.C2() && !abstractC2412ko.j2() && !abstractC2412ko.l2() && !abstractC2412ko.w1() && !abstractC2412ko.R1() && !abstractC2412ko.y1() && !abstractC2412ko.g2() && !abstractC2412ko.F1()) {
                            throw new AssertionError("Instruction that impacts determinism: " + abstractC2412ko);
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }
}
