package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3420yC {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C3150ug b;
    private final C0729q2 c;

    private C3420yC(C0702m c0702m, C0729q2 c0729q2, C3150ug c3150ug) {
        this.a = c0702m;
        this.b = c3150ug;
        this.c = c0729q2;
        C0767v2 c0767v2A = C1473Wg.a(c0729q2, c0702m.a());
        if (!d && ((C3733i) c0702m.f()).d(c0767v2A) != null) {
            throw new AssertionError();
        }
    }

    private C0729q2 b() {
        C0729q2 c0729q2A = this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.yC$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.i;
            }
        }, this.c, this.a, new Consumer() { // from class: com.android.tools.r8.internal.yC$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((com.android.tools.r8.synthesis.P) obj).i();
            }
        });
        if (d || c0729q2A.getAccessFlags().equals(com.android.tools.r8.graph.E.N())) {
            return c0729q2A;
        }
        throw new AssertionError();
    }

    final C3487zC a() {
        return new C3487zC(b(), this.b, this.c);
    }
}
