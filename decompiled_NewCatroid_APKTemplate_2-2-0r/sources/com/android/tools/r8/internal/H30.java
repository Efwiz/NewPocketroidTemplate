package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H30 extends AbstractC1200My {
    private final C1691bP b = C1840dP.w();
    final /* synthetic */ Q20 c;
    final /* synthetic */ String d;
    final /* synthetic */ int e;
    final /* synthetic */ int f;
    final /* synthetic */ EnumC1364Sy g;
    final /* synthetic */ InterfaceC1475Wi h;

    H30(int i, int i2, InterfaceC1475Wi interfaceC1475Wi, EnumC1364Sy enumC1364Sy, Q20 q20, String str) {
        this.c = q20;
        this.d = str;
        this.e = i;
        this.f = i2;
        this.g = enumC1364Sy;
        this.h = interfaceC1475Wi;
    }

    @Override // com.android.tools.r8.internal.AbstractC1200My
    public final AbstractC1254Oy a(int i) {
        return new A30(this.c, i, new G30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC1200My
    public final InterfaceC1173Ly a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        Q20 q20 = this.c;
        InterfaceC3343xE.a.getClass();
        InterfaceC1173Ly interfaceC1173Ly = null;
        for (InterfaceC3343xE interfaceC3343xE : C3267wE.a()) {
            AbstractC0936Ds.c(interfaceC3343xE, "$this$applySingleExtension");
            C1691bP c1691bP = this.b;
            AbstractC0936Ds.b(c1691bP, "t");
            InterfaceC1173Ly interfaceC1173LyA = ((C3317wu) interfaceC3343xE).a(c2576my, c1691bP, q20);
            if (interfaceC1173LyA != null) {
                if (interfaceC1173Ly != null) {
                    throw new IllegalStateException("Multiple extensions handle the same extension type: " + c2576my);
                }
                interfaceC1173Ly = interfaceC1173LyA;
            }
        }
        return interfaceC1173Ly;
    }

    @Override // com.android.tools.r8.internal.AbstractC1200My
    public final void a() {
        this.b.b(this.c.a(this.d));
        this.b.a(this.e);
        boolean zA = AbstractC3009si.a.a(this.f);
        if (zA != C1840dP.k().n()) {
            this.b.a(zA);
        }
        EnumC1364Sy enumC1364Sy = this.g;
        if (enumC1364Sy == EnumC1364Sy.b) {
            this.b.a(EnumC1762cP.b);
        } else if (enumC1364Sy == EnumC1364Sy.c) {
            this.b.a(EnumC1762cP.c);
        }
        InterfaceC1475Wi interfaceC1475Wi = this.h;
        C1691bP c1691bP = this.b;
        AbstractC0936Ds.b(c1691bP, "t");
        interfaceC1475Wi.a(c1691bP);
    }
}
