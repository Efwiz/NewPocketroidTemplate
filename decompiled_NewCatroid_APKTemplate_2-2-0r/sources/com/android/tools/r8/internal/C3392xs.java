package com.android.tools.r8.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3392xs {
    private final int a;
    private final HashMap b;

    public C3392xs(C3392xs c3392xs) {
        this.a = c3392xs != null ? c3392xs.b.size() + c3392xs.a : 0;
        this.b = new HashMap();
    }

    public final List a() {
        Set setKeySet = this.b.keySet();
        AbstractC0936Ds.b(setKeySet, "interned.keys");
        return AbstractC0823Ab.a(setKeySet, new C3315ws(this.b));
    }

    public final boolean b() {
        return this.b.isEmpty();
    }

    public final int b(Object obj) {
        Integer numA = a(obj);
        if (numA != null) {
            return numA.intValue();
        }
        int size = this.a + this.b.size();
        this.b.put(obj, Integer.valueOf(size));
        return size;
    }

    private final Integer a(Object obj) {
        boolean z = Q30.a;
        return (Integer) this.b.get(obj);
    }
}
