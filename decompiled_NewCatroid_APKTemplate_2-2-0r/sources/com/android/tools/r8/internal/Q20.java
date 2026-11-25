package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q20 {
    private final C1648av a;
    private final C2451lG b;

    public /* synthetic */ Q20(C1648av c1648av) {
        this(c1648av, C1940eg.a);
    }

    public final C1648av a() {
        return this.a;
    }

    public final C2451lG b() {
        return this.b;
    }

    public Q20(C1648av c1648av, List list) {
        AbstractC0936Ds.c(list, "contextExtensions");
        this.a = c1648av;
        this.b = new C2451lG();
    }

    public final int a(String str) {
        AbstractC0936Ds.c(str, "string");
        return this.a.a(str);
    }

    public final int b(String str) {
        AbstractC0936Ds.c(str, "name");
        return R20.a(this.a, str);
    }
}
