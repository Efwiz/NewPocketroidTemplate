package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class UQ implements Consumer {
    protected final AbstractC0727q0 a;
    final /* synthetic */ VQ b;

    UQ(VQ vq, AbstractC0727q0 abstractC0727q0) {
        this.b = vq;
        this.a = abstractC0727q0;
    }

    final Iterable a() {
        return com.android.tools.r8.utils.I1.a(this.a.H0(), new Function() { // from class: com.android.tools.r8.internal.UQ$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Iterable a(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.b.b.a.c(c0767v2);
        if (abstractC0727q0C != null) {
            return Collections.singletonList(abstractC0727q0C);
        }
        return Collections.emptyList();
    }
}
