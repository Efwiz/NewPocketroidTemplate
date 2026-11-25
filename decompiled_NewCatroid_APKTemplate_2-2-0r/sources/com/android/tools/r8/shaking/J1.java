package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class J1 extends J5 {
    final /* synthetic */ K1 e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private J1(K1 k1, o5 o5Var) {
        super(k1.a(), o5Var);
        this.e = k1;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        this.e.c.accept(c0684j2.e);
        Consumer consumer = this.e.c;
        C0737r2 c0737r2 = c0684j2.h;
        consumer.accept(c0737r2.e);
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            consumer.accept(c0767v2);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        this.e.c.accept(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        this.e.c.accept(y0.e);
        this.e.c.accept(y0.h);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
        this.e.c.accept(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        this.e.c.accept(c0684j2.e);
        Consumer consumer = this.e.c;
        C0737r2 c0737r2 = c0684j2.h;
        consumer.accept(c0737r2.e);
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            consumer.accept(c0767v2);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
        this.e.c.accept(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        this.e.c.accept(c0684j2.e);
        Consumer consumer = this.e.c;
        C0737r2 c0737r2 = c0684j2.h;
        consumer.accept(c0737r2.e);
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            consumer.accept(c0767v2);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        this.e.c.accept(c0684j2.e);
        Consumer consumer = this.e.c;
        C0737r2 c0737r2 = c0684j2.h;
        consumer.accept(c0737r2.e);
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            consumer.accept(c0767v2);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        this.e.c.accept(c0684j2.e);
        Consumer consumer = this.e.c;
        C0737r2 c0737r2 = c0684j2.h;
        consumer.accept(c0737r2.e);
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            consumer.accept(c0767v2);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
        this.e.c.accept(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        this.e.c.accept(y0.e);
        this.e.c.accept(y0.h);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        this.e.c.accept(y0.e);
        this.e.c.accept(y0.h);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        this.e.c.accept(y0.e);
        this.e.c.accept(y0.h);
    }
}
