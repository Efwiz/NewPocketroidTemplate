package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class v30 extends AbstractC0914Cy {
    private final EO b;
    final /* synthetic */ Q20 c;
    final /* synthetic */ String d;
    final /* synthetic */ int e;
    final /* synthetic */ int f;
    final /* synthetic */ int g;
    final /* synthetic */ InterfaceC1475Wi h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    v30(int i, int i2, int i3, InterfaceC1475Wi interfaceC1475Wi, Q20 q20, String str) {
        super(0);
        this.c = q20;
        this.d = str;
        this.e = i;
        this.f = i2;
        this.g = i3;
        this.h = interfaceC1475Wi;
        this.b = FO.K();
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        return new H30(i2, i, new t30(this), enumC1364Sy, this.c, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1254Oy b(int i) {
        return new A30(this.c, i, new C2825q30(this));
    }

    public final EO c() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1254Oy c(int i) {
        return new A30(this.c, i, new r30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1254Oy a(int i) {
        return new A30(this.c, i, new C2670o30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1520Xy b() {
        return new N30(this.c, new u30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1338Ry a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return new K30(i, new s30(this), this.c, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final InterfaceC0874By a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        return (InterfaceC0874By) AbstractC2939rh.a(c2576my, new C2752p30(c2576my, this, this.c));
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final void a() {
        this.b.d(this.c.a(this.d));
        if (this.e != FO.m().n()) {
            this.b.b(this.e);
        }
        if (AbstractC2868qi.a.a(this.e)) {
            this.b.c(this.f);
        }
        if (AbstractC2868qi.b.a(this.e)) {
            this.b.e(this.g);
        }
        InterfaceC1475Wi interfaceC1475Wi = this.h;
        EO eo = this.b;
        AbstractC0936Ds.b(eo, "t");
        interfaceC1475Wi.a(eo);
    }
}
