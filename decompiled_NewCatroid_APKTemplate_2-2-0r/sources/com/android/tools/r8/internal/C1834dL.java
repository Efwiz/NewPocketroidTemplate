package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1834dL extends AbstractC2148hL {
    private final List p;

    C1834dL(C2225iL c2225iL, com.android.tools.r8.graph.o5 o5Var, List list, List list2) {
        super(c2225iL, o5Var, list);
        this.p = list2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2148hL
    protected final void a(int i, int i2, ZK zk) {
        this.p.add(zk);
    }
}
