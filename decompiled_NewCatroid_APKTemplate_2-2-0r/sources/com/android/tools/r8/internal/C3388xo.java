package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3388xo extends AbstractC0904Co implements InterfaceC2376kK {
    private C0838Ao f;
    final /* synthetic */ C0964Eo g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3388xo(C0964Eo c0964Eo) {
        super(c0964Eo, 0);
        this.g = c0964Eo;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        C0838Ao c0838Ao = new C0838Ao(this.g, b());
        this.f = c0838Ao;
        return c0838Ao;
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        C0838Ao c0838Ao = new C0838Ao(this.g, d());
        this.f = c0838Ao;
        return c0838Ao;
    }

    @Override // com.android.tools.r8.internal.AbstractC0904Co, java.util.ListIterator, java.util.Iterator
    public final void remove() {
        super.remove();
        this.f.a = -1;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
