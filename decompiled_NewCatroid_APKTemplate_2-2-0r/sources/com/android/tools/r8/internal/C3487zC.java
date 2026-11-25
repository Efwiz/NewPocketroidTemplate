package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3912j0;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3487zC extends AbstractC1447Vg {
    static final /* synthetic */ boolean d = true;
    private final C0729q2 b;
    private final C3150ug c;

    public C3487zC(C0729q2 c0729q2, C3150ug c3150ug, C0729q2 c0729q3) {
        super(c0729q3);
        this.b = c0729q2;
        this.c = c3150ug;
    }

    public static C3420yC a(C0702m c0702m, C0729q2 c0729q2, C3150ug c3150ug) {
        return new C3420yC(c0702m, c0729q2, c3150ug);
    }

    private com.android.tools.r8.graph.o5 b(final C0702m c0702m) {
        final C0722p1 c0722p1A = c0702m.a();
        final C3214vV c3214vVA = c0702m.b().a(c0722p1A.c("null"));
        return a(c0702m, c0722p1A.c("stringValueOf"), c0722p1A.b(c0722p1A.X1, c0722p1A.A1), new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.zC$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(c0702m, c0722p1A, c3214vVA, c0684j2);
            }
        });
    }

    private com.android.tools.r8.graph.o5 c(final C0702m c0702m) {
        final C0722p1 c0722p1A = c0702m.a();
        return a(c0702m, c0722p1A.c("valueOf"), c0722p1A.b(c0722p1A.A1, c0722p1A.X1), new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.zC$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(c0702m, c0722p1A, c0684j2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC1447Vg
    public final void a(final C0702m c0702m) {
        this.c.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.zC$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0702m, (com.android.tools.r8.graph.Y0) obj, (AbstractC3303wg) obj2);
            }
        });
        if (this.c.a.containsKey(c0702m.a().r4.a)) {
            b(c0702m);
            c(c0702m);
        }
    }

    public final C0767v2 c() {
        return this.b.getType();
    }

    public final com.android.tools.r8.graph.o5 b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, VE ve) {
        com.android.tools.r8.graph.o5 o5VarC = c(c0702m);
        ve.g(o5VarC, o5Var);
        return o5VarC;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.graph.Y0 y0, AbstractC3303wg abstractC3303wg) {
        if (abstractC3303wg.d()) {
            a(c0702m, y0);
        }
    }

    public final com.android.tools.r8.graph.o5 a(C0702m c0702m, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.o5 o5Var, VE ve) {
        com.android.tools.r8.graph.o5 o5VarA = a(c0702m, y0);
        ve.g(o5VarA, o5Var);
        return o5VarA;
    }

    private com.android.tools.r8.graph.o5 a(final C0702m c0702m, final com.android.tools.r8.graph.Y0 y0) {
        C0759u2 c0759u2C0;
        C0722p1 c0722p1A = c0702m.a();
        String string = y0.C0().toString();
        if (y0.B0() == b().getType()) {
            c0759u2C0 = c0722p1A.c("get" + string.substring(0, 1).toUpperCase() + string.substring(1));
        } else {
            if (!d && y0 != c0702m.a().r4.a && y0 != c0702m.a().r4.b) {
                throw new AssertionError();
            }
            c0759u2C0 = y0.C0();
        }
        return a(c0702m, c0759u2C0, c0722p1A.b(y0.getType(), c0722p1A.A1), new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.zC$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(c0702m, y0, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W a(C0702m c0702m, com.android.tools.r8.graph.Y0 y0, C0684j2 c0684j2) {
        return new C1181Mg(c0702m, this.b.getType(), this.c, y0, null).b();
    }

    public final com.android.tools.r8.graph.o5 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, VE ve) {
        com.android.tools.r8.graph.o5 o5VarB = b(c0702m);
        ve.g(o5VarB, o5Var);
        return o5VarB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W a(C0702m c0702m, C0722p1 c0722p1, B1 b1, C0684j2 c0684j2) {
        return new C1181Mg(c0702m, this.b.getType(), this.c, c0722p1.r4.a, b1).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W a(C0702m c0702m, C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C1208Ng(c0702m, this.b.getType(), b().getType(), this.c.a(c0722p1.r4.a).c()).b();
    }

    private com.android.tools.r8.graph.o5 a(final C0702m c0702m, C0759u2 c0759u2, C0737r2 c0737r2, final com.android.tools.r8.synthesis.G g) {
        com.android.tools.r8.synthesis.D D = c0702m.D();
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.zC$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.i;
            }
        };
        C0729q2 c0729q2B = b();
        Consumer consumerB = C3912j0.b();
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.zC$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3487zC.a(c0702m, g, (com.android.tools.r8.synthesis.H) obj);
            }
        };
        D.getClass();
        return D.a(c0759u2, c0737r2, c, c0729q2B, c0702m, consumerB, consumer, C3912j0.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.synthesis.G g, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).b(c0702m.o()).a(c0702m.o()).a(g).a(Z8.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC1447Vg
    public final C0729q2 a() {
        return this.b;
    }
}
