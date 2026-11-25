package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BZ implements C7 {
    private final C0702m a;
    private final C0722p1 b;
    private final C0737r2 c;
    private final C0684j2 d;
    private final C0684j2 e;

    public BZ(C0702m c0702m) {
        this.a = c0702m;
        C0722p1 c0722p1A = c0702m.a();
        this.b = c0722p1A;
        this.c = c0722p1A.b(c0722p1A.D1, c0722p1A.m3, c0722p1A.Z1);
        C0722p1.d dVar = c0722p1A.n4;
        this.d = dVar.a;
        this.e = dVar.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection b(C0737r2 c0737r2, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.B;
            }
        };
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda11
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2356k4.s1((C0722p1) obj, (C0684j2) obj2);
            }
        };
        Objects.requireNonNull(g7);
        return a(c, c0737r2, biFunction, c1230Ob, new BZ$$ExternalSyntheticLambda7(g7), c1230Ob.b());
    }

    private C3224ve d() {
        C0722p1 c0722p1A = this.a.a();
        final C0737r2 c0737r2B = c0722p1A.b(c0722p1A.a(1, c0722p1A.m3), c0722p1A.m3);
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.b(c0737r2B, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        if (!b7.H()) {
            return C3224ve.c();
        }
        if (b7.K()) {
            C0684j2 c0684j2T = b7.l().T();
            C0759u2 c0759u2 = c0684j2T.f;
            C0722p1 c0722p1 = this.b;
            if (c0759u2 == c0722p1.L4 && c0684j2T.h == c0722p1.M4) {
                return c();
            }
        }
        if (!this.a.Y().c0()) {
            C0684j2 c0684j2 = this.d;
            if (b7.H()) {
                C0684j2 c0684j2T2 = b7.l().T();
                if (c0684j2T2.f == c0684j2.f && c0684j2T2.h == c0684j2.h && a(c0684j2T2.e)) {
                    return a();
                }
            }
            C0684j2 c0684j3 = this.e;
            if (b7.H()) {
                C0684j2 c0684j2T3 = b7.l().T();
                if (c0684j2T3.f == c0684j3.f && c0684j2T3.h == c0684j3.h && a(c0684j2T3.e)) {
                    return d();
                }
            }
        }
        return C3224ve.c();
    }

    private C3224ve c() {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    private C3224ve a() {
        C0722p1 c0722p1A = this.a.a();
        C0767v2 c0767v2 = c0722p1A.D1;
        C0767v2 c0767v3 = c0722p1A.m3;
        final C0737r2 c0737r2B = c0722p1A.b(c0767v2, c0767v3, c0767v3);
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(c0737r2B, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection a(C0737r2 c0737r2, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.B;
            }
        };
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2356k4.r1((C0722p1) obj, (C0684j2) obj2);
            }
        };
        Objects.requireNonNull(g7);
        return a(c, c0737r2, biFunction, c1230Ob, new BZ$$ExternalSyntheticLambda7(g7), c1230Ob.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, final G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.K;
            }
        };
        C0737r2 c0737r2 = this.c;
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2356k4.n((C0722p1) obj, (C0684j2) obj2);
            }
        };
        Objects.requireNonNull(g7);
        return a(c, c0737r2, biFunction, c1230Ob, new BiConsumer() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                g7.f((com.android.tools.r8.graph.o5) obj, (com.android.tools.r8.graph.o5) obj2);
            }
        }, c1230Ob.b());
    }

    private AbstractC1639am a(com.android.tools.r8.synthesis.C c, final C0737r2 c0737r2, final BiFunction biFunction, C1230Ob c1230Ob, BiConsumer biConsumer, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.o5 o5VarB = this.a.D().b(c, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2, biFunction, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        biConsumer.accept(o5VarB, o5Var);
        return AbstractC1639am.c(new I7(184, o5VarB.getReference(), false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0737r2 c0737r2, final BiFunction biFunction, com.android.tools.r8.synthesis.H h) {
        h.b().a(c0737r2).a(C0725p4.Q()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.BZ$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(biFunction, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(BiFunction biFunction, C0684j2 c0684j2) {
        return (com.android.tools.r8.graph.W) biFunction.apply(this.a.a(), c0684j2);
    }

    private boolean a(C0767v2 c0767v2) {
        while (c0767v2 != null) {
            C0722p1 c0722p1 = this.b;
            if (c0767v2 == c0722p1.Z1) {
                return false;
            }
            if (c0767v2 == c0722p1.m3) {
                return true;
            }
            AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
            if (abstractC0727q0C != null) {
                c0767v2 = abstractC0727q0C.f;
            } else {
                throw new C1367Tb("Class or interface " + c0767v2.r0() + " required for desugaring of try-with-resources is not found.");
            }
        }
        return false;
    }
}
