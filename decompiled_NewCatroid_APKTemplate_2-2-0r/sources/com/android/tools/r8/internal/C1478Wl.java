package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1478Wl extends AbstractC3028t {
    final /* synthetic */ AbstractC1639am c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1478Wl(AbstractC1639am abstractC1639am, int i, int i2) {
        super(i, i2);
        this.c = abstractC1639am;
    }

    @Override // com.android.tools.r8.internal.AbstractC3028t
    protected final Object a(int i) {
        return this.c.get(i);
    }
}
