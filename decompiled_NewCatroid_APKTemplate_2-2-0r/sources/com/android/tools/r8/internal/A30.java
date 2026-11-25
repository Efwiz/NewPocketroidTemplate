package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class A30 extends AbstractC1254Oy {
    private final VO b;
    final /* synthetic */ Q20 c;
    final /* synthetic */ int d;
    final /* synthetic */ InterfaceC1475Wi e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    A30(Q20 q20, int i, InterfaceC1475Wi interfaceC1475Wi) {
        super(0);
        this.c = q20;
        this.d = i;
        this.e = interfaceC1475Wi;
        this.b = WO.N();
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final void b(String str) {
        AbstractC0936Ds.c(str, "name");
        this.b.d(this.c.b(str));
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final void c(int i) {
        this.b.e(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final void a(String str) {
        AbstractC0936Ds.c(str, "name");
        this.b.a(this.c.b(str));
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final void b() {
        VO vo = this.b;
        SO soO = UO.o();
        soO.a(TO.e);
        vo.a(soO);
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final AbstractC1254Oy a(int i, EnumC1364Sy enumC1364Sy) {
        return new A30(this.c, i, new x30(this, enumC1364Sy));
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final AbstractC1254Oy a(int i) {
        return new A30(this.c, i, new w30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final AbstractC1254Oy b(int i) {
        return new A30(this.c, i, new z30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final AbstractC1254Oy a(int i, String str) {
        Q20 q20 = this.c;
        return new A30(q20, i, new y30(str, this, q20));
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final InterfaceC1090Iy a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        Q20 q20 = this.c;
        InterfaceC3343xE.a.getClass();
        InterfaceC1090Iy interfaceC1090Iy = null;
        for (InterfaceC3343xE interfaceC3343xE : C3267wE.a()) {
            AbstractC0936Ds.c(interfaceC3343xE, "$this$applySingleExtension");
            VO vo = this.b;
            AbstractC0936Ds.b(vo, "t");
            InterfaceC1090Iy interfaceC1090IyA = ((C3317wu) interfaceC3343xE).a(c2576my, vo, q20);
            if (interfaceC1090IyA != null) {
                if (interfaceC1090Iy != null) {
                    throw new IllegalStateException("Multiple extensions handle the same extension type: " + c2576my);
                }
                interfaceC1090Iy = interfaceC1090IyA;
            }
        }
        return interfaceC1090Iy;
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final void a() {
        if (AbstractC2940ri.a.a(this.d)) {
            this.b.a(true);
        }
        int i = this.d >> 1;
        if (i != WO.o().p()) {
            this.b.b(i);
        }
        InterfaceC1475Wi interfaceC1475Wi = this.e;
        VO vo = this.b;
        AbstractC0936Ds.b(vo, "t");
        interfaceC1475Wi.a(vo);
    }
}
