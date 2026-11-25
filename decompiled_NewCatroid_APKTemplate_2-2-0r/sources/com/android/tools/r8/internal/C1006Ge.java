package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ge, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1006Ge implements C7 {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final C1179Me b;
    private final Map c;
    private final Map d;
    private final Map e;
    private final Map f;
    private final Map g;

    public C1006Ge(C0702m c0702m) {
        this.a = c0702m;
        this.b = new C1179Me(c0702m);
        C2215iD c2215iD = c0702m.Y().C1;
        this.c = c2215iD.x();
        this.d = c2215iD.k();
        this.e = c2215iD.y();
        this.f = c2215iD.t();
        this.g = c2215iD.n();
    }

    private C3224ve b(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        final com.android.tools.r8.graph.Y0 y0;
        final AbstractC2206i7 abstractC2206i7H = b7.h();
        C0680j c0680jG = this.a.g();
        com.android.tools.r8.graph.Y0 field = abstractC2206i7H.getField();
        c0680jG.getClass();
        com.android.tools.r8.graph.T0 t0S = c0680jG.a(field.e, field, o5Var).s();
        if (t0S == null) {
            y0 = null;
        } else {
            if (!h && !t0S.E0() && this.c.containsKey(t0S.getReference())) {
                throw new AssertionError();
            }
            y0 = (com.android.tools.r8.graph.Y0) this.c.get(t0S.getReference());
        }
        return y0 == null ? C3224ve.c() : C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.Ge$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return C1006Ge.a(abstractC2206i7H, y0, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    public final void a(Consumer consumer) {
        this.e.keySet().forEach(consumer);
        this.f.keySet().forEach(consumer);
        this.g.keySet().forEach(consumer);
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        b7.getClass();
        if (b7 instanceof O8) {
            return b(o5Var, b7);
        }
        if (b7.H()) {
            if (this.a.a().f3.contains(o5Var.t())) {
                return C3224ve.c();
            }
            I7 i7L = b7.l();
            C0684j2 c0684j2T = i7L.T();
            C0680j c0680jG = this.a.g();
            com.android.tools.r8.graph.I4 i4B = c0680jG.b(c0684j2T, i7L.V());
            if (!i4B.x()) {
                return C3224ve.c();
            }
            boolean z = h;
            if (!z && i4B.t() == null) {
                throw new AssertionError();
            }
            C0684j2 reference = i4B.t().getReference();
            if (i7L.K()) {
                return a((C0684j2) this.e.get(reference));
            }
            C3224ve c3224veA = a(reference, false);
            if (!c3224veA.b()) {
                return C3224ve.c();
            }
            if (i7L.b(o5Var.t())) {
                AbstractC0748t0 abstractC0748t0A = c0680jG.a(c0684j2T, o5Var.a(), this.a, c0680jG);
                if (abstractC0748t0A != null) {
                    if (z || !abstractC0748t0A.e().E0()) {
                        return a(abstractC0748t0A.getReference(), true);
                    }
                    throw new AssertionError();
                }
            }
            return c3224veA;
        }
        return C3224ve.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0684j2 b(C0684j2 c0684j2, G7 g7, C1230Ob c1230Ob) {
        return this.b.a(c0684j2, g7);
    }

    private C3224ve a(final BiFunction biFunction) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.Ge$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(biFunction, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection a(BiFunction biFunction, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        C0684j2 c0684j2 = (C0684j2) biFunction.apply(g7, c1230Ob);
        boolean z = h;
        if (!z && this.a.c(c0684j2.B0()) == null) {
            throw new AssertionError();
        }
        if (z || !this.a.c(c0684j2.B0()).isInterface()) {
            return Collections.singletonList(new I7(184, c0684j2, false));
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(AbstractC2206i7 abstractC2206i7, com.android.tools.r8.graph.Y0 y0, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return Collections.singletonList(abstractC2206i7.a(y0));
    }

    final C3224ve a(final C0684j2 c0684j2) {
        if (c0684j2 == null) {
            return C3224ve.c();
        }
        return a(new BiFunction() { // from class: com.android.tools.r8.internal.Ge$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.b(c0684j2, (G7) obj, (C1230Ob) obj2);
            }
        });
    }

    private C3224ve a(final C0684j2 c0684j2, final boolean z) {
        final C2404kg c2404kg = (C2404kg) this.g.get(c0684j2);
        if (c2404kg != null) {
            return a(new BiFunction() { // from class: com.android.tools.r8.internal.Ge$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return this.f$0.a(z, c2404kg, (G7) obj, (C1230Ob) obj2);
                }
            });
        }
        if (this.d.containsKey(c0684j2)) {
            return a(new BiFunction() { // from class: com.android.tools.r8.internal.Ge$$ExternalSyntheticLambda2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return this.f$0.a(c0684j2, (G7) obj, (C1230Ob) obj2);
                }
            });
        }
        return a((C0684j2) this.f.get(c0684j2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0684j2 a(boolean z, C2404kg c2404kg, G7 g7, C1230Ob c1230Ob) {
        if (z) {
            C1179Me c1179Me = this.b;
            return c1179Me.a(c1179Me.a(c2404kg), g7);
        }
        return this.b.a(c2404kg, g7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0684j2 a(C0684j2 c0684j2, G7 g7, C1230Ob c1230Ob) {
        return this.b.a(c0684j2, (C0684j2) this.d.get(c0684j2), g7, c1230Ob);
    }
}
