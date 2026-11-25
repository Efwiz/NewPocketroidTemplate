package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0792w4;
import com.android.tools.r8.utils.AbstractC3951q0;
import com.android.tools.r8.utils.InterfaceC3936n0;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class FP extends AbstractC3951q0 {
    static final /* synthetic */ boolean i = true;
    final /* synthetic */ GP f;
    final /* synthetic */ JP g;
    final /* synthetic */ IP h;

    FP(IP ip, GP gp, JP jp) {
        this.h = ip;
        this.f = gp;
        this.g = jp;
    }

    @Override // com.android.tools.r8.utils.AbstractC3951q0
    protected final com.android.tools.r8.utils.D3 a(InterfaceC3936n0 interfaceC3936n0, final Function function) {
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) interfaceC3936n0.c();
        interfaceC3936n0.a(new HP());
        if (abstractC0727q0.n1() != null) {
            C0702m c0702m = this.h.a;
            com.android.tools.r8.graph.O oF = c0702m.f().f(abstractC0727q0.n1());
            Objects.requireNonNull(function);
            oF.b(new Consumer() { // from class: com.android.tools.r8.internal.FP$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    function.apply((AbstractC0727q0) obj);
                }
            });
        }
        return com.android.tools.r8.utils.D3.e();
    }

    @Override // com.android.tools.r8.utils.AbstractC3951q0
    protected final com.android.tools.r8.utils.C3 a(InterfaceC3936n0 interfaceC3936n0, List list) {
        final AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) interfaceC3936n0.c();
        boolean z = i;
        if (!z && list.size() < 1) {
            C0767v2 type = abstractC0727q0.getType();
            IP ip = this.h;
            if (type != ip.b.Z1 && !abstractC0727q0.a((C0680j) ip.a.f())) {
                throw new AssertionError();
            }
        }
        if (!z && list.size() > 1 && !this.h.c.K0) {
            throw new AssertionError();
        }
        final HP hp = (HP) interfaceC3936n0.a();
        if (!list.isEmpty()) {
            hp.a.add((HP) ((InterfaceC3936n0) list.get(0)).a());
        }
        final Map mapB = abstractC0727q0.e0() ? this.h.b(abstractC0727q0.a0(), hp, this.f) : null;
        C0792w4 c0792w4Y = abstractC0727q0.Y();
        final GP gp = this.f;
        final JP jp = this.g;
        c0792w4Y.d(new Function() { // from class: com.android.tools.r8.internal.FP$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(abstractC0727q0, hp, mapB, gp, jp, (com.android.tools.r8.graph.W0) obj);
            }
        });
        return com.android.tools.r8.utils.D3.b(hp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W0 a(AbstractC0727q0 abstractC0727q0, HP hp, Map map, GP gp, JP jp, final com.android.tools.r8.graph.W0 w0) {
        AbstractC0717o2 abstractC0717o2A;
        AbstractC0717o2 abstractC0717o2O1 = w0.o1();
        if (!abstractC0727q0.e0()) {
            C0722p1 c0722p1 = this.h.b;
            hp.getClass();
            if (!abstractC0717o2O1.a().equals(c0722p1.Y0)) {
                hp.b.put(abstractC0717o2O1, abstractC0717o2O1);
            }
            return w0;
        }
        if (!i && map == null) {
            throw new AssertionError();
        }
        if (w0.C1()) {
            abstractC0717o2A = (AbstractC0717o2) map.get(abstractC0717o2O1);
        } else {
            abstractC0717o2A = hp.a(abstractC0717o2O1, this.h.b, gp, true);
        }
        if (abstractC0717o2O1.equals(abstractC0717o2A)) {
            C0722p1 c0722p2 = this.h.b;
            hp.getClass();
            if (!abstractC0717o2O1.a().equals(c0722p2.Y0)) {
                hp.b.put(abstractC0717o2O1, abstractC0717o2O1);
            }
            return w0;
        }
        C0729q2 c0729q2A0 = abstractC0727q0.a0();
        C0722p1 c0722p3 = this.h.b;
        abstractC0717o2A.getClass();
        C0767v2 type = c0729q2A0.getType();
        type.getClass();
        C0684j2 c0684j2A = c0722p3.a(type, abstractC0717o2A.b(), abstractC0717o2A.a());
        final com.android.tools.r8.graph.proto.k kVarA = jp.a(c0684j2A, w0);
        return w0.a(c0684j2A, new Consumer() { // from class: com.android.tools.r8.internal.FP$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FP.a(kVarA, w0, (com.android.tools.r8.graph.W0.a) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.proto.k kVar, com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0.a aVar) {
        if (kVar.g()) {
            return;
        }
        aVar.a(kVar.a(w0)).a(C0706m3.g.d());
    }
}
