package com.android.tools.r8.synthesis;

import com.android.tools.r8.SyntheticInfoConsumerData;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class B implements SyntheticInfoConsumerData {
    private final C0767v2 a;
    private final C0767v2 b;

    public B(C0767v2 c0767v2, C0767v2 c0767v3) {
        this.a = c0767v2;
        this.b = c0767v3;
    }

    @Override // com.android.tools.r8.SyntheticInfoConsumerData
    public final ClassReference getSynthesizingContextClass() {
        return Reference.classFromDescriptor(this.b.b1());
    }

    @Override // com.android.tools.r8.SyntheticInfoConsumerData
    public final ClassReference getSyntheticClass() {
        return Reference.classFromDescriptor(this.a.b1());
    }
}
