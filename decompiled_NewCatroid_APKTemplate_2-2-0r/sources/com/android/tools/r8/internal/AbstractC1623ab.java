package com.android.tools.r8.internal;

import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ab, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1623ab extends AbstractC1363Sx {
    private final C1610aO b;
    private final Q20 c;

    public /* synthetic */ AbstractC1623ab(C1648av c1648av) {
        this(c1648av, C1940eg.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC1363Sx
    public final InterfaceC1337Rx a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        InterfaceC3343xE.a.getClass();
        InterfaceC1337Rx interfaceC1337Rx = null;
        for (InterfaceC3343xE interfaceC3343xE : C3267wE.a()) {
            AbstractC0936Ds.c(interfaceC3343xE, "$this$applySingleExtension");
            InterfaceC1337Rx interfaceC1337RxA = ((C3317wu) interfaceC3343xE).a(c2576my, g(), f());
            if (interfaceC1337RxA != null) {
                if (interfaceC1337Rx != null) {
                    throw new IllegalStateException("Multiple extensions handle the same extension type: " + c2576my);
                }
                interfaceC1337Rx = interfaceC1337RxA;
            }
        }
        return interfaceC1337Rx;
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC1029Gy b(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return new F30(i, new C1467Wa(this), this.c, str);
    }

    public final void c(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        if (i != C1761cO.o().q()) {
            this.b.e(i);
        }
        this.b.f(this.c.b(str));
    }

    public final AbstractC1254Oy d(int i) {
        return new A30(this.c, i, new C1441Va(this));
    }

    public final void e(String str) {
        AbstractC0936Ds.c(str, "name");
        this.b.b(this.c.b(str));
    }

    protected final Q20 f() {
        return this.c;
    }

    protected final C1610aO g() {
        return this.b;
    }

    public final AbstractC1520Xy h() {
        return new N30(this.c, new C1522Ya(this));
    }

    public AbstractC1623ab(C1648av c1648av, List list) {
        AbstractC0936Ds.c(list, "contextExtensions");
        this.b = C1761cO.P();
        this.c = new Q20(c1648av, list);
    }

    @Override // com.android.tools.r8.internal.AbstractC1363Sx
    public final void e() {
        C2919rP c2919rP = (C2919rP) this.c.b().a();
        if (c2919rP != null) {
            this.b.a(c2919rP);
        }
    }

    public final void b(String str) {
        AbstractC0936Ds.c(str, "name");
        C1610aO c1610aO = this.b;
        C2775pO c2775pOO = C2846qO.o();
        c2775pOO.a(this.c.a(str));
        c1610aO.a(c2775pOO);
    }

    public final void d(String str) {
        AbstractC0936Ds.c(str, "name");
        this.b.a(this.c.a(str));
    }

    public final void c(String str) {
        AbstractC0936Ds.c(str, "name");
        this.b.g(this.c.a(str));
    }

    public final AbstractC1254Oy b(int i) {
        return new A30(this.c, i, new C1313Ra(this));
    }

    public final AbstractC1254Oy c(int i) {
        return new A30(this.c, i, new C1366Ta(this));
    }

    public final AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        return new H30(i2, i, new C1496Xa(this), enumC1364Sy, this.c, str);
    }

    public final AbstractC1572Zx a(int i) {
        return new U20(this.c, i, new C1286Qa(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC3025sy a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return new C2586n30(i, new C1340Sa(this), this.c, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC0914Cy a(int i, int i2, int i3, String str) {
        AbstractC0936Ds.c(str, "name");
        return O30.a(i, i2, i3, new C1394Ua(this), this.c, str);
    }

    public final void a(String str) {
        AbstractC0936Ds.c(str, "name");
        this.b.d(this.c.a(str));
    }
}
