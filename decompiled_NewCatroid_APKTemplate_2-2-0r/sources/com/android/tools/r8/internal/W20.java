package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class W20 extends AbstractC1804cy {
    private final C2152hO b = C2232iO.j();
    final /* synthetic */ Q20 c;
    final /* synthetic */ InterfaceC1475Wi d;

    W20(Q20 q20, InterfaceC1475Wi interfaceC1475Wi) {
        this.c = q20;
        this.d = interfaceC1475Wi;
    }

    @Override // com.android.tools.r8.internal.AbstractC1804cy
    public final AbstractC2425ky a(EnumC2344jy enumC2344jy, EnumC2269iy enumC2269iy) {
        AbstractC0936Ds.c(enumC2344jy, ModuleXmlParser.TYPE);
        return new C1583a30(this.c, enumC2344jy, enumC2269iy, new V20(this));
    }

    public final C2152hO b() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1804cy
    public final void a() {
        InterfaceC1475Wi interfaceC1475Wi = this.d;
        C2152hO c2152hO = this.b;
        AbstractC0936Ds.b(c2152hO, "t");
        interfaceC1475Wi.a(c2152hO);
    }
}
