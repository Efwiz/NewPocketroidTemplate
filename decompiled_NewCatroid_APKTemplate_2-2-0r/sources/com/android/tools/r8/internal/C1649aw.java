package com.android.tools.r8.internal;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1649aw extends AbstractC1517Xv {
    private final InterfaceC2118gw h;
    private final C2814px.a i;
    private final C2033fw j;

    private C1649aw(InterfaceC2118gw interfaceC2118gw) {
        this.i = C2814px.a();
        c();
        this.j = new C2033fw();
        this.h = interfaceC2118gw;
    }

    @Override // com.android.tools.r8.internal.AbstractC1517Xv, com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        C2033fw c2033fw = this.j;
        final C2814px.a aVar = this.i;
        Objects.requireNonNull(aVar);
        AbstractC2509m2 abstractC2509m2B = c2033fw.b(new Consumer() { // from class: com.android.tools.r8.internal.aw$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                aVar.a((C1651ax) obj);
            }
        }, str);
        return abstractC2509m2B != null ? abstractC2509m2B : super.a(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        String str = C2043g2.e.a;
        return "KeepTarget";
    }

    @Override // com.android.tools.r8.internal.AbstractC1517Xv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        super.a();
        this.h.accept(this.i.a(d()).a());
    }
}
