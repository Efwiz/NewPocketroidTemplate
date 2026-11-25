package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0507t3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3133uU extends C2972s9 {
    public static final /* synthetic */ int l = 0;

    public C3133uU(S10 s10, S10 s11, C0767v2 c0767v2) {
        super(s10, s11, c0767v2);
    }

    public static C3063tU L2() {
        return new C3063tU();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C3133uU C0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.C2972s9, com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new C3261w8(J2()), this);
    }

    @Override // com.android.tools.r8.internal.C2972s9, com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.C2972s9
    final com.android.tools.r8.dex.code.P c(int i) {
        return new C0507t3(i, J2());
    }
}
