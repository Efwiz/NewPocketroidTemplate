package com.android.tools.r8.ir.optimize.info;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.T0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1008Gg;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1428Un;
import com.android.tools.r8.internal.AbstractC1617aV;
import com.android.tools.r8.internal.B1;
import com.android.tools.r8.internal.InterfaceC2090ga;
import com.android.tools.r8.internal.M5;
import com.android.tools.r8.ir.optimize.V;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.BitSet;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class r extends n {
    private static r a = new r();

    r() {
    }

    public static r b() {
        return a;
    }

    public static void k(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).a0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC1448Vh
    public final void a(T0 t0, int i) {
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(o5 o5Var, InterfaceC2090ga interfaceC2090ga) {
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(Set set, W0 w0) {
    }

    public final void c(T0 t0) {
        t0.W0().m();
    }

    @Override // com.android.tools.r8.internal.NE
    public final void d(W0 w0) {
    }

    @Override // com.android.tools.r8.internal.NE
    public final void e(W0 w0) {
        w0.g1().H();
    }

    @Override // com.android.tools.r8.internal.NE
    public final void f(W0 w0) {
        w0.g1().G();
    }

    public final void g(W0 w0) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).S();
            }
        };
        if (w0.i1().d()) {
            consumer.accept(w0.i1().a());
        }
    }

    public final void h(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).V();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void i(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).Y();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void j(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).Z();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void l(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).b0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void m(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).c0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void n(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).d0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void o(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).e0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void p(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).f0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void q(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).g0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void r(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).h0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void s(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).i0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void t(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).j0();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public static void d(o5 o5Var) {
        o5Var.e().g1().Q();
    }

    public final void b(T0 t0) {
        t0.W0().l();
    }

    @Override // com.android.tools.r8.internal.NE
    public final void c(W0 w0) {
        w0.g1().J();
    }

    public final void e(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).R();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    public final void f(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).T();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    @Override // com.android.tools.r8.internal.NE
    public final void b(W0 w0) {
        w0.g1().K();
    }

    @Override // com.android.tools.r8.internal.NE
    public final void c(o5 o5Var) {
        o5Var.e().g1().L();
    }

    public static void a(T0 t0) {
        t0.W0().k();
    }

    @Override // com.android.tools.r8.internal.NE
    public final void b(W0 w0, BitSet bitSet) {
        w0.g1().b(bitSet);
    }

    public final void g(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).U();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC1448Vh
    public final void a(T0 t0, AbstractC1154Lf abstractC1154Lf) {
        t0.W0().a(abstractC1154Lf);
    }

    @Override // com.android.tools.r8.internal.NE
    public void b(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).X();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC1448Vh
    public final void a(T0 t0, C0702m c0702m, B1 b1) {
        if (((C3733i) c0702m.f()).b(c0702m, t0.getReference())) {
            t0.W0().a(b1);
        }
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(W0 w0, int i) {
        w0.g1().c(i);
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(W0 w0, C0702m c0702m, B1 b1) {
        w0.g1().a(b1);
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(C0702m c0702m, W0 w0, AbstractC1154Lf abstractC1154Lf) {
        w0.g1().a(c0702m, w0, abstractC1154Lf);
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(W0 w0, V v) {
        w0.a(v);
    }

    public static void a(o5 o5Var, a aVar) {
        o5Var.e().g1().a(aVar);
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(W0 w0, M5 m5) {
        w0.g1().a(m5);
    }

    public static void a(AbstractC0748t0 abstractC0748t0) {
        abstractC0748t0.e().g1().O();
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(o5 o5Var, AbstractC1008Gg abstractC1008Gg) {
        o5Var.e().g1().a(abstractC1008Gg);
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(W0 w0, AbstractC1428Un abstractC1428Un) {
        w0.g1().a(abstractC1428Un);
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(W0 w0) {
        w0.g1().P();
    }

    public static void a(AbstractC3938n2 abstractC3938n2, o5 o5Var) {
        o5Var.e().g1().a(abstractC3938n2);
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(W0 w0, BitSet bitSet) {
        w0.g1().c(bitSet);
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(o5 o5Var, AbstractC1617aV abstractC1617aV) {
        o5Var.e().g1().a(abstractC1617aV);
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(o5 o5Var, BitSet bitSet) {
        o5Var.e().g1().d(bitSet);
    }

    public static void a(o5 o5Var, Consumer consumer) {
        if (o5Var.B().v()) {
            l lVarG1 = o5Var.e().g1();
            BitSet bitSet = (BitSet) lVarG1.t().clone();
            consumer.accept(bitSet);
            lVarG1.a(bitSet);
        }
    }

    @Override // com.android.tools.r8.internal.NE
    public final void a(o5 o5Var) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.ir.optimize.info.r$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((l) obj).W();
            }
        };
        if (o5Var.B().d()) {
            consumer.accept(o5Var.B().a());
        }
    }
}
