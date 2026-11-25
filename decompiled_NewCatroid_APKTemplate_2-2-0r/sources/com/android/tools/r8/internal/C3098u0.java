package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.u0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3098u0 implements Iterator {
    Map.Entry a;
    final /* synthetic */ Iterator b;
    final /* synthetic */ AbstractC3250w0 c;

    C3098u0(AbstractC3250w0 abstractC3250w0, Iterator it) {
        this.c = abstractC3250w0;
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
        return new C3029t0(this, entry);
    }

    @Override // java.util.Iterator
    public final void remove() {
        AbstractC3123uM.b("no calls to next() since the last call to remove()", this.a != null);
        AbstractC3250w0.a(this.c, ((C2549md) this.a.getValue()).c());
        this.b.remove();
        this.a = null;
    }
}
