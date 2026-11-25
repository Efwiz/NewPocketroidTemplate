package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1794cp extends C {
    final /* synthetic */ C2181hp b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C1794cp(C2181hp c2181hp) {
        super(c2181hp);
        this.b = c2181hp;
    }

    @Override // com.android.tools.r8.internal.Q, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC3018sr iterator() {
        return new C2026fp(this.b);
    }

    /* synthetic */ C1794cp(C2181hp c2181hp, int i) {
        this(c2181hp);
    }
}
