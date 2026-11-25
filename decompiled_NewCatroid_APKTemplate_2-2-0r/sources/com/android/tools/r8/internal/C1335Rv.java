package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1335Rv extends AbstractC1517Xv {
    private final InterfaceC2118gw h;

    public C1335Rv(InterfaceC2118gw interfaceC2118gw) {
        this.h = interfaceC2118gw;
    }

    @Override // com.android.tools.r8.internal.AbstractC1517Xv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        super.a();
        this.h.accept(C2572mv.a().a(d()).a());
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        String str = AbstractC1967f2.a;
        return "KeepCondition";
    }
}
