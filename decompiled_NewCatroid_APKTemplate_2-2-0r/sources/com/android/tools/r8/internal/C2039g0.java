package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2039g0 implements Iterator {
    final Iterator a;
    Object b = null;
    Collection c = null;
    Iterator d = EnumC3463yt.a;
    final /* synthetic */ AbstractC2889r0 e;

    C2039g0(AbstractC2889r0 abstractC2889r0) {
        this.e = abstractC2889r0;
        this.a = abstractC2889r0.e.entrySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext() || this.d.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!this.d.hasNext()) {
            Map.Entry entry = (Map.Entry) this.a.next();
            this.b = entry.getKey();
            Collection collection = (Collection) entry.getValue();
            this.c = collection;
            this.d = collection.iterator();
        }
        return new C1325Rl(this.b, this.d.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.d.remove();
        if (this.c.isEmpty()) {
            this.a.remove();
        }
        AbstractC2889r0.c(this.e);
    }
}
