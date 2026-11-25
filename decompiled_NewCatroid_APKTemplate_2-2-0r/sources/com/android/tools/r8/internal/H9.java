package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H9 extends AbstractC2309jV {
    private final com.android.tools.r8.utils.A1.h b;
    private final com.android.tools.r8.synthesis.D c;

    public H9(C0702m c0702m) {
        this.b = c0702m.Y().O0();
        this.c = c0702m.D();
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        if (!this.b.i()) {
            com.android.tools.r8.synthesis.D d = this.c;
            d.getClass();
            if (d.i(c0729q2.d)) {
                return false;
            }
        }
        return !this.b.g() || this.c.c(c0729q2);
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "CheckSyntheticClasses";
    }
}
