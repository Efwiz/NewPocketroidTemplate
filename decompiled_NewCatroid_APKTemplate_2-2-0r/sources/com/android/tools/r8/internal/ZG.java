package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZG extends AbstractC2309jV {
    private final Set b = OU.c();

    public ZG(C0702m c0702m) {
        Iterator it = ((C3733i) c0702m.f()).u().iterator();
        while (it.hasNext()) {
            this.b.add(((C0684j2) it.next()).e);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        return !this.b.contains(c0729q2.getType());
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoFailedResolutionTargets";
    }
}
