package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.S10;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.s, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3583s {
    public static void a(C0702m c0702m, C2409kl c2409kl) {
        AbstractC1305Qs abstractC1305QsB0;
        AbstractC0748t0 abstractC0748t0H;
        if (c0702m.q()) {
            C0702m<?> c0702mH0 = c0702m.h0();
            B4 b4S = c2409kl.s();
            while (b4S.hasNext()) {
                InterfaceC2641no interfaceC2641noA = b4S.next().a(c2409kl);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next = interfaceC2641noA.next();
                    if (next.Y1() && (abstractC0748t0H = (abstractC1305QsB0 = next.b0()).h(c0702mH0, c2409kl.h())) != null && abstractC0748t0H.B().w()) {
                        S10 s10Q2 = abstractC1305QsB0.Q2();
                        if (abstractC1305QsB0.g1()) {
                            abstractC1305QsB0.b().g(s10Q2);
                        }
                        if (s10Q2.w().N().e()) {
                            interfaceC2641noA.s();
                        } else {
                            interfaceC2641noA.a(c0702mH0, s10Q2);
                        }
                    }
                }
            }
        }
    }
}
