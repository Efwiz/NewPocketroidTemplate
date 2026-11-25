package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3539zy extends AbstractC0914Cy {
    private int b;
    private String c;
    private int d;
    private int e;
    private final ArrayList f;
    private C0942Dy g;
    private final ArrayList h;
    private C1284Py i;
    public C0942Dy j;
    private final ArrayList k;
    private final ArrayList l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3539zy(int i, int i2, int i3, String str) {
        super(0);
        AbstractC0936Ds.c(str, "name");
        this.b = i;
        this.c = str;
        this.d = i2;
        this.e = i3;
        this.f = new ArrayList(0);
        this.h = new ArrayList(0);
        this.k = new ArrayList(0);
        InterfaceC3343xE.a.getClass();
        List listA = C3267wE.a();
        ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listA));
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            arrayList.add(((C3317wu) ((InterfaceC3343xE) it.next())).e());
        }
        this.l = arrayList;
    }

    public final void a(C0942Dy c0942Dy) {
        this.g = c0942Dy;
    }

    public final void b(C0942Dy c0942Dy) {
        AbstractC0936Ds.c(c0942Dy, "<set-?>");
        this.j = c0942Dy;
    }

    public final ArrayList c() {
        return this.h;
    }

    public final int d() {
        return this.b;
    }

    public final int e() {
        return this.d;
    }

    public final String f() {
        return this.c;
    }

    public final C0942Dy g() {
        return this.g;
    }

    public final C0942Dy h() {
        C0942Dy c0942Dy = this.j;
        if (c0942Dy != null) {
            return c0942Dy;
        }
        AbstractC0936Ds.a("returnType");
        throw null;
    }

    public final int i() {
        return this.e;
    }

    public final C1284Py j() {
        return this.i;
    }

    public final ArrayList k() {
        return this.f;
    }

    public final ArrayList l() {
        return this.k;
    }

    public final void a(C1284Py c1284Py) {
        this.i = c1284Py;
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1254Oy b(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        this.g = c0942Dy;
        return c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1254Oy c(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        b(c0942Dy);
        return c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        return (AbstractC1200My) AbstractC2909rH.a(new C1119Jy(i, str, i2, enumC1364Sy), this.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1520Xy b() {
        return (AbstractC1520Xy) AbstractC2909rH.a(new C1439Uy(), this.k);
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1254Oy a(int i) {
        return (AbstractC1254Oy) AbstractC2909rH.a(new C0942Dy(i), this.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final AbstractC1338Ry a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        C1284Py c1284Py = new C1284Py(i, str);
        this.i = c1284Py;
        return c1284Py;
    }

    @Override // com.android.tools.r8.internal.AbstractC0914Cy
    public final InterfaceC0874By a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        return (InterfaceC0874By) AbstractC2939rh.a(this.l, c2576my);
    }

    public final void a(AbstractC0914Cy abstractC0914Cy) {
        AbstractC1338Ry abstractC1338RyA;
        AbstractC1254Oy abstractC1254OyB;
        AbstractC0936Ds.c(abstractC0914Cy, "visitor");
        for (C1119Jy c1119Jy : this.f) {
            AbstractC1200My abstractC1200MyA = abstractC0914Cy.a(c1119Jy.b(), c1119Jy.d(), c1119Jy.c(), c1119Jy.f());
            if (abstractC1200MyA != null) {
                c1119Jy.a(abstractC1200MyA);
            }
        }
        C0942Dy c0942Dy = this.g;
        if (c0942Dy != null && (abstractC1254OyB = abstractC0914Cy.b(c0942Dy.e())) != null) {
            c0942Dy.a(abstractC1254OyB);
        }
        for (C0942Dy c0942Dy2 : this.h) {
            AbstractC1254Oy abstractC1254OyA = abstractC0914Cy.a(c0942Dy2.e());
            if (abstractC1254OyA != null) {
                c0942Dy2.a(abstractC1254OyA);
            }
        }
        C1284Py c1284Py = this.i;
        if (c1284Py != null && (abstractC1338RyA = abstractC0914Cy.a(c1284Py.b(), c1284Py.c())) != null) {
            c1284Py.a(abstractC1338RyA);
        }
        AbstractC1254Oy abstractC1254OyC = abstractC0914Cy.c(h().e());
        if (abstractC1254OyC != null) {
            h().a(abstractC1254OyC);
        }
        for (C1439Uy c1439Uy : this.k) {
            AbstractC1520Xy abstractC1520XyB = abstractC0914Cy.b();
            if (abstractC1520XyB != null) {
                c1439Uy.a(abstractC1520XyB);
            }
        }
        for (InterfaceC0874By interfaceC0874By : this.l) {
            ((com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l) interfaceC0874By).getClass();
            InterfaceC0874By interfaceC0874ByA = abstractC0914Cy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l.b);
            if (interfaceC0874ByA != null) {
                ((C1056Hu) interfaceC0874By).a(interfaceC0874ByA);
            }
        }
        abstractC0914Cy.a();
    }
}
