package com.android.tools.r8.internal;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.v0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3175v0 implements Iterator {
    final Iterator a;
    Map.Entry b;
    int c;
    boolean d;
    final /* synthetic */ AbstractC3250w0 e;

    C3175v0(AbstractC3250w0 abstractC3250w0) {
        this.e = abstractC3250w0;
        this.a = abstractC3250w0.c.entrySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.c > 0 || this.a.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.c == 0) {
            Map.Entry entry = (Map.Entry) this.a.next();
            this.b = entry;
            this.c = ((C2549md) entry.getValue()).b();
        }
        this.c--;
        this.d = true;
        return this.b.getKey();
    }

    @Override // java.util.Iterator
    public final void remove() {
        AbstractC3123uM.b("no calls to next() since the last call to remove()", this.d);
        if (((C2549md) this.b.getValue()).b() <= 0) {
            throw new ConcurrentModificationException();
        }
        if (((C2549md) this.b.getValue()).a() == 0) {
            this.a.remove();
        }
        AbstractC3250w0.a(this.e);
        this.d = false;
    }
}
