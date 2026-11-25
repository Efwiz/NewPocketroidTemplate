package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class SD extends UD {
    public static int a(int i) {
        if (i < 0) {
            return i;
        }
        if (i < 3) {
            return i + 1;
        }
        if (i < 1073741824) {
            return (int) ((i / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }

    public static Map a(ArrayList arrayList) {
        int size = arrayList.size();
        if (size == 0) {
            return C2017fg.a;
        }
        if (size == 1) {
            C2772pL c2772pL = (C2772pL) arrayList.get(0);
            AbstractC0936Ds.c(c2772pL, "pair");
            Map mapSingletonMap = Collections.singletonMap(c2772pL.c(), c2772pL.d());
            AbstractC0936Ds.b(mapSingletonMap, "singletonMap(pair.first, pair.second)");
            return mapSingletonMap;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(a(arrayList.size()));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            C2772pL c2772pL2 = (C2772pL) it.next();
            linkedHashMap.put(c2772pL2.a(), c2772pL2.b());
        }
        return linkedHashMap;
    }
}
