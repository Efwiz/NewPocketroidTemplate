package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C0862Bm;
import com.android.tools.r8.internal.DH;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class z {
    static final /* synthetic */ boolean b = true;
    private final ConcurrentHashMap a;

    private z() {
        this.a = new ConcurrentHashMap();
    }

    final void a(DH dh) {
        if (b) {
            return;
        }
        ConcurrentHashMap.KeySetView keySetViewKeySet = this.a.keySet();
        ConcurrentHashMap.KeySetView keySetViewKeySet2 = this.a.keySet();
        dh.getClass();
        C0862Bm c0862Bm = new C0862Bm();
        Iterator it = keySetViewKeySet2.iterator();
        while (it.hasNext()) {
            c0862Bm.a(dh.a(AbstractC1476Wj.e(), (C0767v2) it.next()));
        }
        if (!keySetViewKeySet.equals(c0862Bm.a())) {
            throw new AssertionError();
        }
    }
}
