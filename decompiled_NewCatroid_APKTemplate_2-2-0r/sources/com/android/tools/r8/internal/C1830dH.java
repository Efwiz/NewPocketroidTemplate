package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1830dH extends AbstractC2309jV {
    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        Iterator<com.android.tools.r8.graph.T0> it = c0729q2.w1().iterator();
        while (it.hasNext()) {
            if (it.next().y0()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoInstanceFieldAnnotations";
    }
}
