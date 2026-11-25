package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.py, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2815py extends AbstractC3025sy {
    private int b;
    private String c;
    private final ArrayList d;
    private C0942Dy e;
    private final ArrayList f;
    private final ArrayList g;
    public C0942Dy h;
    private final ArrayList i;
    private C1653ay j;
    private final ArrayList k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2815py(int i, String str) {
        super(0);
        AbstractC0936Ds.c(str, "name");
        this.b = i;
        this.c = str;
        this.d = new ArrayList(0);
        this.f = new ArrayList(0);
        this.g = new ArrayList();
        this.i = new ArrayList(0);
        InterfaceC3343xE.a.getClass();
        List listA = C3267wE.a();
        ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listA));
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            arrayList.add(((C3317wu) ((InterfaceC3343xE) it.next())).c());
        }
        this.k = arrayList;
    }

    public final void a(C0942Dy c0942Dy) {
        this.e = c0942Dy;
    }

    public final void b(C0942Dy c0942Dy) {
        AbstractC0936Ds.c(c0942Dy, "<set-?>");
        this.h = c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1254Oy c(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        b(c0942Dy);
        return c0942Dy;
    }

    public final ArrayList d() {
        return this.f;
    }

    public final C1653ay e() {
        return this.j;
    }

    public final int f() {
        return this.b;
    }

    public final String g() {
        return this.c;
    }

    public final C0942Dy h() {
        return this.e;
    }

    public final C0942Dy i() {
        C0942Dy c0942Dy = this.h;
        if (c0942Dy != null) {
            return c0942Dy;
        }
        AbstractC0936Ds.a("returnType");
        throw null;
    }

    public final List<C1119Jy> j() {
        return this.d;
    }

    public final List<C1284Py> k() {
        return this.g;
    }

    public final ArrayList l() {
        return this.i;
    }

    public final void a(C1653ay c1653ay) {
        this.j = c1653ay;
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1254Oy b(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        this.e = c0942Dy;
        return c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1520Xy c() {
        return (AbstractC1520Xy) AbstractC2909rH.a(new C1439Uy(), this.i);
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        return (AbstractC1200My) AbstractC2909rH.a(new C1119Jy(i, str, i2, enumC1364Sy), this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1254Oy a(int i) {
        return (AbstractC1254Oy) AbstractC2909rH.a(new C0942Dy(i), this.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1338Ry a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return (AbstractC1338Ry) AbstractC2909rH.a(new C1284Py(i, str), this.g);
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final AbstractC1804cy a() {
        C1653ay c1653ay = new C1653ay();
        this.j = c1653ay;
        return c1653ay;
    }

    @Override // com.android.tools.r8.internal.AbstractC3025sy
    public final InterfaceC2956ry a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        return (InterfaceC2956ry) AbstractC2939rh.a(this.k, c2576my);
    }

    public final void a(AbstractC3025sy abstractC3025sy) {
        AbstractC1804cy abstractC1804cyA;
        AbstractC1254Oy abstractC1254OyB;
        AbstractC0936Ds.c(abstractC3025sy, "visitor");
        for (C1119Jy c1119Jy : this.d) {
            AbstractC1200My abstractC1200MyA = abstractC3025sy.a(c1119Jy.b(), c1119Jy.d(), c1119Jy.c(), c1119Jy.f());
            if (abstractC1200MyA != null) {
                c1119Jy.a(abstractC1200MyA);
            }
        }
        C0942Dy c0942Dy = this.e;
        if (c0942Dy != null && (abstractC1254OyB = abstractC3025sy.b(c0942Dy.e())) != null) {
            c0942Dy.a(abstractC1254OyB);
        }
        for (C0942Dy c0942Dy2 : this.f) {
            AbstractC1254Oy abstractC1254OyA = abstractC3025sy.a(c0942Dy2.e());
            if (abstractC1254OyA != null) {
                c0942Dy2.a(abstractC1254OyA);
            }
        }
        for (C1284Py c1284Py : this.g) {
            AbstractC1338Ry abstractC1338RyA = abstractC3025sy.a(c1284Py.b(), c1284Py.c());
            if (abstractC1338RyA != null) {
                c1284Py.a(abstractC1338RyA);
            }
        }
        AbstractC1254Oy abstractC1254OyC = abstractC3025sy.c(i().e());
        if (abstractC1254OyC != null) {
            i().a(abstractC1254OyC);
        }
        for (C1439Uy c1439Uy : this.i) {
            AbstractC1520Xy abstractC1520XyC = abstractC3025sy.c();
            if (abstractC1520XyC != null) {
                c1439Uy.a(abstractC1520XyC);
            }
        }
        C1653ay c1653ay = this.j;
        if (c1653ay != null && (abstractC1804cyA = abstractC3025sy.a()) != null) {
            c1653ay.a(abstractC1804cyA);
        }
        for (InterfaceC2956ry interfaceC2956ry : this.k) {
            ((com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f) interfaceC2956ry).getClass();
            InterfaceC2956ry interfaceC2956ryA = abstractC3025sy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f.b);
            if (interfaceC2956ryA != null) {
                ((C2338ju) interfaceC2956ry).a(interfaceC2956ryA);
            }
        }
        abstractC3025sy.b();
    }
}
