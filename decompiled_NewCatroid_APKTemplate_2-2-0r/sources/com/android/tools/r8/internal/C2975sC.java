package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2975sC extends AbstractC3433yP {
    private final com.android.tools.r8.graph.Y0 a;

    public C2975sC(com.android.tools.r8.graph.Y0 y0) {
        this.a = y0;
    }

    @Override // com.android.tools.r8.internal.LP
    public final C2975sC a() {
        return this;
    }

    public final com.android.tools.r8.graph.Y0 g() {
        return this.a;
    }

    public final String toString() {
        return "LiveProtoFieldObject(" + this.a.r0() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.LP
    public final AbstractC2412ko a(C0702m c0702m, C2409kl c2409kl) {
        S10 s10A = c2409kl.a((AbstractC3472z00) AbstractC3472z00.b((C0702m<?>) c0702m, WH.b()));
        return c0702m.Y().k1() ? new C2477lf(s10A, this.a, C1294Qh.g()) : new C1033Hc(s10A, this.a.f);
    }
}
