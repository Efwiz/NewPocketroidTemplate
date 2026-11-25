package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.C1230Ob;
import com.android.tools.r8.internal.C2049g6;
import com.android.tools.r8.internal.C2284j7;
import com.android.tools.r8.internal.C2413kp;
import com.android.tools.r8.internal.C3039t7;
import com.android.tools.r8.internal.C3186v8;
import com.android.tools.r8.internal.D8;
import com.android.tools.r8.internal.EnumC1076Il;
import com.android.tools.r8.internal.EnumC1812d20;
import com.android.tools.r8.internal.I7;
import com.android.tools.r8.internal.InterfaceC1348Si;
import com.android.tools.r8.internal.M7;
import com.android.tools.r8.internal.O7;
import com.android.tools.r8.internal.V7;
import com.android.tools.r8.internal.V8;
import com.android.tools.r8.internal.VE;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.position.Position;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class U0 {
    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W a(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        return new C0753u(c0767v2, 2, 1, AbstractC1639am.a(m7, new O7(enumC1812d20, 0), new C3039t7(EnumC1076Il.EQ, enumC1812d20, m9), m8, new V7(null, c0722p1.e("Ljava/lang/ClassCastException;")), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.e("Ljava/lang/ClassCastException;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new V8(), m9, new C2284j7(new C2413kp(new int[]{0}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.Z1)}, 0)), new C3186v8(), m10, new B7[0]), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }

    public static T0 b(final C0702m c0702m, VE ve, C1230Ob c1230Ob) {
        final C0722p1 c0722p1A = c0702m.a();
        final C0737r2 c0737r2B = c0722p1A.b(c0722p1A.D1, c0722p1A.Z1);
        o5 o5VarB = c0702m.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda15
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.E;
            }
        }, c1230Ob.a(), c0702m, new Consumer() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                U0.f(c0702m, c0722p1A, c0737r2B, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        ve.q(o5VarB, c1230Ob.b());
        return new T0(o5VarB);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W c(C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C0753u(c0684j2.e, 2, 0, AbstractC1639am.a(new M7(), new V7(null, c0722p1.e("Ljava/lang/IncompatibleClassChangeError;")), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.e("Ljava/lang/IncompatibleClassChangeError;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new V8()), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W d(C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C0753u(c0684j2.e, 2, 0, AbstractC1639am.a(new M7(), new V7(null, c0722p1.e("Ljava/lang/NoSuchMethodError;")), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.e("Ljava/lang/NoSuchMethodError;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new V8()), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W e(C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C0753u(c0684j2.e, 3, 1, AbstractC1639am.a(new M7(), new V7(null, c0722p1.e("Ljava/lang/RuntimeException;")), new D8(D8.a.Dup), new O7(EnumC1812d20.OBJECT, 0), new I7(183, c0722p1.a(c0722p1.e("Ljava/lang/RuntimeException;"), c0722p1.b(c0722p1.D1, c0722p1.X1), c0722p1.c("<init>")), false), new V8(), new M7()), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(C0702m c0702m, final C0722p1 c0722p1, C0737r2 c0737r2, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(Z8.h).b(c0702m.o()).a(c0702m.o()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return U0.f(c0722p1, c0684j2);
            }
        }).a(c0737r2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W f(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        return new C0753u(c0767v2, 1, 1, AbstractC1639am.a(m7, new O7(enumC1812d20, 0), new C3039t7(EnumC1076Il.EQ, enumC1812d20, m9), m8, new O7(enumC1812d20, 0), new I7(182, c0722p1.a(c0722p1.Z1, c0722p1.b(c0722p1.X1, new C0767v2[0]), c0722p1.c("toString")), false), new D8(D8.a.Pop), m9, new C2284j7(new C2413kp(new int[]{0}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.Z1)}, 0)), new C3186v8(), m10), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W b(C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C0753u(c0684j2.e, 2, 0, AbstractC1639am.a(new M7(), new V7(null, c0722p1.e("Ljava/lang/IllegalAccessError;")), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.e("Ljava/lang/IllegalAccessError;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new V8()), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(C0702m c0702m, final C0722p1 c0722p1, C0737r2 c0737r2, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(Z8.h).b(c0702m.o()).a(c0702m.o()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return U0.c(c0722p1, c0684j2);
            }
        }).a(c0737r2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(C0702m c0702m, final C0722p1 c0722p1, C0737r2 c0737r2, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(Z8.h).b(c0702m.o()).a(c0702m.o()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return U0.d(c0722p1, c0684j2);
            }
        }).a(c0737r2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(C0702m c0702m, final C0722p1 c0722p1, C0737r2 c0737r2, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(Z8.h).b(c0702m.o()).a(c0702m.o()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return U0.e(c0722p1, c0684j2);
            }
        }).a(c0737r2);
    }

    public static T0 d(final C0702m c0702m, V0 v0, C1230Ob c1230Ob) {
        final C0722p1 c0722p1A = c0702m.a();
        final C0737r2 c0737r2B = c0722p1A.b(c0722p1A.k3, c0722p1A.X1);
        o5 o5VarB = c0702m.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda17
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.J;
            }
        }, c1230Ob.a(), c0702m, new Consumer() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                U0.e(c0702m, c0722p1A, c0737r2B, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        v0.n(o5VarB, c1230Ob.b());
        return new T0(o5VarB);
    }

    public static T0 c(final C0702m c0702m, V0 v0, C1230Ob c1230Ob) {
        final C0722p1 c0722p1A = c0702m.a();
        final C0737r2 c0737r2B = c0722p1A.b(c0722p1A.r3, new C0767v2[0]);
        o5 o5VarB = c0702m.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.I;
            }
        }, c1230Ob.a(), c0702m, new Consumer() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                U0.d(c0702m, c0722p1A, c0737r2B, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        v0.r(o5VarB, c1230Ob.b());
        return new T0(o5VarB);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C0702m c0702m, final C0722p1 c0722p1, C0737r2 c0737r2, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(Z8.h).b(c0702m.o()).a(c0702m.o()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return U0.b(c0722p1, c0684j2);
            }
        }).a(c0737r2);
    }

    public static T0 a(final C0702m c0702m, VE ve, C1230Ob c1230Ob) {
        final C0722p1 c0722p1A = c0702m.a();
        final C0737r2 c0737r2B = c0722p1A.b(c0722p1A.D1, c0722p1A.Z1);
        o5 o5VarB = c0702m.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.F;
            }
        }, c1230Ob.a(), c0702m, new Consumer() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                U0.a(c0702m, c0722p1A, c0737r2B, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        ve.s(o5VarB, c1230Ob.b());
        return new T0(o5VarB);
    }

    public static T0 b(final C0702m c0702m, V0 v0, C1230Ob c1230Ob) {
        final C0722p1 c0722p1A = c0702m.a();
        final C0737r2 c0737r2B = c0722p1A.b(c0722p1A.p3, new C0767v2[0]);
        o5 o5VarB = c0702m.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.H;
            }
        }, c1230Ob.a(), c0702m, new Consumer() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                U0.c(c0702m, c0722p1A, c0737r2B, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        v0.t(o5VarB, c1230Ob.b());
        return new T0(o5VarB);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, final C0722p1 c0722p1, C0737r2 c0737r2, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(Z8.h).b(c0702m.o()).a(c0702m.o()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return U0.a(c0722p1, c0684j2);
            }
        }).a(c0737r2);
    }

    public static T0 a(final C0702m c0702m, V0 v0, C1230Ob c1230Ob) {
        final C0722p1 c0722p1A = c0702m.a();
        final C0737r2 c0737r2B = c0722p1A.b(c0722p1A.n3, new C0767v2[0]);
        o5 o5VarB = c0702m.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.G;
            }
        }, c1230Ob.a(), c0702m, new Consumer() { // from class: com.android.tools.r8.ir.optimize.U0$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                U0.b(c0702m, c0722p1A, c0737r2B, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        v0.d(o5VarB, c1230Ob.b());
        return new T0(o5VarB);
    }
}
