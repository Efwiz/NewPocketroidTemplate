package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1111Jq extends AbstractC1246Oq implements InterfaceC1912eK {
    private C1192Mq g;
    final /* synthetic */ C1303Qq h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C1111Jq(C1303Qq c1303Qq) {
        super(c1303Qq, 0);
        this.h = c1303Qq;
    }

    @Override // java.util.Iterator
    public final Object next() {
        C1192Mq c1192Mq = new C1192Mq(this.h, a());
        this.g = c1192Mq;
        return c1192Mq;
    }

    @Override // com.android.tools.r8.internal.AbstractC1246Oq, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ C1111Jq(C1303Qq c1303Qq, int i) {
        this(c1303Qq);
    }
}
