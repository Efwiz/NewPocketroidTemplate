package com.android.tools.r8.graph;

import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.utils.AbstractC3938n2;

/* JADX INFO: renamed from: com.android.tools.r8.graph.e, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0639e {
    static final /* synthetic */ boolean a = true;

    public static AbstractC3938n2 a(AbstractC0727q0 abstractC0727q0, InterfaceC0694k5 interfaceC0694k5, C0702m c0702m) {
        return a(abstractC0727q0, interfaceC0694k5, ((C0680j) c0702m.f()).p(), c0702m.Y(), c0702m.C(), c0702m.D());
    }

    public static AbstractC3938n2 a(AbstractC0727q0 abstractC0727q0, InterfaceC0593a0 interfaceC0593a0, C1121Ka c1121Ka, com.android.tools.r8.utils.A1 a1, AbstractC3135uW abstractC3135uW, com.android.tools.r8.synthesis.D d) {
        if (!abstractC0727q0.J1() && !abstractC0727q0.getType().a(interfaceC0593a0.C())) {
            return AbstractC3938n2.b;
        }
        if (abstractC0727q0.e0() && interfaceC0593a0.j()) {
            C0729q2 c0729q2A0 = abstractC0727q0.a0();
            InterfaceC0694k5 interfaceC0694k5I = interfaceC0593a0.i();
            c1121Ka.getClass();
            FeatureSplit featureSplitA = c1121Ka.a(c0729q2A0.getType(), a1, abstractC3135uW, d);
            if (!featureSplitA.isBase() && featureSplitA != c1121Ka.a(interfaceC0694k5I.C(), a1, abstractC3135uW, d)) {
                return AbstractC3938n2.c;
            }
        }
        return AbstractC3938n2.a;
    }

    public static AbstractC3938n2 a(AbstractC0741s0 abstractC0741s0, AbstractC0727q0 abstractC0727q0, InterfaceC0593a0 interfaceC0593a0, C0702m c0702m) {
        return a(abstractC0741s0, abstractC0727q0, interfaceC0593a0, c0702m, (C0680j) c0702m.f());
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0066, code lost:
    
        if (r7.g1() == r9.g1()) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.android.tools.r8.utils.AbstractC3938n2 a(com.android.tools.r8.graph.AbstractC0741s0 r7, com.android.tools.r8.graph.AbstractC0727q0 r8, com.android.tools.r8.graph.InterfaceC0593a0 r9, com.android.tools.r8.graph.C0702m r10, com.android.tools.r8.graph.C0680j r11) {
        /*
            com.android.tools.r8.graph.U0 r0 = r7.e()
            com.android.tools.r8.graph.g r0 = r0.getAccessFlags()
            com.android.tools.r8.graph.q0 r1 = r8.b()
            com.android.tools.r8.internal.Ka r3 = r11.p()
            com.android.tools.r8.utils.A1 r4 = r11.l()
            com.android.tools.r8.internal.uW r5 = r10.C()
            com.android.tools.r8.synthesis.D r6 = r11.g()
            r2 = r9
            com.android.tools.r8.utils.n2 r8 = a(r1, r2, r3, r4, r5, r6)
            boolean r10 = r8.a()
            if (r10 == 0) goto L2a
            com.android.tools.r8.utils.n2 r7 = com.android.tools.r8.utils.AbstractC3938n2.b
            return r7
        L2a:
            boolean r10 = r0.m()
            if (r10 == 0) goto L31
            return r8
        L31:
            boolean r10 = r0.j()
            if (r10 == 0) goto L6c
            com.android.tools.r8.graph.q0 r7 = r7.a()
            com.android.tools.r8.graph.q0 r9 = r9.b()
            if (r7 != r9) goto L42
            goto L68
        L42:
            if (r9 != 0) goto L51
            boolean r7 = com.android.tools.r8.graph.AbstractC0639e.a
            if (r7 == 0) goto L49
            goto L69
        L49:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            java.lang.String r8 = "context should not be null"
            r7.<init>(r8)
            throw r7
        L51:
            boolean r10 = r7.D1()
            if (r10 == 0) goto L69
            boolean r10 = r9.D1()
            if (r10 != 0) goto L5e
            goto L69
        L5e:
            com.android.tools.r8.graph.v2 r7 = r7.g1()
            com.android.tools.r8.graph.v2 r9 = r9.g1()
            if (r7 != r9) goto L69
        L68:
            return r8
        L69:
            com.android.tools.r8.utils.n2 r7 = com.android.tools.r8.utils.AbstractC3938n2.b
            return r7
        L6c:
            com.android.tools.r8.graph.v2 r10 = r7.t()
            com.android.tools.r8.graph.v2 r1 = r9.C()
            boolean r10 = r10.a(r1)
            if (r10 == 0) goto L7b
            return r8
        L7b:
            boolean r10 = r0.l()
            if (r10 == 0) goto L90
            com.android.tools.r8.graph.v2 r9 = r9.C()
            com.android.tools.r8.graph.v2 r7 = r7.t()
            boolean r7 = r11.c(r9, r7)
            if (r7 == 0) goto L90
            return r8
        L90:
            com.android.tools.r8.utils.n2 r7 = com.android.tools.r8.utils.AbstractC3938n2.b
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.graph.AbstractC0639e.a(com.android.tools.r8.graph.s0, com.android.tools.r8.graph.q0, com.android.tools.r8.graph.a0, com.android.tools.r8.graph.m, com.android.tools.r8.graph.j):com.android.tools.r8.utils.n2");
    }
}
