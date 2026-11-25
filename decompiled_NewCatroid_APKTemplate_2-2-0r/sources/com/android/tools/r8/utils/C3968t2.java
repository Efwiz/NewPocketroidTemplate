package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.utils.t2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3968t2 extends AbstractC3972u2 {
    final /* synthetic */ Map c;

    C3968t2(HashMap map) {
        this.c = map;
    }

    @Override // com.android.tools.r8.utils.AbstractC3972u2
    public final C0759u2 a(C0729q2 c0729q2) {
        return (C0759u2) this.c.get(c0729q2.getType());
    }
}
