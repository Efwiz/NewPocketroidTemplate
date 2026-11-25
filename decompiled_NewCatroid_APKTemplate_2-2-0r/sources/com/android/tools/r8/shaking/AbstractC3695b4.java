package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AN;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.b4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3695b4 {
    final Set a;
    final Set b;
    private final A c;
    final Map d;
    final List e;
    public final AN f;

    AbstractC3695b4(Set set, Set set2, A a, Map map, List list, AN an) {
        this.a = set;
        this.b = set2;
        this.c = a;
        this.d = map;
        this.e = list;
        this.f = an;
    }

    public abstract A a();

    final Set a(C0767v2 c0767v2) {
        return (Set) this.d.get(c0767v2);
    }
}
