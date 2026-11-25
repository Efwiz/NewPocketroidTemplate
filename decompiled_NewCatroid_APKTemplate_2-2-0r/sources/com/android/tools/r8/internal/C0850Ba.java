package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ba, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C0850Ba {
    final AbstractC0727q0 a;
    final C0729q2 b;
    final BiConsumer c;

    public C0850Ba(AbstractC0727q0 abstractC0727q0, C0729q2 c0729q2, BiConsumer biConsumer) {
        this.a = abstractC0727q0;
        this.b = c0729q2;
        this.c = biConsumer;
    }

    C0850Ba a(AbstractC0727q0 abstractC0727q0) {
        return new C0850Ba(abstractC0727q0, abstractC0727q0.e0() ? abstractC0727q0.a0() : this.b, this.c);
    }

    public AbstractC0727q0 a(C0702m c0702m, C0767v2 c0767v2) {
        C0664h c0664hF = c0702m.f();
        AbstractC0727q0 abstractC0727q0 = this.a;
        c0664hF.getClass();
        if (abstractC0727q0.d == c0767v2) {
            return abstractC0727q0;
        }
        AbstractC0727q0 abstractC0727q0C = c0664hF.c(c0767v2);
        if (abstractC0727q0C != null && !abstractC0727q0C.f0() && !abstractC0727q0.f0()) {
            c0664hF.a(abstractC0727q0, abstractC0727q0C);
        }
        return abstractC0727q0C;
    }

    public void a(C0767v2 c0767v2) {
        this.c.accept(this.b, c0767v2);
    }
}
