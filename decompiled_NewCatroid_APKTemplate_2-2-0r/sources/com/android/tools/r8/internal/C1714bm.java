package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1714bm extends AbstractC3013sm {
    public final void a(C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0) {
        AbstractC2625nb.a(c0767v2, abstractC0727q0);
        Collection collection = (Collection) this.a.get(c0767v2);
        if (collection == null) {
            LinkedHashMap linkedHashMap = this.a;
            ArrayList arrayList = new ArrayList();
            linkedHashMap.put(c0767v2, arrayList);
            collection = arrayList;
        }
        collection.add(abstractC0727q0);
    }

    public final C1790cm a() {
        Set<Map.Entry> setEntrySet = this.a.entrySet();
        if (setEntrySet.isEmpty()) {
            return C1528Yf.g;
        }
        C1946em c1946em = new C1946em(setEntrySet.size());
        int size = 0;
        for (Map.Entry entry : setEntrySet) {
            Object key = entry.getKey();
            AbstractC1639am abstractC1639amA = AbstractC1639am.a((Collection) entry.getValue());
            if (!abstractC1639amA.isEmpty()) {
                c1946em.a(key, abstractC1639amA);
                size += abstractC1639amA.size();
            }
        }
        return new C1790cm(size, c1946em.a());
    }
}
