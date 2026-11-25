package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Di, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0926Di extends AbstractC3028t {
    final /* synthetic */ C0957Ei c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0926Di(C0957Ei c0957Ei, int i) {
        super(i);
        this.c = c0957Ei;
    }

    @Override // com.android.tools.r8.internal.AbstractC3028t
    public final Object a(int i) {
        return this.c.b[i].iterator();
    }
}
