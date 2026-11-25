package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2463lR extends AbstractC2383kR implements InterfaceC2282j5 {
    final /* synthetic */ C2537mR g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2463lR(C2537mR c2537mR) {
        super(c2537mR, 0);
        this.g = c2537mR;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Boolean.valueOf(this.g.b[a()]);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2282j5
    public final boolean q() {
        return this.g.b[a()];
    }
}
