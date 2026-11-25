package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2353k20 extends AbstractC2309jV {
    static final /* synthetic */ boolean c = true;
    private final AbstractC2309jV b;

    public C2353k20(AbstractC2309jV abstractC2309jV) {
        this.b = abstractC2309jV;
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        if (c || this.b.a(c0729q2)) {
            return true;
        }
        throw new AssertionError("Verification of single class policies failed");
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "VerifySingleClassPolicyAlwaysSatisfied(" + this.b.e() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        return !com.android.tools.r8.utils.A1.b() || this.b.j();
    }
}
