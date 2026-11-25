package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class x30 extends AbstractC3416yA implements InterfaceC1475Wi {
    final /* synthetic */ A30 a;
    final /* synthetic */ EnumC1364Sy b;

    x30(A30 a30, EnumC1364Sy enumC1364Sy) {
        this.a = a30;
        this.b = enumC1364Sy;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1475Wi
    public final Object a(Object obj) {
        VO vo = (VO) obj;
        AbstractC0936Ds.c(vo, "argument");
        VO vo2 = this.a.b;
        SO soO = UO.o();
        EnumC1364Sy enumC1364Sy = this.b;
        if (enumC1364Sy == EnumC1364Sy.b) {
            soO.a(TO.b);
        } else if (enumC1364Sy == EnumC1364Sy.c) {
            soO.a(TO.c);
        }
        soO.a(vo.c());
        vo2.a(soO);
        return C2042g10.a;
    }
}
