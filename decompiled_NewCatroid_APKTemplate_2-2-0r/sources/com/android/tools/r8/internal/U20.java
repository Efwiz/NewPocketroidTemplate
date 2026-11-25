package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U20 extends AbstractC1572Zx {
    private final C1921eO b = C1998fO.q();
    final /* synthetic */ Q20 c;
    final /* synthetic */ int d;
    final /* synthetic */ InterfaceC1475Wi e;

    U20(Q20 q20, int i, InterfaceC1475Wi interfaceC1475Wi) {
        this.c = q20;
        this.d = i;
        this.e = interfaceC1475Wi;
    }

    @Override // com.android.tools.r8.internal.AbstractC1572Zx
    public final AbstractC1338Ry a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return new K30(i, new S20(this), this.c, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1572Zx
    public final AbstractC1520Xy b() {
        return new N30(this.c, new T20(this));
    }

    public final C1921eO c() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1572Zx
    public final InterfaceC1546Yx a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        Q20 q20 = this.c;
        InterfaceC3343xE.a.getClass();
        InterfaceC1546Yx interfaceC1546Yx = null;
        for (InterfaceC3343xE interfaceC3343xE : C3267wE.a()) {
            AbstractC0936Ds.c(interfaceC3343xE, "$this$applySingleExtension");
            C1921eO c1921eOC = c();
            AbstractC0936Ds.b(c1921eOC, "t");
            InterfaceC1546Yx interfaceC1546YxA = ((C3317wu) interfaceC3343xE).a(c2576my, c1921eOC, q20);
            if (interfaceC1546YxA != null) {
                if (interfaceC1546Yx != null) {
                    throw new IllegalStateException("Multiple extensions handle the same extension type: " + c2576my);
                }
                interfaceC1546Yx = interfaceC1546YxA;
            }
        }
        return interfaceC1546Yx;
    }

    @Override // com.android.tools.r8.internal.AbstractC1572Zx
    public final void a() {
        if (this.d != C1998fO.k().l()) {
            this.b.b(this.d);
        }
        InterfaceC1475Wi interfaceC1475Wi = this.e;
        C1921eO c1921eO = this.b;
        AbstractC0936Ds.b(c1921eO, "t");
        interfaceC1475Wi.a(c1921eO);
    }
}
