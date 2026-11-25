package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class TQ extends UQ {
    final /* synthetic */ VQ c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    TQ(VQ vq, AbstractC0727q0 abstractC0727q0) {
        super(vq, abstractC0727q0);
        this.c = vq;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(AbstractC0727q0 abstractC0727q0) {
        return ((Boolean) this.c.a.get(abstractC0727q0)).booleanValue() || (abstractC0727q0.f0() && this.c.b.b.a(abstractC0727q0.d0()));
    }

    @Override // java.util.function.Consumer
    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        b();
    }

    public final void b() {
        this.c.a.put(this.a, Boolean.valueOf(AbstractC2734ot.b(a(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.TQ$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a((AbstractC0727q0) obj);
            }
        })));
    }
}
