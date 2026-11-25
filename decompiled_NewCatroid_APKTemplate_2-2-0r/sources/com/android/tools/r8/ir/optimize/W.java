package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AF;
import com.android.tools.r8.internal.AbstractC1084Is;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C1621aZ;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2179hn;
import com.android.tools.r8.internal.C2289jB;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3344xF;
import com.android.tools.r8.internal.C3387xn;
import com.android.tools.r8.internal.C3446yc;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.EnumC3423yF;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.internal.ML;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.V1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class W extends X {
    static final /* synthetic */ boolean f = true;
    public final o5 a;
    public final AbstractC1084Is b;
    final EnumC3542a0 c;
    private boolean d;
    private C0729q2 e;

    W(o5 o5Var, AbstractC1084Is abstractC1084Is, EnumC3542a0 enumC3542a0) {
        this.a = o5Var;
        this.b = abstractC1084Is;
        this.c = enumC3542a0;
    }

    @Override // com.android.tools.r8.ir.optimize.X
    final W a() {
        return this;
    }

    final void b() {
        this.d = true;
    }

    final void a(C0729q2 c0729q2) {
        this.e = c0729q2;
    }

    final Y a(final C0702m c0702m, final AbstractC1305Qs abstractC1305Qs, o5 o5Var, C3387xn c3387xn, C2289jB c2289jB) {
        InterfaceC2641no interfaceC2641noA;
        S10 s10B;
        S10 s10B2;
        C0722p1 c0722p1A = c0702m.a();
        A1 a1Y = c0702m.Y();
        final C2409kl c2409klB = c3387xn.b(this.a, abstractC1305Qs);
        if (this.d) {
            N0 n0A = P0.a(c0702m, o5Var, c2409klB);
            C3332x4 c3332x4J = c2409klB.j();
            Consumer consumerB = C3912j0.b();
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.W$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c2409klB, (C3332x4) obj);
                }
            };
            List listA = n0A.a();
            if (!n0A.b() && ((!n0A.c() || listA.size() <= 1) && (!n0A.c() || !((C3332x4) listA.get(0)).D()))) {
                n0A.a(consumerB);
                listA.forEach(consumer);
            } else {
                consumer.accept(c3332x4J);
            }
        }
        boolean z = this.a.e().f.c0() && (a1Y.f instanceof ClassFileConsumer);
        boolean z2 = z && !this.a.e().E0();
        if (abstractC1305Qs.Z1() && !abstractC1305Qs.c0().U2().P() && !z2) {
            N0 n0A2 = P0.a(c0702m, c2409klB);
            C3332x4 c3332x4J2 = c2409klB.j();
            Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.ir.optimize.W$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((AbstractC2412ko) obj);
                }
            };
            Consumer consumer3 = new Consumer() { // from class: com.android.tools.r8.ir.optimize.W$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c0702m, c2409klB, abstractC1305Qs, (C3332x4) obj);
                }
            };
            List listA2 = n0A2.a();
            if (!n0A2.b() && ((!n0A2.c() || listA2.size() <= 1) && (!n0A2.c() || !((C3332x4) listA2.get(0)).D()))) {
                n0A2.a(consumer2);
                listA2.forEach(consumer3);
            } else {
                consumer3.accept(c3332x4J2);
            }
        }
        if (z) {
            AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(c0722p1A.m3, WH.b(), (C0702m<?>) c0702m);
            c2409klB.x();
            C3332x4 c3332x4 = new C3332x4();
            c3332x4.g(c2409klB.f.a());
            ArrayList arrayList = new ArrayList();
            Iterator<C3332x4> it = c2409klB.d.iterator();
            while (it.hasNext()) {
                C3332x4 next = it.next();
                if (next.a() && (!next.D() || !next.o().a(c0722p1A))) {
                    C3332x4 c3332x4A = C3332x4.a(c2409klB.f.a(), AbstractC1915eM.u(), c2409klB.t(), c3332x4);
                    InterfaceC2641no interfaceC2641noA2 = c3332x4A.a(c2409klB);
                    interfaceC2641noA2.a(AbstractC1915eM.v());
                    interfaceC2641noA2.add(new AF(c2409klB.a(abstractC3472z00A), c0722p1A.m3, a1Y));
                    next.a(c3332x4A, c0722p1A.m3);
                    arrayList.add(c3332x4A);
                    it = it;
                }
            }
            if (arrayList.isEmpty()) {
                interfaceC2641noA = null;
            } else {
                if (arrayList.size() == 1) {
                    s10B2 = ((C3332x4) V1.a(arrayList)).p().getFirst().b();
                } else {
                    ML ml = new ML(c2409klB.e.a(), c3332x4, abstractC3472z00A, null, ML.a.a);
                    ml.a(V1.a((Collection) arrayList, new Function() { // from class: com.android.tools.r8.ir.optimize.W$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return W.a((C3332x4) obj);
                        }
                    }));
                    s10B2 = ml;
                }
                interfaceC2641noA = c3332x4.a(c2409klB);
                interfaceC2641noA.a(AbstractC1915eM.v());
                interfaceC2641noA.add(new C1621aZ(s10B2));
                c3332x4.r().addAll(arrayList);
                c2409klB.d.addAll(arrayList);
                c2409klB.d.add(c3332x4);
            }
            C3332x4 c3332x4J3 = c2409klB.j();
            InterfaceC2641no interfaceC2641noA3 = c3332x4J3.a(c2409klB);
            interfaceC2641noA3.a(CM.a(new InterfaceC3205vM() { // from class: com.android.tools.r8.ir.optimize.W$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return ((AbstractC2412ko) obj).m1();
                }
            }));
            interfaceC2641noA3.previous();
            C3332x4 c3332x4A2 = interfaceC2641noA3.a(c2409klB, 0, (ListIterator<C3332x4>) null);
            if (!f && c3332x4A2.D()) {
                throw new AssertionError();
            }
            InterfaceC2641no interfaceC2641noA4 = c3332x4A2.a(c2409klB);
            interfaceC2641noA4.a(abstractC1305Qs.Y0());
            if (this.a.e().E0()) {
                s10B = c2409klB.a(AbstractC3472z00.a(c0722p1A.Z1, WH.b(), (C0702m<?>) c0702m));
                interfaceC2641noA4.add(new C3446yc(s10B, this.a.t()));
            } else {
                s10B = c3332x4J3.p().getFirst().q().b();
            }
            interfaceC2641noA4.add(new C3344xF(EnumC3423yF.ENTER, s10B));
            if (interfaceC2641noA != null) {
                interfaceC2641noA.previous();
                interfaceC2641noA.add(new C3344xF(EnumC3423yF.EXIT, s10B));
                c3332x4.a((C2102gl) null);
            }
            for (C3332x4 c3332x5 : c2409klB.d) {
                if (c3332x5.m().u2()) {
                    if (!f && c3332x5.a()) {
                        throw new AssertionError();
                    }
                    InterfaceC2641no interfaceC2641noA5 = c3332x5.a(c2409klB, c3332x5.p().size() - 1);
                    interfaceC2641noA5.a(AbstractC1915eM.v());
                    interfaceC2641noA5.add(new C3344xF(EnumC3423yF.EXIT, s10B));
                }
            }
        }
        if (c3387xn.a(this.a)) {
            if (!f && c2289jB == null) {
                throw new AssertionError();
            }
            c2289jB.a(this.a, c2409klB, c3387xn.a());
        }
        Consumer<C2409kl> consumer4 = a1Y.n1.Z;
        if (consumer4 != null) {
            consumer4.accept(c2409klB);
        }
        if (f || c2409klB.b((C0702m<?>) c0702m)) {
            return new Y(c2409klB, this.c);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ S10 a(C3332x4 c3332x4) {
        return c3332x4.p().getFirst().b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2409kl c2409kl, C3332x4 c3332x4) {
        boolean z = f;
        if (!z && c3332x4.D()) {
            throw new AssertionError();
        }
        C3332x4 c3332x4A = c3332x4.a(c2409kl, c3332x4.y().isEmpty() ? c2409kl.c().size() : 0).a(c2409kl, 0, (ListIterator<C3332x4>) null);
        if (!z && c3332x4A.D()) {
            throw new AssertionError();
        }
        InterfaceC2641no interfaceC2641noA = c3332x4A.a(c2409kl);
        interfaceC2641noA.a(this.b.Y0());
        interfaceC2641noA.add(new C2179hn(this.a.t(), c2409kl.a((AbstractC3472z00) AbstractC3472z00.k())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0702m c0702m, C2409kl c2409kl, AbstractC1305Qs abstractC1305Qs, C3332x4 c3332x4) {
        List<S10> listC = c2409kl.c();
        if (!listC.isEmpty()) {
            S10 s10 = listC.get(0);
            boolean z = f;
            if (!z && !s10.Q()) {
                throw new AssertionError();
            }
            C3332x4 c3332x4A = c3332x4.a(c2409kl, c3332x4.y().isEmpty() ? listC.size() : 0).a(c2409kl, 0, (ListIterator<C3332x4>) null);
            if (!z && c3332x4A.D()) {
                throw new AssertionError();
            }
            InterfaceC2641no interfaceC2641noA = c3332x4A.a(c2409kl);
            interfaceC2641noA.a(abstractC1305Qs.Y0());
            interfaceC2641noA.add(new C2115gt(c0702m.a().g4.e, null, AbstractC1639am.c(s10)));
            return;
        }
        if (!f) {
            throw new AssertionError("Unable to synthesize a null check for the receiver");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC2412ko abstractC2412ko) {
        AbstractC1915eM abstractC1915eMY0 = abstractC2412ko.Y0();
        if (abstractC1915eMY0 == null) {
            if (!f) {
                throw new AssertionError("Expected position for inlinee call to receiver");
            }
        } else {
            AbstractC1915eM abstractC1915eMH = abstractC1915eMY0.h();
            abstractC2412ko.a(abstractC1915eMY0.a(abstractC1915eMH, abstractC1915eMH.a().b(true).a()));
        }
    }
}
