package com.android.tools.r8.internal;

import java.util.TreeMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1017Gn {
    static final /* synthetic */ boolean b = true;
    TreeMap a = new TreeMap(new C1018Gn$$ExternalSyntheticLambda0());

    public final C1017Gn a(com.android.tools.r8.graph.Y0 y0, InterfaceC0989Fn interfaceC0989Fn) {
        if (!b && this.a.containsKey(y0)) {
            throw new AssertionError();
        }
        if (!interfaceC0989Fn.isUnknown()) {
            this.a.put(y0, interfaceC0989Fn);
        }
        return this;
    }

    public final AbstractC1049Hn a() {
        if (this.a.isEmpty()) {
            return C1554Zf.c();
        }
        return new EH(this.a);
    }
}
