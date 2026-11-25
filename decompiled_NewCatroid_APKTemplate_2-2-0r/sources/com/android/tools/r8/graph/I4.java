package com.android.tools.r8.graph;

import com.android.tools.r8.internal.JA;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3912j0;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class I4 extends AbstractC0713n4<W0, C0684j2> {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class a extends H4 {
        protected final Collection b;

        private a(Collection collection) {
            this.b = collection;
        }

        @Override // com.android.tools.r8.graph.I4
        public final /* bridge */ /* synthetic */ W0 a(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
            return null;
        }

        @Override // com.android.tools.r8.graph.I4
        public final /* bridge */ /* synthetic */ InterfaceC0669h4 a(AbstractC0727q0 abstractC0727q0, C0680j c0680j) {
            return null;
        }

        @Override // com.android.tools.r8.graph.I4
        public final /* bridge */ /* synthetic */ InterfaceC0707m4 a(com.android.tools.r8.shaking.U0 u0, C0680j c0680j) {
            return null;
        }

        @Override // com.android.tools.r8.graph.I4
        public final /* bridge */ /* synthetic */ W0 b(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
            return null;
        }

        @Override // com.android.tools.r8.graph.I4
        public final /* bridge */ /* synthetic */ AbstractC0748t0 c(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
            return null;
        }

        @Override // com.android.tools.r8.graph.AbstractC0713n4
        public final boolean h() {
            return true;
        }

        @Override // com.android.tools.r8.graph.I4
        public final a k() {
            return this;
        }

        @Override // com.android.tools.r8.graph.I4
        public final boolean y() {
            return false;
        }

        public boolean z() {
            return false;
        }

        @Override // com.android.tools.r8.graph.I4
        public final AbstractC3938n2 b(InterfaceC0694k5 interfaceC0694k5, C0702m c0702m) {
            return AbstractC3938n2.b;
        }

        @Override // com.android.tools.r8.graph.I4
        public final AbstractC0701l4 a(C0729q2 c0729q2, C0702m c0702m, K3 k3, InterfaceC0679i5 interfaceC0679i5) {
            return AbstractC0701l4.c();
        }

        @Override // com.android.tools.r8.graph.I4
        public final AbstractC0701l4 a(C0729q2 c0729q2, C0702m c0702m, C0729q2 c0729q3, C0729q2 c0729q4) {
            return AbstractC0701l4.c();
        }

        public void a(Consumer<C0767v2> consumer, Consumer<? super W0> consumer2) {
            Collection collection = this.b;
            if (collection != null) {
                collection.forEach(consumer);
            }
        }

        @Override // com.android.tools.r8.graph.AbstractC0713n4
        public final AbstractC3938n2 a(InterfaceC0694k5 interfaceC0694k5, C0702m c0702m, C0680j c0680j) {
            return AbstractC3938n2.b;
        }

        @Override // com.android.tools.r8.graph.I4
        public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3, Consumer consumer4) {
            consumer4.accept(this);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b extends a {
        static final b c = new b();

        /* JADX WARN: Multi-variable type inference failed */
        private b() {
            super(null);
        }

        public static b A() {
            return c;
        }

        @Override // com.android.tools.r8.graph.I4
        public final boolean b(AbstractC0727q0 abstractC0727q0, C0702m c0702m, C0680j c0680j) {
            return true;
        }

        @Override // com.android.tools.r8.graph.I4
        public final b l() {
            return this;
        }

        @Override // com.android.tools.r8.graph.I4
        public final boolean u() {
            return true;
        }

        protected b(Set set) {
            super(set);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class c<T extends AbstractC0727q0> extends I4 implements C5 {
        static final /* synthetic */ boolean e = true;
        private final AbstractC0727q0 b;
        private final AbstractC0727q0 c;
        private final W0 d;

        public c(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, W0 w0) {
            boolean z = e;
            if (!z && abstractC0727q0 == null) {
                throw new AssertionError();
            }
            if (!z && abstractC0727q1 == null) {
                throw new AssertionError();
            }
            if (!z && w0 == null) {
                throw new AssertionError();
            }
            if (!z && abstractC0727q1.d != w0.J0()) {
                throw new AssertionError();
            }
            this.c = abstractC0727q1;
            this.d = w0;
            this.b = abstractC0727q0;
            if (!z && w0.L1() && abstractC0727q0.d != w0.J0()) {
                throw new AssertionError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
            return true;
        }

        public abstract c a(AbstractC0727q0 abstractC0727q0);

        @Override // com.android.tools.r8.graph.C5
        public final U0 b() {
            return this.d;
        }

        @Override // com.android.tools.r8.graph.I4
        public final AbstractC0748t0 c(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
            AbstractC0727q0 abstractC0727q0;
            if (this.d.C1() || !(((abstractC0727q0 = this.b) == c0729q2 || c0680j.b(c0729q2.d, abstractC0727q0.d)) && a((InterfaceC0694k5) c0729q2, c0702m, c0680j).c())) {
                return null;
            }
            return a(c0729q2, c0680j, new BiPredicate() { // from class: com.android.tools.r8.graph.I4$c$$ExternalSyntheticLambda7
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return I4.c.a((AbstractC0727q0) obj, (AbstractC0727q0) obj2);
                }
            });
        }

        @Override // com.android.tools.r8.graph.I4, com.android.tools.r8.graph.C5
        public T d() {
            return (T) this.c;
        }

        @Override // com.android.tools.r8.graph.I4, com.android.tools.r8.graph.AbstractC0713n4
        public final C5 g() {
            return this;
        }

        @Override // com.android.tools.r8.graph.I4, com.android.tools.r8.graph.AbstractC0713n4
        public final boolean j() {
            return true;
        }

        @Override // com.android.tools.r8.graph.I4
        public final c o() {
            return this;
        }

        @Override // com.android.tools.r8.graph.I4
        /* JADX INFO: renamed from: q */
        public final AbstractC0748t0 c() {
            return AbstractC0748t0.a(this.c, this.d);
        }

        @Override // com.android.tools.r8.graph.I4
        public W0 r() {
            return this.d;
        }

        @Override // com.android.tools.r8.graph.I4
        public final o5 s() {
            if (this.c.e0()) {
                return new o5(this.c.a0(), this.d);
            }
            return null;
        }

        @Override // com.android.tools.r8.graph.I4
        public final boolean x() {
            return true;
        }

        @Override // com.android.tools.r8.graph.I4
        public final boolean y() {
            return this.d.S1();
        }

        @Override // com.android.tools.r8.graph.AbstractC0713n4
        public final AbstractC3938n2 a(InterfaceC0694k5 interfaceC0694k5, C0702m c0702m, C0680j c0680j) {
            return AbstractC0639e.a(c(), this.b, interfaceC0694k5, c0702m, c0680j);
        }

        @Override // com.android.tools.r8.graph.I4
        public final AbstractC3938n2 b(InterfaceC0694k5 interfaceC0694k5, C0702m c0702m) {
            return this.d.S1() ? a(interfaceC0694k5, c0702m, (C0680j) c0702m.f()) : AbstractC3938n2.b;
        }

        @Override // com.android.tools.r8.graph.I4, com.android.tools.r8.graph.C5
        public final AbstractC0727q0 a() {
            return this.b;
        }

        private static AbstractC0727q0 a(C0680j c0680j, C0767v2 c0767v2) {
            if (c0767v2 == null) {
                return null;
            }
            O oF = c0680j.f(c0767v2);
            if (c0680j.l().I0) {
                return oF.x();
            }
            return oF.R();
        }

        @Override // com.android.tools.r8.graph.I4
        public final AbstractC0748t0 b(C0729q2 c0729q2, C0702m c0702m) {
            final C0680j c0680j = (C0680j) c0702m.f();
            if (a((InterfaceC0694k5) c0729q2, (C0702m<? extends C0680j>) c0702m).c()) {
                return a(c0729q2, c0680j, new BiPredicate() { // from class: com.android.tools.r8.graph.I4$c$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiPredicate
                    public final boolean test(Object obj, Object obj2) {
                        return I4.c.a(c0680j, (AbstractC0727q0) obj, (AbstractC0727q0) obj2);
                    }
                });
            }
            return null;
        }

        @Override // com.android.tools.r8.graph.I4
        public final W0 b(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
            if (!a((InterfaceC0694k5) c0729q2, c0702m, c0680j).a() && this.d.E0()) {
                return this.d;
            }
            return null;
        }

        @Override // com.android.tools.r8.graph.I4
        public final W0 a(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
            if (!a((InterfaceC0694k5) c0729q2, c0702m, c0680j).a() && this.d.y1()) {
                return this.d;
            }
            return null;
        }

        private AbstractC0748t0 a(C0729q2 c0729q2, C0680j c0680j, BiPredicate biPredicate) {
            if (r().E0()) {
                return null;
            }
            if (d().isInterface() && r().O0()) {
                return c();
            }
            AbstractC0727q0 abstractC0727q0 = this.b;
            AbstractC0727q0 abstractC0727q0A = (this.d.C1() || abstractC0727q0.isInterface() || !biPredicate.test(abstractC0727q0, c0729q2)) ? abstractC0727q0 : a(c0680j, c0729q2.f);
            if (abstractC0727q0A == null) {
                return null;
            }
            C0684j2 c0684j2M0 = r().getReference();
            AbstractC0727q0 abstractC0727q0A2 = abstractC0727q0A;
            AbstractC0748t0 abstractC0748t0B = null;
            while (abstractC0727q0A2 != null) {
                abstractC0748t0B = abstractC0727q0A2.a(c0684j2M0);
                if (abstractC0748t0B != null) {
                    break;
                }
                abstractC0727q0A2 = a(c0680j, abstractC0727q0A2.f);
            }
            if (abstractC0748t0B == null) {
                abstractC0748t0B = c0680j.b(abstractC0727q0A, c0684j2M0);
            }
            if (abstractC0748t0B == null || abstractC0748t0B.getAccessFlags().o()) {
                return null;
            }
            if (((abstractC0748t0B.e().C1() || abstractC0748t0B.getAccessFlags().j()) && abstractC0748t0B.t() != abstractC0727q0.d) || abstractC0748t0B.getAccessFlags().W()) {
                return null;
            }
            return abstractC0748t0B;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean a(C0680j c0680j, AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
            return c0680j.b(abstractC0727q1.d, abstractC0727q0.d);
        }

        @Override // com.android.tools.r8.graph.I4
        public final AbstractC0701l4 a(C0729q2 c0729q2, final C0702m c0702m, K3 k3, InterfaceC0679i5 interfaceC0679i5) {
            final C0680j c0680j = (C0680j) c0702m.f();
            boolean z = e;
            if (!z && !c0680j.c(this.b.d, this.c.d)) {
                throw new AssertionError(this.b.d + " is not a subtype of " + this.c.d);
            }
            if (c0729q2 != null && a((InterfaceC0694k5) c0729q2, (C0702m<? extends C0680j>) c0702m).a()) {
                return AbstractC0701l4.b();
            }
            if (this.d.L1()) {
                boolean z2 = interfaceC0679i5.a(this.c) && interfaceC0679i5.a(this.d);
                AbstractC0748t0 abstractC0748t0C = c();
                return AbstractC0701l4.a(Collections.singletonMap(abstractC0748t0C.getReference(), abstractC0748t0C), Collections.emptyList(), Collections.emptyList(), z2 ? 2 : 1);
            }
            if (!z && !this.d.H1()) {
                throw new AssertionError();
            }
            final C0693k4 c0693k4F = AbstractC0701l4.a.f();
            final C0655f4 c0655f4 = new C0655f4(interfaceC0679i5);
            k3.a(this.b.d, new Consumer() { // from class: com.android.tools.r8.graph.I4$c$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c0655f4, c0680j, c0693k4F, (C0729q2) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.graph.I4$c$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c0702m, c0680j, c0693k4F, (JA) obj);
                }
            });
            return c0693k4F.a(c0655f4.a(this.d.getReference(), c0680j)).a();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(C0655f4 c0655f4, C0680j c0680j, C0693k4 c0693k4, C0729q2 c0729q2) {
            c0655f4.a(c0729q2);
            C0767v2 c0767v2 = this.c.d;
            Objects.requireNonNull(c0693k4);
            InterfaceC0669h4 interfaceC0669h4A = a(c0729q2, c0680j, c0767v2, new I4$c$$ExternalSyntheticLambda0(c0693k4), new I4$c$$ExternalSyntheticLambda1(c0693k4));
            if (interfaceC0669h4A != null) {
                c0655f4.a(interfaceC0669h4A);
                a(interfaceC0669h4A, this.c.isInterface(), c0693k4);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(C0702m c0702m, C0680j c0680j, C0693k4 c0693k4, JA ja) {
            if (!e && !this.c.isInterface() && this.c.d != c0702m.a().Z1) {
                throw new AssertionError();
            }
            Objects.requireNonNull(c0693k4);
            InterfaceC0707m4 interfaceC0707m4A = a(ja, c0680j, new I4$c$$ExternalSyntheticLambda0(c0693k4), new I4$c$$ExternalSyntheticLambda1(c0693k4));
            if (interfaceC0707m4A != null) {
                if (interfaceC0707m4A.n()) {
                    c0693k4.a(interfaceC0707m4A.o());
                } else {
                    a(interfaceC0707m4A.f(), this.c.isInterface(), c0693k4);
                }
            }
        }

        @Override // com.android.tools.r8.graph.I4
        public final AbstractC0701l4 a(C0729q2 c0729q2, C0702m c0702m, C0729q2 c0729q3, C0729q2 c0729q4) {
            C3733i c3733i = (C3733i) c0702m.f();
            boolean z = e;
            if (!z && c0729q3 == null) {
                throw new AssertionError();
            }
            if (!z && !c3733i.c(c0729q3.d, this.b.d)) {
                throw new AssertionError();
            }
            if (!z && c0729q4 != null && !c3733i.c(c0729q4.d, c0729q3.d)) {
                throw new AssertionError();
            }
            com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N(Boolean.FALSE);
            K3 k3A = a(c3733i, c0729q3, c0729q4, n);
            Objects.requireNonNull(c3733i);
            AbstractC0701l4 abstractC0701l4A = a(c0729q2, c0702m, k3A, new I4$$ExternalSyntheticLambda0(c3733i));
            if (((Boolean) n.a()).booleanValue() && abstractC0701l4A.e()) {
                abstractC0701l4A.a().k();
            }
            return abstractC0701l4A;
        }

        private static K3 a(final C3733i c3733i, final C0729q2 c0729q2, final C0729q2 c0729q3, final com.android.tools.r8.utils.N n) {
            return new K3() { // from class: com.android.tools.r8.graph.I4$c$$ExternalSyntheticLambda6
                @Override // com.android.tools.r8.graph.K3
                public final void a(C0767v2 c0767v2, Consumer consumer, Consumer consumer2) {
                    I4.c.a(c3733i, n, c0729q3, c0729q2, c0767v2, consumer, consumer2);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(final C3733i c3733i, final com.android.tools.r8.utils.N n, C0729q2 c0729q2, C0729q2 c0729q3, C0767v2 c0767v2, final Consumer consumer, Consumer consumer2) {
            Consumer consumer3 = new Consumer() { // from class: com.android.tools.r8.graph.I4$c$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    I4.c.a(consumer, c3733i, n, (C0729q2) obj);
                }
            };
            if (c0729q2 == null) {
                c3733i.a(c0729q3.d, consumer3, consumer2);
            } else {
                c3733i.a(c0729q3, c0729q2, consumer3);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(Consumer consumer, C3733i c3733i, com.android.tools.r8.utils.N n, C0729q2 c0729q2) {
            consumer.accept(c0729q2);
            if (c3733i.d(c0729q2)) {
                n.a(Boolean.TRUE);
            }
        }

        private static void a(InterfaceC0669h4 interfaceC0669h4, boolean z, C0693k4 c0693k4) {
            boolean z2 = e;
            if (!z2 && !interfaceC0669h4.h()) {
                throw new AssertionError();
            }
            W0 w0E = interfaceC0669h4.f().e();
            if (!z2 && w0E.L1()) {
                throw new AssertionError();
            }
            if (z) {
                if (w0E.w1()) {
                    c0693k4.a(interfaceC0669h4);
                }
                if (w0E.f.W() || !w0E.f.X()) {
                    return;
                }
                c0693k4.a(interfaceC0669h4);
                return;
            }
            c0693k4.a(interfaceC0669h4);
        }

        @Override // com.android.tools.r8.graph.I4
        public final InterfaceC0707m4 a(com.android.tools.r8.shaking.U0 u0, C0680j c0680j) {
            if (u0.c()) {
                return a(u0.a(), c0680j);
            }
            return a(u0.b(), c0680j, C3912j0.b(), C3912j0.b());
        }

        @Override // com.android.tools.r8.graph.I4
        public final InterfaceC0669h4 a(AbstractC0727q0 abstractC0727q0, C0680j c0680j) {
            return a(abstractC0727q0, c0680j, this.b.d, C3912j0.b(), C3912j0.b());
        }

        public final InterfaceC0707m4 a(JA ja, C0680j c0680j, Consumer consumer, Consumer consumer2) {
            if (ja.b().a(this.d)) {
                C0684j2 c0684j2U0 = ja.f.u0();
                AbstractC0748t0 abstractC0748t0A = c0684j2U0.a(a(c0680j, c0684j2U0.B0()));
                if (abstractC0748t0A == null) {
                    return null;
                }
                return new C0662g4(ja, abstractC0748t0A);
            }
            I4 i4A = c0680j.a(ja, this.d.getReference());
            if (i4A.x()) {
                return i4A.c();
            }
            if (i4A.h()) {
                i4A.k().a((Consumer<C0767v2>) consumer, (Consumer<? super W0>) consumer2);
                return null;
            }
            if (e || (i4A instanceof E4)) {
                return null;
            }
            throw new AssertionError();
        }

        private InterfaceC0669h4 a(AbstractC0727q0 abstractC0727q0, C0680j c0680j, C0767v2 c0767v2, Consumer consumer, Consumer consumer2) {
            if (!e && !c0680j.c(abstractC0727q0.d, c0767v2)) {
                throw new AssertionError(abstractC0727q0.d + " is not a subtype of " + c0767v2);
            }
            AbstractC0748t0 abstractC0748t0C = c();
            if (this.d.L1()) {
                return abstractC0748t0C;
            }
            boolean zI = this.d.f.i();
            AbstractC0727q0 abstractC0727q0A = abstractC0727q0;
            AbstractC0748t0 abstractC0748t0A = abstractC0748t0C;
            while (abstractC0727q0A != null) {
                W0 w0E = abstractC0748t0A.e();
                W0 w0C = abstractC0727q0A.k.c(w0E.getReference());
                boolean z = e;
                if (!z && w0C != null && w0C.L1()) {
                    throw new AssertionError();
                }
                if (w0C == null) {
                    w0C = null;
                } else if (!a(w0E, w0C)) {
                    w0C = W0.t;
                }
                W0 w0 = W0.t;
                if (w0C == w0 && zI) {
                    if (!z && !abstractC0748t0C.e().f.i()) {
                        throw new AssertionError();
                    }
                    AbstractC0727q0 abstractC0727q0A2 = abstractC0727q0A;
                    while (true) {
                        C0767v2 c0767v3 = abstractC0727q0A2.f;
                        if (c0767v3 == null || (abstractC0727q0A2 = a(c0680j, c0767v3)) == null) {
                            abstractC0748t0A = abstractC0748t0C;
                        } else {
                            W0 w0C2 = abstractC0727q0A2.k.c(abstractC0748t0C.getReference());
                            if (w0C2 != null && a(abstractC0748t0C.e(), w0C2) && (w0C2.f.m() || w0C2.f.l())) {
                                if (!e && abstractC0748t0C.e() == w0C2) {
                                    throw new AssertionError();
                                }
                                abstractC0748t0A = AbstractC0748t0.a(abstractC0727q0A2, w0C2);
                            }
                        }
                        zI = false;
                    }
                } else {
                    if (w0C != null && w0C != w0) {
                        AbstractC0748t0 abstractC0748t0A2 = AbstractC0748t0.a(abstractC0727q0A, w0C);
                        return abstractC0748t0A != abstractC0748t0C ? new C0678i4(abstractC0748t0A2, abstractC0748t0A) : abstractC0748t0A2;
                    }
                    if (abstractC0727q0A.d == abstractC0748t0A.t()) {
                        return null;
                    }
                    abstractC0727q0A = a(c0680j, abstractC0727q0A.f);
                }
            }
            if (!this.c.isInterface()) {
                return null;
            }
            I4 i4C = c0680j.c(abstractC0727q0, this.d.getReference());
            if (i4C.x()) {
                return i4C.c();
            }
            if (i4C.h()) {
                i4C.k().a((Consumer<C0767v2>) consumer, (Consumer<? super W0>) consumer2);
                return null;
            }
            if (e || (i4C instanceof E4)) {
                return null;
            }
            throw new AssertionError();
        }

        public static boolean a(W0 w0, W0 w1) {
            boolean z = e;
            if (!z && !w0.getReference().b(w1.getReference())) {
                throw new AssertionError();
            }
            if (!z && w1.L1()) {
                throw new AssertionError();
            }
            if (w0.f.m() || w0.f.l()) {
                return true;
            }
            if (z || w0.f.i()) {
                return w0.J0().a(w1.J0());
            }
            throw new AssertionError();
        }
    }

    public static F4 p() {
        return new F4();
    }

    public abstract W0 a(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j);

    public abstract InterfaceC0669h4 a(AbstractC0727q0 abstractC0727q0, C0680j c0680j);

    public abstract AbstractC0701l4 a(C0729q2 c0729q2, C0702m<? extends C0680j> c0702m, K3 k3, InterfaceC0679i5 interfaceC0679i5);

    public abstract AbstractC0701l4 a(C0729q2 c0729q2, C0702m<C3733i> c0702m, C0729q2 c0729q3, C0729q2 c0729q4);

    public abstract InterfaceC0707m4 a(com.android.tools.r8.shaking.U0 u0, C0680j c0680j);

    public AbstractC0727q0 a() {
        return null;
    }

    public abstract void a(Consumer consumer, Consumer consumer2, Consumer consumer3, Consumer consumer4);

    public boolean a(AbstractC0727q0 abstractC0727q0, C0702m<?> c0702m, C0680j c0680j) {
        return false;
    }

    public abstract W0 b(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j);

    public abstract AbstractC0748t0 b(C0729q2 c0729q2, C0702m<? extends C0680j> c0702m);

    public abstract AbstractC3938n2 b(InterfaceC0694k5 interfaceC0694k5, C0702m<? extends C0680j> c0702m);

    public boolean b(AbstractC0727q0 abstractC0727q0, C0702m<?> c0702m, C0680j c0680j) {
        return false;
    }

    public final W0 c(C0729q2 c0729q2, C0702m c0702m) {
        return b(c0729q2, c0702m, (C0680j) c0702m.f());
    }

    public abstract AbstractC0748t0 c(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j);

    public AbstractC0727q0 d() {
        return null;
    }

    public final AbstractC0701l4 e(C0729q2 c0729q2, C0702m<C3733i> c0702m) {
        C3733i c3733i = (C3733i) c0702m.f();
        Objects.requireNonNull(c3733i);
        return a(c0729q2, c0702m, c3733i, new I4$$ExternalSyntheticLambda0(c3733i));
    }

    @Override // com.android.tools.r8.graph.AbstractC0713n4
    public final I4 f() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0713n4
    public C5 g() {
        return null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0713n4
    public boolean j() {
        return false;
    }

    public a k() {
        return null;
    }

    public b l() {
        return null;
    }

    public S4 m() {
        return null;
    }

    protected U4 n() {
        return null;
    }

    public c<?> o() {
        return null;
    }

    /* JADX INFO: renamed from: q, reason: merged with bridge method [inline-methods] */
    public AbstractC0748t0 c() {
        return null;
    }

    public W0 r() {
        return null;
    }

    public o5 s() {
        return null;
    }

    public final W0 t() {
        if (x()) {
            return o().r();
        }
        return null;
    }

    public boolean u() {
        return false;
    }

    public boolean v() {
        return false;
    }

    public boolean w() {
        return false;
    }

    public boolean x() {
        return false;
    }

    public abstract boolean y();

    public final boolean b(AbstractC0727q0 abstractC0727q0, C0702m<? extends C0680j> c0702m) {
        return b(abstractC0727q0, c0702m, (C0680j) c0702m.f());
    }

    public final AbstractC0748t0 d(C0729q2 c0729q2, C0702m<? extends C0680j> c0702m) {
        return c(c0729q2, c0702m, (C0680j) c0702m.f());
    }

    public final boolean a(AbstractC0727q0 abstractC0727q0, C0702m<? extends C0680j> c0702m) {
        return a(abstractC0727q0, c0702m, (C0680j) c0702m.f());
    }

    public final void a(Consumer<I4> consumer) {
        a(consumer, consumer, consumer, consumer);
    }

    public final W0 a(C0729q2 c0729q2, C0702m c0702m) {
        return a(c0729q2, c0702m, (C0680j) c0702m.f());
    }

    public static c a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, W0 w0) {
        if (abstractC0727q1.f0()) {
            return new T4(abstractC0727q0, abstractC0727q1.d0(), w0);
        }
        if (abstractC0727q1 instanceof C0754u0) {
            return new S4(abstractC0727q0, abstractC0727q1.m(), w0);
        }
        if (a || abstractC0727q1.e0()) {
            return new U4(abstractC0727q0, abstractC0727q1.a0(), w0);
        }
        throw new AssertionError();
    }
}
