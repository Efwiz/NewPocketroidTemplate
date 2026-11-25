package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3138uX implements InterfaceC2833qB {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C0722p1 b;
    private final com.android.tools.r8.utils.A1 c;
    private final com.android.tools.r8.graph.Z1 d;

    C3138uX(C0702m c0702m) {
        C0722p1 c0722p1A = c0702m.a();
        this.a = c0702m;
        this.b = c0722p1A;
        this.c = c0702m.Y();
        this.d = c0722p1A.W3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final void a(C2409kl c2409kl, B4 b4, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, AbstractC0748t0 abstractC0748t0, Set set, Set set2, InterfaceC2762pB interfaceC2762pB, TE te, C1230Ob c1230Ob) {
        C3067tX c3067tX = (C3067tX) interfaceC2762pB;
        if (abstractC1305Qs.Z1()) {
            AbstractC1332Rs abstractC1332RsC0 = abstractC1305Qs.c0();
            if (this.d.a(abstractC0748t0.getReference())) {
                a(interfaceC2641no, abstractC1332RsC0, abstractC0748t0, c3067tX, te, c1230Ob);
                return;
            }
            if (abstractC0748t0.getReference() == this.b.W3.r) {
                S10 s10U2 = abstractC1332RsC0.U2();
                C0722p1 c0722p1 = this.b;
                while (!s10U2.i()) {
                    AbstractC2412ko abstractC2412koQ = s10U2.q();
                    if (abstractC2412koQ.p2()) {
                        if (abstractC2412koQ.t0().h == c0722p1.v2) {
                            if (abstractC1332RsC0.d1() && abstractC1332RsC0.b().B()) {
                                return;
                            }
                            interfaceC2641no.s();
                            return;
                        }
                        return;
                    }
                    if (!abstractC2412koQ.f2()) {
                        return;
                    }
                    C2115gt c2115gtI0 = abstractC2412koQ.i0();
                    if (!c0722p1.W3.a(c2115gtI0.S2())) {
                        return;
                    } else {
                        s10U2 = c2115gtI0.U2();
                    }
                }
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final C0767v2 getType() {
        return this.b.v2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final InterfaceC2762pB a() {
        return new C3067tX(this);
    }

    private void a(InterfaceC2641no interfaceC2641no, AbstractC1332Rs abstractC1332Rs, AbstractC0748t0 abstractC0748t0, C3067tX c3067tX, TE te, C1230Ob c1230Ob) {
        S10 s10U2 = abstractC1332Rs.U2();
        if (!c3067tX.a.containsKey(s10U2)) {
            c3067tX.a(s10U2);
            if (!C3067tX.c && !c3067tX.a.containsKey(s10U2)) {
                throw new AssertionError();
            }
        }
        boolean zB = c3067tX.a.b(s10U2);
        if (abstractC1332Rs.d1() && (this.c.Y0() || zB)) {
            abstractC1332Rs.b().g(abstractC1332Rs.U2());
            abstractC1332Rs.U2().g0().forEach(new uX$$ExternalSyntheticLambda0());
            abstractC1332Rs.f((S10) null);
        }
        if (zB) {
            if (!e && abstractC1332Rs.d1()) {
                throw new AssertionError();
            }
            C0684j2 reference = abstractC0748t0.getReference();
            if (!this.d.b(reference)) {
                com.android.tools.r8.graph.Z1 z1 = this.d;
                if (reference != z1.l) {
                    if (reference == z1.k) {
                        S10 s10C = abstractC1332Rs.c(1);
                        if (s10C.P()) {
                            int i = C2115gt.m;
                            interfaceC2641no.d(((C2030ft) ((C2030ft) new C2030ft().a(this.b.g4.i)).a(AbstractC1639am.c(s10C))).b());
                            return;
                        } else if (this.c.R()) {
                            int i2 = C1721bt.m;
                            interfaceC2641no.d(((C1646at) ((C1646at) new C1646at().a(this.b.f4.j)).a(AbstractC1639am.c(s10C))).b());
                            interfaceC2641no.previous();
                            return;
                        } else {
                            com.android.tools.r8.ir.optimize.T0 t0B = com.android.tools.r8.ir.optimize.U0.b(this.a, te.b(), c1230Ob);
                            t0B.a(te);
                            int i3 = C1721bt.m;
                            interfaceC2641no.d(((C1646at) ((C1646at) new C1646at().a(t0B.a().getReference())).a(AbstractC1639am.c(s10C))).b());
                            return;
                        }
                    }
                    return;
                }
            }
            interfaceC2641no.s();
        }
    }
}
