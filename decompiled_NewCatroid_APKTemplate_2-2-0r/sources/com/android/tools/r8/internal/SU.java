package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.C3912j0;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class SU extends AbstractC1447Vg {
    private final C0729q2 b;
    private final com.android.tools.r8.graph.o5 c;

    public SU(C0729q2 c0729q2, C0729q2 c0729q3, com.android.tools.r8.graph.o5 o5Var) {
        super(c0729q3);
        this.b = c0729q2;
        this.c = o5Var;
    }

    public static RU a(C0702m c0702m, C3226vg c3226vg, AbstractC1048Hm abstractC1048Hm, com.android.tools.r8.shaking.G0 g0) {
        return new RU(c0702m, c3226vg, abstractC1048Hm, g0);
    }

    private com.android.tools.r8.graph.o5 b(C0702m c0702m) {
        final C0722p1 c0722p1A = c0702m.a();
        return a(c0702m, c0722p1A.c("checkNotZero"), c0722p1A.b(c0722p1A.D1, c0722p1A.A1), new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.SU$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return SU.a(c0722p1A, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W c(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        return new C0753u(c0767v2, 2, 2, AbstractC1639am.a(m7, new O7(enumC1812d20, 0), new C3039t7(EnumC1076Il.EQ, enumC1812d20, m8), new O7(enumC1812d20, 1), new C3039t7(EnumC1076Il.NE, enumC1812d20, m9), m8, new C2284j7(new C2413kp(new int[]{0, 1}, new InterfaceC1348Si[]{InterfaceC1348Si.h(), InterfaceC1348Si.h()}, 0)), new V7(null, c0722p1.e("Ljava/lang/NullPointerException;")), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.e("Ljava/lang/NullPointerException;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new V8(), m9, new C2284j7(new C2413kp(new int[]{0, 1}, new InterfaceC1348Si[]{InterfaceC1348Si.h(), InterfaceC1348Si.h()}, 0)), new O7(enumC1812d20, 0), new O7(enumC1812d20, 1), new C3481z6(C3481z6.a.Sub, EnumC2527mI.INT), new C3109u8(enumC1812d20), m10), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }

    private com.android.tools.r8.graph.o5 d(C0702m c0702m) {
        final C0722p1 c0722p1A = c0702m.a();
        C0759u2 c0759u2C0 = c0722p1A.r4.g.C0();
        C0767v2 c0767v2 = c0722p1A.A1;
        return a(c0702m, c0759u2C0, c0722p1A.b(c0767v2, c0767v2, c0767v2), new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.SU$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return SU.c(c0722p1A, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W e(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        return new C0753u(c0767v2, 2, 1, AbstractC1639am.a(m7, new O7(enumC1812d20, 0), new C3039t7(EnumC1076Il.NE, enumC1812d20, m9), m8, new V7(null, c0722p1.e("Ljava/lang/NullPointerException;")), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.e("Ljava/lang/NullPointerException;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new V8(), m9, new C2284j7(new C2413kp(new int[]{0}, new InterfaceC1348Si[]{InterfaceC1348Si.h()}, 0)), new O7(enumC1812d20, 0), new C1818d7(1L, enumC1812d20), new C3481z6(C3481z6.a.Sub, EnumC2527mI.INT), new C3109u8(enumC1812d20), m10), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }

    private com.android.tools.r8.graph.o5 f(C0702m c0702m) {
        final C0722p1 c0722p1A = c0702m.a();
        C0759u2 c0759u2C0 = c0722p1A.r4.d.C0();
        C0767v2 c0767v2 = c0722p1A.A1;
        return a(c0702m, c0759u2C0, c0722p1A.b(c0767v2, c0767v2), new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.SU$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return SU.e(c0722p1A, c0684j2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC1447Vg
    public final void a(C0702m c0702m) {
        b(c0702m);
        c(c0702m);
        d(c0702m);
        e(c0702m);
        f(c0702m);
    }

    public final com.android.tools.r8.graph.o5 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, VE ve) {
        com.android.tools.r8.graph.o5 o5VarB = b(c0702m);
        ve.e(o5VarB, o5Var);
        return o5VarB;
    }

    public final com.android.tools.r8.graph.o5 b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, VE ve) {
        com.android.tools.r8.graph.o5 o5VarC = c(c0702m);
        ve.e(o5VarC, o5Var);
        return o5VarC;
    }

    public final com.android.tools.r8.graph.o5 d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, VE ve) {
        com.android.tools.r8.graph.o5 o5VarE = e(c0702m);
        ve.e(o5VarE, o5Var);
        return o5VarE;
    }

    private com.android.tools.r8.graph.o5 a(final C0702m c0702m, C0759u2 c0759u2, C0737r2 c0737r2, final com.android.tools.r8.synthesis.G g) {
        com.android.tools.r8.synthesis.D D = c0702m.D();
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.SU$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.j;
            }
        };
        C0729q2 c0729q2B = b();
        Consumer consumerB = C3912j0.b();
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.SU$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SU.a(c0702m, g, (com.android.tools.r8.synthesis.H) obj);
            }
        };
        D.getClass();
        return D.a(c0759u2, c0737r2, c, c0729q2B, c0702m, consumerB, consumer, C3912j0.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W b(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        return new C0753u(c0767v2, 3, 2, AbstractC1639am.a(m7, new O7(enumC1812d20, 0), new C3039t7(EnumC1076Il.NE, enumC1812d20, m9), m8, new V7(null, c0722p1.e("Ljava/lang/NullPointerException;")), new D8(D8.a.Dup), new O7(EnumC1812d20.OBJECT, 1), new I7(183, c0722p1.a(c0722p1.e("Ljava/lang/NullPointerException;"), c0722p1.b(c0722p1.D1, c0722p1.X1), c0722p1.c("<init>")), false), new V8(), m9, new C2284j7(new C2413kp(new int[]{0, 1}, new InterfaceC1348Si[]{InterfaceC1348Si.h(), InterfaceC1348Si.b(c0722p1.X1)}, 0)), new C3186v8(), m10), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.synthesis.G g, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).b(c0702m.o()).a(c0702m.o()).a(g).a(Z8.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC1447Vg
    public final C0729q2 a() {
        return this.b;
    }

    public final com.android.tools.r8.graph.o5 a(com.android.tools.r8.graph.o5 o5Var, VE ve) {
        ve.e(this.c, o5Var);
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W a(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        return new C0753u(c0767v2, 2, 1, AbstractC1639am.a(m7, new O7(enumC1812d20, 0), new C3039t7(EnumC1076Il.NE, enumC1812d20, m9), m8, new V7(null, c0722p1.e("Ljava/lang/NullPointerException;")), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.e("Ljava/lang/NullPointerException;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new V8(), m9, new C2284j7(new C2413kp(new int[]{0}, new InterfaceC1348Si[]{InterfaceC1348Si.h()}, 0)), new C3186v8(), m10, new B7[0]), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }

    private com.android.tools.r8.graph.o5 e(C0702m c0702m) {
        final C0722p1 c0722p1A = c0702m.a();
        C0759u2 c0759u2C0 = c0722p1A.r4.i.C0();
        C0767v2 c0767v2 = c0722p1A.v1;
        C0767v2 c0767v3 = c0722p1A.A1;
        return a(c0702m, c0759u2C0, c0722p1A.b(c0767v2, c0767v3, c0767v3), new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.SU$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return AbstractC1265Pg.a(c0722p1A, c0684j2);
            }
        });
    }

    public final com.android.tools.r8.graph.o5 e(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, VE ve) {
        com.android.tools.r8.graph.o5 o5VarF = f(c0702m);
        ve.e(o5VarF, o5Var);
        return o5VarF;
    }

    private com.android.tools.r8.graph.o5 c(C0702m c0702m) {
        final C0722p1 c0722p1A = c0702m.a();
        return a(c0702m, c0722p1A.c("checkNotZero"), c0722p1A.b(c0722p1A.D1, c0722p1A.A1, c0722p1A.X1), new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.SU$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return SU.b(c0722p1A, c0684j2);
            }
        });
    }

    public final com.android.tools.r8.graph.o5 c(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, VE ve) {
        com.android.tools.r8.graph.o5 o5VarD = d(c0702m);
        ve.e(o5VarD, o5Var);
        return o5VarD;
    }

    public final C0767v2 c() {
        return this.b.getType();
    }
}
