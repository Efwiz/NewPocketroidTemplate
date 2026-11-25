package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC2331jn;
import com.android.tools.r8.internal.C2257in;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3890e3;
import java.util.concurrent.ExecutorService;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.p, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3776p {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final com.android.tools.r8.graph.Y0 b;
    private final C2257in c = AbstractC2331jn.a();

    public C3776p(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a().g4.b;
    }

    public final void a(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).J, new InterfaceC3890e3() { // from class: com.android.tools.r8.shaking.p$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3890e3
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0767v2) obj, (com.android.tools.r8.utils.N3) obj2);
            }
        }, executorService);
        this.a.a(this.c.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:33:0x006d  */
    public void a(C0767v2 c0767v2, com.android.tools.r8.utils.N3 n3) {
        boolean zL;
        C0729q2 c0729q2A = C0729q2.a(this.a.c(c0767v2));
        if (c0729q2A == null) {
            if (!d) {
                throw new AssertionError();
            }
            return;
        }
        com.android.tools.r8.graph.T0 t0A = null;
        for (com.android.tools.r8.graph.T0 t0 : c0729q2A.N1()) {
            if (!t0.X0().i()) {
                if (t0.getAccessFlags().m()) {
                    zL = true;
                } else {
                    int i = AbstractC3771o.a[n3.ordinal()];
                    if (i != 1) {
                        if (i != 2) {
                            if (i != 3) {
                                throw new C3405y10();
                            }
                        } else if (t0.f.i() || t0.f.l()) {
                            zL = true;
                        }
                        zL = false;
                    } else {
                        zL = t0.f.l();
                    }
                }
                if (zL && !t0.getReference().h.Z0()) {
                    if (t0A == null || t0.getAccessFlags().g() > t0A.getAccessFlags().g()) {
                        t0A = t0;
                    }
                    if (t0A.getAccessFlags().m()) {
                        break;
                    }
                }
            }
        }
        if (t0A == null) {
            com.android.tools.r8.graph.Q2 q2K = com.android.tools.r8.graph.Q2.k(4121);
            com.android.tools.r8.graph.T0.a aVarD1 = com.android.tools.r8.graph.T0.d1();
            C0722p1 c0722p1A = this.a.a();
            C0767v2 c0767v3 = c0729q2A.d;
            com.android.tools.r8.graph.Y0 y0 = this.b;
            t0A = aVarD1.a(c0722p1A.a(c0767v3, y0.h, y0.f)).a(q2K).a(this.a.o()).a(!this.a.Y().a().d()).a();
            c0729q2A.b(t0A);
        }
        this.c.a(c0767v2, t0A.getReference());
    }
}
