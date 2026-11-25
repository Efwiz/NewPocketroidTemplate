package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1512Xq extends H {
    final /* synthetic */ C2261ir b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C1512Xq(C2261ir c2261ir) {
        super(c2261ir);
        this.b = c2261ir;
    }

    @Override // com.android.tools.r8.internal.Q, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC3018sr iterator() {
        return new C1483Wq(this.b);
    }

    /* synthetic */ C1512Xq(C2261ir c2261ir, int i) {
        this(c2261ir);
    }
}
