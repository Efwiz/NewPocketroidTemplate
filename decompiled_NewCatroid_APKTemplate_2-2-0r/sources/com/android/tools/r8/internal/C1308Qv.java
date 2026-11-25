package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1308Qv extends AbstractC1057Hv {
    private final InterfaceC2118gw c;
    private final C2187hv d = C2264iv.a();
    private final String b = "KeepEdge";

    public C1308Qv(InterfaceC2118gw interfaceC2118gw) {
        this.c = interfaceC2118gw;
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str, String str2) {
        if (str2.equals(AbstractC1884e2.a)) {
            return new C1281Pv(this.d);
        }
        super.a(str, str2);
        throw null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        this.c.accept(this.d.a());
    }
}
