package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2607nN implements InterfaceC2794pd {
    private final C2626nc a;
    private final InterfaceC2794pd b;

    private C2607nN(C2626nc c2626nc, C2717od c2717od) {
        this.a = c2626nc;
        this.b = c2717od;
    }

    public static InterfaceC2794pd a(AbstractC2151hN abstractC2151hN, C2717od c2717od) {
        abstractC2151hN.getClass();
        return abstractC2151hN instanceof IH ? c2717od : new C2607nN(abstractC2151hN.a(), c2717od);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2794pd
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.a.a(o5Var, o5Var2);
        this.b.a(o5Var, o5Var2);
    }
}
