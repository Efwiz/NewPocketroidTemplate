package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1765cS extends AbstractC2156hS implements InterfaceC1912eK {
    private C2002fS g;
    final /* synthetic */ C2306jS h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C1765cS(C2306jS c2306jS) {
        super(c2306jS, 0);
        this.h = c2306jS;
    }

    @Override // java.util.Iterator
    public final Object next() {
        C2002fS c2002fS = new C2002fS(this.h, a());
        this.g = c2002fS;
        return c2002fS;
    }

    @Override // com.android.tools.r8.internal.AbstractC2156hS, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ C1765cS(C2306jS c2306jS, int i) {
        this(c2306jS);
    }
}
