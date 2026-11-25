package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1994fL extends AbstractC2148hL {
    final /* synthetic */ C2225iL p;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1994fL(C2225iL c2225iL, com.android.tools.r8.graph.o5 o5Var, List list) {
        super(c2225iL, o5Var, list);
        this.p = c2225iL;
    }

    @Override // com.android.tools.r8.internal.AbstractC2148hL
    protected final void a(int i, int i2, ZK zk) {
        synchronized (this.p.b) {
            ((List) this.p.b.computeIfAbsent(zk, new Function() { // from class: com.android.tools.r8.internal.fL$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C1994fL.a((ZK) obj);
                }
            })).add(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(ZK zk) {
        return new ArrayList();
    }
}
