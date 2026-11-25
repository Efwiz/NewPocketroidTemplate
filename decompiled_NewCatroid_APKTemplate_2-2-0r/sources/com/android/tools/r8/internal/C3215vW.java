package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Comparator;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3215vW extends AbstractC2069gN {
    public C3215vW(AbstractC3135uW abstractC3135uW) {
        super(abstractC3135uW);
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final AbstractC2069gN a() {
        return new C3215vW((AbstractC3135uW) this.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final InterfaceC1965f1 b(C0767v2 c0767v2) {
        return C3362xW.d().a(c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final InterfaceC1809d1 c() {
        return AbstractC3135uW.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final Comparator d() {
        return Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.vW$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((InterfaceC2275j1) obj).b();
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final AbstractC2069gN f() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2069gN
    public final InterfaceC2125h1 a(C0684j2 c0684j2) {
        return C3510zW.d().b(c0684j2);
    }
}
