package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0696l0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.ir.desugar.varhandle.VarHandleDesugaringMethods;
import com.android.tools.r8.position.Position;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2044g20 implements C7, N6 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C0722p1 b;

    public C2044g20(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:169:0x029c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:170:0x029e  */
    /* JADX WARN: Code duplicated, block: B:178:0x02c1  */
    /* JADX WARN: Code duplicated, block: B:226:0x02cb A[SYNTHETIC] */
    public Collection a(I7 i7, int i, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        C0767v2 c0767v2B;
        boolean z;
        C0767v2 c0767v2;
        boolean z2;
        int i2;
        int iA;
        int i3 = i;
        if (o5Var.t() != this.b.B2) {
            b(g7, AbstractC1639am.c(o5Var));
        }
        boolean z3 = c;
        if (!z3 && !i7.L()) {
            throw new AssertionError();
        }
        int i4 = 2;
        int i5 = 1;
        if (!z3 && ((i3 != 1 && i3 != 2) || i7.T().H0().t0() < i3)) {
            throw new AssertionError();
        }
        int iT0 = i7.T().H0().t0() - i3;
        if (!z3 && iT0 > 2) {
            throw new AssertionError();
        }
        C0737r2 c0737r2H0 = i7.T().H0();
        C0767v2 c0767v2K = i7.T().H0().k(0);
        C0767v2 c0767v3 = null;
        if (!c0767v2K.O0() && !c0767v2K.M0()) {
            return null;
        }
        if (c0767v2K.M0()) {
            C0722p1 c0722p2 = this.b;
            c0767v2B = c0767v2K.b(c0722p2);
            int iG0 = c0767v2K.G0();
            if (iG0 > 1) {
                c0767v2B = c0722p2.a(iG0 - 1, c0767v2B);
            }
            C0722p1 c0722p3 = this.b;
            if ((c0767v2B != c0722p3.A1 && c0767v2B != c0722p3.B1 && !c0767v2B.X0()) || i7.T().H0().k(1) != this.b.A1) {
                return null;
            }
        } else {
            c0767v2B = null;
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        ArrayList arrayList = new ArrayList(c0737r2H0.f.size());
        if (iT0 > 0) {
            C0767v2 c0767v2J = c0737r2H0.f.j(i3);
            if (!c0767v2J.R0() && !c0767v2J.T0() && !c0767v2J.Y0()) {
                c0767v2J = this.b.Z1;
            }
            c0767v3 = c0767v2J;
            z = false;
            for (int i6 = i3; i6 < c0737r2H0.f.size(); i6++) {
                z = z || c0737r2H0.f.j(i6).Z0();
                C0767v2 c0767v2J2 = c0737r2H0.f.j(i6);
                if (!c0767v2J2.R0() && !c0767v2J2.T0() && !c0767v2J2.Y0()) {
                    c0767v2J2 = this.b.Z1;
                }
                if (c0767v2J2 != c0767v3) {
                    c0767v3 = this.b.Z1;
                }
            }
            if (!c && !c0767v3.R0() && !c0767v3.T0() && c0767v3 != this.b.Z1) {
                throw new AssertionError();
            }
        } else {
            z = false;
        }
        C0759u2 c0759u2C0 = i7.T().C0();
        if (this.b.A4.f.contains(c0759u2C0)) {
            c0767v2 = c0737r2H0.e;
        } else {
            c0767v2 = c0737r2H0.e;
            if (!c0767v2.R0() && !c0767v2.T0() && !c0767v2.Y0()) {
                c0767v2 = this.b.Z1;
            }
        }
        if (c0767v2.Y0() && this.b.A4.d.contains(c0759u2C0)) {
            c0767v2 = this.b.Z1;
            z2 = true;
        } else {
            z2 = false;
        }
        if (i3 == 1) {
            arrayList.add(this.b.Z1);
        } else {
            boolean z4 = c;
            if (!z4 && i3 != 2) {
                throw new AssertionError();
            }
            if (!z4 && c0767v2B == null) {
                throw new AssertionError();
            }
            boolean z5 = c0767v2B.W0() && (c0767v3 == null || c0767v3 == c0767v2B) && (this.b.A4.f.contains(c0759u2C0) || c0767v2.Y0() || c0767v2 == c0767v2B);
            if (!z5) {
                c0767v2K = this.b.Z1;
            }
            arrayList.add(c0767v2K);
            arrayList.add(this.b.A1);
            if (!z5) {
                if (c0767v3 != null) {
                    c0767v3 = this.b.Z1;
                }
                if (!this.b.A4.f.contains(c0759u2C0) && !c0767v2.Y0()) {
                    c0767v2 = this.b.Z1;
                }
            }
        }
        while (i3 < c0737r2H0.f.size()) {
            if (c0767v3.W0()) {
                arrayList.add(c0767v3);
            } else {
                int i8 = i3 == c0737r2H0.f.size() - i5 ? i5 : 0;
                arrayList.add(this.b.Z1);
                if (c0737r2H0.f.j(i3).W0()) {
                    if (i8 == 0) {
                        if (z) {
                            iA = interfaceC1374Ti.a(i4);
                            ac.a(i5);
                            c0767v3 = c0767v3;
                            c1507XlG.a(new R8(EnumC1812d20.a(c0737r2H0.f.j(i3 + 1)), iA));
                            i2 = 1;
                        } else {
                            c1507XlG.a(new D8(D8.a.Swap));
                        }
                        ac.a(i2);
                        c1507XlG.a(new I7(184, this.b.b(c0737r2H0.f.j(i3)), false));
                        if (i8 != 0) {
                            continue;
                        } else if (!z) {
                            c1507XlG.a(new D8(D8.a.Swap));
                        } else {
                            if (c && iA == -1) {
                                throw new AssertionError();
                            }
                            c1507XlG.a(new O7(EnumC1812d20.a(c0737r2H0.f.j(i3 + 1)), iA));
                        }
                    }
                    i2 = 1;
                    iA = -1;
                    ac.a(i2);
                    c1507XlG.a(new I7(184, this.b.b(c0737r2H0.f.j(i3)), false));
                    if (i8 != 0) {
                        continue;
                    } else if (!z) {
                        if (c) {
                        }
                        c1507XlG.a(new O7(EnumC1812d20.a(c0737r2H0.f.j(i3 + 1)), iA));
                    } else {
                        c1507XlG.a(new D8(D8.a.Swap));
                    }
                }
                i3++;
                c0767v3 = c0767v3;
                i4 = 2;
                i5 = 1;
            }
            c0767v3 = c0767v3;
            i3++;
            c0767v3 = c0767v3;
            i4 = 2;
            i5 = 1;
        }
        boolean z6 = c;
        if (!z6 && arrayList.size() != c0737r2H0.f.size()) {
            throw new AssertionError();
        }
        C0767v2 c0767v4 = c0737r2H0.e;
        if (c0767v4 != c0767v2 && c0767v4 != this.b.D1) {
            if (c0767v4.W0()) {
                c1507XlG.a(new X6(this.b.c(c0737r2H0.e), false));
            } else {
                c1507XlG.a(new X6(c0737r2H0.e, false));
            }
            arrayList.add(this.b.m2);
        }
        C0737r2 c0737r2A = this.b.a(c0767v2, arrayList);
        C0722p1 c0722p4 = this.b;
        c1507XlG.a(new I7(182, c0722p4.a(c0722p4.B2, c0737r2A, c0759u2C0), false));
        if (z2) {
            ac.a(1);
            c1507XlG.a(new D8(D8.a.Pop));
        } else if (c0737r2H0.e.W0() && !c0737r2A.e.W0()) {
            if (!z6 && c0737r2A.e != this.b.Z1) {
                throw new AssertionError();
            }
            ac.a(2);
            c1507XlG.a(new M6(this.b.c(c0737r2H0.e), false));
            c1507XlG.a(new I7(182, this.b.f(c0737r2H0.e), false));
        } else if (c0737r2H0.e.O0()) {
            C0767v2 c0767v5 = c0737r2H0.e;
            C0722p1 c0722p5 = this.b;
            if (c0767v5 != c0722p5.Z1 && c0767v5 != c0722p5.D1) {
                ac.a(1);
                c1507XlG.a(new M6(c0737r2H0.e, false));
            }
        }
        return c1507XlG.a();
    }

    private static boolean b(C0722p1 c0722p1, C0767v2 c0767v2) {
        if (c0767v2 != c0722p1.A5) {
            return c0767v2 == c0722p1.B2;
        }
        if (c) {
            return true;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2142hF c() {
        return new C2142hF("VarHandle desugaring");
    }

    public static void d(C0722p1 c0722p1) {
        X3.a(c0722p1, "Ljava/lang/Byte;", "Ljava/lang/ClassCastException;", "Ljava/lang/Double;", "Ljava/lang/Float;");
        X3.a(c0722p1, "Ljava/lang/IllegalArgumentException;", "Ljava/lang/Integer;", "Ljava/lang/Long;", "Ljava/lang/NoSuchFieldException;");
        X3.a(c0722p1, "Ljava/lang/RuntimeException;", "Ljava/lang/Short;", "Ljava/lang/UnsupportedOperationException;", "Ljava/lang/invoke/VarHandle;");
        X3.a(c0722p1, "Ljava/lang/reflect/Field;", "Ljava/lang/reflect/Modifier;", "Lsun/misc/Unsafe;", "[Ljava/lang/reflect/Field;");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection c(C0722p1 c0722p1, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p2) {
        a(g7, AbstractC1639am.c(o5Var));
        D8 d8 = new D8(D8.a.Swap);
        C0767v2 c0767v2 = c0722p1.o5;
        return AbstractC1639am.a(d8, new I7(182, c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.m2), c0722p1.c("toPrivateLookupIn")), false));
    }

    public static boolean b(C0722p1 c0722p1, C0684j2 c0684j2) {
        if (b(c0722p1, c0684j2.e)) {
            return true;
        }
        return b(c0684j2.h, c0722p1);
    }

    private static boolean b(C0737r2 c0737r2, C0722p1 c0722p1) {
        if (b(c0722p1, c0737r2.e)) {
            return true;
        }
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            if (b(c0722p1, c0767v2)) {
                return true;
            }
        }
        return false;
    }

    public final C3224ve c(final C0722p1 c0722p1) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p2) {
                return this.f$0.c(c0722p1, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p2);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection b(C0722p1 c0722p1, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p2) {
        a(g7, AbstractC1639am.c(o5Var));
        ac.a(2);
        return AbstractC1639am.a(new V7(null, c0722p1.o5), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.o5, c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.Y0), false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2142hF d() {
        return new C2142hF("VarHandle desugaring");
    }

    private void b(InterfaceC2128h20 interfaceC2128h20, Collection collection) {
        if (!c && !collection.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((InterfaceC0694k5) obj);
            }
        })) {
            throw new AssertionError();
        }
        com.android.tools.r8.synthesis.D D = this.a.D();
        Supplier supplier = new Supplier() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return C2044g20.d();
            }
        };
        com.android.tools.r8.synthesis.C c2 = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.e;
            }
        };
        C0767v2 c0767v2 = this.b.B2;
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((com.android.tools.r8.synthesis.P) obj);
            }
        };
        Objects.requireNonNull(interfaceC2128h20);
        C0729q2 c0729q2A = D.a(supplier, c2, c0767v2, collection, c0702m, consumer, new g20$$ExternalSyntheticLambda13(interfaceC2128h20));
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            interfaceC2128h20.a(c0729q2A, (InterfaceC0694k5) it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(InterfaceC0694k5 interfaceC0694k5) {
        return interfaceC0694k5.C() != this.b.B2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(com.android.tools.r8.synthesis.P p) {
        VarHandleDesugaringMethods.a(p, this.a.a());
    }

    public final C3224ve b(final C0722p1 c0722p1) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda15
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p2) {
                return this.f$0.b(c0722p1, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p2);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(S6 s6, List list) {
        b((InterfaceC2128h20) s6, (Collection) list);
    }

    public static C2044g20 a(C0702m c0702m) {
        if (c0702m.Y().D1()) {
            return new C2044g20(c0702m);
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.C7
    public final void a(com.android.tools.r8.graph.o5 o5Var, G7 g7) {
        if (o5Var.t() == this.b.B2) {
            return;
        }
        int i = 0;
        for (B7 b7 : o5Var.e().a1().K().I0()) {
            if (!c) {
                b7.getClass();
                if (b7 instanceof C3335x7) {
                    throw new AssertionError();
                }
            }
            if (b7.H()) {
                I7 i7L = b7.l();
                if (!com.android.tools.r8.utils.J.b(i, 2)) {
                    if (b(this.b, i7L.T())) {
                        if (o5Var.t() != this.b.B2) {
                            b(g7, AbstractC1639am.c(o5Var));
                        }
                        i |= 2;
                    }
                }
                if (!com.android.tools.r8.utils.J.b(i, 1)) {
                    if (a(this.b, i7L.T())) {
                        a(g7, AbstractC1639am.c(o5Var));
                        i |= 1;
                    }
                }
            }
        }
    }

    public static boolean a(com.android.tools.r8.graph.Y0 y0, C0722p1 c0722p1) {
        if (b(c0722p1, y0.e)) {
            if (c) {
                return true;
            }
            throw new AssertionError("The VarHandle class has no fields.");
        }
        return b(c0722p1, y0.h);
    }

    public static boolean a(C0722p1 c0722p1, C0767v2 c0767v2) {
        if (c0767v2 != c0722p1.B5) {
            return c0767v2 == c0722p1.E2;
        }
        if (c) {
            return true;
        }
        throw new AssertionError();
    }

    public static boolean a(C0722p1 c0722p1, C0684j2 c0684j2) {
        if (a(c0722p1, c0684j2.e)) {
            return true;
        }
        return a(c0684j2.h, c0722p1);
    }

    private static boolean a(C0737r2 c0737r2, C0722p1 c0722p1) {
        if (a(c0722p1, c0737r2.e)) {
            return true;
        }
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            if (a(c0722p1, c0767v2)) {
                return true;
            }
        }
        return false;
    }

    private void a(InterfaceC2128h20 interfaceC2128h20, Collection collection) {
        if (!c && !collection.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((InterfaceC0694k5) obj);
            }
        })) {
            throw new AssertionError();
        }
        com.android.tools.r8.synthesis.D D = this.a.D();
        Supplier supplier = new Supplier() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda10
            @Override // java.util.function.Supplier
            public final Object get() {
                return C2044g20.c();
            }
        };
        com.android.tools.r8.synthesis.C c2 = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.d;
            }
        };
        C0767v2 c0767v2 = this.b.o5;
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.synthesis.P) obj);
            }
        };
        Objects.requireNonNull(interfaceC2128h20);
        C0729q2 c0729q2A = D.a(supplier, c2, c0767v2, collection, c0702m, consumer, new g20$$ExternalSyntheticLambda13(interfaceC2128h20));
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            interfaceC2128h20.a(c0729q2A, (InterfaceC0694k5) it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(InterfaceC0694k5 interfaceC0694k5) {
        return interfaceC0694k5.C() != this.b.o5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.synthesis.P p) {
        C0722p1 c0722p1A = this.a.a();
        p.a((List) AbstractC1639am.i());
        C0684j2 c0684j2A = c0722p1A.a(p.e(), c0722p1A.b(c0722p1A.c(c0722p1A.c("Ljava/lang/invoke/VarHandle;")), c0722p1A.c(c0722p1A.c("Ljava/lang/Class;")), c0722p1A.c(c0722p1A.c("Ljava/lang/String;")), c0722p1A.c(c0722p1A.c("Ljava/lang/Class;"))), c0722p1A.c("findVarHandle"));
        C0684j2 c0684j2A2 = c0722p1A.a(p.e(), c0722p1A.b(c0722p1A.c(c0722p1A.c("Ljava/lang/invoke/MethodHandles$Lookup;")), c0722p1A.c(c0722p1A.c("Ljava/lang/Class;"))), c0722p1A.c("toPrivateLookupIn"));
        C0684j2 c0684j2A3 = c0722p1A.a(p.e(), c0722p1A.b(c0722p1A.D1, new C0767v2[0]), c0722p1A.c("<init>"));
        com.android.tools.r8.graph.W0.a aVarA = com.android.tools.r8.graph.W0.Y1().a(c0684j2A3).a(C0725p4.b(4097, true));
        M7 m7 = new M7();
        M7 m8 = new M7();
        C0767v2 c0767v2 = c0684j2A3.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        AbstractC1639am abstractC1639amA = AbstractC1639am.a(m7, new O7(enumC1812d20, 0), new I7(183, c0722p1A.a(c0722p1A.Z1, c0722p1A.b(c0722p1A.D1, new C0767v2[0]), c0722p1A.c("<init>")), false), new C3186v8(), m8);
        AbstractC1639am abstractC1639amI = AbstractC1639am.i();
        AbstractC1639am abstractC1639amI2 = AbstractC1639am.i();
        Position position = Position.UNKNOWN;
        p.a((AbstractCollection) AbstractC1639am.c(aVarA.a(new C0753u(c0767v2, 1, 1, abstractC1639amA, abstractC1639amI, abstractC1639amI2, position, C2049g6.a())).c().a()));
        com.android.tools.r8.graph.W0.a aVarA2 = com.android.tools.r8.graph.W0.Y1().a(c0684j2A).a(C0725p4.b(4097, false));
        M7 m9 = new M7();
        M7 m10 = new M7();
        C0767v2 c0767v3 = c0684j2A.e;
        V7 v7 = new V7(null, c0722p1A.e("Ljava/lang/invoke/VarHandle;"));
        D8 d8 = new D8(D8.a.Dup);
        O7 o7 = new O7(enumC1812d20, 1);
        O7 o8 = new O7(enumC1812d20, 2);
        O7 o9 = new O7(enumC1812d20, 3);
        C0767v2 c0767v2E = c0722p1A.e("Ljava/lang/invoke/VarHandle;");
        C0767v2 c0767v4 = c0722p1A.D1;
        C0767v2 c0767v5 = c0722p1A.m2;
        p.b((AbstractCollection) AbstractC1639am.a(aVarA2.a(new C0753u(c0767v3, 5, 4, AbstractC1639am.a(m9, v7, d8, o7, o8, o9, new I7(183, c0722p1A.a(c0767v2E, c0722p1A.b(c0767v4, c0767v5, c0722p1A.X1, c0767v5), c0722p1A.c("<init>")), false), new C3109u8(enumC1812d20), m10), AbstractC1639am.i(), AbstractC1639am.i(), position, C2049g6.a())).c().a(), com.android.tools.r8.graph.W0.Y1().a(c0684j2A2).a(C0725p4.b(4097, false)).a(new C0753u(c0684j2A2.e, 1, 2, AbstractC1639am.a(new M7(), new O7(enumC1812d20, 0), new C3109u8(enumC1812d20), new M7()), AbstractC1639am.i(), AbstractC1639am.i(), position, C2049g6.a())).c().a()));
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        if (!b7.H()) {
            return C3224ve.c();
        }
        I7 i7L = b7.l();
        C0767v2 c0767v2B0 = i7L.T().B0();
        C0722p1 c0722p1 = this.b;
        if (c0767v2B0 != c0722p1.D2 && c0767v2B0 != c0722p1.E2 && c0767v2B0 != c0722p1.B2) {
            return C3224ve.c();
        }
        C0684j2 c0684j2T = i7L.T();
        if (c0684j2T.B0() == this.b.D2) {
            if (c0684j2T.C0().equals(this.b.t1) && c0684j2T.I0() == this.b.o5 && c0684j2T.F0() == 0 && i7L.K()) {
                return b(this.b);
            }
            if (c0684j2T.C0().equals(this.b.u1) && c0684j2T.I0() == this.b.o5 && c0684j2T.F0() == 2 && c0684j2T.k(0) == this.b.m2 && c0684j2T.k(1) == this.b.o5 && i7L.K()) {
                return c(this.b);
            }
            if (c0684j2T.C0().equals(this.b.c("arrayElementVarHandle")) && c0684j2T.I0() == this.b.B2 && c0684j2T.F0() == 1 && c0684j2T.k(0) == this.b.m2 && i7L.K()) {
                return a(this.b);
            }
            return C3224ve.c();
        }
        if (c0684j2T.B0() == this.b.B2) {
            if (!i7L.L()) {
                if (c || i7L.J()) {
                    return C3224ve.c();
                }
                throw new AssertionError();
            }
            boolean z = c;
            if (!z && !i7L.L()) {
                throw new AssertionError();
            }
            C0759u2 c0759u2C0 = c0684j2T.C0();
            int iT0 = c0684j2T.H0().t0();
            if (c0759u2C0.equals(this.b.o1) || c0759u2C0.equals(this.b.p1)) {
                if (!z && iT0 != 3 && iT0 != 4) {
                    throw new AssertionError();
                }
                return a(i7L, iT0 - 2);
            }
            if (c0759u2C0.equals(this.b.m1) || c0759u2C0.equals(this.b.q1)) {
                if (!z && iT0 != 1 && iT0 != 2) {
                    throw new AssertionError();
                }
                return a(i7L, iT0);
            }
            if (!c0759u2C0.equals(this.b.n1) && !c0759u2C0.equals(this.b.r1) && !c0759u2C0.equals(this.b.s1)) {
                return C3224ve.c();
            }
            if (!z && iT0 != 2 && iT0 != 3) {
                throw new AssertionError();
            }
            return a(i7L, iT0 - 1);
        }
        return C3224ve.c();
    }

    public static C3224ve a(final C0722p1 c0722p1) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p2) {
                return C2044g20.a(c0722p1, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p2);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Collection a(C0722p1 c0722p1, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p2) {
        ac.a(2);
        return AbstractC1639am.a(new V7(null, c0722p1.B2), new D8(D8.a.DupX1), new D8(D8.a.Swap), new I7(183, c0722p1.a(c0722p1.B2, c0722p1.b(c0722p1.D1, c0722p1.m2), c0722p1.Y0), false));
    }

    public final C3224ve a(final I7 i7, final int i) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda16
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(i7, i, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    @Override // com.android.tools.r8.internal.N6
    public final String a() {
        return "$varhandle";
    }

    @Override // com.android.tools.r8.internal.N6
    public final void a(C1150Lb c1150Lb, final S6 s6) {
        C0696l0 c0696l0F = this.a.f().b().f();
        a(c0696l0F, new Predicate() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C0696l0) obj).e();
            }
        }, new Function() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0696l0) obj).b();
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(s6, (List) obj);
            }
        });
        a(c0696l0F, new Predicate() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C0696l0) obj).i();
            }
        }, new Function() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0696l0) obj).d();
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.g20$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(s6, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(S6 s6, List list) {
        a((InterfaceC2128h20) s6, (Collection) list);
    }

    private void a(C0696l0 c0696l0, Predicate predicate, Function function, Consumer consumer) {
        if (predicate.test(c0696l0)) {
            ArrayList arrayList = new ArrayList();
            Iterator it = ((Set) function.apply(c0696l0)).iterator();
            while (it.hasNext()) {
                AbstractC0727q0 abstractC0727q0A = this.a.a((C0767v2) it.next());
                boolean z = c;
                if (!z && abstractC0727q0A == null) {
                    throw new AssertionError();
                }
                if (!z && !abstractC0727q0A.e0()) {
                    throw new AssertionError();
                }
                arrayList.add(abstractC0727q0A.a0());
            }
            consumer.accept(arrayList);
        }
    }
}
