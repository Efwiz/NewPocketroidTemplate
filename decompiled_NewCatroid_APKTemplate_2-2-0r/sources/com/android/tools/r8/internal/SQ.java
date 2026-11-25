package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class SQ extends UQ {
    final /* synthetic */ VQ c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SQ(VQ vq, AbstractC0727q0 abstractC0727q0) {
        super(vq, abstractC0727q0);
        this.c = vq;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        com.android.tools.r8.utils.O3 o3 = (com.android.tools.r8.utils.O3) obj;
        o3.b(new TQ(this.c, this.a));
        for (AbstractC0727q0 abstractC0727q0 : a()) {
            if (!this.c.a.containsKey(abstractC0727q0)) {
                o3.b(new SQ(this.c, abstractC0727q0));
            }
        }
    }
}
