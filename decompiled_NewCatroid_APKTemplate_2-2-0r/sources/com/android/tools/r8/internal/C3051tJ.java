package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3051tJ extends Q0 {
    final /* synthetic */ EJ b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C3051tJ(EJ ej) {
        super(ej);
        this.b = ej;
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C2982sJ(this.b);
    }

    /* synthetic */ C3051tJ(EJ ej, int i) {
        this(ej);
    }
}
