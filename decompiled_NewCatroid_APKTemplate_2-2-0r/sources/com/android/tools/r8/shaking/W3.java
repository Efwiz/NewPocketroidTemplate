package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AN;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class W3 extends AbstractC3695b4 {
    W3(Set set, Set set2, A a, IdentityHashMap identityHashMap, ArrayList arrayList, AN an) {
        super(set, set2, a, identityHashMap, arrayList, an);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3695b4
    public final A a() {
        return ((AbstractC3695b4) this).c;
    }

    static X3 a(C0702m c0702m, L l, B5 b5) {
        return new X3(c0702m, l, b5);
    }
}
