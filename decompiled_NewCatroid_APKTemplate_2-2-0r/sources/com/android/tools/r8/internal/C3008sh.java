package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3008sh extends AbstractC3078th {
    static final /* synthetic */ boolean b = true;
    private final long a;

    public C3008sh(long j) {
        this.a = j;
    }

    @Override // com.android.tools.r8.internal.AbstractC3078th
    public final C0767v2 a(C0722p1 c0722p1) {
        return c0722p1.A1;
    }

    public final boolean equals(Object obj) {
        return obj != null && C3008sh.class == obj.getClass() && this.a == ((C3008sh) obj).a;
    }

    public final int hashCode() {
        return Long.hashCode(this.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC3078th
    public final AbstractC3472z00 a(C0702m c0702m, C0767v2 c0767v2) {
        if (b || c0767v2 == c0702m.a().A1) {
            return AbstractC3472z00.k();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3078th
    public final C2853qV a(C0702m c0702m) {
        return c0702m.b().a(this.a);
    }
}
