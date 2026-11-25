package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n30, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2586n30 extends AbstractC3025sy {
    private final C3285wO b;
    final /* synthetic */ Q20 c;
    final /* synthetic */ String d;
    final /* synthetic */ int e;
    final /* synthetic */ InterfaceC1475Wi f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2586n30(int i, InterfaceC1475Wi interfaceC1475Wi, Q20 q20, String str) {
        super(0);
        this.c = q20;
        this.d = str;
        this.e = i;
        this.f = interfaceC1475Wi;
        this.b = C3354xO.J();
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        return new H30(i2, i, new C2355k30(this), enumC1364Sy, this.c, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1254Oy b(int i) {
        return new A30(this.c, i, new C2202i30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1254Oy c(int i) {
        return new A30(this.c, i, new C2280j30(this));
    }

    public final C3285wO d() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1254Oy a(int i) {
        return new A30(this.c, i, new C1970f30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final void b() {
        this.b.c(this.c.a(this.d));
        if (this.e != C3354xO.n().o()) {
            this.b.b(this.e);
        }
        InterfaceC1475Wi interfaceC1475Wi = this.f;
        C3285wO c3285wO = this.b;
        AbstractC0936Ds.b(c3285wO, "t");
        interfaceC1475Wi.a(c3285wO);
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1520Xy c() {
        return new N30(this.c, new C2512m30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1338Ry a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return new K30(i, new C2435l30(this), this.c, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1804cy a() {
        return new W20(this.c, new C2046g30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final InterfaceC2956ry a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        return (InterfaceC2956ry) AbstractC2939rh.a(c2576my, new C2130h30(c2576my, this, this.c));
    }
}
