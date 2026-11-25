package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xe, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3378xe implements C7 {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final Set b;
    private final Set c;
    private final C1317Re d;
    private final Set e;

    public C3378xe(C0702m c0702m, AbstractC1048Hm abstractC1048Hm, Set set) {
        this.a = c0702m;
        c0702m.a();
        this.b = abstractC1048Hm;
        this.c = set;
        this.d = new C1317Re(c0702m);
        if (c0702m.Y().n1.e0) {
            this.e = OU.a();
        } else {
            this.e = null;
        }
    }

    private boolean a(I7 i7, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0748t0 abstractC0748t0C;
        AbstractC0727q0 abstractC0727q0C;
        AbstractC0748t0 abstractC0748t0B;
        if (a(o5Var.t(), this.d, this.a) || this.a.a().f3.contains(o5Var.t())) {
            return false;
        }
        I7 i7L = i7.l();
        C0684j2 c0684j2T = i7L.T();
        C0680j c0680jG = this.a.g();
        if (i7L.b(o5Var.t())) {
            C0702m c0702m = this.a;
            c0680jG.getClass();
            abstractC0748t0C = c0680jG.a(c0684j2T, o5Var.a(), c0702m, c0680jG);
        } else {
            abstractC0748t0C = c0680jG.b(c0684j2T, i7L.V()).c();
        }
        if (abstractC0748t0C == null) {
            return false;
        }
        C0767v2 c0767v2T = abstractC0748t0C.t();
        C0702m c0702m2 = this.a;
        if (c0702m2.t.a(c0702m2, c0767v2T) || c0767v2T.M0() || (abstractC0727q0C = this.a.c(c0767v2T)) == null || !abstractC0727q0C.f0()) {
            return false;
        }
        if ((this.c.contains(abstractC0748t0C.u()) && (abstractC0748t0B = this.a.g().b(abstractC0748t0C.a(), abstractC0748t0C.getReference())) != null && this.a.Y().C1.m().containsKey(abstractC0748t0B.t())) || b(i7L, o5Var)) {
            return false;
        }
        if (this.a.Y().C1.j().get(abstractC0748t0C.getReference()) != null) {
            return true;
        }
        return this.a.t.a(abstractC0748t0C.F(), this.a);
    }

    private boolean b(final I7 i7, final com.android.tools.r8.graph.o5 o5Var) {
        return AbstractC2734ot.b(this.b, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.xe$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3378xe.a(i7, o5Var, (C7) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(final com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        if (!b7.H()) {
            return C3224ve.c();
        }
        final I7 i7L = b7.l();
        if (!a(i7L, o5Var)) {
            return C3224ve.c();
        }
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.xe$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(i7L, o5Var, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    static boolean a(C0767v2 c0767v2, C1317Re c1317Re, C0702m c0702m) {
        return c1317Re.b(c0767v2) || c0702m.D().a(c0767v2, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.xe$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.O;
            }
        });
    }

    public static boolean a(C0767v2 c0767v2) {
        return c0767v2.e.toString().startsWith("L$-vivified-$/");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(I7 i7, com.android.tools.r8.graph.o5 o5Var, C7 c7) {
        return c7.a(o5Var, i7).b();
    }

    public static C0684j2 a(C0702m c0702m, C0684j2 c0684j2, C0767v2 c0767v2) {
        C0767v2[] c0767v2Arr = (C0767v2[]) c0684j2.h.f.a.clone();
        int i = 0;
        for (C0767v2 c0767v3 : c0684j2.h.f.a) {
            if (c0702m.t.a(c0702m, c0767v3)) {
                c0767v2Arr[i] = a(c0702m, c0767v3);
            }
            i++;
        }
        C0767v2 c0767v2A = c0684j2.h.e;
        if (c0702m.t.a(c0702m, c0767v2A)) {
            c0767v2A = a(c0702m, c0767v2A);
        }
        return c0702m.a().a(c0767v2, c0702m.a().b(c0767v2A, c0767v2Arr), c0684j2.f);
    }

    public final void a() {
        Set<C0684j2> set = this.e;
        C0702m c0702m = this.a;
        if (c0702m.Y().n1.e0) {
            StringBuilder sb = new StringBuilder("Tracked desugared API conversions: ");
            for (C0684j2 c0684j2 : set) {
                sb.append("\n");
                sb.append(c0684j2);
            }
            c0702m.Y().e.warning(new StringDiagnostic(sb.toString()));
            set.clear();
        }
    }

    public static C0767v2 a(C0702m c0702m, C0767v2 c0767v2) {
        C0767v2 c0767v2D = c0702m.a().d(C3970u0.H("$-vivified-$." + c0767v2.toString()));
        c0702m.t.a(c0767v2D, c0767v2);
        return c0767v2D;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:26:0x0088  */
    public Collection a(I7 i7, com.android.tools.r8.graph.o5 o5Var, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        boolean zM;
        AbstractC0748t0 abstractC0748t0C;
        C0684j2 c0684j2T = i7.T();
        Set set = this.e;
        if (set != null) {
            set.add(c0684j2T);
        }
        if (this.a.Y().n1.e || i7.b(o5Var.t()) || i7.T().b(this.a.a())) {
            zM = false;
        } else {
            C0684j2 c0684j2T2 = i7.T();
            C0680j c0680jG = this.a.g();
            if (i7.b(o5Var.t())) {
                C0702m c0702m = this.a;
                c0680jG.getClass();
                abstractC0748t0C = c0680jG.a(c0684j2T2, o5Var.a(), c0702m, c0680jG);
            } else {
                abstractC0748t0C = c0680jG.b(c0684j2T2, i7.V()).c();
            }
            if (!f && abstractC0748t0C == null) {
                throw new AssertionError();
            }
            if (this.a.Y().C1.s().contains(abstractC0748t0C.getReference())) {
                zM = false;
            } else {
                zM = abstractC0748t0C.getAccessFlags().m();
            }
        }
        if (zM) {
            return Collections.singletonList(new I7(184, this.d.b().a(i7, g7, o5Var, c1230Ob).getReference(), false));
        }
        return this.d.b().a(i7, c1230Ob, interfaceC1374Ti, ac, g7, o5Var);
    }
}
