package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0741s0;
import com.android.tools.r8.graph.C0662g4;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.InterfaceC0669h4;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.InterfaceC0707m4;
import com.android.tools.r8.shaking.C3822x;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2893r2 extends AbstractC2797pg {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final com.android.tools.r8.androidapi.b b;
    private final com.android.tools.r8.androidapi.h c;

    public C2893r2(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.d();
        this.c = c0702m.o();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0662g4 c0662g4) {
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.graph.m5 m5Var, InterfaceC0694k5 interfaceC0694k5) {
        a((AbstractC0741s0) m5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC0694k5 interfaceC0694k5, com.android.tools.r8.shaking.L l) {
        a((AbstractC0741s0) o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.graph.o5 o5Var, C3822x c3822x) {
        if (!d && !c3822x.f().c(this.c)) {
            throw new AssertionError();
        }
        if (this.a.Y().a().j != null) {
            this.a.Y().a().j.accept(o5Var.y(), c3822x.f());
        }
        a((AbstractC0741s0) o5Var);
        o5Var.e().g(c3822x.f());
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        a((AbstractC0741s0) o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.graph.m5 m5Var) {
        a((AbstractC0741s0) m5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.graph.T t) {
        t.c(new Consumer() { // from class: com.android.tools.r8.internal.r2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0741s0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(InterfaceC0707m4 interfaceC0707m4) {
        interfaceC0707m4.a(new Consumer() { // from class: com.android.tools.r8.internal.r2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((InterfaceC0669h4) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.r2$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2893r2.a((C0662g4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(InterfaceC0669h4 interfaceC0669h4) {
        a((AbstractC0741s0) interfaceC0669h4.p());
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.graph.W0 w0) {
        w0.g(com.android.tools.r8.androidapi.h.f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0741s0 abstractC0741s0) {
        com.android.tools.r8.graph.U0 u0E = abstractC0741s0.e();
        com.android.tools.r8.androidapi.b bVar = this.b;
        AbstractC0642e2 reference = abstractC0741s0.getReference();
        C0722p1 c0722p1A = this.a.a();
        com.android.tools.r8.androidapi.j jVarF = com.android.tools.r8.androidapi.h.f();
        bVar.getClass();
        u0E.f(bVar.a(reference.a(c0722p1A), jVarF));
    }
}
