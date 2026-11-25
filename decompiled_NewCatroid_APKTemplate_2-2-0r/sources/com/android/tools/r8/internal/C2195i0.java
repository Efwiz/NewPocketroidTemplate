package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.i0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2195i0 implements Iterator {
    final Iterator a;
    Collection b;
    final /* synthetic */ C2273j0 c;

    C2195i0(C2273j0 c2273j0) {
        this.c = c2273j0;
        this.a = c2273j0.c.entrySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        Map.Entry entry = (Map.Entry) this.a.next();
        this.b = (Collection) entry.getValue();
        return this.c.a(entry);
    }

    @Override // java.util.Iterator
    public final void remove() {
        AbstractC3123uM.b("no calls to next() since the last call to remove()", this.b != null);
        this.a.remove();
        AbstractC2889r0.b(this.c.d, this.b.size());
        this.b.clear();
        this.b = null;
    }
}
