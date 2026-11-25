package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Optional;
import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2083gV extends com.android.tools.r8.graph.J5 {
    static final /* synthetic */ boolean g = true;
    private final C0702m e;
    final /* synthetic */ C2159hV f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2083gV(C2159hV c2159hV, C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        super(c0702m, o5Var);
        this.f = c2159hV;
        this.e = c0702m;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
        C0767v2 c0767v2A = AbstractC2964s2.a(this.e.E(), c0767v2);
        C0702m c0702m = this.e;
        c0767v2A.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2A));
        if (c0729q2A == null || ((C0680j) this.e.f()).c(((com.android.tools.r8.graph.o5) d()).a(), c0729q2A)) {
            return;
        }
        a(c0729q2A);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.e.E();
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) d();
        abstractC1476WjE.getClass();
        com.android.tools.r8.graph.o5 o5VarS = ((C0680j) this.e.f()).h((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.c, (AbstractC1476Wj) null).b()).s();
        if (o5VarS != null) {
            a(o5VarS);
            C0729q2 c0729q2A = o5VarS.a();
            if (((C0680j) this.e.f()).c(((com.android.tools.r8.graph.o5) d()).a(), c0729q2A)) {
                return;
            }
            a(c0729q2A);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
    }

    private void a(com.android.tools.r8.graph.o5 o5Var) {
        this.f.b.a((com.android.tools.r8.graph.n5) o5Var, new BiFunction() { // from class: com.android.tools.r8.internal.gV$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a((com.android.tools.r8.graph.o5) obj, (Optional) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        C0767v2 c0767v2A = AbstractC2964s2.a(this.e.E(), c0767v2);
        C0702m c0702m = this.e;
        c0767v2A.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2A));
        if (c0729q2A == null || ((C0680j) this.e.f()).c(((com.android.tools.r8.graph.o5) d()).a(), c0729q2A)) {
            return;
        }
        a(c0729q2A);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        C0767v2 c0767v2B0 = this.e.E().c((AbstractC1476Wj) null, y0).B0();
        C0702m c0702m = this.e;
        c0767v2B0.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2B0));
        if (c0729q2A == null || ((C0680j) this.e.f()).c(((com.android.tools.r8.graph.o5) d()).a(), c0729q2A)) {
            return;
        }
        a(c0729q2A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Optional a(com.android.tools.r8.graph.o5 o5Var, Optional optional) {
        if (optional == null) {
            return Optional.of((com.android.tools.r8.graph.o5) d());
        }
        return optional.orElse(null) == d() ? optional : Optional.empty();
    }

    private void a(C0729q2 c0729q2) {
        Optional optional = (Optional) this.f.c.get(c0729q2);
        if (optional == null || (optional.isPresent() && optional.get() != d())) {
            this.f.c.compute(c0729q2, new BiFunction() { // from class: com.android.tools.r8.internal.gV$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return this.f$0.a((C0729q2) obj, (Optional) obj2);
                }
            });
            C0767v2 c0767v2N1 = c0729q2.n1();
            C0702m c0702m = this.e;
            c0767v2N1.getClass();
            C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2N1));
            if (c0729q2A != null) {
                a(c0729q2A);
            }
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        C0767v2 c0767v2B0 = this.e.E().c((AbstractC1476Wj) null, y0).B0();
        C0702m c0702m = this.e;
        c0767v2B0.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2B0));
        if (c0729q2A == null || ((C0680j) this.e.f()).c(((com.android.tools.r8.graph.o5) d()).a(), c0729q2A)) {
            return;
        }
        a(c0729q2A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Optional a(C0729q2 c0729q2, Optional optional) {
        if (optional == null) {
            return Optional.of((com.android.tools.r8.graph.o5) d());
        }
        if (g || optional.orElse(null) != d()) {
            return Optional.empty();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.e.E();
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) d();
        abstractC1476WjE.getClass();
        C0684j2 c0684j3 = (C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.c, (AbstractC1476Wj) null).b();
        C0767v2 c0767v2B0 = c0684j3.B0();
        C0702m c0702m = this.e;
        c0767v2B0.getClass();
        com.android.tools.r8.graph.o5 o5VarA = c0684j3.a(C0729q2.a(c0702m.c(c0767v2B0)));
        if (o5VarA != null) {
            a(o5VarA);
        }
    }
}
