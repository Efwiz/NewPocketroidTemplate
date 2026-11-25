package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.InterfaceC0419c;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q1 implements C7 {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    public Q1(C0702m c0702m) {
        this.a = c0702m;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x005c  */
    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        boolean z;
        if (b7.H()) {
            I7 i7L = b7.l();
            com.android.tools.r8.graph.I4 i4B = ((C0680j) this.a.f()).b(i7L.T(), i7L.V());
            i4B.getClass();
            if ((i4B instanceof com.android.tools.r8.graph.E4) || i4B.w()) {
                z = false;
            } else if (i4B.h()) {
                z = i4B.k().z();
            } else {
                if (!b && !i4B.x()) {
                    throw new AssertionError();
                }
                if (i4B.r().E0() != i7L.K()) {
                    z = true;
                } else {
                    z = false;
                }
            }
            if (z) {
                return a(this.a, i7L, i4B);
            }
        }
        return C3224ve.c();
    }

    public static C3224ve a(final C0702m c0702m, final I7 i7, final com.android.tools.r8.graph.I4 i4) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.Q1$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return Q1.a(c0702m, i7, i4, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(C0702m c0702m, I7 i7, com.android.tools.r8.graph.I4 i4, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return a(c0702m, i7, ac, g7, c1230Ob, a(c0702m, i7, i4, o5Var));
    }

    public static C3224ve a(final C0702m c0702m, final I7 i7, InterfaceC3147ue interfaceC3147ue) {
        AbstractC2795pe abstractC2795peA = C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.Q1$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return Q1.a(c0702m, i7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        });
        abstractC2795peA.a(interfaceC3147ue);
        return abstractC2795peA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(C0702m c0702m, I7 i7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return a(c0702m, i7, ac, g7, c1230Ob, new Jc$$ExternalSyntheticLambda2());
    }

    private static ArrayList a(C0702m c0702m, I7 i7, AC ac, G7 g7, C1230Ob c1230Ob, com.android.tools.r8.ir.optimize.S0 s0) {
        InterfaceC0419c c1668b7;
        D8.a aVar;
        if (s0 == null) {
            if (b) {
                return null;
            }
            throw new AssertionError();
        }
        com.android.tools.r8.graph.o5 o5VarB = s0.a(c0702m, g7, c1230Ob).b();
        ArrayList arrayList = new ArrayList();
        C0800x2 c0800x2G0 = i7.T().G0();
        for (int length = c0800x2G0.a.length - 1; length >= 0; length--) {
            if (c0800x2G0.j(length).Z0()) {
                aVar = D8.a.Pop2;
            } else {
                aVar = D8.a.Pop;
            }
            arrayList.add(new D8(aVar));
        }
        if (!i7.K()) {
            arrayList.add(new D8(D8.a.Pop));
        }
        I7 i8 = new I7(184, o5VarB.getReference(), false);
        if (!b && !i8.T().I0().O0()) {
            throw new AssertionError();
        }
        arrayList.add(i8);
        arrayList.add(new D8(D8.a.Pop));
        C0767v2 c0767v2I0 = i7.T().I0();
        if (!c0767v2I0.Y0()) {
            if (c0767v2I0.W0()) {
                EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
                c1668b7 = new C1818d7(0L, EnumC1812d20.a((char) c0767v2I0.e.e[0]));
            } else {
                c1668b7 = new C1668b7();
            }
            arrayList.add(c1668b7);
        } else {
            ac.a(1);
        }
        return arrayList;
    }

    private static com.android.tools.r8.ir.optimize.S0 a(C0702m c0702m, I7 i7, com.android.tools.r8.graph.I4 i4, com.android.tools.r8.graph.o5 o5Var) {
        if (i4 == null) {
            return new Jc$$ExternalSyntheticLambda2();
        }
        if (i4.x()) {
            if (i4.r().E0() != i7.K()) {
                return new Jc$$ExternalSyntheticLambda3();
            }
            return null;
        }
        if (!i4.h()) {
            return null;
        }
        com.android.tools.r8.graph.I4.a aVarK = i4.k();
        C0680j c0680jG = c0702m.g();
        if (aVarK.a((AbstractC0727q0) o5Var.a(), (C0702m<?>) c0702m, c0680jG)) {
            return new com.android.tools.r8.ir.optimize.S0() { // from class: com.android.tools.r8.internal.Q1$$ExternalSyntheticLambda2
                @Override // com.android.tools.r8.ir.optimize.S0
                public final com.android.tools.r8.ir.optimize.T0 a(C0702m c0702m2, com.android.tools.r8.ir.optimize.V0 v0, C1230Ob c1230Ob) {
                    return com.android.tools.r8.ir.optimize.U0.a(c0702m2, v0, c1230Ob);
                }
            };
        }
        if (aVarK.b((AbstractC0727q0) o5Var.a(), (C0702m<?>) c0702m, c0680jG)) {
            return new Jc$$ExternalSyntheticLambda2();
        }
        if (aVarK.v()) {
            return new Jc$$ExternalSyntheticLambda3();
        }
        return null;
    }
}
