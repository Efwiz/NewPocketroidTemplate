package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.i4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0678i4 implements InterfaceC0669h4 {
    private final AbstractC0748t0 a;
    private final AbstractC0748t0 b;

    public C0678i4(AbstractC0748t0 abstractC0748t0, AbstractC0748t0 abstractC0748t1) {
        this.a = abstractC0748t0;
        this.b = abstractC0748t1;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0669h4
    public final AbstractC0727q0 a() {
        return this.a.a();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0669h4
    public final W0 e() {
        return this.a.e();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0669h4
    public final C0684j2 getReference() {
        return this.a.getReference();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0669h4
    public final AbstractC0748t0 p() {
        return this.a;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0707m4
    public final AbstractC0748t0 r() {
        return this.b;
    }
}
