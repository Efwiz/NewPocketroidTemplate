package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3237vq extends AbstractC0840Aq implements InterfaceC2376kK {
    private C3460yq f;
    final /* synthetic */ C0906Cq g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3237vq(C0906Cq c0906Cq) {
        super(c0906Cq, 0);
        this.g = c0906Cq;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        C3460yq c3460yq = new C3460yq(this.g, b());
        this.f = c3460yq;
        return c3460yq;
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        C3460yq c3460yq = new C3460yq(this.g, d());
        this.f = c3460yq;
        return c3460yq;
    }

    @Override // com.android.tools.r8.internal.AbstractC0840Aq, java.util.ListIterator, java.util.Iterator
    public final void remove() {
        super.remove();
        this.f.a = -1;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
