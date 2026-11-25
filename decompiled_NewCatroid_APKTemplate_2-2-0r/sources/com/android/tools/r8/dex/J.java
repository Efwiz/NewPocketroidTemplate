package com.android.tools.r8.dex;

import com.android.tools.r8.graph.AbstractC0687j5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.OU;
import java.util.Collections;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class J extends AbstractC0687j5 {
    private final Set c;
    final /* synthetic */ K d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    J(K k, C0702m c0702m, C0729q2[] c0729q2Arr) {
        super(c0702m);
        this.d = k;
        Set setC = OU.c();
        this.c = setC;
        Collections.addAll(setC, c0729q2Arr);
    }

    @Override // com.android.tools.r8.graph.AbstractC0687j5
    public final void b(C0729q2 c0729q2) {
        if (this.c.contains(c0729q2)) {
            c0729q2.b(this.d.f);
        }
    }
}
