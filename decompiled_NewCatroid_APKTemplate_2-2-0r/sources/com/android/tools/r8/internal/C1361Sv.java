package com.android.tools.r8.internal;

import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1361Sv extends AbstractC1057Hv {
    private final String b;
    private final InterfaceC2118gw c;
    private final C2649nv.a d = C2649nv.a();

    public C1361Sv(String str, InterfaceC2118gw interfaceC2118gw) {
        this.b = str;
        this.c = interfaceC2118gw;
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str, String str2) {
        if (!str2.equals(C2043g2.e.a)) {
            super.a(str, str2);
            throw null;
        }
        final C2649nv.a aVar = this.d;
        Objects.requireNonNull(aVar);
        return new C1649aw(new InterfaceC2118gw() { // from class: com.android.tools.r8.internal.Sv$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2118gw
            public final void accept(Object obj) {
                aVar.a((C2814px) obj);
            }
        });
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
