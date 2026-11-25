package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2520mB {
    private final C0759u2 a;
    private final C0702m b;
    private boolean c = false;

    public C2520mB(C0702m c0702m, C0759u2 c0759u2, C0729q2 c0729q2) {
        this.a = c0759u2;
        this.b = c0702m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        C0767v2 c0767v2 = c0729q2.f;
        if (c0767v2 != null) {
            a(c0767v2);
        }
        for (C0767v2 c0767v3 : c0729q2.g.a) {
            a(c0767v3);
        }
        if (this.c) {
            return;
        }
        Iterator<com.android.tools.r8.graph.W0> it = c0729q2.M1().iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.o5 o5Var = new com.android.tools.r8.graph.o5(c0729q2, it.next());
            C0684j2 c0684j2 = (C0684j2) o5Var.getReference();
            Iterator<C0767v2> it2 = c0684j2.G0().iterator();
            while (it2.hasNext()) {
                a(it2.next());
            }
            a(c0684j2.I0());
            for (C0633d0 c0633d0 : ((com.android.tools.r8.graph.W0) o5Var.e()).s0().c) {
                if (c0633d0.b.a == this.b.a().h5) {
                    for (com.android.tools.r8.graph.B2 b2 : c0633d0.b.b[0].b.v0().j1()) {
                        a((C0767v2) b2.L0().b);
                    }
                }
            }
            if (!this.c) {
                C2445lB c2445lB = new C2445lB(this, this.b, o5Var);
                o5Var.a(c2445lB);
                this.c = ((Boolean) c2445lB.f()).booleanValue();
            }
            if (this.c) {
                return;
            }
        }
        c0729q2.c(new Consumer() { // from class: com.android.tools.r8.internal.mB$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.T0) obj);
            }
        }, CM.b());
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0016  */
    private void a(C0767v2 c0767v2) {
        boolean z;
        if (this.c) {
            z = true;
        } else {
            C0759u2 c0759u2 = c0767v2.e;
            C0759u2 c0759u3 = this.a;
            c0759u2.getClass();
            if (c0759u2.a(c0759u3.e)) {
                z = true;
            } else {
                z = false;
            }
        }
        this.c = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.T0 t0) {
        com.android.tools.r8.graph.Y0 reference = t0.getReference();
        a(reference.B0());
        a(reference.getType());
    }
}
