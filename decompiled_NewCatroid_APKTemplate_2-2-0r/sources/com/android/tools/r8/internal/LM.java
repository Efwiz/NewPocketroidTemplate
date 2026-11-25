package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0800x2;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class LM extends MM {
    final /* synthetic */ NM b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private LM(NM nm) {
        super();
        this.b = nm;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.internal.MM
    public final void a(C2720of c2720of, C0729q2 c0729q2) {
        C0800x2 c0800x2F1 = c0729q2.f1();
        IM im = this.b.d;
        Objects.requireNonNull(im);
        c2720of.a(c0800x2F1, new IM$$ExternalSyntheticLambda0(im));
        c2720of.a(this.b.c.a(c0729q2), new Function() { // from class: com.android.tools.r8.internal.LM$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0729q2) obj);
            }
        });
        c2720of.b(c0729q2.M1());
    }
}
