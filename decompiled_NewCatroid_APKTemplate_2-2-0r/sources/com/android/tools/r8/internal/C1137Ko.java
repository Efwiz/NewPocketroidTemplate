package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ko, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1137Ko extends AbstractC1244Oo implements InterfaceC0841Ar {
    final /* synthetic */ C1301Qo g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1137Ko(C1301Qo c1301Qo) {
        super(c1301Qo, 0);
        this.g = c1301Qo;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Integer.valueOf(this.g.b[a()]);
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        return this.g.b[a()];
    }
}
