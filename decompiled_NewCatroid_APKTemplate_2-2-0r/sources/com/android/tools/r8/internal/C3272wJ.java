package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3272wJ extends Q0 {
    final /* synthetic */ BJ b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C3272wJ(BJ bj) {
        super(bj);
        this.b = bj;
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C3496zJ(this.b);
    }

    /* synthetic */ C3272wJ(BJ bj, int i) {
        this(bj);
    }
}
