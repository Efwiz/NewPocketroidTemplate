package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.i4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2203i4 extends C1584a4 {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2203i4(C0684j2 c0684j2) {
        super(c0684j2, null, 0);
        c0684j2.f.toString();
    }

    @Override // com.android.tools.r8.internal.C1584a4
    protected final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
        return l.w;
    }

    @Override // com.android.tools.r8.internal.C1584a4, com.android.tools.r8.internal.AbstractC1739c4
    public final Collection a(I7 i7, final C0702m c0702m, W3 w3, C1230Ob c1230Ob, AC ac) {
        C0722p1 c0722p1A = c0702m.a();
        C0729q2 c0729q2A = c0702m.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.i4$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.w;
            }
        }, c1230Ob.a(), c0702m, new Consumer() { // from class: com.android.tools.r8.internal.i4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2203i4.a(c0702m, (com.android.tools.r8.synthesis.P) obj);
            }
        });
        w3.b(c0729q2A, c1230Ob.b());
        ac.a(2);
        return AbstractC1639am.a(new V7(null, c0729q2A.d), new D8(D8.a.DupX1), new D8(D8.a.Swap), new I7(183, c0722p1A.a(c0729q2A.d, c0722p1A.b(c0722p1A.D1, c0722p1A.R2), c0722p1A.Y0), false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.synthesis.P p) {
        new C2131h4(c0702m, p);
    }
}
