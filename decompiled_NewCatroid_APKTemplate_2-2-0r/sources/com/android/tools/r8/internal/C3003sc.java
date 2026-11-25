package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3003sc extends S {
    private final Iterator c;

    C3003sc(C3222vc c3222vc) {
        this.c = c3222vc.c.entrySet().iterator();
    }

    @Override // com.android.tools.r8.internal.S
    protected final Object a() {
        while (this.c.hasNext()) {
            Map.Entry entry = (Map.Entry) this.c.next();
            int i = ((AtomicInteger) entry.getValue()).get();
            if (i != 0) {
                return new C1829dG(i, entry.getKey());
            }
        }
        b();
        return null;
    }
}
