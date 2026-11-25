package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3885d3;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class B2 extends com.android.tools.r8.graph.H5 {
    static final /* synthetic */ boolean h = true;
    private final C0702m f;
    private final H2 g;

    public B2(C0702m c0702m, H2 h2) {
        super(c0702m);
        this.f = c0702m;
        this.g = h2;
    }

    private void c(C0729q2 c0729q2) {
        c0729q2.a(a(new Consumer() { // from class: com.android.tools.r8.internal.B2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                B2.a((com.android.tools.r8.graph.T0.a) obj);
            }
        }, c0729q2.w1()));
        c0729q2.b(a(new Consumer() { // from class: com.android.tools.r8.internal.B2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                B2.b((com.android.tools.r8.graph.T0.a) obj);
            }
        }, c0729q2.N1()));
    }

    private void d(C0729q2 c0729q2) {
        c0729q2.Y().d(new Function() { // from class: com.android.tools.r8.internal.B2$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
    }

    @Override // com.android.tools.r8.graph.H5
    public final C0767v2 b(C0767v2 c0767v2) {
        return c0767v2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0729q2 c0729q2) {
        c(c0729q2);
        d(c0729q2);
    }

    public final void a(com.android.tools.r8.utils.y3 y3Var, Set set, final ExecutorService executorService) {
        if (this.g == null) {
            if (!h && !set.isEmpty()) {
                throw new AssertionError();
            }
        } else {
            if (!h && set.isEmpty()) {
                throw new AssertionError();
            }
            y3Var.a("Fixup application");
            C3879c3.a(set, new Consumer() { // from class: com.android.tools.r8.internal.B2$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((C0729q2) obj);
                }
            }, executorService);
            y3Var.c();
            y3Var.a("Fixup optimization info", new InterfaceC3885d3() { // from class: com.android.tools.r8.internal.B2$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.utils.InterfaceC3885d3
                public final void a() {
                    this.f$0.a(executorService);
                }
            });
            y3Var.a("Rewrite AppView");
            this.f.b((DH) this.g);
            y3Var.c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(com.android.tools.r8.graph.T0.a aVar) {
        aVar.a(C0706m3.e.p());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W0 b(final com.android.tools.r8.graph.W0 w0) {
        C0684j2 reference = w0.getReference();
        final C0684j2 c0684j2H = this.g.h(reference);
        return (c0684j2H != reference || this.g.j(c0684j2H)) ? w0.a(c0684j2H, new Consumer() { // from class: com.android.tools.r8.internal.B2$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2H, w0, (com.android.tools.r8.graph.W0.a) obj);
            }
        }) : w0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.T0.a aVar) {
        aVar.a(C0706m3.e.p());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0.a aVar) {
        if (this.g.j(c0684j2)) {
            com.android.tools.r8.graph.proto.k kVarI = this.g.i(c0684j2);
            aVar.a(kVarI.a(w0)).a(C0706m3.g.d());
            if (w0.B1() && kVarI.a().a(0).c()) {
                aVar.b(new Consumer() { // from class: com.android.tools.r8.internal.B2$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        B2.a((C0725p4) obj);
                    }
                }).f();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0725p4 c0725p4) {
        ((C0725p4) c0725p4.a()).v();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ExecutorService executorService) {
        AbstractC1476Wj abstractC1476WjK = this.g.k();
        com.android.tools.r8.graph.u5 u5VarA = com.android.tools.r8.graph.u5.a(this.f.e());
        com.android.tools.r8.ir.optimize.info.r rVarA = com.android.tools.r8.ir.optimize.info.n.a();
        C0702m c0702m = this.f;
        A2 a2 = new A2(this, abstractC1476WjK, u5VarA);
        rVarA.getClass();
        com.android.tools.r8.ir.optimize.info.n.a(c0702m.f().d(), executorService, a2);
    }

    @Override // com.android.tools.r8.graph.H5
    public final com.android.tools.r8.graph.Y0 a(com.android.tools.r8.graph.Y0 y0) {
        return this.g.d(y0);
    }

    @Override // com.android.tools.r8.graph.H5
    public final C0684j2 a(C0684j2 c0684j2) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.H5
    public final C0767v2 a(C0767v2 c0767v2) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0767v2 c0767v2, C0767v2 c0767v3) {
        throw new C3405y10();
    }
}
