package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.DH;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.k, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3853k extends AbstractC3850h {
    static final /* synthetic */ boolean e = true;

    C3853k(L.b bVar, C3846e c3846e, C0767v2 c0767v2) {
        super(bVar, c3846e, c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.synthesis.U
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C3853k a(C3846e c3846e, DH dh) {
        boolean z = e;
        if (!z) {
            C0767v2 c0767v2 = this.d;
            if (c0767v2 != dh.d(c0767v2)) {
                throw new AssertionError("Unexpected classpath rewrite of type " + this.d.r0());
            }
        }
        if (z || b() == c3846e) {
            return this;
        }
        throw new AssertionError("Unexpected classpath rewrite of context type " + b());
    }

    @Override // com.android.tools.r8.synthesis.U
    final AbstractC3855m a(Function function) {
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) function.apply(this.d);
        if (abstractC0727q0 == null) {
            return null;
        }
        if (e || (abstractC0727q0 instanceof C0754u0)) {
            return new C3852j(c(), b(), abstractC0727q0.m());
        }
        throw new AssertionError();
    }
}
