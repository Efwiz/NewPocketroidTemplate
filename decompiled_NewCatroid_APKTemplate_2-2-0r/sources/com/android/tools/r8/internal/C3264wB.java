package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3264wB extends SF {
    private final int b;

    public C3264wB(C0702m c0702m) {
        c0702m.Y().O0().getClass();
        this.b = 100;
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        if (c2688oE.k()) {
            return Collections.singletonList(c2688oE);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            int iL = c0729q2.Y().l();
            Iterator it2 = linkedHashMap.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    C2688oE c2688oE2 = new C2688oE();
                    c2688oE2.a(c0729q2);
                    linkedHashMap.put(c2688oE2, Integer.valueOf(iL));
                    break;
                }
                Map.Entry entry = (Map.Entry) it2.next();
                C2688oE c2688oE3 = (C2688oE) entry.getKey();
                int iIntValue = ((Integer) entry.getValue()).intValue() + iL;
                if (iIntValue <= this.b) {
                    c2688oE3.a(c0729q2);
                    entry.setValue(Integer.valueOf(iIntValue));
                    break;
                }
            }
        }
        return WL.a(linkedHashMap.keySet());
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "LimitInterfaceGroups";
    }
}
