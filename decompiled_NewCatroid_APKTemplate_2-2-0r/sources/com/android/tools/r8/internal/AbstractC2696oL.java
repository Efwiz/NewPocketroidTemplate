package com.android.tools.r8.internal;

import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2696oL extends AbstractC3468yy {
    private final BO b;
    private final Q20 c;

    public /* synthetic */ AbstractC2696oL(C1648av c1648av) {
        this(c1648av, C1940eg.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC3468yy
    public final InterfaceC3399xy a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        InterfaceC3343xE.a.getClass();
        InterfaceC3399xy interfaceC3399xy = null;
        for (InterfaceC3343xE interfaceC3343xE : C3267wE.a()) {
            AbstractC0936Ds.c(interfaceC3343xE, "$this$applySingleExtension");
            InterfaceC3399xy interfaceC3399xyA = ((C3317wu) interfaceC3343xE).a(c2576my, g(), f());
            if (interfaceC3399xyA != null) {
                if (interfaceC3399xy != null) {
                    throw new IllegalStateException("Multiple extensions handle the same extension type: " + c2576my);
                }
                interfaceC3399xy = interfaceC3399xyA;
            }
        }
        return interfaceC3399xy;
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC1029Gy b(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return new F30(i, new C2605nL(this), this.c, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC3468yy
    public final void e() {
        C2919rP c2919rP = (C2919rP) this.c.b().a();
        if (c2919rP != null) {
            this.b.a(c2919rP);
        }
    }

    protected final Q20 f() {
        return this.c;
    }

    protected final BO g() {
        return this.b;
    }

    public AbstractC2696oL(C1648av c1648av, List list) {
        AbstractC0936Ds.c(list, "contextExtensions");
        this.b = CO.t();
        this.c = new Q20(c1648av, list);
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC3025sy a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return new C2586n30(i, new C2456lL(this), this.c, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC0914Cy a(int i, int i2, int i3, String str) {
        AbstractC0936Ds.c(str, "name");
        return O30.a(i, i2, i3, new C2530mL(this), this.c, str);
    }
}
