package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.d4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1815d4 {
    static final /* synthetic */ boolean d = true;
    private final AbstractC2178hm a;
    private final C0702m b;
    private final IdentityHashMap c = new IdentityHashMap();

    C1815d4(C0702m c0702m) {
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        C0722p1 c0722p1J0 = a1Y.j0();
        this.b = c0702m;
        this.a = v(c0722p1J0);
        if (a1Y.n0()) {
            EnumC3891f enumC3891fB0 = a1Y.B0();
            EnumC3891f enumC3891f = EnumC3891f.K;
            if (enumC3891fB0.e(enumC3891f)) {
                a(c0722p1J0);
                if (b(c0722p1J0.g2, enumC3891f)) {
                    b(c0722p1J0);
                }
            }
            EnumC3891f enumC3891fB1 = a1Y.B0();
            EnumC3891f enumC3891f2 = EnumC3891f.N;
            if (enumC3891fB1.e(enumC3891f2)) {
                c(c0722p1J0);
                if (b(c0722p1J0.g2, enumC3891f2)) {
                    e(c0722p1J0);
                    if (b(c0722p1J0.R2)) {
                        d(c0722p1J0);
                    }
                }
            }
            if (a1Y.B0().e(EnumC3891f.O)) {
                f(c0722p1J0);
                if (b(c0722p1J0.R2)) {
                    g(c0722p1J0);
                }
            }
            if (a1Y.B0().e(EnumC3891f.P)) {
                i(c0722p1J0);
            }
            if (a1Y.B0().e(EnumC3891f.Q)) {
                j(c0722p1J0);
            }
            EnumC3891f enumC3891fB2 = a1Y.B0();
            EnumC3891f enumC3891f3 = EnumC3891f.R;
            if (enumC3891fB2.e(enumC3891f3)) {
                if (a1Y.n1.a || c(c0722p1J0.L2, enumC3891f3)) {
                    m(c0722p1J0);
                }
                if (b(c0722p1J0.g2, enumC3891f3)) {
                    l(c0722p1J0);
                    if (b(c0722p1J0.R2)) {
                        k(c0722p1J0);
                    }
                }
            }
            EnumC3891f enumC3891fB3 = a1Y.B0();
            EnumC3891f enumC3891f4 = EnumC3891f.S;
            if (enumC3891fB3.e(enumC3891f4)) {
                n(c0722p1J0);
                if (a1Y.n1.a || c(c0722p1J0.L2, enumC3891f4)) {
                    o(c0722p1J0);
                }
            }
            if (a1Y.B0().e(EnumC3891f.Sv2)) {
                p(c0722p1J0);
            }
            EnumC3891f enumC3891fB4 = a1Y.B0();
            EnumC3891f enumC3891f5 = EnumC3891f.T;
            if (enumC3891fB4.e(enumC3891f5)) {
                q(c0722p1J0);
                if (c(c0722p1J0.V2, enumC3891f5)) {
                    h(c0722p1J0);
                }
                if (c(c0722p1J0.S2)) {
                    r(c0722p1J0);
                }
            }
            if (a1Y.B0().e(EnumC3891f.U)) {
                if (c(c0722p1J0.Z2)) {
                    t(c0722p1J0);
                }
                s(c0722p1J0);
            }
            u(c0722p1J0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0753u a(C0722p1 c0722p1, int i, C0722p1 c0722p2, C0684j2 c0684j2) {
        C0767v2 c0767v2A = c0722p1.a(1, c0722p1.N2);
        C0767v2 c0767v2 = c0722p1.O2;
        C0767v2 c0767v3 = c0722p1.D1;
        C0767v2 c0767v4 = c0722p1.Z1;
        C0684j2 c0684j2A = c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v4, c0767v4), "<init>");
        C1507Xl c1507XlG = AbstractC1639am.g();
        c1507XlG.a((Object[]) new B7[]{new C1818d7(i, EnumC1812d20.INT), new W7(c0767v2A)});
        for (int i2 = 0; i2 < i; i2++) {
            D8.a aVar = D8.a.Dup;
            EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
            int i3 = i2 * 2;
            c1507XlG.a((Object[]) new B7[]{new D8(aVar), new C1818d7(i2, EnumC1812d20.INT), new V7(null, c0767v2), new D8(aVar), new O7(enumC1812d20, i3), new O7(enumC1812d20, i3 + 1), new I7(183, c0684j2A, false), new D6(EnumC2523mE.OBJECT)});
        }
        C0767v2 c0767v5 = c0722p1.M2;
        c1507XlG.a((Object[]) new B7[]{new I7(184, c0722p1.a(c0767v5, c0722p1.b(c0767v5, c0767v2A), c0722p1.c("ofEntries")), false), new C3109u8(EnumC1812d20.OBJECT)});
        return new C0753u(c0684j2.e, 7, i * 2, c1507XlG.a());
    }

    private boolean b(C0767v2 c0767v2, EnumC3891f enumC3891f) {
        return !b(c0767v2) || c(c0767v2, enumC3891f);
    }

    private boolean c(C0767v2 c0767v2) {
        return c(c0767v2, EnumC3891f.ANDROID_PLATFORM);
    }

    private void d(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.g2;
        C0759u2 c0759u2C = c0722p1.c("requireNonNull");
        C0767v2 c0767v3 = c0722p1.Z1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v3, c0722p1.R2), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda84
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.O0(c0722p2, c0684j2);
            }
        }));
    }

    private void e(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.g2;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0722p1.v1, c0722p1.Z1), c0722p1.c("isNull")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda62
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.J0(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0722p1.v1, c0722p1.Z1), c0722p1.c("nonNull")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda63
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.K0(c0722p2, c0684j2);
            }
        }));
    }

    private void f(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.N1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0722p1.A1, c0722p1.w1), c0722p1.c("toUnsignedInt")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda28
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.i(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0722p1.B1, c0722p1.w1), c0722p1.c("toUnsignedLong")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda40
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.j(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v3 = c0722p1.T1;
        a(new C1584a4(c0722p1.a(c0767v3, c0722p1.b(c0722p1.A1, c0722p1.C1), c0722p1.c("toUnsignedInt")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda43
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.h1(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v3, c0722p1.b(c0722p1.B1, c0722p1.C1), c0722p1.c("toUnsignedLong")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda44
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.i1(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v4 = c0722p1.R1;
        C0759u2 c0759u2C = c0722p1.c("divideUnsigned");
        C0767v2 c0767v5 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0767v5, c0767v5, c0767v5), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda45
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.D(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C2 = c0722p1.c("remainderUnsigned");
        C0767v2 c0767v6 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0767v6, c0767v6, c0767v6), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda47
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.J(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C3 = c0722p1.c("compareUnsigned");
        C0767v2 c0767v7 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0767v7, c0767v7, c0767v7), c0759u2C3), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda48
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.C(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0722p1.B1, c0722p1.A1), c0722p1.c("toUnsignedLong")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda49
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.K(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0722p1.A1, c0722p1.X1), c0722p1.c("parseUnsignedInt")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda50
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.G(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C4 = c0722p1.c("parseUnsignedInt");
        C0767v2 c0767v8 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0767v8, c0722p1.X1, c0767v8), c0759u2C4), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda51
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.I(c0722p2, c0684j2);
            }
        }, 0));
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0722p1.X1, c0722p1.A1), c0722p1.c("toUnsignedString")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda29
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.L(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C5 = c0722p1.c("toUnsignedString");
        C0767v2 c0767v9 = c0722p1.X1;
        C0767v2 c0767v10 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0767v9, c0767v10, c0767v10), c0759u2C5), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda30
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.M(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v11 = c0722p1.S1;
        C0759u2 c0759u2C6 = c0722p1.c("divideUnsigned");
        C0767v2 c0767v12 = c0722p1.B1;
        a(new C1584a4(c0722p1.a(c0767v11, c0722p1.b(c0767v12, c0767v12, c0767v12), c0759u2C6), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda31
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.O(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C7 = c0722p1.c("remainderUnsigned");
        C0767v2 c0767v13 = c0722p1.B1;
        a(new C1584a4(c0722p1.a(c0767v11, c0722p1.b(c0767v13, c0767v13, c0767v13), c0759u2C7), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda32
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.V(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C8 = c0722p1.c("compareUnsigned");
        C0767v2 c0767v14 = c0722p1.A1;
        C0767v2 c0767v15 = c0722p1.B1;
        a(new C1584a4(c0722p1.a(c0767v11, c0722p1.b(c0767v14, c0767v15, c0767v15), c0759u2C8), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda33
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.N(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v11, c0722p1.b(c0722p1.B1, c0722p1.X1), c0722p1.c("parseUnsignedLong")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda34
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.S(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v11, c0722p1.b(c0722p1.B1, c0722p1.X1, c0722p1.A1), c0722p1.c("parseUnsignedLong")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda36
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.U(c0722p2, c0684j2);
            }
        }, 0));
        a(new C1584a4(c0722p1.a(c0767v11, c0722p1.b(c0722p1.X1, c0722p1.B1), c0722p1.c("toUnsignedString")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda37
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.W(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v11, c0722p1.b(c0722p1.X1, c0722p1.B1, c0722p1.A1), c0722p1.c("toUnsignedString")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda38
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.X(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v16 = c0722p1.r2;
        a(new C1888e4(c0722p1.a(c0767v16, c0722p1.b(c0722p1.A1, new C0767v2[0]), c0722p1.c("getParameterCount")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda39
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.y0(c0722p2, c0684j2);
            }
        }, c0767v16));
        C0767v2 c0767v17 = c0722p1.X1;
        a(new C1584a4(c0722p1.a(c0767v17, c0722p1.b(c0722p1.X1, c0722p1.V1, c0722p1.W1), c0722p1.c("join")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda41
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.l1(c0722p2, c0684j2);
            }
        }, 0));
        a(new C1584a4(c0722p1.a(c0767v17, c0722p1.b(c0722p1.X1, c0722p1.V1, c0722p1.i2), c0722p1.c("join")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda42
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.m1(c0722p2, c0684j2);
            }
        }, 0));
    }

    private void g(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.a3;
        a(new C2203i4(c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.R2), c0722p1.c("withInitial"))));
    }

    private void h(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.V2;
        C0767v2[] c0767v2Arr = {c0767v2, c0722p1.W2, c0722p1.Y2, c0722p1.X2};
        a(new C1888e4(c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.R2), c0722p1.c("or")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda97
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.Z0(c0722p2, c0684j2);
            }
        }, c0767v2));
        C0767v2[] c0767v2Arr2 = {c0722p1.Z2, c0722p1.c(c0722p1.c("Ljava/util/stream/DoubleStream;")), c0722p1.c(c0722p1.c("Ljava/util/stream/LongStream;")), c0722p1.c(c0722p1.c("Ljava/util/stream/IntStream;"))};
        InterfaceC2047g4[] interfaceC2047g4Arr = {new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda102
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.a1(c0722p2, c0684j2);
            }
        }, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda103
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.b1(c0722p2, c0684j2);
            }
        }, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda104
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.d1(c0722p2, c0684j2);
            }
        }, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda105
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.c1(c0722p2, c0684j2);
            }
        }};
        C0759u2 c0759u2C = c0722p1.c("stream");
        for (int i = 0; i < 4; i++) {
            C0767v2 c0767v3 = c0767v2Arr[i];
            a(new C1888e4(c0722p1.a(c0767v3, c0722p1.b(c0767v2Arr2[i], new C0767v2[0]), c0759u2C), interfaceC2047g4Arr[i], c0767v3));
        }
        C0767v2[] c0767v2Arr3 = {c0722p1.T2, c0722p1.g3, c0722p1.h3, c0722p1.i3};
        InterfaceC2047g4[] interfaceC2047g4Arr2 = {new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda106
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.R0(c0722p2, c0684j2);
            }
        }, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda107
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.S0(c0722p2, c0684j2);
            }
        }, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda108
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.U0(c0722p2, c0684j2);
            }
        }, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda109
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.T0(c0722p2, c0684j2);
            }
        }};
        for (int i2 = 0; i2 < 4; i2++) {
            C0767v2 c0767v4 = c0767v2Arr[i2];
            a(new C1888e4(c0722p1.a(c0767v4, c0722p1.b(c0722p1.D1, c0767v2Arr3[i2], c0722p1.U2), c0722p1.c("ifPresentOrElse")), interfaceC2047g4Arr2[i2], c0767v4));
        }
        C0767v2[] c0767v2Arr4 = {c0722p1.Z1, c0722p1.y1, c0722p1.B1, c0722p1.A1};
        InterfaceC1665b4[] interfaceC1665b4Arr = {MK.d(), MK.a(), MK.c(), MK.b()};
        C0759u2 c0759u2C2 = c0722p1.c("orElseThrow");
        for (int i3 = 0; i3 < 4; i3++) {
            a(new Z3(c0722p1.a(c0767v2Arr[i3], c0722p1.b(c0767v2Arr4[i3], new C0767v2[0]), c0759u2C2), interfaceC1665b4Arr[i3]));
        }
        InterfaceC2047g4[] interfaceC2047g4Arr3 = {new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda110
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.V0(c0722p2, c0684j2);
            }
        }, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda98
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.W0(c0722p2, c0684j2);
            }
        }, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda99
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.Y0(c0722p2, c0684j2);
            }
        }, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda100
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.X0(c0722p2, c0684j2);
            }
        }};
        C0759u2 c0759u2C3 = c0722p1.c("isEmpty");
        for (int i4 = 0; i4 < 4; i4++) {
            a(new C1888e4(c0722p1.a(c0767v2Arr[i4], c0722p1.b(c0722p1.v1, new C0767v2[0]), c0759u2C3), interfaceC2047g4Arr3[i4], c0767v2Arr[i4]));
        }
    }

    private void i(C0722p1 c0722p1) {
        a(new Z3(c0722p1.H4.b, XD.a()));
    }

    private void j(C0722p1 c0722p1) {
        a(new Z3(c0722p1.I4.b, YD.a()));
    }

    private void k(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.g2;
        C0759u2 c0759u2C = c0722p1.c("requireNonNullElseGet");
        C0767v2 c0767v3 = c0722p1.Z1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v3, c0722p1.R2), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda52
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.M0(c0722p2, c0684j2);
            }
        }));
    }

    private void l(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.g2;
        C0759u2 c0759u2C = c0722p1.c("requireNonNullElse");
        C0767v2 c0767v3 = c0722p1.Z1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v3, c0767v3), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda67
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.L0(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C2 = c0722p1.c("checkIndex");
        C0767v2 c0767v4 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v4, c0767v4, c0767v4), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda69
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.D0(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C3 = c0722p1.c("checkFromToIndex");
        C0767v2 c0767v5 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v5, c0767v5, c0767v5, c0767v5), c0759u2C3), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda70
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.B0(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C4 = c0722p1.c("checkFromIndexSize");
        C0767v2 c0767v6 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v6, c0767v6, c0767v6, c0767v6), c0759u2C4), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda71
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.z0(c0722p2, c0684j2);
            }
        }));
    }

    private void m(final C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.K2;
        C0759u2 c0759u2C = c0722p1.c("of");
        final int i = 0;
        while (i <= 10) {
            C0684j2 c0684j2A = c0722p1.a(c0767v2, c0722p1.a(c0767v2, Collections.nCopies(i, c0722p1.Z1)), c0759u2C);
            a(i == 0 ? new Z3(c0684j2A, AbstractC3298wb.a()) : new C1584a4(c0684j2A, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda72
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC3221vb.a(i, c0722p2, c0684j2);
                }
            }));
            i++;
        }
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.c2), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda73
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.o(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v3 = c0722p1.L2;
        C0759u2 c0759u2C2 = c0722p1.c("of");
        final int i2 = 0;
        while (i2 <= 10) {
            C0684j2 c0684j2A2 = c0722p1.a(c0767v3, c0722p1.a(c0767v3, Collections.nCopies(i2, c0722p1.Z1)), c0759u2C2);
            a(i2 == 0 ? new Z3(c0684j2A2, AbstractC3298wb.c()) : new C1584a4(c0684j2A2, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda74
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC3221vb.b(i2, c0722p2, c0684j2);
                }
            }));
            i2++;
        }
        a(new C1584a4(c0722p1.a(c0767v3, c0722p1.b(c0767v3, c0722p1.c2), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda75
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.r(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v4 = c0722p1.M2;
        C0759u2 c0759u2C3 = c0722p1.c("of");
        final int i3 = 0;
        while (i3 <= 10) {
            C0684j2 c0684j2A3 = c0722p1.a(c0767v4, c0722p1.a(c0767v4, Collections.nCopies(i3 * 2, c0722p1.Z1)), c0759u2C3);
            a(i3 == 0 ? new Z3(c0684j2A3, AbstractC3298wb.b()) : new C1584a4(c0684j2A3, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda76
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return C1815d4.a(c0722p1, i3, c0722p2, c0684j2);
                }
            }));
            i3++;
        }
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0767v4, c0722p1.a(1, c0722p1.N2)), "ofEntries"), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda77
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.q(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v5 = c0722p1.M2;
        C0767v2 c0767v6 = c0722p1.N2;
        C0767v2 c0767v7 = c0722p1.Z1;
        a(new C1584a4(c0722p1.a(c0767v5, c0722p1.b(c0767v6, c0767v7, c0767v7), "entry"), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda78
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.p(c0722p2, c0684j2);
            }
        }));
    }

    private void n(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.N1;
        C0759u2 c0759u2C = c0722p1.c("compareUnsigned");
        C0767v2 c0767v3 = c0722p1.A1;
        C0767v2 c0767v4 = c0722p1.w1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v4, c0767v4), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.h(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v5 = c0722p1.T1;
        C0759u2 c0759u2C2 = c0722p1.c("compareUnsigned");
        C0767v2 c0767v6 = c0722p1.A1;
        C0767v2 c0767v7 = c0722p1.C1;
        a(new C1584a4(c0722p1.a(c0767v5, c0722p1.b(c0767v6, c0767v7, c0767v7), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda35
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.g1(c0722p2, c0684j2);
            }
        }));
        C0767v2[] c0767v2Arr = {c0722p1.j2, c0722p1.k2};
        for (int i = 0; i < 2; i++) {
            C0767v2 c0767v8 = c0767v2Arr[i];
            C0759u2 c0759u2C3 = c0722p1.c("multiplyExact");
            C0767v2 c0767v9 = c0722p1.B1;
            a(new C1584a4(c0722p1.a(c0767v8, c0722p1.b(c0767v9, c0767v9, c0722p1.A1), c0759u2C3), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda46
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.o0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C4 = c0722p1.c("multiplyFull");
            C0767v2 c0767v10 = c0722p1.B1;
            C0767v2 c0767v11 = c0722p1.A1;
            a(new C1584a4(c0722p1.a(c0767v8, c0722p1.b(c0767v10, c0767v11, c0767v11), c0759u2C4), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda57
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.p0(c0722p2, c0684j2);
                }
            }));
            C0759u2 c0759u2C5 = c0722p1.c("multiplyHigh");
            C0767v2 c0767v12 = c0722p1.B1;
            a(new C1584a4(c0722p1.a(c0767v8, c0722p1.b(c0767v12, c0767v12, c0767v12), c0759u2C5), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda68
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.q0(c0722p2, c0684j2);
                }
            }));
            C0759u2 c0759u2C6 = c0722p1.c("floorDiv");
            C0767v2 c0767v13 = c0722p1.B1;
            a(new C1584a4(c0722p1.a(c0767v8, c0722p1.b(c0767v13, c0767v13, c0722p1.A1), c0759u2C6), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda79
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.g0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C7 = c0722p1.c("floorMod");
            C0767v2 c0767v14 = c0722p1.A1;
            a(new C1584a4(c0722p1.a(c0767v8, c0722p1.b(c0767v14, c0722p1.B1, c0767v14), c0759u2C7), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda90
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.j0(c0722p2, c0684j2);
                }
            }, 0));
        }
        a(new Z3(c0722p1.D4.b, LV.a()));
        a(new Z3(c0722p1.E4.b, R00.a()));
    }

    private void o(C0722p1 c0722p1) {
        a(new C1584a4(c0722p1.a(c0722p1.K2, c0722p1.b(c0722p1.K2, c0722p1.P2), c0722p1.c("copyOf")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda80
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.s(c0722p2, c0684j2);
            }
        }, 0));
        a(new C1584a4(c0722p1.a(c0722p1.L2, c0722p1.b(c0722p1.L2, c0722p1.P2), c0722p1.c("copyOf")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda81
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.u(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v2 = c0722p1.M2;
        C0759u2 c0759u2C = c0722p1.c("copyOf");
        C0767v2 c0767v3 = c0722p1.M2;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v3), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda82
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.t(c0722p2, c0684j2);
            }
        }, 0));
    }

    private void p(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.z5;
        C0759u2 c0759u2C = c0722p1.c("compareAndSwapObject");
        C0767v2 c0767v3 = c0722p1.v1;
        C0767v2 c0767v4 = c0722p1.Z1;
        a(new C1971f4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v4, c0722p1.B1, c0767v4, c0767v4), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda111
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.t1(c0722p2, c0684j2);
            }
        }, c0767v2));
        C0767v2 c0767v2E = c0722p1.e("Ljava/util/concurrent/atomic/AtomicReference;");
        C0759u2 c0759u2C2 = c0722p1.c("compareAndSet");
        C0767v2 c0767v5 = c0722p1.v1;
        C0767v2 c0767v6 = c0722p1.Z1;
        a(new C1971f4(c0722p1.a(c0767v2E, c0722p1.b(c0767v5, c0767v6, c0767v6), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda113
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.d(c0722p2, c0684j2);
            }
        }, c0767v2E));
        C0767v2 c0767v2E2 = c0722p1.e("Ljava/util/concurrent/atomic/AtomicReferenceArray;");
        C0759u2 c0759u2C3 = c0722p1.c("compareAndSet");
        C0767v2 c0767v7 = c0722p1.v1;
        C0767v2 c0767v8 = c0722p1.Z1;
        a(new C1971f4(c0722p1.a(c0767v2E2, c0722p1.b(c0767v7, c0722p1.A1, c0767v8, c0767v8), c0759u2C3), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda114
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.b(c0722p2, c0684j2);
            }
        }, c0767v2E2));
        C0767v2 c0767v2E3 = c0722p1.e("Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;");
        C0759u2 c0759u2C4 = c0722p1.c("compareAndSet");
        C0767v2 c0767v9 = c0722p1.v1;
        C0767v2 c0767v10 = c0722p1.Z1;
        a(new C1971f4(c0722p1.a(c0767v2E3, c0722p1.b(c0767v9, c0767v10, c0767v10, c0767v10), c0759u2C4), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda115
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.c(c0722p2, c0684j2);
            }
        }, c0767v2E3));
    }

    private void q(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.R1;
        C0759u2 c0759u2C = c0722p1.c("parseInt");
        C0767v2 c0767v3 = c0722p1.A1;
        C0684j2 c0684j2A = c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0722p1.V1, c0767v3, c0767v3, c0767v3), c0759u2C);
        a(this.b.Y().H() ? new C1584a4(c0684j2A, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda85
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.E(c0722p2, c0684j2);
            }
        }, 0) : new C1584a4(c0684j2A, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda87
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.F(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v4 = c0722p1.R1;
        C0759u2 c0759u2C2 = c0722p1.c("parseUnsignedInt");
        C0767v2 c0767v5 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v4, c0722p1.b(c0767v5, c0722p1.V1, c0767v5, c0767v5, c0767v5), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda88
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.H(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v6 = c0722p1.S1;
        C0759u2 c0759u2C3 = c0722p1.c("parseLong");
        C0767v2 c0767v7 = c0722p1.B1;
        C0767v2 c0767v8 = c0722p1.A1;
        C0684j2 c0684j2A2 = c0722p1.a(c0767v6, c0722p1.b(c0767v7, c0722p1.V1, c0767v8, c0767v8, c0767v8), c0759u2C3);
        a(this.b.Y().H() ? new C1584a4(c0684j2A2, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda89
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.Q(c0722p2, c0684j2);
            }
        }, 0) : new C1584a4(c0684j2A2, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda91
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.R(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v9 = c0722p1.S1;
        C0759u2 c0759u2C4 = c0722p1.c("parseUnsignedLong");
        C0767v2 c0767v10 = c0722p1.B1;
        C0767v2 c0767v11 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v9, c0722p1.b(c0767v10, c0722p1.V1, c0767v11, c0767v11, c0767v11), c0759u2C4), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda92
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.T(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v12 = c0722p1.X1;
        a(new C1888e4(c0722p1.a(c0767v12, c0722p1.b(c0722p1.X1, c0722p1.A1), c0722p1.c("repeat")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda93
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.n1(c0722p2, c0684j2);
            }
        }, c0767v12));
        C0767v2 c0767v13 = c0722p1.X1;
        a(new C1888e4(c0722p1.a(c0767v13, c0722p1.b(c0722p1.v1, new C0767v2[0]), c0722p1.c("isBlank")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda94
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.k1(c0722p2, c0684j2);
            }
        }, c0767v13));
        C0767v2 c0767v14 = c0722p1.X1;
        a(new C1888e4(c0722p1.a(c0767v14, c0722p1.b(c0722p1.X1, new C0767v2[0]), c0722p1.c("strip")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda95
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.o1(c0722p2, c0684j2);
            }
        }, c0767v14));
        C0767v2 c0767v15 = c0722p1.X1;
        a(new C1888e4(c0722p1.a(c0767v15, c0722p1.b(c0722p1.X1, new C0767v2[0]), c0722p1.c("stripLeading")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda96
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.p1(c0722p2, c0684j2);
            }
        }, c0767v15));
        C0767v2 c0767v16 = c0722p1.X1;
        a(new C1888e4(c0722p1.a(c0767v16, c0722p1.b(c0722p1.X1, new C0767v2[0]), c0722p1.c("stripTrailing")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda86
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.q1(c0722p2, c0684j2);
            }
        }, c0767v16));
    }

    private void r(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.S2;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0767v2), c0722p1.c("not")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda66
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.e1(c0722p2, c0684j2);
            }
        }, 0));
    }

    private void s(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.g2;
        C0759u2 c0759u2C = c0722p1.c("checkIndex");
        C0767v2 c0767v3 = c0722p1.B1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v3, c0767v3), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda116
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.E0(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C2 = c0722p1.c("checkFromToIndex");
        C0767v2 c0767v4 = c0722p1.B1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v4, c0767v4, c0767v4, c0767v4), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda117
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.C0(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C3 = c0722p1.c("checkFromIndexSize");
        C0767v2 c0767v5 = c0722p1.B1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v5, c0767v5, c0767v5, c0767v5), c0759u2C3), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda118
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.A0(c0722p2, c0684j2);
            }
        }));
        C0767v2[] c0767v2Arr = {c0722p1.j2, c0722p1.k2};
        for (int i = 0; i < 2; i++) {
            C0767v2 c0767v6 = c0767v2Arr[i];
            C0759u2 c0759u2C4 = c0722p1.c("absExact");
            C0767v2 c0767v7 = c0722p1.A1;
            a(new C1584a4(c0722p1.a(c0767v6, c0722p1.b(c0767v7, c0767v7), c0759u2C4), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda119
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.Y(c0722p2, c0684j2);
                }
            }));
            C0767v2 c0767v8 = c0722p1.B1;
            a(new C1584a4(c0722p1.a(c0767v6, c0722p1.b(c0767v8, c0767v8), c0759u2C4), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda120
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.Z(c0722p2, c0684j2);
                }
            }));
        }
        a(c0722p1, c0722p1.k2);
    }

    private void t(C0722p1 c0722p1) {
        a(new C1584a4(c0722p1.a(c0722p1.Z2, c0722p1.b(c0722p1.Z2, c0722p1.Z1), c0722p1.c("ofNullable")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda121
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.j1(c0722p2, c0684j2);
            }
        }, 0));
    }

    private void u(C0722p1 c0722p1) {
        a(new C1584a4(c0722p1.a(c0722p1.O1, c0722p1.b(c0722p1.X1, c0722p1.A1), c0722p1.c("toString")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda64
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.m(c0722p2, c0684j2);
            }
        }, 0));
        C0767v2 c0767v2 = c0722p1.V1;
        C0759u2 c0759u2C = c0722p1.c("compare");
        C0767v2 c0767v3 = c0722p1.A1;
        C0767v2 c0767v4 = c0722p1.V1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v4, c0767v4), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda65
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.k(c0722p2, c0684j2);
            }
        }, 0));
    }

    private AbstractC2178hm v(C0722p1 c0722p1) {
        C1946em c1946emE = AbstractC2178hm.e();
        c1946emE.a(c0722p1.g2, EnumC3891f.K);
        C0767v2 c0767v2 = c0722p1.V2;
        EnumC3891f enumC3891f = EnumC3891f.N;
        c1946emE.a(c0767v2, enumC3891f);
        c1946emE.a(c0722p1.S2, enumC3891f);
        c1946emE.a(c0722p1.L2, EnumC3891f.B);
        c1946emE.a(c0722p1.Z2, enumC3891f);
        c1946emE.a(c0722p1.R2, enumC3891f);
        AbstractC2178hm abstractC2178hmA = c1946emE.a();
        if (!d) {
            a(abstractC2178hmA);
        }
        return abstractC2178hmA;
    }

    private boolean b(C0767v2 c0767v2) {
        return this.b.Y().B0().a((EnumC3891f) this.a.get(c0767v2)) || a(c0767v2);
    }

    private boolean c(C0767v2 c0767v2, EnumC3891f enumC3891f) {
        if (a(c0767v2)) {
            return enumC3891f.d(EnumC3891f.N) && !this.b.Y().C1.D();
        }
        if (this.b.Y().B0().a((EnumC3891f) this.a.get(c0767v2))) {
            return this.b.Y().B0().e(enumC3891f);
        }
        return false;
    }

    private void b(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.g2;
        C0759u2 c0759u2C = c0722p1.c("compare");
        C0767v2 c0767v3 = c0722p1.A1;
        C0767v2 c0767v4 = c0722p1.Z1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v4, c0767v4, c0722p1.Q2), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda122
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.F0(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C2 = c0722p1.c("deepEquals");
        C0767v2 c0767v5 = c0722p1.v1;
        C0767v2 c0767v6 = c0722p1.Z1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v5, c0767v6, c0767v6), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.G0(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C3 = c0722p1.c(Namer.EQUALS_METHOD_NAME);
        C0767v2 c0767v7 = c0722p1.v1;
        C0767v2 c0767v8 = c0722p1.Z1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v7, c0767v8, c0767v8), c0759u2C3), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.H0(c0722p2, c0684j2);
            }
        }));
        a(new Z3(c0722p1.a(c0767v2, c0722p1.b(c0722p1.A1, c0722p1.c2), c0722p1.c("hash")), BK.b()));
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0722p1.A1, c0722p1.Z1), c0722p1.c("hashCode")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.I0(c0722p2, c0684j2);
            }
        }));
        a(new Z3(c0722p1.f4.e, BK.a()));
        C0684j2 c0684j2 = c0722p1.f4.f;
        AbstractC0748t0 abstractC0748t0B = this.b.q() ? this.b.b(c0684j2) : null;
        if (abstractC0748t0B == null || !abstractC0748t0B.B().w()) {
            a(new C1584a4(c0684j2, new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j3) {
                    return AbstractC2356k4.N0(c0722p2, c0684j3);
                }
            }, 0));
        }
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0722p1.X1, c0722p1.Z1), c0722p1.c("toString")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j3) {
                return AbstractC2356k4.P0(c0722p2, c0684j3);
            }
        }));
        C0759u2 c0759u2C4 = c0722p1.c("toString");
        C0767v2 c0767v9 = c0722p1.X1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v9, c0722p1.Z1, c0767v9), c0759u2C4), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j3) {
                return AbstractC2356k4.Q0(c0722p2, c0684j3);
            }
        }, 0));
    }

    private void c(C0722p1 c0722p1) {
        a(new Z3(c0722p1.a(c0722p1.N1, c0722p1.b(c0722p1.A1, c0722p1.w1), c0722p1.c("hashCode")), AbstractC2372kI.a()));
        a(new Z3(c0722p1.a(c0722p1.T1, c0722p1.b(c0722p1.A1, c0722p1.C1), c0722p1.c("hashCode")), AbstractC2372kI.a()));
        C0767v2 c0767v2 = c0722p1.R1;
        C0759u2 c0759u2C = c0722p1.c("hashCode");
        C0767v2 c0767v3 = c0722p1.A1;
        a(new Z3(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v3), c0759u2C), AbstractC2372kI.a()));
        C0759u2 c0759u2C2 = c0722p1.c("max");
        C0767v2 c0767v4 = c0722p1.A1;
        a(new Z3(c0722p1.a(c0767v2, c0722p1.b(c0767v4, c0767v4, c0767v4), c0759u2C2), AbstractC2372kI.c()));
        C0759u2 c0759u2C3 = c0722p1.c("min");
        C0767v2 c0767v5 = c0722p1.A1;
        a(new Z3(c0722p1.a(c0767v2, c0722p1.b(c0767v5, c0767v5, c0767v5), c0759u2C3), AbstractC2372kI.c()));
        C0759u2 c0759u2C4 = c0722p1.c("sum");
        C0767v2 c0767v6 = c0722p1.A1;
        a(new Z3(c0722p1.a(c0767v2, c0722p1.b(c0767v6, c0767v6, c0767v6), c0759u2C4), AbstractC2372kI.b()));
        C0767v2 c0767v7 = c0722p1.P1;
        a(new C1584a4(c0722p1.a(c0767v7, c0722p1.b(c0722p1.A1, c0722p1.y1), c0722p1.c("hashCode")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.y(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C5 = c0722p1.c("max");
        C0767v2 c0767v8 = c0722p1.y1;
        a(new Z3(c0722p1.a(c0767v7, c0722p1.b(c0767v8, c0767v8, c0767v8), c0759u2C5), AbstractC2372kI.c()));
        C0759u2 c0759u2C6 = c0722p1.c("min");
        C0767v2 c0767v9 = c0722p1.y1;
        a(new Z3(c0722p1.a(c0767v7, c0722p1.b(c0767v9, c0767v9, c0767v9), c0759u2C6), AbstractC2372kI.c()));
        C0759u2 c0759u2C7 = c0722p1.c("sum");
        C0767v2 c0767v10 = c0722p1.y1;
        a(new Z3(c0722p1.a(c0767v7, c0722p1.b(c0767v10, c0767v10, c0767v10), c0759u2C7), AbstractC2372kI.b()));
        a(new C1584a4(c0722p1.a(c0767v7, c0722p1.b(c0722p1.v1, c0722p1.y1), c0722p1.c("isFinite")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda17
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.z(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v11 = c0722p1.Q1;
        a(new Z3(c0722p1.a(c0767v11, c0722p1.b(c0722p1.A1, c0722p1.z1), c0722p1.c("hashCode")), AbstractC0830Ai.a()));
        C0759u2 c0759u2C8 = c0722p1.c("max");
        C0767v2 c0767v12 = c0722p1.z1;
        a(new Z3(c0722p1.a(c0767v11, c0722p1.b(c0767v12, c0767v12, c0767v12), c0759u2C8), AbstractC2372kI.c()));
        C0759u2 c0759u2C9 = c0722p1.c("min");
        C0767v2 c0767v13 = c0722p1.z1;
        a(new Z3(c0722p1.a(c0767v11, c0722p1.b(c0767v13, c0767v13, c0767v13), c0759u2C9), AbstractC2372kI.c()));
        C0759u2 c0759u2C10 = c0722p1.c("sum");
        C0767v2 c0767v14 = c0722p1.z1;
        a(new Z3(c0722p1.a(c0767v11, c0722p1.b(c0767v14, c0767v14, c0767v14), c0759u2C10), AbstractC2372kI.b()));
        a(new C1584a4(c0722p1.a(c0767v11, c0722p1.b(c0722p1.v1, c0722p1.z1), c0722p1.c("isFinite")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda18
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.A(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v15 = c0722p1.M1;
        a(new C1584a4(c0722p1.a(c0767v15, c0722p1.b(c0722p1.A1, c0722p1.v1), c0722p1.c("hashCode")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda19
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.f(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C11 = c0722p1.c("logicalAnd");
        C0767v2 c0767v16 = c0722p1.v1;
        a(new Z3(c0722p1.a(c0767v15, c0722p1.b(c0767v16, c0767v16, c0767v16), c0759u2C11), AbstractC2754p5.a()));
        C0759u2 c0759u2C12 = c0722p1.c("logicalOr");
        C0767v2 c0767v17 = c0722p1.v1;
        a(new Z3(c0722p1.a(c0767v15, c0722p1.b(c0767v17, c0767v17, c0767v17), c0759u2C12), AbstractC2754p5.b()));
        C0759u2 c0759u2C13 = c0722p1.c("logicalXor");
        C0767v2 c0767v18 = c0722p1.v1;
        a(new Z3(c0722p1.a(c0767v15, c0722p1.b(c0767v18, c0767v18, c0767v18), c0759u2C13), AbstractC2754p5.c()));
        C0767v2 c0767v19 = c0722p1.S1;
        a(new C1584a4(c0722p1.a(c0767v19, c0722p1.b(c0722p1.A1, c0722p1.B1), c0722p1.c("hashCode")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda20
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.P(c0722p2, c0684j2);
            }
        }));
        C0759u2 c0759u2C14 = c0722p1.c("max");
        C0767v2 c0767v20 = c0722p1.B1;
        a(new Z3(c0722p1.a(c0767v19, c0722p1.b(c0767v20, c0767v20, c0767v20), c0759u2C14), AbstractC2372kI.c()));
        C0759u2 c0759u2C15 = c0722p1.c("min");
        C0767v2 c0767v21 = c0722p1.B1;
        a(new Z3(c0722p1.a(c0767v19, c0722p1.b(c0767v21, c0767v21, c0767v21), c0759u2C15), AbstractC2372kI.c()));
        C0759u2 c0759u2C16 = c0722p1.c("sum");
        C0767v2 c0767v22 = c0722p1.B1;
        a(new Z3(c0722p1.a(c0767v19, c0722p1.b(c0767v22, c0767v22, c0767v22), c0759u2C16), AbstractC2372kI.b()));
        a(new Z3(c0722p1.a(c0722p1.O1, c0722p1.b(c0722p1.A1, c0722p1.x1), c0722p1.c("hashCode")), AbstractC2372kI.a()));
        C0767v2[] c0767v2Arr = {c0722p1.j2, c0722p1.k2};
        for (int i = 0; i < 2; i++) {
            C0767v2 c0767v23 = c0767v2Arr[i];
            C0759u2 c0759u2C17 = c0722p1.c("addExact");
            C0767v2 c0767v24 = c0722p1.A1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v24, c0767v24, c0767v24), c0759u2C17), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda21
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.a0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C18 = c0722p1.c("addExact");
            C0767v2 c0767v25 = c0722p1.B1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v25, c0767v25, c0767v25), c0759u2C18), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda23
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.b0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C19 = c0722p1.c("floorDiv");
            C0767v2 c0767v26 = c0722p1.A1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v26, c0767v26, c0767v26), c0759u2C19), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda24
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.e0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C20 = c0722p1.c("floorDiv");
            C0767v2 c0767v27 = c0722p1.B1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v27, c0767v27, c0767v27), c0759u2C20), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda25
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.f0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C21 = c0722p1.c("floorMod");
            C0767v2 c0767v28 = c0722p1.A1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v28, c0767v28, c0767v28), c0759u2C21), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda26
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.h0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C22 = c0722p1.c("floorMod");
            C0767v2 c0767v29 = c0722p1.B1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v29, c0767v29, c0767v29), c0759u2C22), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda8
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.i0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C23 = c0722p1.c("multiplyExact");
            C0767v2 c0767v30 = c0722p1.A1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v30, c0767v30, c0767v30), c0759u2C23), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda9
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.m0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C24 = c0722p1.c("multiplyExact");
            C0767v2 c0767v31 = c0722p1.B1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v31, c0767v31, c0767v31), c0759u2C24), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda10
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.n0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C25 = c0722p1.c("nextDown");
            C0767v2 c0767v32 = c0722p1.y1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v32, c0767v32), c0759u2C25), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda12
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.t0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C26 = c0722p1.c("nextDown");
            C0767v2 c0767v33 = c0722p1.z1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v33, c0767v33), c0759u2C26), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda13
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.u0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C27 = c0722p1.c("subtractExact");
            C0767v2 c0767v34 = c0722p1.A1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v34, c0767v34, c0767v34), c0759u2C27), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda14
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.v0(c0722p2, c0684j2);
                }
            }, 0));
            C0759u2 c0759u2C28 = c0722p1.c("subtractExact");
            C0767v2 c0767v35 = c0722p1.B1;
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0767v35, c0767v35, c0767v35), c0759u2C28), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda15
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.w0(c0722p2, c0684j2);
                }
            }, 0));
            a(new C1584a4(c0722p1.a(c0767v23, c0722p1.b(c0722p1.A1, c0722p1.B1), c0722p1.c("toIntExact")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda16
                @Override // com.android.tools.r8.internal.InterfaceC2047g4
                public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                    return AbstractC2356k4.x0(c0722p2, c0684j2);
                }
            }));
        }
        a(c0722p1, c0722p1.j2);
        a(new Z3(c0722p1.F4.b, AbstractC2169hd.a()));
        a(new Z3(c0722p1.G4.b, AbstractC1007Gf.a()));
    }

    private void a(AbstractC2178hm abstractC2178hm) {
        abstractC2178hm.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda83
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0767v2) obj, (EnumC3891f) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2, EnumC3891f enumC3891f) {
        com.android.tools.r8.androidapi.h hVarA = this.b.d().a(c0767v2, com.android.tools.r8.androidapi.h.f());
        if (hVarA.y()) {
            EnumC3891f enumC3891fA = hVarA.b0().a();
            if (a(c0767v2)) {
                if (!d && !enumC3891fA.equals(this.b.Y().B0())) {
                    throw new AssertionError();
                }
                return;
            }
            AbstractC0727q0 abstractC0727q0R = this.b.f().f(c0767v2).R();
            if (d || enumC3891fA.equals(enumC3891f.c(this.b.Y().B0()))) {
                return;
            }
            if (abstractC0727q0R == null || !abstractC0727q0R.e0()) {
                throw new AssertionError();
            }
        }
    }

    private boolean a(C0767v2 c0767v2) {
        C0702m c0702m = this.b;
        return c0702m.t.a(c0702m, c0767v2) || this.b.Y().C1.m().containsKey(c0767v2) || this.b.Y().C1.r().contains(c0767v2);
    }

    public final void a(Consumer consumer) {
        this.c.keySet().forEach(consumer);
    }

    private void a(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.N1;
        C0759u2 c0759u2C = c0722p1.c("compare");
        C0767v2 c0767v3 = c0722p1.A1;
        C0767v2 c0767v4 = c0722p1.w1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v4, c0767v4), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda53
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.g(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v5 = c0722p1.T1;
        C0759u2 c0759u2C2 = c0722p1.c("compare");
        C0767v2 c0767v6 = c0722p1.A1;
        C0767v2 c0767v7 = c0722p1.C1;
        a(new C1584a4(c0722p1.a(c0767v5, c0722p1.b(c0767v6, c0767v7, c0767v7), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda54
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.f1(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v8 = c0722p1.R1;
        C0759u2 c0759u2C3 = c0722p1.c("compare");
        C0767v2 c0767v9 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v8, c0722p1.b(c0767v9, c0767v9, c0767v9), c0759u2C3), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda55
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.B(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v10 = c0722p1.S1;
        C0759u2 c0759u2C4 = c0722p1.c("compare");
        C0767v2 c0767v11 = c0722p1.A1;
        C0767v2 c0767v12 = c0722p1.B1;
        a(new Z3(c0722p1.a(c0767v10, c0722p1.b(c0767v11, c0767v12, c0767v12), c0759u2C4), AbstractC1824dD.a()));
        C0767v2 c0767v13 = c0722p1.M1;
        C0759u2 c0759u2C5 = c0722p1.c("compare");
        C0767v2 c0767v14 = c0722p1.A1;
        C0767v2 c0767v15 = c0722p1.v1;
        a(new C1584a4(c0722p1.a(c0767v13, c0722p1.b(c0767v14, c0767v15, c0767v15), c0759u2C5), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda56
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.e(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v16 = c0722p1.O1;
        C0759u2 c0759u2C6 = c0722p1.c("compare");
        C0767v2 c0767v17 = c0722p1.A1;
        C0767v2 c0767v18 = c0722p1.x1;
        a(new C1584a4(c0722p1.a(c0767v16, c0722p1.b(c0767v17, c0767v18, c0767v18), c0759u2C6), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda58
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.l(c0722p2, c0684j2);
            }
        }));
        C0767v2 c0767v19 = c0722p1.h2;
        a(new C1584a4(c0722p1.a(c0767v19, c0722p1.b(c0722p1.t5, new C0767v2[0]), c0722p1.c("emptyEnumeration")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda59
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.v(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v19, c0722p1.b(c0722p1.r5, new C0767v2[0]), c0722p1.c("emptyIterator")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda60
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.w(c0722p2, c0684j2);
            }
        }));
        a(new C1584a4(c0722p1.a(c0767v19, c0722p1.b(c0722p1.s5, new C0767v2[0]), c0722p1.c("emptyListIterator")), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda61
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.x(c0722p2, c0684j2);
            }
        }));
    }

    private void a(C0722p1 c0722p1, C0767v2 c0767v2) {
        C0759u2 c0759u2C = c0722p1.c("decrementExact");
        C0767v2 c0767v3 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v3), c0759u2C), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda101
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.c0(c0722p2, c0684j2);
            }
        }, 0));
        C0759u2 c0759u2C2 = c0722p1.c("decrementExact");
        C0767v2 c0767v4 = c0722p1.B1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v4, c0767v4), c0759u2C2), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda112
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.d0(c0722p2, c0684j2);
            }
        }, 0));
        C0759u2 c0759u2C3 = c0722p1.c("incrementExact");
        C0767v2 c0767v5 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v5, c0767v5), c0759u2C3), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda123
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.k0(c0722p2, c0684j2);
            }
        }, 0));
        C0759u2 c0759u2C4 = c0722p1.c("incrementExact");
        C0767v2 c0767v6 = c0722p1.B1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v6, c0767v6), c0759u2C4), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.l0(c0722p2, c0684j2);
            }
        }, 0));
        C0759u2 c0759u2C5 = c0722p1.c("negateExact");
        C0767v2 c0767v7 = c0722p1.A1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v7, c0767v7), c0759u2C5), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda22
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.r0(c0722p2, c0684j2);
            }
        }, 0));
        C0759u2 c0759u2C6 = c0722p1.c("negateExact");
        C0767v2 c0767v8 = c0722p1.B1;
        a(new C1584a4(c0722p1.a(c0767v2, c0722p1.b(c0767v8, c0767v8), c0759u2C6), new InterfaceC2047g4() { // from class: com.android.tools.r8.internal.d4$$ExternalSyntheticLambda27
            @Override // com.android.tools.r8.internal.InterfaceC2047g4
            public final C0753u a(C0722p1 c0722p2, C0684j2 c0684j2) {
                return AbstractC2356k4.s0(c0722p2, c0684j2);
            }
        }, 0));
    }

    private void a(AbstractC1739c4 abstractC1739c4) {
        AbstractC1739c4 abstractC1739c5 = (AbstractC1739c4) this.c.put(abstractC1739c4.a, abstractC1739c4);
        if (!d && abstractC1739c5 != null) {
            throw new AssertionError();
        }
    }
}
