package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1205Nd extends AbstractC3433yP {
    private final C0767v2 a;
    private final C0759u2 b;

    public C1205Nd(C0759u2 c0759u2, C0767v2 c0767v2) {
        this.a = c0767v2;
        this.b = c0759u2;
    }

    @Override // com.android.tools.r8.internal.LP
    public final AbstractC2412ko a(C0702m c0702m, C2409kl c2409kl) {
        throw new C3405y10();
    }

    public final String toString() {
        return "DeadProtoFieldObject(" + this.a.r0() + "." + this.b.r0() + Constant.AFTER_QUTO;
    }
}
