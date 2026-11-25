package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class YG extends AbstractC2309jV {
    private final C0702m b;
    private final C2537mR c = new C2537mR();

    public YG(C0702m c0702m) {
        this.b = c0702m;
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        return (c0729q2.B1() || a((AbstractC0727q0) c0729q2)) ? false : true;
    }

    @Override // com.android.tools.r8.internal.WL
    public final void d() {
        this.c.clear();
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoEnums";
    }

    /* JADX WARN: Code duplicated, block: B:20:0x003f  */
    private boolean a(AbstractC0727q0 abstractC0727q0) {
        boolean z;
        if (this.c.containsKey(abstractC0727q0)) {
            return this.c.b(abstractC0727q0);
        }
        if (abstractC0727q0.d != this.b.a().Z1) {
            if (abstractC0727q0.d != this.b.a().e2) {
                C0767v2 c0767v2 = abstractC0727q0.f;
                AbstractC0727q0 abstractC0727q0C = c0767v2 != null ? this.b.c(c0767v2) : null;
                z = abstractC0727q0C != null && a(abstractC0727q0C);
            }
        }
        this.c.a(abstractC0727q0, z);
        return z;
    }
}
