package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.IntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class JQ extends KQ {
    private final String[] c;

    private JQ(String[] strArr, com.android.tools.r8.graph.Y0[] y0Arr) {
        super(y0Arr);
        this.c = strArr;
    }

    @Override // com.android.tools.r8.internal.KQ
    public final C0759u2 a(C0767v2 c0767v2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        return a(c0767v2, c0702m, abstractC1476Wj, new IntFunction() { // from class: com.android.tools.r8.internal.JQ$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return this.f$0.a(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String a(int i) {
        return this.c[i];
    }
}
