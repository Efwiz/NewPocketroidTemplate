package com.android.tools.r8.graph;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U implements l5 {
    private final InterfaceC0593a0 a;
    private final l5 b;

    private U(InterfaceC0593a0 interfaceC0593a0, l5 l5Var) {
        this.a = interfaceC0593a0;
        this.b = l5Var;
    }

    public static U a(P p, l5 l5Var) {
        return new U(p, l5Var);
    }

    @Override // com.android.tools.r8.graph.l5
    public final InterfaceC0593a0 getContext() {
        return this.a;
    }

    public static U a(InterfaceC0630c4 interfaceC0630c4, l5 l5Var) {
        return new U(interfaceC0630c4, l5Var);
    }
}
