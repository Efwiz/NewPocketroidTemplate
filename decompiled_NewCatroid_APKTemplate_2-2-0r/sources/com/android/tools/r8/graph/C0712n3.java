package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.n3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0712n3 extends C0706m3.i {
    static final /* synthetic */ boolean b = true;
    final C0767v2 a;

    C0712n3(C0767v2 c0767v2) {
        boolean z = b;
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        if (!z && !c0767v2.W0()) {
            throw new AssertionError(c0767v2.b1());
        }
        this.a = c0767v2;
    }

    @Override // com.android.tools.r8.graph.C0706m3.i
    public final C0712n3 c() {
        return this;
    }

    @Override // com.android.tools.r8.graph.C0706m3.i
    public final C0706m3.a f() {
        if (b || !this.a.Y0()) {
            return new C0706m3.a(this, C0706m3.k.a);
        }
        throw new AssertionError();
    }
}
