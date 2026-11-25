package com.android.tools.r8.graph;

import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface V4 extends com.android.tools.r8.utils.structural.x {
    default int a(V4 v4, AbstractC3649o0 abstractC3649o0) {
        V4 v5 = (V4) U();
        com.android.tools.r8.utils.structural.v vVar = new com.android.tools.r8.utils.structural.v() { // from class: com.android.tools.r8.graph.V4$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.v
            public final int a(Object obj, Object obj2, com.android.tools.r8.utils.structural.a aVar) {
                return ((V4) obj).a((V4) obj2, aVar);
            }
        };
        if (v5 == v4) {
            return 0;
        }
        return vVar.a(v5, v4, new com.android.tools.r8.utils.structural.d(abstractC3649o0));
    }
}
