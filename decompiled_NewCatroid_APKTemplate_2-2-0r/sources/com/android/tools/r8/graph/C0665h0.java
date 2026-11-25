package com.android.tools.r8.graph;

import com.android.tools.r8.synthesis.C3842a;
import com.android.tools.r8.synthesis.InterfaceC3856n;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.graph.h0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0665h0 implements Q0 {
    final /* synthetic */ InterfaceC3856n a;
    final /* synthetic */ AbstractC0681j0 b;

    C0665h0(C3842a c3842a, AbstractC0681j0 abstractC0681j0) {
        this.a = c3842a;
        this.b = abstractC0681j0;
    }

    @Override // com.android.tools.r8.graph.Q0
    public final C0722p1 a() {
        return this.b.e;
    }

    @Override // com.android.tools.r8.graph.Q0
    public final AbstractC0727q0 c(C0767v2 c0767v2) {
        InterfaceC3856n interfaceC3856n = this.a;
        AbstractC0681j0 abstractC0681j0 = this.b;
        Objects.requireNonNull(abstractC0681j0);
        return interfaceC3856n.a(new h$$ExternalSyntheticLambda0(abstractC0681j0), c0767v2).R();
    }

    @Override // com.android.tools.r8.graph.Q0
    public final O f(C0767v2 c0767v2) {
        InterfaceC3856n interfaceC3856n = this.a;
        AbstractC0681j0 abstractC0681j0 = this.b;
        Objects.requireNonNull(abstractC0681j0);
        return interfaceC3856n.a(new h$$ExternalSyntheticLambda0(abstractC0681j0), c0767v2);
    }
}
