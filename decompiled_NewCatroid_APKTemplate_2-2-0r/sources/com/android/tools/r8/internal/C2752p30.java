package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p30, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2752p30 extends AbstractC3416yA implements InterfaceC1475Wi {
    final /* synthetic */ C2576my a;
    final /* synthetic */ v30 b;
    final /* synthetic */ Q20 c;

    C2752p30(C2576my c2576my, v30 v30Var, Q20 q20) {
        this.a = c2576my;
        this.b = v30Var;
        this.c = q20;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1475Wi
    public final Object a(Object obj) {
        InterfaceC3343xE interfaceC3343xE = (InterfaceC3343xE) obj;
        AbstractC0936Ds.c(interfaceC3343xE, "$this$applySingleExtension");
        C2576my c2576my = this.a;
        EO eoC = this.b.c();
        AbstractC0936Ds.b(eoC, "t");
        return ((C3317wu) interfaceC3343xE).a(c2576my, eoC, this.c);
    }
}
