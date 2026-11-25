package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.origin.Origin;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Z9 extends com.android.tools.r8.graph.W {
    static final /* synthetic */ boolean f = true;
    private final AbstractC1639am d;
    private final C0684j2 e;

    private Z9(AbstractC1639am abstractC1639am, C0684j2 c0684j2) {
        this.d = abstractC1639am;
        this.e = c0684j2;
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        if (!f && this.d.isEmpty()) {
            throw new AssertionError();
        }
        AbstractC1915eM.c cVarA = AbstractC1915eM.c.x().a(0).a(this.e).a();
        C3155ul c3155ul = new C3155ul();
        C2145hI c2145hI = new C2145hI();
        C2145hI c2145hI2 = new C2145hI();
        C3332x4 c3332x4 = new C3332x4();
        c3332x4.g(c2145hI.a());
        AbstractC2963s10 it = this.d.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.o5 o5Var2 = (com.android.tools.r8.graph.o5) it.next();
            int i = C1721bt.m;
            c3332x4.a(((C1646at) ((C1646at) new C1646at().a(o5Var2.getReference())).a(cVarA)).b(), c3155ul);
        }
        int i2 = C1616aU.j;
        c3332x4.a(((ZT) new ZT().a(AbstractC1915eM.u())).b(), c3155ul);
        c3332x4.Y();
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        LinkedList linkedList = new LinkedList();
        linkedList.add(c3332x4);
        C2409kl c2409kl = new C2409kl(a1Y, o5Var, cVarA, linkedList, c2145hI2, c2145hI, c3155ul, origin, aVar);
        B4 b4S = c2409kl.s();
        InterfaceC2641no interfaceC2641noA = b4S.next().a(c2409kl);
        Set<C3332x4> setC = OU.c();
        AbstractC2963s10 it2 = this.d.iterator();
        while (it2.hasNext()) {
            com.android.tools.r8.graph.o5 o5Var3 = (com.android.tools.r8.graph.o5) it2.next();
            if (!interfaceC2641noA.hasNext()) {
                interfaceC2641noA = b4S.next().a(c2409kl);
            }
            InterfaceC2641no interfaceC2641no = interfaceC2641noA;
            C1721bt c1721btG0 = interfaceC2641no.next().g0();
            if (!f && c1721btG0 == null) {
                throw new AssertionError();
            }
            C2409kl c2409klA = o5Var3.e().a1().a(o5Var, o5Var3, c0702m, c0702m.n(), c2145hI2, cVarA, o5Var3.getOrigin(), com.android.tools.r8.graph.proto.k.h());
            interfaceC2641no.previous();
            Set<C3332x4> set = setC;
            interfaceC2641no.a((C0702m<?>) c0702m, c2409kl, c2409klA, b4S, set, (C0729q2) null);
            setC = set;
            interfaceC2641noA = interfaceC2641no;
        }
        c2409kl.b((Collection<C3332x4>) setC);
        c2409kl.y();
        return c2409kl;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.J5 j5) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(com.android.tools.r8.graph.S s, com.android.tools.r8.graph.J5 j5) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.utils.P2 p2) {
        throw new C3405y10();
    }
}
