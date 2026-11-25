package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.DH;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class J extends U implements InterfaceC3845d {
    static final /* synthetic */ boolean e = true;
    final C0684j2 d;

    J(L.b bVar, C3846e c3846e, C0684j2 c0684j2) {
        super(bVar, c3846e);
        this.d = c0684j2;
    }

    @Override // com.android.tools.r8.synthesis.U, com.android.tools.r8.synthesis.InterfaceC3845d
    public final C0767v2 a() {
        return this.d.e;
    }

    @Override // com.android.tools.r8.synthesis.U
    final AbstractC0744s2 d() {
        return this.d;
    }

    @Override // com.android.tools.r8.synthesis.U
    final AbstractC3855m a(Function function) {
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) function.apply(this.d.e);
        if (abstractC0727q0 != null) {
            if (!e && !abstractC0727q0.e0()) {
                throw new AssertionError();
            }
            o5 o5VarF = abstractC0727q0.a0().f(this.d);
            if (o5VarF != null) {
                return new I(c(), b(), o5VarF);
            }
        }
        return null;
    }

    @Override // com.android.tools.r8.synthesis.U
    final U a(C3846e c3846e, DH dh) {
        C0684j2 c0684j2C = dh.c((AbstractC1476Wj) null, this.d);
        if (this.d.B0() == c0684j2C.B0() || dh.a(this.d, c0684j2C)) {
            return (c3846e == b() && c0684j2C == this.d) ? this : new J(c(), c3846e, c0684j2C);
        }
        if (e) {
            return null;
        }
        C0767v2 c0767v2 = c0684j2C.e;
        int i = L.W;
        L.a(c0767v2.b1());
        return null;
    }
}
