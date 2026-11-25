package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.DH;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class S extends AbstractC3850h implements InterfaceC3845d {
    static final /* synthetic */ boolean e = true;

    S(L.b bVar, C3846e c3846e, C0767v2 c0767v2) {
        super(bVar, c3846e, c0767v2);
    }

    @Override // com.android.tools.r8.synthesis.U
    final AbstractC3855m a(Function function) {
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) function.apply(this.d);
        if (abstractC0727q0 == null) {
            return null;
        }
        if (e || abstractC0727q0.e0()) {
            return new Q(c(), b(), abstractC0727q0.a0());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.synthesis.U
    final U a(C3846e c3846e, DH dh) {
        C0767v2 c0767v2 = this.d;
        dh.getClass();
        C0767v2 c0767v2A = dh.a(AbstractC1476Wj.e(), c0767v2);
        C0767v2 c0767v3 = this.d;
        if (c0767v3 == c0767v2A || dh.a(c0767v3, c0767v2A)) {
            return (c3846e == b() && c0767v2A == this.d) ? this : new S(c(), c3846e, c0767v2A);
        }
        return null;
    }
}
