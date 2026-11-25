package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class JC extends OC implements InterfaceC1912eK {
    private MC g;
    final /* synthetic */ QC h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private JC(QC qc) {
        super(qc, 0);
        this.h = qc;
    }

    @Override // java.util.Iterator
    public final Object next() {
        MC mc = new MC(this.h, a());
        this.g = mc;
        return mc;
    }

    @Override // com.android.tools.r8.internal.OC, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ JC(QC qc, int i) {
        this(qc);
    }
}
