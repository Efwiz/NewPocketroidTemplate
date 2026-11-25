package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1790cm;
import com.android.tools.r8.internal.VF;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.b0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3871b0 extends AbstractC3876c0 {
    private final VF b;

    private C3871b0(com.android.tools.r8.graph.I i, C1790cm c1790cm) {
        super(i);
        this.b = c1790cm;
    }

    @Override // com.android.tools.r8.utils.AbstractC3876c0
    public final void a(C0767v2 c0767v2, Consumer consumer) {
        Iterator it = this.b.get(c0767v2).iterator();
        while (it.hasNext()) {
            consumer.accept((AbstractC0727q0) it.next());
        }
    }

    public final String toString() {
        return "preloaded(" + this.b.size() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.utils.AbstractC3876c0
    public final Collection a() {
        return this.b.c();
    }
}
