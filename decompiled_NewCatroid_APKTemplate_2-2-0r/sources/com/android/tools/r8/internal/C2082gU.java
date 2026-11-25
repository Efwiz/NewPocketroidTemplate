package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2082gU extends AbstractC2158hU {
    private final int b;

    public C2082gU(int i) {
        this.b = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2158hU
    final C1434Ut a() {
        return new C1434Ut("removeInnerFrames(" + this.b + Constant.AFTER_QUTO);
    }

    @Override // com.android.tools.r8.internal.AbstractC2158hU
    public final void a(DT dt) {
        dt.a(this.b);
    }

    public static C2082gU a(String str) {
        try {
            return new C2082gU(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            throw new C1367Tb("Unexpected number for removeInnerFrames: " + str);
        }
    }
}
