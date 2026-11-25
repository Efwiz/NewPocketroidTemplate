package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC3054tM extends InterfaceC1348Si {
    static {
        boolean z = AbstractC2985sM.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1348Si
    default InterfaceC3054tM a(Function function) {
        if (!AbstractC2985sM.a && G()) {
            throw new AssertionError();
        }
        if (N()) {
            C0767v2 c0767v2T = j().T();
            C0767v2 c0767v2 = (C0767v2) function.apply(c0767v2T);
            if (c0767v2T != c0767v2) {
                return InterfaceC1348Si.b(c0767v2);
            }
        } else if (y()) {
            C0767v2 c0767v2D = D();
            C0767v2 c0767v3 = (C0767v2) function.apply(c0767v2D);
            if (c0767v2D != c0767v3) {
                return new C1733c10(R(), c0767v3);
            }
        }
        return this;
    }
}
