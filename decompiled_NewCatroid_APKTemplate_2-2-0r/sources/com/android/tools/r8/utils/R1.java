package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class R1 {
    public static void a(AbstractC1476Wj abstractC1476Wj, AbstractC0744s2 abstractC0744s2, Consumer consumer) {
        AbstractC0744s2 abstractC0744s2A = abstractC1476Wj.a(abstractC0744s2, (AbstractC1476Wj) null);
        abstractC0744s2A.getClass();
        if ((abstractC0744s2A instanceof C0767v2) && abstractC0744s2A.w0().W0()) {
            return;
        }
        consumer.accept(abstractC0744s2A);
    }
}
