package com.android.tools.r8.internal;

import com.android.tools.r8.internal.AbstractC2839qH;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.j6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2283j6<N extends AbstractC2839qH<N>> {
    final Map a;

    public AbstractC2283j6(Map map) {
        this.a = map;
    }

    public final AbstractC2839qH a(com.android.tools.r8.graph.o5 o5Var) {
        return (AbstractC2839qH) this.a.get(o5Var.getReference());
    }

    public boolean b() {
        return this.a.isEmpty();
    }

    public Collection<N> a() {
        return this.a.values();
    }
}
