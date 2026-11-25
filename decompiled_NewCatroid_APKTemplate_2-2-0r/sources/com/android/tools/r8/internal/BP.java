package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BP implements InterfaceC3457yn {
    static final /* synthetic */ boolean c = true;
    private final InterfaceC3457yn a;
    private final VP b;

    public BP(C0702m c0702m, InterfaceC3457yn interfaceC3457yn) {
        this.a = interfaceC3457yn;
        this.b = c0702m.Z().h;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3457yn
    public final EnumC3542a0 a(AbstractC1305Qs abstractC1305Qs, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, com.android.tools.r8.ir.optimize.N n, TE te) {
        VP vp = this.b;
        C0729q2 c0729q2A = o5Var2.a();
        vp.getClass();
        C0767v2 c0767v2 = c0729q2A.d;
        if ((c0767v2 == vp.j || c0767v2 == vp.k) && (abstractC1305Qs instanceof C1798ct)) {
            return EnumC3542a0.b;
        }
        VP vp2 = this.b;
        vp2.getClass();
        if (!vp2.a(o5Var.getReference())) {
            VP vp3 = this.b;
            vp3.getClass();
            C0684j2 reference = o5Var.getReference();
            TP tp = vp3.q;
            if (reference != tp.b && reference != tp.c) {
                return this.a.a(abstractC1305Qs, o5Var, o5Var2, n, te);
            }
        }
        VP vp4 = this.b;
        vp4.getClass();
        if (vp4.a(o5Var.getReference())) {
            VP vp5 = this.b;
            C0729q2 c0729q2A2 = o5Var2.a();
            vp5.getClass();
            C0759u2 c0759u2 = c0729q2A2.d.e;
            C0759u2 c0759u3 = vp5.B;
            c0759u2.getClass();
            if (c0759u2.a(c0759u3.e)) {
                return EnumC3542a0.f;
            }
        }
        S10 s10M = ((S10) ((ArrayList) abstractC1305Qs.h1()).get(1)).m();
        if (s10M.i()) {
            return EnumC3542a0.f;
        }
        AbstractC2412ko abstractC2412ko = s10M.b;
        abstractC2412ko.getClass();
        if (!(abstractC2412ko instanceof QW)) {
            return EnumC3542a0.f;
        }
        com.android.tools.r8.graph.Y0 field = abstractC2412ko.F0().getField();
        VP vp6 = this.b;
        UP up = vp6.t;
        if (field == up.b || field == up.c || field == up.e || field == up.f || field == up.g) {
            return EnumC3542a0.b;
        }
        if (c || field.e != vp6.n || field == up.a || field == up.d) {
            return EnumC3542a0.f;
        }
        throw new AssertionError();
    }
}
