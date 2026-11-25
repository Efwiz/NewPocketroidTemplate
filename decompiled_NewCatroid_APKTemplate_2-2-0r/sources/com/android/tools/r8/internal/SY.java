package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.C3879c3;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class SY {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final InterfaceC2802pl b;
    private final EnumC0928Dk c;
    private final List d;
    private final Set e;

    private SY(C0702m c0702m, InterfaceC2802pl interfaceC2802pl, EnumC0928Dk enumC0928Dk, ArrayList arrayList, Set set) {
        this.a = c0702m;
        this.b = interfaceC2802pl;
        this.c = enumC0928Dk;
        this.d = arrayList;
        this.e = set;
    }

    public static RY a(C0702m c0702m, InterfaceC2802pl interfaceC2802pl, EnumC0928Dk enumC0928Dk) {
        return new RY(c0702m, interfaceC2802pl, enumC0928Dk);
    }

    public final void b(ExecutorService executorService) {
        if (this.e.isEmpty()) {
            return;
        }
        final C3012sl c3012sl = new C3012sl(a());
        C3879c3.a(this.e, new Consumer() { // from class: com.android.tools.r8.internal.SY$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3012sl, (C0729q2) obj);
            }
        }, executorService);
    }

    public final void a(ExecutorService executorService) {
        if (this.d.isEmpty()) {
            return;
        }
        final C3012sl c3012sl = new C3012sl(a());
        C3879c3.a(this.d, new Consumer() { // from class: com.android.tools.r8.internal.SY$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3012sl, (com.android.tools.r8.graph.o5) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(final C3012sl c3012sl, C0729q2 c0729q2) {
        boolean z = f;
        if (!z && !this.a.Y().Y0()) {
            throw new AssertionError();
        }
        if (!z && !this.c.a()) {
            throw new AssertionError();
        }
        c0729q2.f(new Consumer() { // from class: com.android.tools.r8.internal.SY$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c3012sl, (com.android.tools.r8.graph.o5) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.SY$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SY.a((com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.W0 w0) {
        return w0.a1().z0();
    }

    private C0702m a() {
        boolean z = f;
        if (!z && !this.a.q()) {
            throw new AssertionError();
        }
        if (!z && !this.a.f().h()) {
            throw new AssertionError();
        }
        this.a.a().c();
        C0702m c0702mC = C0702m.c(C0664h.a(this.a.f().b(), com.android.tools.r8.synthesis.y.d()));
        c0702mC.b(this.a.E());
        c0702mC.a(this.a.n());
        return c0702mC;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C3012sl c3012sl, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC1476Wj abstractC1476WjA = o5Var.e().a1().a(this.a);
        boolean z = f;
        if (!z && abstractC1476WjA == this.a.n()) {
            throw new AssertionError();
        }
        c3012sl.a(this.b.a(o5Var), com.android.tools.r8.ir.optimize.info.q.b(), com.android.tools.r8.utils.y3.b());
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        if (!z && !wA1.B0()) {
            throw new AssertionError();
        }
        o5Var.a(wA1.P().a(abstractC1476WjA), this.a);
    }

    public final boolean b() {
        return this.d.isEmpty() && this.e.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3012sl c3012sl, com.android.tools.r8.graph.o5 o5Var) {
        c3012sl.a(this.b.a(o5Var), com.android.tools.r8.ir.optimize.info.q.b(), com.android.tools.r8.utils.y3.b());
    }
}
