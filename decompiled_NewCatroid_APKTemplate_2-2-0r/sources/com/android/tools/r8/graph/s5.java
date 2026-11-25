package com.android.tools.r8.graph;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class s5 implements Iterable {
    protected final Map a;

    protected s5(TreeMap treeMap) {
        this.a = treeMap;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.a.values().iterator();
    }
}
