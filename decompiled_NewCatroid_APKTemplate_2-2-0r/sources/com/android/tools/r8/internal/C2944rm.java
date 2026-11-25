package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2944rm extends AbstractC2963s10 {
    final AbstractC2963s10 a;
    Object b = null;
    AbstractC2963s10 c = AbstractC3533zt.a();

    C2944rm(AbstractC3232vm abstractC3232vm) {
        this.a = abstractC3232vm.e.entrySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.c.hasNext() || this.a.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!this.c.hasNext()) {
            Map.Entry entry = (Map.Entry) this.a.next();
            this.b = entry.getKey();
            this.c = ((AbstractC1271Pl) entry.getValue()).iterator();
        }
        return new C1325Rl(this.b, this.c.next());
    }
}
