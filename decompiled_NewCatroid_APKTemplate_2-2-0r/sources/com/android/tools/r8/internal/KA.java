package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class KA {
    static final /* synthetic */ boolean a = true;

    public static void a(C0702m c0702m, Collection collection) {
        if (!a && !c0702m.Y().A0.b() && !collection.isEmpty()) {
            throw new AssertionError();
        }
        C0684j2 c0684j2 = c0702m.a().K5;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            ((C0729q2) it.next()).e(c0684j2);
        }
    }
}
