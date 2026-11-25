package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3297wa {
    static final C3297wa d = new C3297wa(null, AbstractC1639am.i(), C3374xa.c);
    final C3297wa a;
    final AbstractC1639am b;
    final C3374xa c;

    C3297wa(C3297wa c3297wa, AbstractC1639am abstractC1639am, C3374xa c3374xa) {
        this.a = c3297wa;
        this.b = abstractC1639am;
        this.c = c3374xa;
    }

    final boolean a(final AbstractC0748t0 abstractC0748t0) {
        C3297wa c3297wa;
        return com.android.tools.r8.utils.I1.a(this.b, new Function() { // from class: com.android.tools.r8.internal.wa$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC0748t0) obj).e();
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.wa$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3297wa.a(abstractC0748t0, (com.android.tools.r8.graph.W0) obj);
            }
        }) || ((c3297wa = this.a) != null && c3297wa.a(abstractC0748t0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.W0 w0) {
        return w0 == abstractC0748t0.e();
    }
}
