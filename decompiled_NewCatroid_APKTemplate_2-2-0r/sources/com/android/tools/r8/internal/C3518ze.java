package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ze, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3518ze {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C1317Re c;

    public C3518ze(C0702m c0702m, C1317Re c1317Re) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = c1317Re;
    }

    public final com.android.tools.r8.graph.W0 a(com.android.tools.r8.graph.Y0 y0) {
        return this.c.a(this.a.a().a(y0.B0(), this.a.a().b(this.a.a().v1, this.a.a().Z1), this.a.a().U), new C2097gh(this.a, y0, y0.B0()).b());
    }

    public final com.android.tools.r8.graph.W0 b(C0684j2 c0684j2, com.android.tools.r8.graph.Y0 y0) {
        return this.c.a(C3378xe.a(this.a, c0684j2, y0.B0()), (C0753u) null);
    }

    public final com.android.tools.r8.graph.W0 b(C0684j2 c0684j2, com.android.tools.r8.graph.Y0 y0, InterfaceC1445Ve interfaceC1445Ve, Supplier supplier) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0684j2.B0());
        if (!d && abstractC0727q0C == null && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        boolean z = abstractC0727q0C == null || abstractC0727q0C.isInterface();
        com.android.tools.r8.graph.o5 o5VarS = this.a.g().b(c0684j2, z).s();
        C0684j2 c0684j2B = b(c0684j2, true, interfaceC1445Ve, o5VarS, supplier);
        C0684j2[] c0684j2ArrA = a(c0684j2, false, interfaceC1445Ve, o5VarS, supplier);
        return this.c.a(a(c0684j2, false, c0684j2B, c0684j2ArrA, y0.B0()), new C1962f(this.a, y0.B0(), c0684j2, z, c0684j2B, c0684j2ArrA, y0).b());
    }

    public final com.android.tools.r8.graph.W0 a(C0684j2 c0684j2, com.android.tools.r8.graph.Y0 y0) {
        return this.c.a(this.b.a(y0.B0(), c0684j2.h, c0684j2.f), (C0753u) null);
    }

    public final com.android.tools.r8.graph.W0 a(C0684j2 c0684j2, com.android.tools.r8.graph.Y0 y0, InterfaceC1445Ve interfaceC1445Ve, Supplier supplier) {
        boolean zIsInterface;
        C0767v2 c0767v2B0;
        C0684j2 c0684j2A = this.b.a(y0.B0(), c0684j2.h, c0684j2.f);
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0684j2.B0());
        if (abstractC0727q0C == null) {
            if (!d && !this.a.Y().C1.b(c0684j2.B0())) {
                throw new AssertionError();
            }
            zIsInterface = true;
        } else {
            zIsInterface = abstractC0727q0C.isInterface();
        }
        boolean z = zIsInterface;
        com.android.tools.r8.graph.o5 o5VarS = this.a.g().b(c0684j2, z).s();
        C0684j2 c0684j2B = b(c0684j2, false, interfaceC1445Ve, o5VarS, supplier);
        C0684j2[] c0684j2ArrA = a(c0684j2, true, interfaceC1445Ve, o5VarS, supplier);
        if (this.a.t.a(this.a, c0684j2.B0())) {
            c0767v2B0 = C3378xe.a(this.a, c0684j2.B0());
        } else {
            c0767v2B0 = c0684j2.B0();
        }
        return this.c.a(c0684j2A, new C1962f(this.a, y0.B0(), a(c0684j2, true, c0684j2B, c0684j2ArrA, c0767v2B0), z, c0684j2B, c0684j2ArrA, y0).b());
    }

    public final com.android.tools.r8.graph.W0 b(com.android.tools.r8.graph.Y0 y0) {
        return this.c.a(this.a.a().a(y0.B0(), this.a.a().b(this.a.a().A1, new C0767v2[0]), this.a.a().V), new C3230vk(this.a, y0, y0.B0()).b());
    }

    private C0684j2 b(C0684j2 c0684j2, final boolean z, final InterfaceC1398Ue interfaceC1398Ue, final com.android.tools.r8.graph.o5 o5Var, final Supplier supplier) {
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda9
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.b(z, interfaceC1398Ue, o5Var, supplier, (C0767v2) obj, (C0684j2) obj2);
            }
        };
        C0767v2 c0767v2 = c0684j2.h.e;
        int iF0 = c0684j2.F0();
        C0684j2[] c0684j2Arr = this.a.Y().C1.j().get(c0684j2);
        C0684j2 c0684j3 = c0684j2Arr == null ? null : c0684j2Arr[iF0];
        if (this.c.a(c0767v2, c0684j3, c0684j2, o5Var)) {
            return (C0684j2) biFunction.apply(c0767v2, c0684j3);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0684j2 b(boolean z, InterfaceC1398Ue interfaceC1398Ue, com.android.tools.r8.graph.o5 o5Var, Supplier supplier, C0767v2 c0767v2, C0684j2 c0684j2) {
        return this.c.a(z, interfaceC1398Ue, o5Var, supplier, c0767v2, c0684j2);
    }

    private C0684j2 b(C0684j2 c0684j2, final boolean z, final InterfaceC1445Ve interfaceC1445Ve, final com.android.tools.r8.graph.o5 o5Var, final Supplier supplier) {
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.b(z, interfaceC1445Ve, o5Var, supplier, (C0767v2) obj, (C0684j2) obj2);
            }
        };
        C0767v2 c0767v2 = c0684j2.h.e;
        int iF0 = c0684j2.F0();
        C0684j2[] c0684j2Arr = this.a.Y().C1.j().get(c0684j2);
        C0684j2 c0684j3 = c0684j2Arr == null ? null : c0684j2Arr[iF0];
        if (this.c.a(c0767v2, c0684j3, c0684j2, o5Var)) {
            return (C0684j2) biFunction.apply(c0767v2, c0684j3);
        }
        return null;
    }

    public final com.android.tools.r8.graph.o5 a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1344Se interfaceC1344Se, final C1203Nb c1203Nb) {
        final C0729q2 c0729q2A = o5Var.a();
        C0684j2 c0684j2B = b(o5Var.getReference(), true, (InterfaceC1398Ue) interfaceC1344Se, o5Var, new Supplier() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return c1203Nb.a(c0729q2A);
            }
        });
        C0684j2[] c0684j2ArrA = a(o5Var.getReference(), false, (InterfaceC1398Ue) interfaceC1344Se, o5Var, new Supplier() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return c1203Nb.a(c0729q2A);
            }
        });
        C0684j2 reference = o5Var.getReference();
        C0684j2 c0684j2A = a(reference, false, c0684j2B, c0684j2ArrA, reference.B0());
        C0753u c0753uB = new C1962f(this.a, o5Var.t(), o5Var.getReference(), c0729q2A.isInterface(), c0684j2B, c0684j2ArrA).b();
        com.android.tools.r8.graph.W0 w0A = this.c.a(c0684j2A, c0753uB);
        w0A.a((com.android.tools.r8.graph.W) c0753uB, (InterfaceC0992Fq) com.android.tools.r8.graph.W0.u);
        if (o5Var.e().D1().d()) {
            w0A.a(AbstractC3938n2.a);
        }
        com.android.tools.r8.graph.o5 o5VarA = w0A.a(c0729q2A);
        if (!d && interfaceC1344Se == null) {
            throw new AssertionError();
        }
        interfaceC1344Se.b(o5VarA, o5Var);
        return o5VarA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0684j2 b(boolean z, InterfaceC1445Ve interfaceC1445Ve, com.android.tools.r8.graph.o5 o5Var, Supplier supplier, C0767v2 c0767v2, C0684j2 c0684j2) {
        return this.c.a(z, interfaceC1445Ve, o5Var, supplier, c0767v2, c0684j2);
    }

    public final com.android.tools.r8.graph.o5 a(final I7 i7, InterfaceC1370Te interfaceC1370Te, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        C0737r2 c0737r2A;
        final C0684j2 c0684j2T = i7.T();
        if (i7.K()) {
            c0737r2A = c0684j2T.h;
        } else {
            c0737r2A = this.b.a(c0684j2T.H0(), c0684j2T.B0());
        }
        final C0737r2 c0737r2 = c0737r2A;
        Objects.requireNonNull(c1230Ob);
        final C0684j2 c0684j2B = b(c0684j2T, false, (InterfaceC1398Ue) interfaceC1370Te, o5Var, (Supplier) new ze$$ExternalSyntheticLambda6(c1230Ob));
        final C0684j2[] c0684j2ArrA = a(c0684j2T, true, (InterfaceC1398Ue) interfaceC1370Te, o5Var, (Supplier) new ze$$ExternalSyntheticLambda6(c1230Ob));
        com.android.tools.r8.graph.o5 o5VarB = this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.O;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2, c0684j2T, c0684j2B, c0684j2ArrA, i7, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        interfaceC1370Te.p(o5VarB, o5Var);
        return o5VarB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0737r2 c0737r2, final C0684j2 c0684j2, final C0684j2 c0684j3, final C0684j2[] c0684j2Arr, final I7 i7, com.android.tools.r8.synthesis.H h) {
        h.a(c0737r2).a(C0725p4.Q()).b().a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j4) {
                return this.f$0.a(c0684j2, c0684j3, c0684j2Arr, i7, c0684j4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W a(C0684j2 c0684j2, C0684j2 c0684j3, C0684j2[] c0684j2Arr, I7 i7, C0684j2 c0684j4) {
        return new C1962f(this.a, c0684j4.e, a(c0684j2, true, c0684j3, c0684j2Arr, c0684j2.B0()), i7.V(), c0684j3, c0684j2Arr, i7.U()).b();
    }

    public final ArrayList a(I7 i7, C1230Ob c1230Ob, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var) {
        C0684j2 c0684j2T = i7.T();
        Objects.requireNonNull(c1230Ob);
        C0684j2 c0684j2B = b(c0684j2T, false, (InterfaceC1398Ue) g7, o5Var, (Supplier) new ze$$ExternalSyntheticLambda6(c1230Ob));
        C0684j2[] c0684j2ArrA = a(c0684j2T, true, (InterfaceC1398Ue) g7, o5Var, (Supplier) new ze$$ExternalSyntheticLambda6(c1230Ob));
        int size = c0684j2T.G0().size();
        ArrayList arrayList = new ArrayList();
        if (size != 0) {
            int i = 0;
            boolean z = false;
            while (true) {
                boolean z2 = true;
                if (i >= c0684j2ArrA.length - 2) {
                    break;
                }
                if (c0684j2ArrA[i] == null) {
                    z2 = false;
                }
                z |= z2;
                i++;
            }
            if (c0684j2T.G0().j(size - 1).Z0() | z) {
                a(c0684j2ArrA, arrayList, c1230Ob, c0684j2T, interfaceC1374Ti, ac, g7);
            } else {
                if (c0684j2ArrA.length > 0 && c0684j2ArrA[c0684j2ArrA.length - 1] != null) {
                    arrayList.add(new I7(184, c0684j2ArrA[c0684j2ArrA.length - 1], false));
                }
                if (c0684j2ArrA.length > 1 && c0684j2ArrA[c0684j2ArrA.length - 2] != null) {
                    if (!d && c0684j2T.G0().j(c0684j2T.G0().size() - 1).Z0()) {
                        throw new AssertionError();
                    }
                    D8.a aVar = D8.a.Swap;
                    arrayList.add(new D8(aVar));
                    arrayList.add(new I7(184, c0684j2ArrA[c0684j2ArrA.length - 2], false));
                    arrayList.add(new D8(aVar));
                }
            }
        }
        arrayList.add(new I7(i7.U(), a(c0684j2T, true, c0684j2B, c0684j2ArrA, c0684j2T.B0()), i7.V()));
        if (c0684j2B != null) {
            arrayList.add(new I7(184, c0684j2B, false));
        }
        return arrayList;
    }

    private void a(final C0684j2[] c0684j2Arr, ArrayList arrayList, C1230Ob c1230Ob, final C0684j2 c0684j2, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7) {
        C0767v2 c0767v2K;
        ac.a(4);
        final C0737r2 c0737r2B = this.a.a().b(this.a.a().c2, c0684j2.G0().a);
        com.android.tools.r8.graph.o5 o5VarB = this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.P;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2B, c0684j2, c0684j2Arr, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        g7.p(o5VarB, c1230Ob.b());
        arrayList.add(new I7(184, o5VarB.getReference(), false));
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        int iA = interfaceC1374Ti.a(enumC1812d20.c());
        arrayList.add(new R8(enumC1812d20, iA));
        for (int i = 0; i < c0684j2Arr.length; i++) {
            arrayList.add(new O7(EnumC1812d20.OBJECT, iA));
            arrayList.add(new C1818d7(i, EnumC1812d20.INT));
            C0684j2 c0684j3 = c0684j2Arr[i];
            if (c0684j3 != null) {
                c0767v2K = c0684j3.I0();
            } else {
                c0767v2K = c0684j2.k(i);
            }
            arrayList.add(new B6(EnumC2523mE.OBJECT));
            if (c0767v2K.W0()) {
                arrayList.add(new M6(this.b.c(c0767v2K), false));
                arrayList.add(new I7(182, this.a.a().f(c0767v2K), false));
            } else {
                arrayList.add(new M6(c0767v2K, false));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0737r2 c0737r2, final C0684j2 c0684j2, final C0684j2[] c0684j2Arr, com.android.tools.r8.synthesis.H h) {
        h.a(c0737r2).a(C0725p4.Q()).b().a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j3) {
                return this.f$0.a(c0684j2, c0684j2Arr, c0684j3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W a(C0684j2 c0684j2, C0684j2[] c0684j2Arr, C0684j2 c0684j3) {
        C0767v2 c0767v2 = c0684j3.e;
        ArrayList arrayList = new ArrayList();
        arrayList.add(new C1818d7(c0684j2Arr.length, EnumC1812d20.INT));
        arrayList.add(new W7(this.b.c2));
        int i = 0;
        for (int i2 = 0; i2 < c0684j2.F0(); i2++) {
            arrayList.add(new D8(D8.a.Dup));
            arrayList.add(new C1818d7(i2, EnumC1812d20.INT));
            C0767v2 c0767v2K = c0684j2.k(i2);
            arrayList.add(new O7(EnumC1812d20.a(c0767v2K), i));
            if (c0684j2Arr[i2] != null) {
                arrayList.add(new I7(184, c0684j2Arr[i2], false));
            }
            if (c0767v2K.W0()) {
                arrayList.add(new I7(184, this.a.a().b(c0767v2K), false));
            }
            arrayList.add(new D6(EnumC2523mE.OBJECT));
            if (c0767v2K == this.a.a().B1 || c0767v2K == this.a.a().y1) {
                i++;
            }
            i++;
        }
        arrayList.add(new C3109u8(EnumC1812d20.OBJECT));
        return new C0753u(c0767v2, i + 4, i, arrayList);
    }

    private C0684j2[] a(C0684j2 c0684j2, final boolean z, final InterfaceC1398Ue interfaceC1398Ue, final com.android.tools.r8.graph.o5 o5Var, final Supplier supplier) {
        C1317Re c1317Re = this.c;
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda8
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(z, interfaceC1398Ue, o5Var, supplier, (C0767v2) obj, (C0684j2) obj2);
            }
        };
        C0684j2[] c0684j2Arr = new C0684j2[c0684j2.F0()];
        C0767v2[] c0767v2Arr = c0684j2.h.f.a;
        for (int i = 0; i < c0767v2Arr.length; i++) {
            C0684j2[] c0684j2Arr2 = this.a.Y().C1.j().get(c0684j2);
            C0684j2 c0684j3 = c0684j2Arr2 == null ? null : c0684j2Arr2[i];
            C0767v2 c0767v2 = c0767v2Arr[i];
            if (c1317Re.a(c0767v2, c0684j3, c0684j2, o5Var)) {
                c0684j2Arr[i] = (C0684j2) biFunction.apply(c0767v2, c0684j3);
            }
        }
        return c0684j2Arr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0684j2 a(boolean z, InterfaceC1398Ue interfaceC1398Ue, com.android.tools.r8.graph.o5 o5Var, Supplier supplier, C0767v2 c0767v2, C0684j2 c0684j2) {
        return this.c.a(z, interfaceC1398Ue, o5Var, supplier, c0767v2, c0684j2);
    }

    private C0684j2[] a(C0684j2 c0684j2, final boolean z, final InterfaceC1445Ve interfaceC1445Ve, final com.android.tools.r8.graph.o5 o5Var, final Supplier supplier) {
        C1317Re c1317Re = this.c;
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.ze$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(z, interfaceC1445Ve, o5Var, supplier, (C0767v2) obj, (C0684j2) obj2);
            }
        };
        C0684j2[] c0684j2Arr = new C0684j2[c0684j2.F0()];
        C0767v2[] c0767v2Arr = c0684j2.h.f.a;
        for (int i = 0; i < c0767v2Arr.length; i++) {
            C0684j2[] c0684j2Arr2 = this.a.Y().C1.j().get(c0684j2);
            C0684j2 c0684j3 = c0684j2Arr2 == null ? null : c0684j2Arr2[i];
            C0767v2 c0767v2 = c0767v2Arr[i];
            if (c1317Re.a(c0767v2, c0684j3, c0684j2, o5Var)) {
                c0684j2Arr[i] = (C0684j2) biFunction.apply(c0767v2, c0684j3);
            }
        }
        return c0684j2Arr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0684j2 a(boolean z, InterfaceC1445Ve interfaceC1445Ve, com.android.tools.r8.graph.o5 o5Var, Supplier supplier, C0767v2 c0767v2, C0684j2 c0684j2) {
        return this.c.a(z, interfaceC1445Ve, o5Var, supplier, c0767v2, c0684j2);
    }

    private C0684j2 a(C0684j2 c0684j2, boolean z, C0684j2 c0684j3, C0684j2[] c0684j2Arr, C0767v2 c0767v2) {
        C0767v2 c0767v2I0;
        C0767v2 c0767v2K;
        C0767v2 c0767v2K2;
        if (c0684j3 == null) {
            c0767v2I0 = c0684j2.I0();
        } else if (z) {
            c0767v2I0 = c0684j3.k(0);
        } else {
            c0767v2I0 = c0684j3.I0();
        }
        C0767v2[] c0767v2Arr = new C0767v2[c0684j2Arr.length];
        for (int i = 0; i < c0684j2Arr.length; i++) {
            C0684j2 c0684j4 = c0684j2Arr[i];
            if (c0684j4 == null) {
                c0767v2K2 = c0684j2.k(i);
            } else if (z) {
                c0767v2K2 = c0684j4.I0();
            } else {
                c0767v2K2 = c0684j4.k(0);
            }
            c0767v2Arr[i] = c0767v2K2;
        }
        C0684j2 c0684j2A = this.a.a().a(c0767v2, this.a.a().b(c0767v2I0, c0767v2Arr), c0684j2.f);
        if (!d && c0684j2A != C3378xe.a(this.a, c0684j2, c0767v2) && !this.a.Y().C1.j().containsKey(c0684j2)) {
            C0684j2 c0684j2A2 = C3378xe.a(this.a, c0684j2, c0684j2.e);
            if (c0684j2.I0() != c0684j2A2.I0() && c0684j3 == null) {
                c0767v2K = c0684j2.I0();
            } else {
                for (int i2 = 0; i2 < c0684j2.F0(); i2++) {
                    if (c0684j2.k(i2) != c0684j2A2.k(i2) && c0684j2Arr[i2] == null) {
                        c0767v2K = c0684j2.k(i2);
                    }
                }
                c0767v2K = null;
            }
            if (c0767v2K == null) {
                throw new AssertionError();
            }
        }
        return c0684j2A;
    }
}
