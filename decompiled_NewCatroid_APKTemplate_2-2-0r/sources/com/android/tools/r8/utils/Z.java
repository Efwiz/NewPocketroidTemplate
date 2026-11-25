package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.OU;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class Z extends AbstractC3876c0 {
    private final List b;

    private Z(com.android.tools.r8.graph.I i, ArrayList arrayList) {
        super(i);
        this.b = arrayList;
    }

    @Override // com.android.tools.r8.utils.AbstractC3876c0
    public final void a(C0767v2 c0767v2, Consumer consumer) {
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            ((AbstractC3876c0) it.next()).a(c0767v2, consumer);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        String str = "combined(";
        for (AbstractC3876c0 abstractC3876c0 : this.b) {
            sb.append(str);
            sb.append(abstractC3876c0);
            str = ", ";
        }
        return sb.append(Constant.AFTER_QUTO).toString();
    }

    @Override // com.android.tools.r8.utils.AbstractC3876c0
    public final Collection a() {
        Set setC = OU.c();
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            setC.addAll(((AbstractC3876c0) it.next()).a());
        }
        return setC;
    }
}
