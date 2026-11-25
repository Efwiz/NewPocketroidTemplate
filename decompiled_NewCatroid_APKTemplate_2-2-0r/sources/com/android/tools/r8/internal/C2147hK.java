package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hK, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2147hK extends V0 {
    protected final InterfaceC1912eK a;

    public C2147hK(InterfaceC1912eK interfaceC1912eK) {
        this.a = interfaceC1912eK;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return this.a.next();
    }
}
