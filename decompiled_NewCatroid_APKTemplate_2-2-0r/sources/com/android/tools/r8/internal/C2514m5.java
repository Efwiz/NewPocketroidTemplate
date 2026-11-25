package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.m5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2514m5 extends AbstractC2194i {
    protected final InterfaceC2282j5 a;

    public C2514m5(InterfaceC2282j5 interfaceC2282j5) {
        this.a = interfaceC2282j5;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2282j5
    public final boolean q() {
        return this.a.q();
    }
}
