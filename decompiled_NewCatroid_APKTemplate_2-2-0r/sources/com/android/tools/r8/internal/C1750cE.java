package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1750cE extends com.android.tools.r8.graph.J5 {
    final /* synthetic */ Set e;
    final /* synthetic */ C1826dE f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1750cE(C1826dE c1826dE, C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, Set set) {
        super(c0702m, o5Var);
        this.f = c1826dE;
        this.e = set;
    }

    private void i(final com.android.tools.r8.graph.Y0 y0) {
        AbstractC0677i3 abstractC0677i3C = ((C3733i) this.f.b.f()).c(y0);
        final Set set = this.e;
        abstractC0677i3C.b(new Consumer() { // from class: com.android.tools.r8.internal.cE$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1750cE.a(y0, set, (AbstractC0677i3.a) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
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

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.Y0 y0, Set set, AbstractC0677i3.a aVar) {
        if (aVar.s().getReference() != y0) {
            set.add(y0);
        }
    }
}
