package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0632d;
import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0592a;
import com.android.tools.r8.graph.C0607b;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0739r4;
import com.android.tools.r8.graph.C0767v2;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2216iE extends com.android.tools.r8.graph.J5 {
    static final /* synthetic */ boolean j = true;
    private final C0680j e;
    private final com.android.tools.r8.graph.T2 f;
    private final C0739r4 g;
    private final Set h;
    private final Set i;

    public C2216iE(C0702m c0702m, com.android.tools.r8.graph.T2 t2, C0739r4 c0739r4, Set set, Set set2, com.android.tools.r8.graph.o5 o5Var) {
        super(c0702m, o5Var);
        this.e = (C0680j) c0702m.f();
        this.f = t2;
        this.g = c0739r4;
        this.h = set;
        this.i = set2;
    }

    private void i(final com.android.tools.r8.graph.Y0 y0) {
        if (this.h.add(y0)) {
            this.e.c(y0).b(new Consumer() { // from class: com.android.tools.r8.internal.iE$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(y0, (AbstractC0677i3.a) obj);
                }
            });
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
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
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        a(c0684j2, this.g.f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.Y0 y0, AbstractC0677i3.a aVar) {
        com.android.tools.r8.graph.Y0 reference = aVar.s().getReference();
        if (y0 == reference) {
            return;
        }
        com.android.tools.r8.graph.U2 u2A = this.f.a(reference, new Function() { // from class: com.android.tools.r8.internal.iE$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new com.android.tools.r8.graph.U2((com.android.tools.r8.graph.Y0) obj);
            }
        });
        synchronized (u2A) {
            AbstractC0632d abstractC0632dG = u2A.g();
            abstractC0632dG.getClass();
            if (abstractC0632dG instanceof C0607b) {
                u2A.a(new C0592a());
            } else if (!j) {
                AbstractC0632d abstractC0632dG2 = u2A.g();
                abstractC0632dG2.getClass();
                if (!(abstractC0632dG2 instanceof C0592a)) {
                    throw new AssertionError();
                }
            }
            u2A.g().a().d().put(y0, CN.e());
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        a(c0684j2, this.g.c());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        a(c0684j2, this.g.d());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        a(c0684j2, this.g.e());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        a(c0684j2, this.g.b());
    }

    private void a(C0684j2 c0684j2, Map map) {
        com.android.tools.r8.graph.I4.c<?> cVarO;
        if (this.i.add(c0684j2) && !c0684j2.B0().M0()) {
            C0680j c0680j = this.e;
            C0767v2 c0767v2B0 = c0684j2.B0();
            com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) d();
            c0680j.getClass();
            AbstractC0727q0 abstractC0727q0B = c0680j.b(o5Var.a(), c0767v2B0);
            if (abstractC0727q0B == null || (cVarO = this.e.g(abstractC0727q0B, c0684j2).o()) == null || c0684j2 == cVarO.r().getReference()) {
                return;
            }
            map.put(c0684j2, CN.e());
        }
    }
}
