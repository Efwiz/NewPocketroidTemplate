package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.e4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0644e4 extends AbstractC0748t0 implements InterfaceC0630c4 {
    static final /* synthetic */ boolean e = true;

    public C0644e4(C0635d2 c0635d2, W0 w0) {
        super(c0635d2, w0);
    }

    @Override // com.android.tools.r8.graph.AbstractC0741s0
    /* JADX INFO: renamed from: I, reason: merged with bridge method [inline-methods] */
    public final C0635d2 a() {
        AbstractC0727q0 abstractC0727q0A = super.a();
        if (e || abstractC0727q0A.f0()) {
            return abstractC0727q0A.d0();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final C0644e4 W() {
        return this;
    }
}
