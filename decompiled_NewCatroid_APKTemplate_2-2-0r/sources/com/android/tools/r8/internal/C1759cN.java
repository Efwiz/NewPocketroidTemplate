package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1759cN implements InterfaceC1997fN {
    final /* synthetic */ C0684j2 a;
    final /* synthetic */ AbstractC2069gN b;

    C1759cN(AbstractC2069gN abstractC2069gN, C0684j2 c0684j2) {
        this.b = abstractC2069gN;
        this.a = c0684j2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1997fN
    public final InterfaceC1997fN a(C0767v2 c0767v2) {
        this.b.a(c0767v2);
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1997fN
    public final InterfaceC1997fN a(C0684j2 c0684j2) {
        final InterfaceC2125h1 interfaceC2125h1 = (InterfaceC2125h1) this.b.c.get(this.a);
        this.b.a(c0684j2, new Consumer() { // from class: com.android.tools.r8.internal.cN$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((InterfaceC2125h1) obj).a(interfaceC2125h1);
            }
        });
        this.b.e.a(c0684j2, this.a);
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1997fN
    public final void a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var) {
        this.b.a(c0684j2, o5Var);
    }
}
