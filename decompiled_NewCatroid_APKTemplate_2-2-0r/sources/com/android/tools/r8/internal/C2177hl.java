package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2177hl implements Iterator {
    private final InterfaceC2488lo a;
    private C3406y2 b;

    C2177hl(C2409kl c2409kl) {
        InterfaceC2488lo interfaceC2488loR = c2409kl.j().R();
        this.a = interfaceC2488loR;
        this.b = interfaceC2488loR.next().q();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b != null;
    }

    @Override // java.util.Iterator
    public final Object next() {
        C3406y2 c3406y2 = this.b;
        if (c3406y2 == null) {
            throw new NoSuchElementException();
        }
        this.b = this.a.next().q();
        return c3406y2;
    }
}
