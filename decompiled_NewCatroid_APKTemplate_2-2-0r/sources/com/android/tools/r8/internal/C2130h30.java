package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.h30, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2130h30 extends AbstractC3416yA implements InterfaceC1475Wi {
    final /* synthetic */ C2576my a;
    final /* synthetic */ C2586n30 b;
    final /* synthetic */ Q20 c;

    C2130h30(C2576my c2576my, C2586n30 c2586n30, Q20 q20) {
        this.a = c2576my;
        this.b = c2586n30;
        this.c = q20;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1475Wi
    public final Object a(Object obj) {
        InterfaceC3343xE interfaceC3343xE = (InterfaceC3343xE) obj;
        AbstractC0936Ds.c(interfaceC3343xE, "$this$applySingleExtension");
        C2576my c2576my = this.a;
        C3285wO c3285wOD = this.b.d();
        AbstractC0936Ds.b(c3285wOD, "t");
        return ((C3317wu) interfaceC3343xE).a(c2576my, c3285wOD, this.c);
    }
}
