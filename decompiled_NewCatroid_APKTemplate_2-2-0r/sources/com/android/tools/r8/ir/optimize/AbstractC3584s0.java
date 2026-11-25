package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.C1406Ug$$ExternalSyntheticLambda13;
import com.android.tools.r8.internal.EnumC2523mE;
import com.android.tools.r8.internal.S10;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.s0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3584s0 {
    protected final S10 a;
    protected final EnumC2523mE b;

    private AbstractC3584s0(S10 s10, EnumC2523mE enumC2523mE) {
        this.a = s10;
        this.b = enumC2523mE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static AbstractC3584s0 a(S10 s10, S10 s11, EnumC2523mE enumC2523mE) {
        return s11.b(new C1406Ug$$ExternalSyntheticLambda13()) ? new C3586t0(s10, s11.q().E().N2(), enumC2523mE) : new C3589u0(s10, s11, enumC2523mE);
    }

    public abstract boolean a(int i);
}
