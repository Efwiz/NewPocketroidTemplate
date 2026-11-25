package com.android.tools.r8.utils;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class x3 extends y3 {
    private final y3 f;
    private final A1 g;

    @Override // com.android.tools.r8.utils.y3
    public final w3 a(int i, String str) {
        return this.f.a(i, str);
    }

    @Override // com.android.tools.r8.utils.y3
    public final void c() {
        this.f.c();
    }

    @Override // com.android.tools.r8.utils.y3
    public final void d() {
        this.f.d();
    }

    x3(A1 a1, y3 y3Var) {
        super("<cancel>", false);
        this.f = y3Var;
        this.g = a1;
    }

    @Override // com.android.tools.r8.utils.y3
    public final void a(String str, InterfaceC3885d3 interfaceC3885d3) {
        this.f.a(str, interfaceC3885d3);
    }

    @Override // com.android.tools.r8.utils.y3
    public final Object a(String str, InterfaceC3949p3 interfaceC3949p3) {
        return this.f.a(str, interfaceC3949p3);
    }

    @Override // com.android.tools.r8.utils.y3
    public final void a(String str) {
        if (!this.g.d0()) {
            this.f.a(str);
            return;
        }
        throw new P();
    }
}
