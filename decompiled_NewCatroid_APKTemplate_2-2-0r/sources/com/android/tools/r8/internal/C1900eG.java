package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1900eG implements Iterator {
    private final InterfaceC1600aG a;
    private final Iterator b;
    private ZF c;
    private int d;
    private int e;
    private boolean f;

    C1900eG(InterfaceC1600aG interfaceC1600aG, Iterator it) {
        this.a = interfaceC1600aG;
        this.b = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.d > 0 || this.b.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (this.d == 0) {
            ZF zf = (ZF) this.b.next();
            this.c = zf;
            int iA = zf.a();
            this.d = iA;
            this.e = iA;
        }
        this.d--;
        this.f = true;
        return this.c.b();
    }

    @Override // java.util.Iterator
    public final void remove() {
        AbstractC3123uM.b("no calls to next() since the last call to remove()", this.f);
        if (this.e == 1) {
            this.b.remove();
        } else {
            this.a.remove(this.c.b());
        }
        this.e--;
        this.f = false;
    }
}
