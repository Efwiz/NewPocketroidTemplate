package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class NA implements C7 {
    private final C0702m a;
    private final Set b = OU.c();

    public NA(C0702m c0702m) {
        this.a = c0702m;
    }

    public final boolean a(C0699l2 c0699l2) {
        return c0699l2.d.d() && this.b.contains(c0699l2.u0());
    }

    @Override // com.android.tools.r8.internal.C7
    public final void a(com.android.tools.r8.graph.o5 o5Var, G7 g7) {
        for (B7 b7 : o5Var.e().a1().K().I0()) {
            if (b7.J()) {
                C0684j2 c0684j2T = b7.l().T();
                C0759u2 c0759u2C0 = c0684j2T.C0();
                C0759u2 c0759u2 = this.a.a().e1;
                c0759u2C0.getClass();
                if (c0759u2C0.a(c0759u2.e)) {
                    this.b.add(c0684j2T);
                }
            }
        }
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        C0702m c0702m = this.a;
        b7.getClass();
        if (!(b7 instanceof J7) || JA.b(b7.n().T(), c0702m, c0702m.g(), o5Var) == null) {
            return C3224ve.c();
        }
        return a(b7);
    }

    private C3224ve a(final B7 b7) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.NA$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(b7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection a(B7 b7, InterfaceC1374Ti interfaceC1374Ti, AC ac, final G7 g7, final com.android.tools.r8.graph.o5 o5Var, final C1230Ob c1230Ob, final D7 d7, C0722p1 c0722p1) {
        return a(b7.n(), interfaceC1374Ti, ac, g7, o5Var, c1230Ob, new MA() { // from class: com.android.tools.r8.internal.NA$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.internal.MA
            public final Collection a(I7 i7, InterfaceC1374Ti interfaceC1374Ti2, AC ac2) {
                return NA.a(d7, g7, o5Var, c1230Ob, i7, interfaceC1374Ti2, ac2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(D7 d7, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, I7 i7, InterfaceC1374Ti interfaceC1374Ti, AC ac) {
        return d7.a(i7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob);
    }

    private AbstractCollection a(J7 j7, final InterfaceC1374Ti interfaceC1374Ti, AC ac, LA la, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, MA ma) {
        HA haA = a(j7, o5Var, c1230Ob, ma);
        if (haA == null) {
            return null;
        }
        la.a(haA, o5Var);
        if (haA.f()) {
            return AbstractC1639am.c(new I7(184, haA.c(), false));
        }
        if (haA.g()) {
            com.android.tools.r8.graph.Y0 y0 = haA.h;
            return AbstractC1639am.c(new O8(y0, y0));
        }
        C0800x2 c0800x2 = haA.d.i;
        final ArrayDeque arrayDeque = new ArrayDeque((c0800x2.size() * 2) + 3);
        arrayDeque.add(new V7(null, haA.c));
        arrayDeque.add(new D8(D8.a.Dup));
        c0800x2.forEach(new Consumer() { // from class: com.android.tools.r8.internal.NA$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NA.a(interfaceC1374Ti, arrayDeque, (C0767v2) obj);
            }
        });
        arrayDeque.add(new I7(183, haA.e, false));
        ac.a(2);
        return arrayDeque;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(InterfaceC1374Ti interfaceC1374Ti, Deque deque, C0767v2 c0767v2) {
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        EnumC1812d20 enumC1812d20A = EnumC1812d20.a((char) c0767v2.e.e[0]);
        int iA = interfaceC1374Ti.a(enumC1812d20A.c());
        deque.addFirst(new R8(enumC1812d20A, iA));
        deque.addLast(new O7(enumC1812d20A, iA));
    }

    private HA a(J7 j7, final com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, final MA ma) {
        C0721p0 c0721p0T = j7.T();
        C0702m c0702m = this.a;
        final JA jaB = JA.b(c0721p0T, c0702m, c0702m.g(), o5Var);
        if (jaB == null) {
            return null;
        }
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        C0729q2 c0729q2A = this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.NA$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.v;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.NA$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(n, o5Var, jaB, ma, (com.android.tools.r8.synthesis.P) obj);
            }
        });
        HA ha = (HA) n.a();
        ha.a(c0729q2A);
        return ha;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.utils.N n, com.android.tools.r8.graph.o5 o5Var, JA ja, MA ma, com.android.tools.r8.synthesis.P p) {
        n.a(new HA(p, this.a, this, o5Var, ja, ma));
    }
}
