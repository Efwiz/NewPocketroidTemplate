package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2957rz extends AbstractC3323wz {
    private final C2973sA a;
    private final boolean b;

    private C2957rz(C2973sA c2973sA, boolean z) {
        this.a = c2973sA;
        this.b = z;
    }

    @Override // com.android.tools.r8.internal.AbstractC3323wz
    final boolean a(final C0942Dy c0942Dy, C0702m c0702m) {
        return this.a.b(new Consumer() { // from class: com.android.tools.r8.internal.rz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0942Dy, (String) obj);
            }
        }, c0702m, "Lkotlin/Any;");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC1254Oy abstractC1254Oy, String str) {
        abstractC1254Oy.a(AbstractC1745cA.a(str, this.b));
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.a.a(q0);
    }

    @Override // com.android.tools.r8.internal.AbstractC3323wz
    public final C0767v2 a(AbstractC1476Wj abstractC1476Wj) {
        return this.a.a(abstractC1476Wj);
    }
}
