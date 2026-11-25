package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class y30 extends AbstractC3416yA implements InterfaceC1475Wi {
    final /* synthetic */ String a;
    final /* synthetic */ A30 b;
    final /* synthetic */ Q20 c;

    y30(String str, A30 a30, Q20 q20) {
        this.a = str;
        this.b = a30;
        this.c = q20;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1475Wi
    public final Object a(Object obj) {
        VO vo = (VO) obj;
        AbstractC0936Ds.c(vo, "it");
        if (this.a != null) {
            this.b.b.c(this.c.a(this.a));
        }
        this.b.b.c(vo.c());
        return C2042g10.a;
    }
}
