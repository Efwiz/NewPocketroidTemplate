package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.s20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2965s20 extends BE {
    static final /* synthetic */ boolean h = true;
    final IdentityHashMap g;

    public C2965s20(C0702m c0702m, com.android.tools.r8.graph.H3 h3, C1678bF c1678bF) {
        super(c0702m, h3, c1678bF);
        this.g = new IdentityHashMap();
    }

    private void l(final C0729q2 c0729q2) {
        final C2894r20 c2894r20 = new C2894r20(this, c0729q2);
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.s20$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2894r20, c0729q2, (com.android.tools.r8.graph.o5) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.h(consumer, CM.b());
        this.g.put(c0729q2, c2894r20);
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void a(Collection collection) {
        super.a(collection);
        if (!h) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                C0729q2 c0729q2 = (C0729q2) it.next();
                if (!h && !a(c0729q2)) {
                    throw new AssertionError();
                }
            }
        }
        boolean z = h;
        if (!z && !z && !this.g.isEmpty()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void i(C0729q2 c0729q2) {
        a(c0729q2, (C2894r20) this.g.remove(c0729q2));
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void k(C0729q2 c0729q2) {
        if (!h && this.g.containsKey(c0729q2)) {
            throw new AssertionError();
        }
        l(c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C2894r20 c2894r20, final C0729q2 c0729q2, final com.android.tools.r8.graph.o5 o5Var) {
        YE yeB = this.f.b(o5Var);
        if (yeB.d()) {
            return;
        }
        if (yeB.isUnknown()) {
            c2894r20.a.b(o5Var, C2508m10.k());
            return;
        }
        AbstractC1777cc abstractC1777ccE = yeB.e();
        abstractC1777ccE.getClass();
        if (abstractC1777ccE instanceof C1853dc) {
            return;
        }
        abstractC1777ccE.j().a(new BiConsumer() { // from class: com.android.tools.r8.internal.s20$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c2894r20, o5Var, c0729q2, (C1207Nf) obj, (InterfaceC2013fc) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2894r20 c2894r20, com.android.tools.r8.graph.o5 o5Var, C0729q2 c0729q2, C1207Nf c1207Nf, InterfaceC2013fc interfaceC2013fc) {
        C0767v2 c0767v2R;
        if (c1207Nf.m()) {
            c2894r20.a.a(this.a, o5Var, (YE) interfaceC2013fc);
            return;
        }
        C1229Oa c1229OaB = c1207Nf.o().b();
        if (c1229OaB.R() == this.a.a().Z1 && c1229OaB.S().e()) {
            c0767v2R = c1229OaB.S().d();
        } else {
            c0767v2R = c1229OaB.R();
        }
        C0729q2 c0729q2A = C0729q2.a(this.a.c(c0767v2R));
        if (c0729q2A == null || c0729q2A == c0729q2) {
            if (c1207Nf.g()) {
                ((C1751cF) c2894r20.b.computeIfAbsent(c1207Nf.d().R(), com.android.tools.r8.utils.Z1.a(new r20$$ExternalSyntheticLambda2()))).a(this.a, o5Var, (YE) interfaceC2013fc);
                return;
            } else {
                c2894r20.a.a(this.a, o5Var, (YE) interfaceC2013fc);
                return;
            }
        }
        if (!h && c0729q2.getType().c(this.a).b(c1229OaB, this.a)) {
            throw new AssertionError();
        }
        ((C1751cF) c2894r20.c.computeIfAbsent(c1207Nf, com.android.tools.r8.utils.Z1.a(new r20$$ExternalSyntheticLambda2()))).a(this.a, o5Var, (YE) interfaceC2013fc);
    }

    private void a(C0729q2 c0729q2, final C2894r20 c2894r20) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.s20$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2894r20, (com.android.tools.r8.graph.o5) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.h(consumer, CM.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2894r20 c2894r20, com.android.tools.r8.graph.o5 o5Var) {
        if (!o5Var.e().q1()) {
            this.f.a(o5Var, C3480z5.k());
            return;
        }
        C1678bF c1678bF = this.f;
        c1678bF.getClass();
        YE yeA = c1678bF.a(o5Var.getReference());
        if (yeA.h() || yeA.isUnknown()) {
            return;
        }
        if (!h && !yeA.d() && !yeA.c()) {
            throw new AssertionError();
        }
        if (!C2894r20.e) {
            c2894r20.getClass();
            if (!o5Var.e().H1()) {
                throw new AssertionError();
            }
        }
        YE yeA2 = c2894r20.a.b(o5Var).a();
        if (!c2894r20.b.isEmpty()) {
            AbstractC0717o2 abstractC0717o2Z = o5Var.z();
            Iterator it = c2894r20.b.values().iterator();
            while (it.hasNext()) {
                yeA2 = yeA2.a(c2894r20.d.a, abstractC0717o2Z, ((C1751cF) it.next()).b(o5Var), FW.a());
            }
        }
        if (!h && yeA2.f()) {
            AbstractC1777cc abstractC1777ccE = yeA2.e();
            abstractC1777ccE.getClass();
            if (!(abstractC1777ccE instanceof C1853dc)) {
                throw new AssertionError();
            }
        }
        this.f.b(o5Var, yeA2);
    }
}
