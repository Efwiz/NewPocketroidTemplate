package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ee, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1937ee {
    static final C1937ee c = new C1937ee(Collections.emptyList(), Collections.emptyList());
    private final List a;
    private final List b;

    private C1937ee(List list, List list2) {
        this.a = list;
        this.b = list2;
    }

    final C0684j2 a(C0684j2 c0684j2) {
        Iterator it = this.a.iterator();
        C0684j2 c0684j3 = null;
        while (it.hasNext()) {
            C0684j2 reference = ((com.android.tools.r8.graph.W0) it.next()).getReference();
            if (reference.h == c0684j2.h && reference.f == c0684j2.f) {
                if (c0684j3 != null) {
                    return null;
                }
                c0684j3 = reference;
            }
        }
        return c0684j3;
    }
}
