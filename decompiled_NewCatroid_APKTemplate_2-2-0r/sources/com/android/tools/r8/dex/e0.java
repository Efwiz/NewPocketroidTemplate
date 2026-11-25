package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0640e0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0663g5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.S0;
import com.android.tools.r8.graph.Y0;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class e0 implements L {
    final /* synthetic */ f0 a;

    private e0(f0 f0Var) {
        this.a = f0Var;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(Y0 y0) {
        return true;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0684j2 c0684j2) {
        return true;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0699l2 c0699l2) {
        return true;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0767v2 c0767v2) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0633d0 c0633d0) {
        this.a.f.add(c0633d0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0658g0 c0658g0) {
        if (this.a.b.Y().l() || !c0658g0.isEmpty()) {
            this.a.h.add(c0658g0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0663g5 c0663g5) {
        if (c0663g5.isEmpty()) {
            return;
        }
        this.a.i.add(c0663g5);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0759u2 c0759u2) {
        return this.a.m.add(c0759u2);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0737r2 c0737r2) {
        C0800x2 c0800x2U0 = c0737r2.u0();
        if (c0800x2U0.isEmpty()) {
            return true;
        }
        this.a.n.add(c0800x2U0);
        return true;
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0721p0 c0721p0) {
        this.a.l.add(c0721p0.t0());
        return true;
    }

    public final boolean a(C0729q2 c0729q2) {
        if (c0729q2.c2() || c0729q2.b2()) {
            this.a.j.add(c0729q2);
        }
        C0800x2 c0800x2F1 = c0729q2.f1();
        if (!c0800x2F1.isEmpty()) {
            this.a.n.add(c0800x2F1);
        }
        C0640e0 c0640e0B = this.a.c.b(c0729q2);
        if (c0640e0B != null) {
            this.a.g.add(c0640e0B);
            c0640e0B.a(new Consumer() { // from class: com.android.tools.r8.dex.e0$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C0633d0) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.dex.e0$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C0658g0) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.dex.e0$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C0663g5) obj);
                }
            });
        }
        S0 s0C = this.a.c.c(c0729q2);
        if (s0C == null) {
            return true;
        }
        this.a.l.add(s0C);
        return true;
    }
}
