package com.android.tools.r8.graph;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.dex.code.C0430e0;
import com.android.tools.r8.dex.code.C0478n3;
import com.android.tools.r8.internal.A7;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1745cA;
import com.android.tools.r8.internal.AbstractC3477z20;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.C1071Ii;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C1818d7;
import com.android.tools.r8.internal.C1891e7;
import com.android.tools.r8.internal.C3017sq;
import com.android.tools.r8.internal.C3089ts;
import com.android.tools.r8.internal.C3109u8;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.D8;
import com.android.tools.r8.internal.EnumC1812d20;
import com.android.tools.r8.internal.EnumC2527mI;
import com.android.tools.r8.internal.I7;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.internal.InterfaceC1285Pz;
import com.android.tools.r8.internal.InterfaceC1894eA;
import com.android.tools.r8.internal.O7;
import com.android.tools.r8.internal.P7;
import com.android.tools.r8.internal.Q7;
import com.android.tools.r8.internal.R8;
import com.android.tools.r8.internal.V7;
import com.android.tools.r8.internal.V8;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.ir.optimize.AbstractC3578p0;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3912j0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class W0 extends U0<W0, C0684j2> implements com.android.tools.r8.utils.structural.x<W0> {
    public static final W0[] s = new W0[0];
    public static final W0 t = new W0(null, C0725p4.g(0), C0706m3.g.d(), C0658g0.t0(), C0663g5.t0(), null, false, com.android.tools.r8.androidapi.h.w(), com.android.tools.r8.androidapi.h.w(), null, com.android.tools.r8.ir.optimize.info.d.b, false);
    public static final C3017sq u = new C3017sq(0);
    static final /* synthetic */ boolean v = true;
    public final C0725p4 f;
    public final boolean g;
    public C0663g5 h;
    private W i;
    private X0 j;
    private com.android.tools.r8.ir.optimize.info.h k;
    private Z8 l;
    private com.android.tools.r8.androidapi.h m;
    private InterfaceC1894eA n;
    private C0706m3.g o;
    private AbstractC3938n2 p;
    private InterfaceC0992Fq q;
    private boolean r;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean t = true;
        private C0725p4 a;
        private W b;
        private C0684j2 c;
        private C0706m3.g d;
        private C0658g0 e;
        private AbstractC3938n2 f;
        private C0663g5 g;
        private X0 h;
        private com.android.tools.r8.ir.optimize.info.h i;
        private InterfaceC1894eA j;
        private Z8 k;
        private com.android.tools.r8.androidapi.h l;
        private com.android.tools.r8.androidapi.h m;
        private final boolean n;
        private boolean o;
        private boolean p;
        private boolean q;
        private boolean r;
        private Consumer s;

        private a(boolean z) {
            this.d = C0706m3.g.d();
            this.e = C0658g0.t0();
            this.f = AbstractC3938n2.c;
            this.g = C0663g5.t0();
            this.h = X0.NOT_PROCESSED;
            this.i = com.android.tools.r8.ir.optimize.info.d.b;
            this.j = AbstractC1745cA.c();
            this.k = null;
            this.l = com.android.tools.r8.androidapi.h.w();
            this.m = com.android.tools.r8.androidapi.h.w();
            this.o = false;
            this.p = true;
            this.q = true;
            this.r = true;
            this.s = C3912j0.b();
            this.n = z;
        }

        public final a a(Consumer consumer) {
            consumer.accept(this);
            return this;
        }

        public final a b(Consumer consumer) {
            consumer.accept(this.a);
            return this;
        }

        public a c() {
            this.r = false;
            return this;
        }

        public a d() {
            this.p = false;
            return this;
        }

        public final a e() {
            this.q = false;
            return this;
        }

        public final void f() {
            this.f = AbstractC3938n2.c;
        }

        public final a a(boolean z, Consumer consumer) {
            Consumer consumerB = C3912j0.b();
            if (z) {
                consumer.accept(this);
            } else {
                consumerB.accept(this);
            }
            return this;
        }

        public final a b() {
            this.e = C0658g0.t0();
            this.g = C0663g5.t0();
            return this;
        }

        public final a a(final C0702m c0702m, final w5 w5Var) {
            return a(new BiConsumer() { // from class: com.android.tools.r8.graph.W0$a$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((com.android.tools.r8.ir.optimize.info.l) obj2).a(c0702m, w5Var);
                }
            });
        }

        public final a b(com.android.tools.r8.androidapi.h hVar) {
            this.l = hVar;
            return this;
        }

        public a a(C0725p4 c0725p4) {
            this.a = c0725p4;
            return this;
        }

        public a a(C0684j2 c0684j2) {
            this.c = c0684j2;
            return this;
        }

        public final a a(X0 x0) {
            if (!t && this.h != X0.NOT_PROCESSED) {
                throw new AssertionError();
            }
            this.h = x0;
            return this;
        }

        public final a a(AbstractC3938n2 abstractC3938n2) {
            if (!t && abstractC3938n2.e()) {
                throw new AssertionError();
            }
            this.f = abstractC3938n2;
            return this;
        }

        public final a a(C0658g0 c0658g0) {
            this.e = c0658g0;
            return this;
        }

        public final a a(C0663g5 c0663g5) {
            this.g = c0663g5;
            return this;
        }

        public final void a(W0 w0, com.android.tools.r8.graph.proto.c cVar) {
            List list;
            if (this.g.isEmpty()) {
                return;
            }
            if (cVar.d() || cVar.e()) {
                ArrayList arrayList = new ArrayList(this.g.s0());
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < w0.l1().size(); i3++) {
                    if (!cVar.a(w0.d1() + i3).c()) {
                        if (this.g.k(i3)) {
                            i2++;
                        } else {
                            arrayList.add(this.g.j(i3));
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    this.g = C0663g5.t0();
                    return;
                }
                if (!cVar.d()) {
                    i = i2;
                    list = arrayList;
                } else {
                    if (i2 > 0) {
                        this.g = C0663g5.t0();
                        return;
                    }
                    List listAsList = Arrays.asList(new C0658g0[w0.l1().size()]);
                    for (int i4 = i2; i4 < w0.l1().size(); i4++) {
                        listAsList.set(cVar.a(w0.d1() + i4, 0) - w0.d1(), (C0658g0) arrayList.get(i4 - i2));
                    }
                    list = listAsList;
                }
                this.g = C0663g5.a((C0658g0[]) list.toArray(C0658g0.e), i);
            }
        }

        private a(boolean z, W0 w0) {
            com.android.tools.r8.ir.optimize.info.h hVarI1;
            this.d = C0706m3.g.d();
            this.e = C0658g0.t0();
            this.f = AbstractC3938n2.c;
            this.g = C0663g5.t0();
            this.h = X0.NOT_PROCESSED;
            this.i = com.android.tools.r8.ir.optimize.info.d.b;
            this.j = AbstractC1745cA.c();
            this.k = null;
            this.l = com.android.tools.r8.androidapi.h.w();
            this.m = com.android.tools.r8.androidapi.h.w();
            this.o = false;
            this.p = true;
            this.q = true;
            this.r = true;
            this.s = C3912j0.b();
            this.c = w0.getReference();
            this.a = w0.getAccessFlags().P();
            this.d = w0.e1();
            this.e = w0.s0();
            this.b = w0.a1();
            this.l = w0.I0();
            this.m = w0.Y0();
            if (w0.i1().d()) {
                hVarI1 = w0.i1().a().N();
            } else {
                hVarI1 = w0.i1();
            }
            this.i = hVarI1;
            this.j = w0.f1();
            this.k = w0.l;
            this.n = z;
            this.o = w0.x1();
            if (!w0.j1().isEmpty() && w0.j1().size() != w0.l1().size()) {
                this.g = w0.j1().l(w0.l1().size());
            } else {
                this.g = w0.j1();
            }
        }

        public final a a(com.android.tools.r8.ir.optimize.info.h hVar) {
            this.i = hVar;
            return this;
        }

        public final a a(final BiConsumer biConsumer) {
            this.s = this.s.andThen(new Consumer() { // from class: com.android.tools.r8.graph.W0$a$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(biConsumer, (W0) obj);
                }
            });
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(BiConsumer biConsumer, W0 w0) {
            if (this.i.d()) {
                biConsumer.accept(w0, this.i.a());
            }
        }

        public a a(W w) {
            this.b = w;
            return this;
        }

        public final a a(Function function) {
            this.b = (W) function.apply(this.c);
            return this;
        }

        public final a a(C0706m3.g gVar) {
            this.d = gVar;
            return this;
        }

        public final a a(com.android.tools.r8.androidapi.h hVar) {
            this.m = hVar;
            return this;
        }

        public final a a(boolean z) {
            this.o = z;
            return this;
        }

        public final a a(Z8 z8) {
            this.k = z8;
            return this;
        }

        public W0 a() {
            boolean z = t;
            if (!z && this.p && this.c == null) {
                throw new AssertionError();
            }
            if (!z && this.a == null) {
                throw new AssertionError();
            }
            if (!z && this.e == null) {
                throw new AssertionError();
            }
            if (!z && this.g == null) {
                throw new AssertionError();
            }
            if (!z && this.q && !this.g.isEmpty() && this.g.size() != this.c.h.f.size()) {
                throw new AssertionError();
            }
            if (!z && this.r && this.l == null) {
                throw new AssertionError();
            }
            if (!z && this.r && this.m == null) {
                throw new AssertionError();
            }
            W0 w0 = new W0(this.c, this.a, this.d, this.e, this.g, this.b, this.n, this.l, this.m, this.k, this.i, this.o);
            w0.a(this.j);
            w0.j = this.h;
            if (!this.f.e()) {
                w0.a(this.f);
            }
            this.s.accept(w0);
            return w0;
        }
    }

    private W0(C0684j2 c0684j2, C0725p4 c0725p4, C0706m3.g gVar, C0658g0 c0658g0, C0663g5 c0663g5, W w, boolean z, com.android.tools.r8.androidapi.h hVar, com.android.tools.r8.androidapi.h hVar2, Z8 z8, com.android.tools.r8.ir.optimize.info.h hVar3, boolean z2) {
        super(c0684j2, c0658g0, z, hVar);
        this.j = X0.NOT_PROCESSED;
        this.n = AbstractC1745cA.c();
        this.p = AbstractC3938n2.g();
        this.q = u;
        this.r = false;
        this.f = c0725p4;
        this.g = z2;
        this.o = gVar;
        this.h = c0663g5;
        this.i = w;
        this.l = z8;
        this.m = hVar2;
        this.k = (com.android.tools.r8.ir.optimize.info.h) Objects.requireNonNull(hVar3);
        boolean z3 = v;
        if (!z3 && c0725p4 == null) {
            throw new AssertionError();
        }
        if (!z3 && w != null && X1()) {
            throw new AssertionError();
        }
        if (!z3 && c0663g5 == null) {
            throw new AssertionError();
        }
        if (!z3 && hVar == null) {
            throw new AssertionError();
        }
        if (!z3 && hVar2 == null) {
            throw new AssertionError();
        }
    }

    public static a S0() {
        return new a(false);
    }

    private void T0() {
        if (!v && this.r) {
            throw new AssertionError();
        }
    }

    public static a Y1() {
        return new a(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new W0$$ExternalSyntheticLambda12()).c(new Function() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W0) obj).getAccessFlags();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W0) obj).s0();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W0) obj).h;
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W0) obj).l;
            }
        }).b(new Predicate() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((W0) obj).N0();
            }
        }).a(new Predicate() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return W0.c((W0) obj);
            }
        }).a(new Function() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W0) obj).a1();
            }
        }, new com.android.tools.r8.utils.structural.v() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.structural.v
            public final int a(Object obj, Object obj2, com.android.tools.r8.utils.structural.a aVar) {
                return W0.a((W) obj, (W) obj2, aVar);
            }
        }, new com.android.tools.r8.utils.structural.w() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.structural.w
            public final void a(Object obj, com.android.tools.r8.utils.structural.o oVar) {
                W0.a((W) obj, oVar);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(W0 w0) {
        return w0.o.a();
    }

    public static a d(W0 w0) {
        return new a(true, w0);
    }

    public boolean A1() {
        T0();
        return C1() || u1();
    }

    public final boolean B1() {
        return !E0();
    }

    public boolean C1() {
        T0();
        return this.f.Y() && !this.f.o();
    }

    @Override // com.android.tools.r8.graph.O0
    public final boolean D0() {
        T0();
        return true;
    }

    public AbstractC3938n2 D1() {
        return H1() ? this.p : AbstractC3938n2.b;
    }

    @Override // com.android.tools.r8.graph.U0
    public final void E() {
        this.o = C0706m3.g.d();
    }

    @Override // com.android.tools.r8.graph.O0
    public boolean E0() {
        T0();
        return this.f.o();
    }

    public final boolean E1() {
        return this.f.Z();
    }

    @Override // com.android.tools.r8.graph.U0, com.android.tools.r8.graph.O0
    public final boolean F0() {
        T0();
        return E0();
    }

    public final boolean F1() {
        T0();
        return (this.f.W() || this.f.Z()) ? false : true;
    }

    @Override // com.android.tools.r8.graph.U0
    public final void G0() {
        this.n = AbstractC1745cA.c();
    }

    public final boolean G1() {
        T0();
        return S1() && !this.f.W();
    }

    @Override // com.android.tools.r8.graph.U0
    public final com.android.tools.r8.androidapi.h H0() {
        com.android.tools.r8.androidapi.h hVarI0 = I0();
        return X1() ? hVarI0 : hVarI0.e(this.m);
    }

    public boolean H1() {
        T0();
        return !L1() && S1();
    }

    public final boolean I1() {
        T0();
        return B1() && O0();
    }

    public final boolean J1() {
        return this.r;
    }

    public final boolean K1() {
        return this.j == X0.PROCESSED_INLINING_CANDIDATE_SAME_NEST;
    }

    @Override // com.android.tools.r8.graph.U0
    public final com.android.tools.r8.ir.optimize.info.g L0() {
        T0();
        return this.k;
    }

    public final boolean L1() {
        T0();
        return this.f.j();
    }

    @Override // com.android.tools.r8.graph.O0
    public final void M() {
        x0();
        this.h = C0663g5.t0();
    }

    public final boolean M1() {
        T0();
        return this.j != X0.NOT_PROCESSED;
    }

    public final boolean N1() {
        T0();
        return this.f.l();
    }

    public boolean O1() {
        T0();
        return this.f.m();
    }

    public final boolean P1() {
        T0();
        return this.f.k();
    }

    public final boolean Q0() {
        return this.f.N();
    }

    public final boolean Q1() {
        return this.f.c0();
    }

    public final boolean R0() {
        return !this.f.N();
    }

    public boolean R1() {
        T0();
        return this.f.q();
    }

    public boolean S1() {
        T0();
        return (this.f.o() || this.f.Y()) ? false : true;
    }

    public final void T1() {
        T0();
        this.j = X0.NOT_PROCESSED;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final void U0() {
        this.m = com.android.tools.r8.androidapi.h.w();
    }

    public String U1() {
        T0();
        return getReference().K0();
    }

    public String V0() {
        T0();
        W w = this.i;
        return w == null ? "<no code>" : w.a(this, com.android.tools.r8.utils.P2.a());
    }

    public C0767v2 V1() {
        return getReference().h.e;
    }

    public String W0() {
        T0();
        return a(AbstractC3649o0.a());
    }

    public final void W1() {
        this.r = true;
    }

    @Override // com.android.tools.r8.graph.O0
    /* JADX INFO: renamed from: X0, reason: merged with bridge method [inline-methods] */
    public C0725p4 getAccessFlags() {
        return this.f;
    }

    public boolean X1() {
        return this.f.W() || this.f.Z();
    }

    public final com.android.tools.r8.androidapi.h Y0() {
        return this.m;
    }

    @Override // com.android.tools.r8.graph.U0
    public final InterfaceC1285Pz Z() {
        return this.n;
    }

    public final Z8 Z0() {
        T0();
        if (v || this.l != null) {
            return this.l;
        }
        throw new AssertionError();
    }

    public final void Z1() {
        T0();
        this.i = null;
    }

    public W a1() {
        T0();
        return this.i;
    }

    public final void a2() {
        this.r = false;
    }

    public final X0 b1() {
        return this.j;
    }

    public final M2 c1() {
        W wA1 = a1();
        if (!v && wA1 != null && !wA1.C0()) {
            throw new AssertionError();
        }
        if (wA1 == null) {
            return null;
        }
        return wA1.u0();
    }

    public final int d1() {
        return !E0() ? 1 : 0;
    }

    public C0706m3.g e1() {
        return this.o;
    }

    public final InterfaceC1894eA f1() {
        return this.n;
    }

    public final void g(com.android.tools.r8.androidapi.h hVar) {
        if (!v && hVar == null) {
            throw new AssertionError();
        }
        this.m = hVar;
    }

    public synchronized com.android.tools.r8.ir.optimize.info.l g1() {
        com.android.tools.r8.ir.optimize.info.l lVar;
        T0();
        lVar = (com.android.tools.r8.ir.optimize.info.l) this.k.b();
        this.k = lVar;
        return lVar;
    }

    public final int h1() {
        return getReference().a(E0());
    }

    public final com.android.tools.r8.ir.optimize.info.h i1() {
        T0();
        return this.k;
    }

    public final C0767v2 j(int i) {
        return getReference().a(i, E0());
    }

    public C0663g5 j1() {
        return this.h;
    }

    public C0767v2 k(int i) {
        return getReference().k(i);
    }

    public final InterfaceC0992Fq k1() {
        return this.q;
    }

    public C0658g0 l(int i) {
        return j1().j(i);
    }

    public C0800x2 l1() {
        return getReference().G0();
    }

    public final int m(int i) {
        if (v || i >= d1()) {
            return i - d1();
        }
        throw new AssertionError();
    }

    public C0737r2 m1() {
        return getReference().H0();
    }

    public C0767v2 n1() {
        return getReference().I0();
    }

    public final AbstractC0717o2 o1() {
        return new C0705m2(getReference());
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                W0.a(zVar);
            }
        };
    }

    public final boolean p1() {
        T0();
        return this.l != null;
    }

    public boolean q1() {
        return this.i != null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public String r0() {
        T0();
        return getReference().r0();
    }

    public final boolean r1() {
        return this.q != u;
    }

    public boolean s1() {
        return this.f.W();
    }

    public boolean t1() {
        return this.f.X();
    }

    public String toString() {
        T0();
        return "Encoded method " + getReference();
    }

    public boolean u1() {
        T0();
        return this.f.Y() && this.f.o();
    }

    public final boolean v1() {
        T0();
        return C1() && l1().isEmpty();
    }

    @Override // com.android.tools.r8.graph.O0
    public final W0 w0() {
        T0();
        return this;
    }

    public final boolean w1() {
        return (E0() || s1() || L1() || C1()) ? false : true;
    }

    public final boolean x1() {
        return this.g;
    }

    public final boolean y1() {
        T0();
        return (this.f.j() || this.f.Y()) && !this.f.o();
    }

    @Override // com.android.tools.r8.graph.O0
    public final boolean z0() {
        return y0() || !j1().isEmpty();
    }

    public boolean z1() {
        return this.f.h();
    }

    public final boolean b(C0702m c0702m, W0 w0) {
        boolean z = v;
        if (!z && getReference().H0() != w0.getReference().H0()) {
            throw new AssertionError();
        }
        if (!z && !c0702m.a(J0(), w0.J0()).d() && !c0702m.a(w0.J0(), J0()).d()) {
            throw new AssertionError();
        }
        C0725p4 accessFlags = getAccessFlags();
        if (accessFlags.g() < w0.getAccessFlags().g()) {
            return false;
        }
        if (accessFlags.j()) {
            return J0() == w0.J0();
        }
        if (accessFlags.m()) {
            return true;
        }
        if (z || accessFlags.i() || accessFlags.l()) {
            return J0().I0().equals(w0.J0().I0());
        }
        throw new AssertionError();
    }

    public final boolean c(C0722p1 c0722p1) {
        T0();
        C0759u2 c0759u2K0 = K0();
        C0759u2 c0759u2 = c0722p1.a1;
        c0759u2K0.getClass();
        if (!c0759u2K0.a(c0759u2.e)) {
            return false;
        }
        boolean z = v;
        if (!z && !O0()) {
            throw new AssertionError();
        }
        if (z || !E0()) {
            return true;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0633d0 c(BiFunction biFunction, C0633d0 c0633d0) {
        return (C0633d0) biFunction.apply(c0633d0, EnumC0608b0.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(W w, W w2, com.android.tools.r8.utils.structural.a aVar) {
        if (w == w2) {
            return 0;
        }
        if (w != null && w2 != null) {
            if (w.A0() && w2.A0()) {
                return w.t0().a(w2.t0(), aVar);
            }
            if (w.C0() && w2.C0()) {
                return w.u0().a(w2.u0(), aVar);
            }
            throw new C3405y10("Unexpected attempt to compare incompatible synthetic objects: " + w + " and " + w2);
        }
        return aVar.a(w != null, w2 != null);
    }

    public final void b(com.android.tools.r8.dex.V v2) {
        M2 m2C1 = c1();
        if (m2C1 != null && v2.a(this)) {
            m2C1.a(v2);
        }
        C0658g0 c0658g0S0 = s0();
        c0658g0S0.getClass();
        v2.a(c0658g0S0);
        AbstractC0594a1.a(v2, c0658g0S0.c);
        this.h.a(v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(W w, com.android.tools.r8.utils.structural.o oVar) {
        if (w == null) {
            return;
        }
        if (w.A0()) {
            w.t0().a(oVar);
        } else {
            if (!v && !w.C0()) {
                throw new AssertionError();
            }
            w.u0().a(oVar);
        }
    }

    public final void a(AbstractC3938n2 abstractC3938n2) {
        boolean z = v;
        if (!z && !H1()) {
            throw new AssertionError();
        }
        if (!z && abstractC3938n2.e()) {
            throw new AssertionError();
        }
        if (!z && !abstractC3938n2.b() && !this.p.c()) {
            throw new AssertionError("Method `" + getReference().r0() + "` went from not overriding a library method to overriding a library method");
        }
        if (!z && !abstractC3938n2.c() && !this.p.b()) {
            throw new AssertionError("Method `" + getReference().r0() + "` went from overriding a library method to not overriding a library method");
        }
        this.p = abstractC3938n2;
    }

    public final void b(Z8 z8) {
        T0();
        if (!v && z8 == null) {
            throw new AssertionError();
        }
        this.l = (Z8) com.android.tools.r8.utils.structural.s.c(this.l, z8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0633d0 b(BiFunction biFunction, C0633d0 c0633d0) {
        return (C0633d0) biFunction.apply(c0633d0, EnumC0608b0.b);
    }

    private C0772w0 b(C0722p1 c0722p1) {
        T0();
        C0759u2 c0759u2C = c0722p1.c("Shaking error: Missing method in " + getReference().e.r0() + ": " + com.android.tools.r8.naming.Q.b.a(getReference()));
        C0759u2 c0759u2C2 = c0722p1.c("[R8]");
        C0767v2 c0767v2 = c0722p1.X1;
        C0684j2 c0684j2A = c0722p1.a(c0722p1.N3, c0722p1.b(c0722p1.A1, c0767v2, c0767v2), c0722p1.c("e"));
        C0767v2 c0767v3 = c0722p1.k3;
        return a(2, 2, new C0430e0(0, c0759u2C2), new C0430e0(1, c0759u2C), new com.android.tools.r8.dex.code.U1(2, c0684j2A, 0, 1, 0, 0, 0), new com.android.tools.r8.dex.code.P2(0, c0767v3), new com.android.tools.r8.dex.code.M1(2, c0722p1.a(c0767v3, c0722p1.b(c0722p1.D1, c0722p1.X1), c0722p1.Y0), 0, 1, 0, 0, 0), new com.android.tools.r8.dex.code.e4(0));
    }

    @Override // com.android.tools.r8.graph.U0
    public final Object a(Function function, Function function2) {
        return function2.apply(this);
    }

    public static AbstractC0748t0 a(W0 w0, Q0 q0) {
        if (w0 != null) {
            if (!v && !w0.getReference().e.O0()) {
                throw new AssertionError();
            }
            AbstractC0727q0 abstractC0727q0A = q0.a((AbstractC0642e2) w0.getReference());
            if (abstractC0727q0A != null) {
                return AbstractC0748t0.a(abstractC0727q0A, w0);
            }
        }
        return null;
    }

    public final o5 a(C0729q2 c0729q2) {
        if (v || J0() == c0729q2.getType()) {
            return new o5(c0729q2, this);
        }
        throw new AssertionError();
    }

    public static o5 a(W0 w0, C3733i c3733i) {
        if (w0 != null) {
            if (!v && !w0.getReference().e.O0()) {
                throw new AssertionError();
            }
            C0684j2 reference = w0.getReference();
            c3733i.getClass();
            C0729q2 c0729q2A = C0729q2.a(c3733i.c(reference.e));
            if (c0729q2A != null) {
                return new o5(c0729q2A, w0);
            }
        }
        return null;
    }

    public final W a(C0767v2 c0767v2, boolean z, com.android.tools.r8.utils.A1 a1) {
        char c = 2;
        if (a1.f instanceof ClassFileConsumer) {
            B7[] b7Arr = new B7[(com.android.tools.r8.utils.M.a(z) * 2) + 3];
            b7Arr[0] = new O7(EnumC1812d20.OBJECT, 0);
            b7Arr[1] = new A7(c0767v2);
            if (z) {
                b7Arr[2] = new C1818d7(1L, EnumC1812d20.INT);
                b7Arr[3] = new Q7(P7.f, EnumC2527mI.INT);
                c = 4;
            }
            b7Arr[c] = new C3109u8(EnumC1812d20.INT);
            return new C0753u(getReference().e, com.android.tools.r8.utils.M.a(z) + 1, getReference().F0() + 1, Arrays.asList(b7Arr));
        }
        com.android.tools.r8.dex.code.B1[] b1Arr = new com.android.tools.r8.dex.code.B1[com.android.tools.r8.utils.M.a(z) + 2];
        b1Arr[0] = new com.android.tools.r8.dex.code.A1(0, 0, c0767v2);
        if (z) {
            b1Arr[1] = new com.android.tools.r8.dex.code.n4(0, 0, 1);
        } else {
            c = 1;
        }
        b1Arr[c] = new C0478n3(0);
        return a(1, 0, b1Arr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0684j2 c0684j2, C0702m c0702m, C1071Ii c1071Ii) {
        C1071Ii c1071IiB = c1071Ii.b(c0684j2);
        C0684j2 reference = getReference();
        C0767v2 c0767v2B0 = getReference().B0();
        c0767v2B0.getClass();
        c1071IiB.c(reference, c0702m.c(c0767v2B0).isInterface());
    }

    public final W0 b(C0702m c0702m) {
        W wB;
        a aVar = new a(N0(), this);
        if (c0702m.Y().f instanceof ClassFileConsumer) {
            wB = a(c0702m.a());
        } else {
            wB = b(c0702m.a());
        }
        a aVarA = aVar.a(wB);
        boolean z = R0() && !D1().e();
        AbstractC3938n2 abstractC3938n2D1 = D1();
        if (z) {
            aVarA = aVarA.a(abstractC3938n2D1);
        } else {
            aVarA.getClass();
        }
        this.r = true;
        return aVarA.a();
    }

    public final void a(InterfaceC1894eA interfaceC1894eA) {
        if (!v && this.n != AbstractC1745cA.c()) {
            throw new AssertionError();
        }
        this.n = interfaceC1894eA;
    }

    public final boolean a(o5 o5Var, EnumC3542a0 enumC3542a0, C0680j c0680j, AbstractC3477z20 abstractC3477z20) {
        T0();
        return a(o5Var.t(), enumC3542a0, c0680j, abstractC3477z20);
    }

    public final boolean a(C0767v2 c0767v2, EnumC3542a0 enumC3542a0, C0680j c0680j, AbstractC3477z20 abstractC3477z20) {
        T0();
        if (enumC3542a0 == EnumC3542a0.a) {
            if (a(c0767v2, EnumC3542a0.e, c0680j, abstractC3477z20)) {
                return true;
            }
            throw new C3089ts("FORCE inlining on non-inlinable: " + r0());
        }
        switch (this.j) {
            case NOT_PROCESSED:
                abstractC3477z20.i();
                return false;
            case PROCESSED_NOT_INLINING_CANDIDATE:
                abstractC3477z20.h();
                return false;
            case PROCESSED_INLINING_CANDIDATE_ANY:
                return true;
            case PROCESSED_INLINING_CANDIDATE_SUBCLASS:
                if (c0680j.c(c0767v2, getReference().e)) {
                    return true;
                }
                abstractC3477z20.e();
                return false;
            case PROCESSED_INLINING_CANDIDATE_SAME_PACKAGE:
                if (c0767v2.a(getReference().e)) {
                    return true;
                }
                abstractC3477z20.d();
                return false;
            case PROCESSED_INLINING_CANDIDATE_SAME_NEST:
                if (AbstractC3578p0.a(c0767v2, getReference().e, c0680j)) {
                    return true;
                }
                abstractC3477z20.c();
                return false;
            case PROCESSED_INLINING_CANDIDATE_SAME_CLASS:
                if (c0767v2 == getReference().e) {
                    return true;
                }
                abstractC3477z20.b();
                return false;
            default:
                throw new C3405y10("Unexpected compilation state: " + this.j);
        }
    }

    public final boolean a(com.android.tools.r8.ir.optimize.V v2) {
        T0();
        X0 x0 = this.j;
        switch (V0.b[v2.a.ordinal()]) {
            case 1:
                this.j = X0.PROCESSED_INLINING_CANDIDATE_ANY;
                break;
            case 2:
                this.j = X0.PROCESSED_INLINING_CANDIDATE_SUBCLASS;
                break;
            case 3:
                this.j = X0.PROCESSED_INLINING_CANDIDATE_SAME_PACKAGE;
                break;
            case 4:
                this.j = X0.PROCESSED_INLINING_CANDIDATE_SAME_NEST;
                break;
            case 5:
                this.j = X0.PROCESSED_INLINING_CANDIDATE_SAME_CLASS;
                break;
            case 6:
                this.j = X0.PROCESSED_NOT_INLINING_CANDIDATE;
                break;
        }
        return x0 != this.j;
    }

    public final void a(W w, InterfaceC0992Fq interfaceC0992Fq) {
        T0();
        this.i = w;
        this.q = interfaceC0992Fq;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v2) {
        v2.b(this);
    }

    public final void a(Z8 z8) {
        T0();
        if (!v && z8 == null) {
            throw new AssertionError();
        }
        this.l = (Z8) com.android.tools.r8.utils.structural.s.d(this.l, z8);
    }

    public final String a(AbstractC3649o0 abstractC3649o0) {
        T0();
        StringBuilder sb = new StringBuilder("(");
        for (C0767v2 c0767v2 : getReference().h.f.a) {
            sb.append(abstractC3649o0.c(c0767v2).toString());
        }
        sb.append(Constant.AFTER_QUTO);
        sb.append(abstractC3649o0.c(getReference().h.e).toString());
        return sb.toString();
    }

    @Override // com.android.tools.r8.graph.O0
    public final void a(final BiFunction biFunction) {
        a(s0().a(new Function() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return W0.b(biFunction, (C0633d0) obj);
            }
        }));
        this.h = j1().a(new Function() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return W0.c(biFunction, (C0633d0) obj);
            }
        });
    }

    public final String a(com.android.tools.r8.utils.P2 p2) {
        T0();
        StringBuilder sb = new StringBuilder(".method ");
        sb.append(this.f.F());
        sb.append(" ");
        sb.append(getReference().f.toString());
        C0737r2 c0737r2 = getReference().h;
        c0737r2.getClass();
        sb.append(c0737r2.a(AbstractC3649o0.a()));
        sb.append("\n");
        W w = this.i;
        if (w != null) {
            C0772w0 c0772w0P = w.P();
            sb.append("    .registers ");
            sb.append(c0772w0P.d);
            sb.append("\n\n");
            StringBuilder sb2 = new StringBuilder();
            HashMap map = new HashMap();
            HashSet hashSet = new HashSet();
            for (com.android.tools.r8.dex.code.B1 b1 : c0772w0P.i) {
                int[] iArrB = b1.B();
                com.android.tools.r8.dex.code.B1[] b1Arr = com.android.tools.r8.dex.code.B1.b;
                if (iArrB != null && iArrB != com.android.tools.r8.dex.code.B1.c) {
                    if (!C0772w0.n && iArrB.length > 2) {
                        throw new AssertionError();
                    }
                    hashSet.add(Integer.valueOf(b1.u() + iArrB[0]));
                } else if (b1.D()) {
                    hashSet.add(Integer.valueOf(b1.x() + b1.u()));
                    map.put(Integer.valueOf(b1.x() + b1.u()), b1);
                }
            }
            for (com.android.tools.r8.dex.code.B1 b2 : c0772w0P.i) {
                if (b2.M()) {
                    com.android.tools.r8.dex.code.B1 b3 = (com.android.tools.r8.dex.code.B1) map.get(Integer.valueOf(b2.u()));
                    if (b2 instanceof com.android.tools.r8.dex.code.d4) {
                        for (int i : ((com.android.tools.r8.dex.code.d4) b2).P()) {
                            hashSet.add(Integer.valueOf(b3.u() + i));
                        }
                    }
                }
            }
            for (com.android.tools.r8.dex.code.B1 b4 : c0772w0P.i) {
                if (hashSet.contains(Integer.valueOf(b4.u()))) {
                    sb2.append("  :label_");
                    sb2.append(b4.u());
                    sb2.append("\n");
                }
                if (b4.M()) {
                    sb2.append(b4.a((com.android.tools.r8.dex.code.B1) map.get(Integer.valueOf(b4.u())))).append('\n');
                } else {
                    sb2.append(b4.a(p2)).append('\n');
                }
            }
            if (c0772w0P.g.length > 0) {
                sb2.append("Tries (numbers are offsets)\n");
                for (C0772w0.a aVar : c0772w0P.g) {
                    sb2.append("  ");
                    sb2.append(aVar.toString());
                    sb2.append('\n');
                }
                sb2.append("Handlers (numbers are offsets)\n");
                for (C0772w0.b bVar : c0772w0P.h) {
                    sb2.append(bVar.toString());
                    sb2.append('\n');
                }
            }
            sb.append(sb2.toString());
        }
        sb.append(".end method\n");
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [int] */
    /* JADX WARN: Type inference failed for: r6v3 */
    private C0772w0 a(int i, int i2, com.android.tools.r8.dex.code.B1... b1Arr) {
        int iZ = 0;
        for (com.android.tools.r8.dex.code.B1 b1 : b1Arr) {
            b1.g(iZ);
            iZ += b1.z();
        }
        boolean z = !this.f.o();
        C0767v2[] c0767v2Arr = getReference().h.f.a;
        int length = c0767v2Arr.length;
        ?? r6 = z;
        int i3 = 0;
        while (i3 < length) {
            int iC = r6 + EnumC1812d20.a(c0767v2Arr[i3]).c();
            i3++;
            r6 = iC;
        }
        return new C0772w0(Math.max(i, (int) r6), r6, i2, b1Arr, new C0772w0.a[0], new C0772w0.b[0], null);
    }

    public static void a(W w, int i, C0702m c0702m) {
        if (w.B0()) {
            C0772w0 c0772w0P = w.P();
            J0.a aVarA = c0772w0P.a(c0702m.a());
            if (!v && aVarA != null && i != aVarA.f.length) {
                throw new AssertionError();
            }
            c0772w0P.a((J0) aVarA);
            return;
        }
        if (!v && !w.z0()) {
            throw new AssertionError();
        }
        w.K().a(c0702m.a());
    }

    private C0753u a(C0722p1 c0722p1) {
        T0();
        C0759u2 c0759u2C = c0722p1.c("Shaking error: Missing method in " + getReference().e.r0() + ": " + com.android.tools.r8.naming.Q.b.a(getReference()));
        C0759u2 c0759u2C2 = c0722p1.c("[R8]");
        C0767v2 c0767v2 = c0722p1.E3;
        C0684j2 c0684j2A = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.X1), c0722p1.c("getLogger"));
        C0684j2 c0684j2A2 = c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, c0722p1.X1), c0722p1.c("severe"));
        C0767v2 c0767v3 = c0722p1.k3;
        C0684j2 c0684j2A3 = c0722p1.a(c0767v3, c0722p1.b(c0722p1.D1, c0722p1.X1), c0722p1.Y0);
        int size = getReference().h.f.size();
        int i = size + 1;
        T0();
        if (!E0()) {
            i = size + 2;
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        C1507Xl c1507XlA = c1507XlG.a(new C1891e7(c0759u2C2)).a(new I7(184, c0684j2A, false));
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        int i2 = i - 1;
        c1507XlA.a(new R8(enumC1812d20, i2)).a(new O7(enumC1812d20, i2)).a(new C1891e7(c0759u2C)).a(new I7(182, c0684j2A2, false)).a(new V7(c0767v3)).a(new D8(D8.a.Dup)).a(new C1891e7(c0759u2C)).a(new I7(183, c0684j2A3, false)).a(new V8());
        return new C0753u(getReference().e, 3, i, c1507XlG.a());
    }

    public final W0 a(C0684j2 c0684j2) {
        a aVar;
        T0();
        boolean zN0 = N0();
        T0();
        if (zN0) {
            aVar = d(this);
        } else {
            aVar = new a(N0(), this);
        }
        if (H1() && D1() != AbstractC3938n2.c) {
            aVar.a(D1());
        }
        aVar.a(c0684j2);
        return aVar.a();
    }

    public final W0 a(C0684j2 c0684j2, Consumer consumer) {
        a aVar;
        T0();
        boolean zN0 = N0();
        T0();
        if (zN0) {
            aVar = d(this);
        } else {
            aVar = new a(N0(), this);
        }
        if (H1() && D1() != AbstractC3938n2.c) {
            aVar.a(D1());
        }
        aVar.a(c0684j2);
        if (consumer != null) {
            consumer.accept(aVar);
        }
        return aVar.a();
    }

    public final o5 a(C0729q2 c0729q2, C0684j2 c0684j2) {
        boolean z = v;
        if (!z && E0()) {
            throw new AssertionError();
        }
        if (!z && O0()) {
            throw new AssertionError();
        }
        if (!z && J0() != c0684j2.B0()) {
            throw new AssertionError();
        }
        T0();
        return new o5(c0729q2, d(this).a(c0684j2).b(new Consumer() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                W0.a((C0725p4) obj);
            }
        }).a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0725p4 c0725p4) {
        c0725p4.E();
        c0725p4.K();
        c0725p4.L();
        c0725p4.A();
    }

    public final W0 a(final C0702m c0702m, AbstractC0727q0 abstractC0727q0, Consumer consumer) {
        C0684j2 reference = getReference();
        C0722p1 c0722p1A = c0702m.a();
        reference.getClass();
        final C0684j2 c0684j2B = reference.b(abstractC0727q0.C(), c0722p1A);
        T0();
        this.f.a();
        a aVarB = d(this).a(c0684j2B).b(new Consumer() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C0725p4) obj).E();
            }
        });
        aVarB.d = C0706m3.g.d();
        a aVarA = aVarB.a(!s1(), new Consumer() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0702m, c0684j2B, (W0.a) obj);
            }
        });
        boolean z = (E0() || D1().e()) ? false : true;
        AbstractC3938n2 abstractC3938n2D1 = D1();
        if (z) {
            aVarA = aVarA.a(abstractC3938n2D1);
        }
        return aVarA.a(consumer).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C0702m c0702m, final C0684j2 c0684j2, a aVar) {
        aVar.a(C1071Ii.a(c0702m.a()).d(c0684j2).a(E0(), new Consumer() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2, c0702m, (C1071Ii) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c0684j2, c0702m, (C1071Ii) obj);
            }
        }).a()).b(new Consumer() { // from class: com.android.tools.r8.graph.W0$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C0725p4) obj).f0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, C0702m c0702m, C1071Ii c1071Ii) {
        C1071Ii c1071IiD = c1071Ii.d(c0684j2);
        C0684j2 reference = getReference();
        C0767v2 c0767v2B0 = getReference().B0();
        c0767v2B0.getClass();
        c1071IiD.b(reference, c0702m.c(c0767v2B0).isInterface());
    }

    public static W0 a(AbstractC0748t0 abstractC0748t0, AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2, C0722p1 c0722p1) {
        if (!v && c0684j2 == null) {
            throw new AssertionError();
        }
        C0684j2 reference = abstractC0748t0.getReference();
        reference.getClass();
        C0684j2 c0684j2B = reference.b(abstractC0727q0.C(), c0722p1);
        C0725p4 c0725p4P = abstractC0748t0.getAccessFlags().P();
        c0725p4P.E();
        c0725p4P.f(1024);
        a aVarA = Y1().a(c0684j2B).a(c0725p4P);
        aVarA.d = C0706m3.g.d();
        aVarA.e = C0658g0.t0();
        a aVarA2 = aVarA.a(C1071Ii.a(c0722p1).b(c0684j2B).b(c0684j2, false).a());
        aVarA2.l = abstractC0748t0.e().I0();
        aVarA2.m = abstractC0748t0.e().m;
        return aVarA2.a();
    }

    public final void a(com.android.tools.r8.ir.optimize.info.l lVar) {
        T0();
        this.k = lVar;
    }

    public final void a(C0702m c0702m, W0 w0) {
        T0();
        if (w0.p1()) {
            b(w0.Z0());
        }
        if (c0702m.Y().a().b && c0702m.q()) {
            this.m = this.m.e(w0.m);
        }
    }

    public final void a(C0706m3.g gVar) {
        if (!v && gVar == null) {
            throw new AssertionError();
        }
        this.o = gVar;
    }

    public final W0 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        a aVar = new a(N0(), this);
        aVar.a(getReference().b(c0767v2, c0722p1));
        return aVar.a();
    }
}
