package com.android.tools.r8.internal;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3431yN implements Iterable {
    private static final C3431yN b = new C3431yN(AbstractC2178hm.s());
    static final /* synthetic */ boolean c = true;
    private Map a;

    private C3431yN(Map map) {
        this.a = map;
    }

    public static C3431yN a() {
        return new C3431yN(new IdentityHashMap());
    }

    public static C3431yN b() {
        return b;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.a.values().iterator();
    }

    public final boolean a(com.android.tools.r8.graph.m5 m5Var) {
        com.android.tools.r8.graph.m5 m5Var2 = (com.android.tools.r8.graph.m5) this.a.put(m5Var.getReference(), m5Var);
        if (c || m5Var2 == null || (m5Var2.e() == m5Var.e() && m5Var2.a() == m5Var.a())) {
            return m5Var2 == null;
        }
        throw new AssertionError();
    }

    public final boolean b(com.android.tools.r8.graph.m5 m5Var) {
        return this.a.containsKey(m5Var.getReference());
    }
}
