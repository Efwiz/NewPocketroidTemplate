package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1745cA;
import com.android.tools.r8.internal.InterfaceC1001Fz;
import com.android.tools.r8.internal.InterfaceC1285Pz;
import com.android.tools.r8.utils.C3912j0;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class T0 extends U0<T0, Y0> implements com.android.tools.r8.utils.structural.x<T0> {
    public static final T0[] m = new T0[0];
    static final /* synthetic */ boolean n = true;
    public final Q2 f;
    private B2 g;
    private final boolean h;
    private C0706m3.e i;
    private com.android.tools.r8.ir.optimize.info.e j;
    private InterfaceC1001Fz k;
    private boolean l;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean m = true;
        private Y0 a;
        private C0658g0 b;
        private Q2 c;
        private C0706m3.e d;
        private InterfaceC1001Fz e;
        private B2 f;
        private com.android.tools.r8.androidapi.h g;
        private com.android.tools.r8.ir.optimize.info.e h;
        private boolean i;
        private final boolean j;
        private Consumer k;
        private boolean l;

        private a(boolean z) {
            this.b = C0658g0.t0();
            this.d = C0706m3.e.p();
            this.e = AbstractC1745cA.c();
            this.f = null;
            this.g = com.android.tools.r8.androidapi.h.w();
            this.h = com.android.tools.r8.ir.optimize.info.c.k();
            this.k = C3912j0.b();
            this.l = true;
            this.j = z;
        }

        public final a a(Consumer consumer) {
            consumer.accept(this);
            return this;
        }

        public final void b(Consumer consumer) {
            consumer.accept(this.c);
        }

        public final a c() {
            this.k = this.k.andThen(new Consumer() { // from class: com.android.tools.r8.graph.T0$a$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    T0.a.a((T0) obj);
                }
            });
            return this;
        }

        public a d() {
            return a(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(T0 t0) {
            com.android.tools.r8.ir.optimize.info.r.b().a(t0, AbstractC1154Lf.n());
        }

        public final a b() {
            this.b = C0658g0.t0();
            return this;
        }

        public final a a(C0658g0 c0658g0) {
            this.b = c0658g0;
            return this;
        }

        public final a b(boolean z) {
            this.i = z;
            return this;
        }

        public a a(Y0 y0) {
            this.a = y0;
            return this;
        }

        public final a a(Q2 q2) {
            this.c = q2;
            return this;
        }

        public final a a(com.android.tools.r8.androidapi.h hVar) {
            this.g = hVar;
            return this;
        }

        public final a a(C0706m3.e eVar) {
            this.d = eVar;
            return this;
        }

        public final a a(B2 b2) {
            this.f = b2;
            return this;
        }

        public final a a(boolean z) {
            if (z) {
                this.l = false;
            }
            return this;
        }

        public T0 a() {
            boolean z = m;
            if (!z && this.a == null) {
                throw new AssertionError();
            }
            if (!z && this.c == null) {
                throw new AssertionError();
            }
            if (!z && this.d == null) {
                throw new AssertionError();
            }
            if (!z && this.b == null) {
                throw new AssertionError();
            }
            if (!z && this.l && this.g.J()) {
                throw new AssertionError();
            }
            T0 t0 = new T0(this.a, this.c, this.d, this.b, this.f, this.g, this.i, this.j);
            t0.a(this.e);
            t0.j = this.h;
            this.k.accept(t0);
            return t0;
        }

        private a(boolean z, T0 t0) {
            com.android.tools.r8.ir.optimize.info.e eVarN;
            this.b = C0658g0.t0();
            this.d = C0706m3.e.p();
            this.e = AbstractC1745cA.c();
            this.f = null;
            this.g = com.android.tools.r8.androidapi.h.w();
            this.h = com.android.tools.r8.ir.optimize.info.c.k();
            this.k = C3912j0.b();
            this.l = true;
            this.a = t0.getReference();
            this.c = t0.f.N();
            this.d = t0.T0();
            this.e = t0.V0();
            this.b = t0.s0();
            this.f = t0.g;
            this.g = t0.H0();
            if (t0.j.d()) {
                eVarN = t0.j.c().n();
            } else {
                eVarN = t0.j;
            }
            this.h = eVarN;
            this.i = t0.a1();
            this.j = z;
        }
    }

    private T0(Y0 y0, Q2 q2, C0706m3.e eVar, C0658g0 c0658g0, B2 b2, com.android.tools.r8.androidapi.h hVar, boolean z, boolean z2) {
        super(y0, c0658g0, z2, hVar);
        this.j = com.android.tools.r8.ir.optimize.info.c.k();
        this.k = AbstractC1745cA.c();
        this.l = false;
        this.f = q2;
        this.g = b2;
        this.h = z;
        this.i = eVar;
        boolean z3 = n;
        if (!z3 && eVar == null) {
            throw new AssertionError();
        }
        if (!z3 && !E3.a(eVar, c0658g0)) {
            throw new AssertionError();
        }
    }

    public static a Q0() {
        return new a(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new T0$$ExternalSyntheticLambda1()).c(new Function() { // from class: com.android.tools.r8.graph.T0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((T0) obj).getAccessFlags();
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.T0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((T0) obj).g;
            }
        }).b(new Predicate() { // from class: com.android.tools.r8.graph.T0$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((T0) obj).a1();
            }
        }).a(new Predicate() { // from class: com.android.tools.r8.graph.T0$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return T0.c((T0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(T0 t0) {
        return t0.i.a();
    }

    public static a d1() {
        return new a(true);
    }

    @Override // com.android.tools.r8.graph.U0
    public final void E() {
        this.i = C0706m3.e.p();
    }

    @Override // com.android.tools.r8.graph.O0
    public boolean E0() {
        return this.f.o();
    }

    @Override // com.android.tools.r8.graph.U0
    public final void G0() {
        this.k = AbstractC1745cA.c();
    }

    @Override // com.android.tools.r8.graph.U0
    public final com.android.tools.r8.androidapi.h H0() {
        return I0();
    }

    @Override // com.android.tools.r8.graph.U0
    public final com.android.tools.r8.ir.optimize.info.g L0() {
        return this.j;
    }

    public final void R0() {
        if (!n && !this.f.o()) {
            throw new AssertionError();
        }
        this.g = null;
    }

    @Override // com.android.tools.r8.graph.O0
    /* JADX INFO: renamed from: S0, reason: merged with bridge method [inline-methods] */
    public Q2 getAccessFlags() {
        return this.f;
    }

    public C0706m3.e T0() {
        return this.i;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final boolean U0() {
        return this.l;
    }

    public final InterfaceC1001Fz V0() {
        return this.k;
    }

    public final synchronized com.android.tools.r8.ir.optimize.info.k W0() {
        com.android.tools.r8.ir.optimize.info.k kVar;
        kVar = (com.android.tools.r8.ir.optimize.info.k) this.j.b();
        this.j = kVar;
        return kVar;
    }

    public final com.android.tools.r8.ir.optimize.info.e X0() {
        return this.j;
    }

    public B2 Y0() {
        if (!n && !this.f.o()) {
            throw new AssertionError();
        }
        B2 b2 = this.g;
        return b2 == null ? B2.a(getReference().h) : b2;
    }

    @Override // com.android.tools.r8.graph.U0
    public final InterfaceC1285Pz Z() {
        return this.k;
    }

    public boolean Z0() {
        if (n || this.f.o()) {
            return this.g != null;
        }
        throw new AssertionError();
    }

    public final boolean a1() {
        return this.h;
    }

    public final boolean b1() {
        return this.f.R();
    }

    public final boolean c1() {
        return this.f.V();
    }

    public C0767v2 e1() {
        return getReference().h;
    }

    public C0767v2 getType() {
        return getReference().getType();
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.T0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                T0.a(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final String q0() {
        return getReference().q0();
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public String r0() {
        return getReference().r0();
    }

    public final String toString() {
        return "Encoded field " + getReference();
    }

    @Override // com.android.tools.r8.graph.O0
    public final T0 u0() {
        return this;
    }

    public final boolean b(C0722p1 c0722p1) {
        if (this.f.o() && this.g != null) {
            if (getReference().h.W0() && !n && this.g.a(c0722p1) != getReference().h) {
                throw new AssertionError("Static " + getReference() + " has invalid static value " + this.g + ".");
            }
            if (this.g.b1() && !n && !getReference().h.X0()) {
                throw new AssertionError("Static " + getReference() + " has invalid null static value.");
            }
        }
        return true;
    }

    public final void a(com.android.tools.r8.ir.optimize.info.k kVar) {
        this.j = kVar;
    }

    public final void a(InterfaceC1001Fz interfaceC1001Fz) {
        if (!n && this.k != AbstractC1745cA.c()) {
            throw new AssertionError();
        }
        this.k = interfaceC1001Fz;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        C0658g0 c0658g0S0 = s0();
        c0658g0S0.getClass();
        v.a(c0658g0S0);
        AbstractC0594a1.a(v, c0658g0S0.c);
    }

    @Override // com.android.tools.r8.graph.U0
    public final Object a(Function function, Function function2) {
        return function.apply(this);
    }

    public final void a(B2 b2) {
        boolean z = n;
        if (!z && !this.f.o()) {
            throw new AssertionError();
        }
        if (!z && b2 == null) {
            throw new AssertionError();
        }
        this.g = b2;
    }

    public final T0 a(C0702m c0702m, Y0 y0, Consumer consumer) {
        if (getReference() == y0) {
            return this;
        }
        return a(this).a(y0).a((c0702m.Y().a().b && c0702m.q()) ? false : true).a(consumer).a();
    }

    public final void a(C0706m3.e eVar) {
        if (!n && eVar == null) {
            throw new AssertionError();
        }
        this.i = eVar;
    }

    public static a a(T0 t0) {
        return new a(t0.N0(), t0);
    }

    public final boolean a(C0722p1 c0722p1) {
        if (this.l) {
            return true;
        }
        if (!E0() || !this.f.h() || !Z0()) {
            return false;
        }
        if (getType().W0()) {
            return true;
        }
        if (getType() != c0722p1.X1 || !Y0().e1()) {
            return false;
        }
        this.l = true;
        return true;
    }
}
