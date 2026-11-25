package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class L9 extends M9 {
    private final Set f;

    L9(C0702m c0702m, PM pm, C0825Ad c0825Ad, C2569ms c2569ms) {
        super(c0702m, pm, c0825Ad, c2569ms);
        this.f = OU.a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.internal.M9
    /* JADX INFO: renamed from: a */
    public final void b(G7 g7, C0729q2 c0729q2) {
        if (new C2595nB(this.a).a(c0729q2)) {
            this.f.add(c0729q2.getType());
        } else {
            ((M9) this).b.a(c0729q2, g7, ((M9) this).c, ((M9) this).d);
        }
    }

    @Override // com.android.tools.r8.internal.M9
    final void a() {
        this.a.a(this.f);
    }
}
