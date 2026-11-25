package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2348k0 implements Iterator {
    Map.Entry a;
    final /* synthetic */ Iterator b;
    final /* synthetic */ C2428l0 c;

    C2348k0(C2428l0 c2428l0, Iterator it) {
        this.c = c2428l0;
        this.b = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        Map.Entry entry = (Map.Entry) this.b.next();
        this.a = entry;
        return entry.getKey();
    }

    @Override // java.util.Iterator
    public final void remove() {
        AbstractC3123uM.b("no calls to next() since the last call to remove()", this.a != null);
        Collection collection = (Collection) this.a.getValue();
        this.b.remove();
        AbstractC2889r0.b(this.c.b, collection.size());
        collection.clear();
        this.a = null;
    }
}
