package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0996Fu {
    public static final ArrayList a(List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.ensureCapacity(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C1487Wu c1487Wu = (C1487Wu) it.next();
            int iJ = c1487Wu.j();
            for (int i = 0; i < iJ; i++) {
                arrayList.add(c1487Wu);
            }
        }
        arrayList.trimToSize();
        return arrayList;
    }
}
