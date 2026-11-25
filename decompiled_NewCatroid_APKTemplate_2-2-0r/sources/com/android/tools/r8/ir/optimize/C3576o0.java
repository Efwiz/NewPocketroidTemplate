package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.X4;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.y3;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.o0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3576o0 {
    private C0702m a;

    public C3576o0(C0702m c0702m) {
        this.a = c0702m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final C0729q2 c0729q2) {
        final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K(c0729q2.g(new o0$$ExternalSyntheticLambda3()).iterator().hasNext());
        c0729q2.i1().removeIf(new Predicate() { // from class: com.android.tools.r8.ir.optimize.o0$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(c0729q2, k, (X4) obj);
            }
        });
        if (k.d() && this.a.Y().E0) {
            c0729q2.i1().clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(C0729q2 c0729q2) {
        C0702m c0702m = this.a;
        C0767v2 c0767v2G1 = c0729q2.g1();
        c0702m.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0767v2G1 == c0729q2.d ? c0729q2 : c0702m.a(c0767v2G1));
        if (c0729q2A == null || !c0729q2A.G1()) {
            c0729q2.P0();
        }
    }

    public final void a(ExecutorService executorService, y3 y3Var) {
        y3Var.a("NestReduction");
        if (this.a.Y().V()) {
            a(executorService);
        } else {
            for (C0729q2 c0729q2 : ((C3733i) this.a.f()).d()) {
                if (c0729q2.D1()) {
                    if (c0729q2.G1()) {
                        c0729q2.Q0();
                    } else {
                        c0729q2.P0();
                    }
                }
            }
        }
        y3Var.c();
    }

    private void a(ExecutorService executorService) {
        Set setC = OU.c();
        Set setC2 = OU.c();
        for (C0729q2 c0729q2 : ((C3733i) this.a.f()).d()) {
            if (c0729q2.D1()) {
                if (c0729q2.G1()) {
                    setC.add(c0729q2);
                } else {
                    setC2.add(c0729q2);
                }
            }
        }
        C3879c3.a(setC, new Consumer() { // from class: com.android.tools.r8.ir.optimize.o0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0729q2) obj);
            }
        }, executorService);
        C3879c3.a(setC2, new Consumer() { // from class: com.android.tools.r8.ir.optimize.o0$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((C0729q2) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0729q2 c0729q2, com.android.tools.r8.utils.K k, X4 x4) {
        C0702m c0702m = this.a;
        C0767v2 c0767v2A = x4.a();
        c0702m.getClass();
        C0767v2 c0767v2 = c0729q2.d;
        AbstractC0727q0 abstractC0727q0A = c0729q2;
        if (c0767v2A != c0767v2) {
            abstractC0727q0A = c0702m.a(c0767v2A);
        }
        final C0729q2 c0729q2A = C0729q2.a(abstractC0727q0A);
        if (c0729q2A == null) {
            return true;
        }
        k.a(new BooleanSupplier() { // from class: com.android.tools.r8.ir.optimize.o0$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return C3576o0.a(c0729q2A);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C0729q2 c0729q2) {
        return c0729q2.g(new o0$$ExternalSyntheticLambda3()).iterator().hasNext();
    }
}
