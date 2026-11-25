package com.android.tools.r8.utils;

import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.utils.p0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3946p0 extends AbstractC3955r0 {
    public abstract D3 a(InterfaceC3925l0 interfaceC3925l0);

    protected abstract D3 a(InterfaceC3925l0 interfaceC3925l0, Function function);

    @Override // com.android.tools.r8.utils.AbstractC3955r0
    final C3931m0 a(Object obj) {
        return new C3931m0(obj);
    }

    @Override // com.android.tools.r8.utils.AbstractC3955r0
    final D3 b(C3931m0 c3931m0) {
        return a(c3931m0, new Function() { // from class: com.android.tools.r8.utils.p0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(obj);
            }
        });
    }

    @Override // com.android.tools.r8.utils.AbstractC3955r0
    protected final D3 a(C3931m0 c3931m0) {
        return a((InterfaceC3925l0) c3931m0);
    }
}
