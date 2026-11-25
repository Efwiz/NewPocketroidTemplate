package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class OH extends AbstractC1154Lf {
    private static final OH c = new OH();
    static final /* synthetic */ boolean d = true;

    private OH() {
    }

    public static OH o() {
        return c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final AbstractC1154Lf a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, Set set) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final C1229Oa e() {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final WH f() {
        return WH.b();
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean k() {
        return true;
    }

    public final String toString() {
        return "NotNullDynamicType";
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final AbstractC3472z00 a(AbstractC3472z00 abstractC3472z00) {
        if (d || abstractC3472z00.I()) {
            return abstractC3472z00.d().a(WH.b());
        }
        throw new AssertionError();
    }
}
