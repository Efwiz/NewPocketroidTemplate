package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.utils.C3894f2;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.j0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3566j0 extends AbstractC3564i0 {
    private final Predicate f;

    public C3566j0(C0702m c0702m, B5 b5) {
        this(c0702m, b5, CM.b());
    }

    public static boolean a(W0 w0) {
        return !w0.L1() || w0.E0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C3562h0 b(AbstractC0727q0 abstractC0727q0) {
        return new C3562h0(this.a, abstractC0727q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(AbstractC0727q0 abstractC0727q0) {
        final AbstractC0727q0 abstractC0727q0C;
        final C3562h0 c3562h0 = (C3562h0) this.d.computeIfAbsent(abstractC0727q0, new Function() { // from class: com.android.tools.r8.ir.optimize.j0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((AbstractC0727q0) obj);
            }
        });
        abstractC0727q0.f(new Consumer() { // from class: com.android.tools.r8.ir.optimize.j0$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3562h0, (W0) obj);
            }
        });
        C0767v2 c0767v2 = abstractC0727q0.f;
        if (c0767v2 != null && (abstractC0727q0C = this.b.c(c0767v2)) != null) {
            C3562h0 c3562h1 = (C3562h0) this.d.computeIfAbsent(abstractC0727q0C, new Function() { // from class: com.android.tools.r8.ir.optimize.j0$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(abstractC0727q0C, (AbstractC0727q0) obj);
                }
            });
            c3562h1.b(c3562h0);
            c3562h0.c(c3562h1);
        }
        if (abstractC0727q0.isInterface()) {
            Iterator it = this.c.c(abstractC0727q0.d).iterator();
            while (it.hasNext()) {
                final AbstractC0727q0 abstractC0727q0C2 = this.b.c((C0767v2) it.next());
                if (abstractC0727q0C2 != null) {
                    C3562h0 c3562h2 = (C3562h0) this.d.computeIfAbsent(abstractC0727q0C2, new Function() { // from class: com.android.tools.r8.ir.optimize.j0$$ExternalSyntheticLambda4
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return this.f$0.b(abstractC0727q0C2, (AbstractC0727q0) obj);
                        }
                    });
                    c3562h0.b(c3562h2);
                    c3562h2.a(c3562h0);
                }
            }
        }
    }

    public C3566j0(C0702m c0702m, B5 b5, Predicate predicate) {
        super(c0702m, C3894f2.a(), b5);
        this.f = predicate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C3562h0 b(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        return new C3562h0(this.a, abstractC0727q0);
    }

    @Override // com.android.tools.r8.ir.optimize.AbstractC3564i0
    final Runnable a(final AbstractC0727q0 abstractC0727q0) {
        return new Runnable() { // from class: com.android.tools.r8.ir.optimize.j0$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.c(abstractC0727q0);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3562h0 c3562h0, W0 w0) {
        if (this.f.test(w0)) {
            c3562h0.b(this.a.c(w0.getReference()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C3562h0 a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        return new C3562h0(this.a, abstractC0727q0);
    }
}
