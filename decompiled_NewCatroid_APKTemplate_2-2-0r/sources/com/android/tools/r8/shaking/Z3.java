package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AbstractC2151hN;
import java.util.List;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Z3 extends C3707d4 {
    private Z3(C0702m c0702m, AbstractC2151hN abstractC2151hN, B5 b5, List list) {
        super(c0702m, U3.a(abstractC2151hN), b5, list);
    }

    @Override // com.android.tools.r8.shaking.C3707d4
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final Y3 a(ExecutorService executorService) {
        C3686a4 c3686a4A = super.a(executorService);
        return new Y3(((AbstractC3695b4) c3686a4A).c, c3686a4A.g, c3686a4A.t, c3686a4A.e);
    }
}
