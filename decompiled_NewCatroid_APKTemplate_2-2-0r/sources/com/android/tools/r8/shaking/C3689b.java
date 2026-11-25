package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0816z2;
import com.android.tools.r8.graph.EnumC0608b0;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC3424yG;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CM;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.b, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3689b {
    static final /* synthetic */ boolean c = true;
    private final AbstractC1476Wj a;
    private final AbstractC1476Wj b;

    public C3689b(AbstractC3424yG abstractC3424yG, AbstractC1476Wj abstractC1476Wj) {
        this.a = abstractC3424yG;
        this.b = abstractC1476Wj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0767v2 a(C0767v2 c0767v2) {
        return this.a.a(this.b, c0767v2);
    }

    public final void a(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            c0729q2.a(c0729q2.s0().a(new b$$ExternalSyntheticLambda0(this)));
            c0729q2.f(new Consumer() { // from class: com.android.tools.r8.shaking.b$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((com.android.tools.r8.graph.W0) obj);
                }
            });
            c0729q2.c(new Consumer() { // from class: com.android.tools.r8.shaking.b$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((com.android.tools.r8.graph.T0) obj);
                }
            }, CM.b());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.W0 w0) {
        w0.a(new BiFunction() { // from class: com.android.tools.r8.shaking.b$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a((C0633d0) obj, (EnumC0608b0) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0633d0 a(C0633d0 c0633d0, EnumC0608b0 enumC0608b0) {
        return a(c0633d0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.T0 t0) {
        t0.a(t0.s0().a(new b$$ExternalSyntheticLambda0(this)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0633d0 a(C0633d0 c0633d0) {
        return c0633d0.a(new Function() { // from class: com.android.tools.r8.shaking.b$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.R0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.R0 a(com.android.tools.r8.graph.R0 r0) {
        return r0.a(new Function() { // from class: com.android.tools.r8.shaking.b$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.b$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0649f0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0649f0 a(C0649f0 c0649f0) {
        com.android.tools.r8.graph.B2 b2A = a(c0649f0.b);
        return b2A != c0649f0.b ? new C0649f0(c0649f0.a, b2A) : c0649f0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.B2 a(com.android.tools.r8.graph.B2 b2) {
        b2.getClass();
        if (b2 instanceof com.android.tools.r8.graph.B2.a) {
            com.android.tools.r8.graph.B2[] b2ArrJ1 = b2.v0().j1();
            com.android.tools.r8.graph.B2[] b2Arr = (com.android.tools.r8.graph.B2[]) com.android.tools.r8.utils.B.a(b2ArrJ1, new Function() { // from class: com.android.tools.r8.shaking.b$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a((com.android.tools.r8.graph.B2) obj);
                }
            }, com.android.tools.r8.graph.B2.a);
            if (b2Arr != b2ArrJ1) {
                return new com.android.tools.r8.graph.B2.a(b2Arr);
            }
        } else if (b2 instanceof com.android.tools.r8.graph.A2) {
            com.android.tools.r8.graph.A2 a2U0 = b2.u0();
            com.android.tools.r8.graph.R0 r0A = a(a2U0.b);
            return a2U0.b == r0A ? b2 : new com.android.tools.r8.graph.A2(r0A);
        }
        if (b2 instanceof C0816z2) {
            C0816z2 c0816z2T0 = b2.t0();
            AbstractC0744s2 abstractC0744s2 = (AbstractC0744s2) c0816z2T0.b;
            AbstractC0744s2 abstractC0744s2A = this.a.a(abstractC0744s2);
            return abstractC0744s2 != abstractC0744s2A ? new C0816z2(abstractC0744s2A, c0816z2T0.j1()) : b2;
        }
        if (b2 instanceof com.android.tools.r8.graph.C2) {
            com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) b2.A0().b;
            com.android.tools.r8.graph.Y0 y0C = this.a.c(this.b, y0);
            return y0 != y0C ? new com.android.tools.r8.graph.C2(y0C) : b2;
        }
        if (!(b2 instanceof com.android.tools.r8.graph.D2)) {
            if (!(b2 instanceof com.android.tools.r8.graph.F2)) {
                if (!(b2 instanceof com.android.tools.r8.graph.G2)) {
                    if (!(b2 instanceof com.android.tools.r8.graph.H2)) {
                        if (b2.e1()) {
                            return b2;
                        }
                        if (b2.f1()) {
                            C0767v2 c0767v2 = (C0767v2) b2.L0().b;
                            C0767v2 c0767v2A = a(c0767v2);
                            return c0767v2A != c0767v2 ? new com.android.tools.r8.graph.B2.k(c0767v2A) : b2;
                        }
                        if (c || !b2.g1()) {
                            return b2;
                        }
                        throw new AssertionError();
                    }
                    throw new C3405y10("Unexpected method type in annotation");
                }
                throw new C3405y10("Unexpected method handle in annotation");
            }
            throw new C3405y10("Unexpected method in annotation");
        }
        throw new C3405y10("Unexpected field in annotation");
    }
}
