package com.android.tools.r8.naming;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/* JADX INFO: renamed from: com.android.tools.r8.naming.m, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3644m {
    final /* synthetic */ boolean a;
    final /* synthetic */ TreeMap b;
    final /* synthetic */ List c;

    C3644m(boolean z, TreeMap treeMap, ArrayList arrayList) {
        this.a = z;
        this.b = treeMap;
        this.c = arrayList;
    }

    public final Integer a(int i) {
        if (!this.a) {
            i = ((Integer) this.b.ceilingKey(Integer.valueOf(i))).intValue();
        }
        return Integer.valueOf(i);
    }

    public final List b(int i) {
        return this.a ? this.c : (List) this.b.get(Integer.valueOf(i));
    }
}
