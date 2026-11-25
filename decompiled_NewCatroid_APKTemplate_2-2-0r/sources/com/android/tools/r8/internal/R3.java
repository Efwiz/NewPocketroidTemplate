package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class R3 extends SF {
    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        LinkedList linkedList = new LinkedList();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (a(c0729q2)) {
                C2688oE c2688oE2 = new C2688oE();
                c2688oE2.a(c0729q2);
                linkedList.add(c2688oE2);
            }
        }
        if (linkedList.size() <= 1) {
            return AbstractC1639am.c(c2688oE);
        }
        Iterator itA = com.android.tools.r8.utils.L1.a(linkedList);
        Iterator it2 = c2688oE.iterator();
        while (it2.hasNext()) {
            C0729q2 c0729q3 = (C0729q2) it2.next();
            if (!a(c0729q3)) {
                ((C2688oE) itA.next()).a(c0729q3);
            }
        }
        return WL.a(linkedList);
    }

    abstract boolean a(C0729q2 c0729q2);
}
