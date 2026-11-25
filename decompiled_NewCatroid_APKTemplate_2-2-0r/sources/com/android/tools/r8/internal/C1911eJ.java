package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1911eJ extends AbstractC2223iJ implements InterfaceC2376kK {
    final /* synthetic */ C2375kJ f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1911eJ(C2375kJ c2375kJ) {
        super(c2375kJ, 0);
        this.f = c2375kJ;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        return this.f.b[b()];
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        return this.f.b[d()];
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
