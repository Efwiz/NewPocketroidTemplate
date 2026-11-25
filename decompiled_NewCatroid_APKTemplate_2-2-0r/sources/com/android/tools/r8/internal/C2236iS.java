package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2236iS extends AbstractC2156hS implements InterfaceC1912eK {
    final /* synthetic */ C2306jS g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2236iS(C2306jS c2306jS) {
        super(c2306jS, 0);
        this.g = c2306jS;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return this.g.b[a()];
    }
}
