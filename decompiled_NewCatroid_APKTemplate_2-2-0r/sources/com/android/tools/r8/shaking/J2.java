package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.OU;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class J2 {
    private final Set a = OU.c();

    public final void a(C0729q2 c0729q2) {
        this.a.add(c0729q2.getType());
    }

    public final K2 a() {
        return new K2(this.a);
    }
}
