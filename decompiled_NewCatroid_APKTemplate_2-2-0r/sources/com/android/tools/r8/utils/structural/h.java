package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class h {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(C0767v2 c0767v2) {
        return c0767v2;
    }

    public static int a(x xVar, Object obj, v vVar) {
        t tVar = new t() { // from class: com.android.tools.r8.utils.structural.h$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.t
            public final C0767v2 a(C0767v2 c0767v2) {
                return h.a(c0767v2);
            }
        };
        if (xVar == obj) {
            return 0;
        }
        return vVar.a(xVar, obj, new f(tVar));
    }
}
