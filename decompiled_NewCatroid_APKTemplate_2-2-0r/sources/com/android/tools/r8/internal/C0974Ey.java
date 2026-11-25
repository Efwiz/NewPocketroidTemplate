package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ey, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0974Ey extends AbstractC1029Gy {
    private int b;
    private String c;
    private final ArrayList d;
    public C0942Dy e;
    public C0942Dy f;
    private final ArrayList g;
    private final ArrayList h;
    private final ArrayList i;

    public C0974Ey(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        this.b = i;
        this.c = str;
        this.d = new ArrayList(0);
        this.g = new ArrayList(0);
        this.h = new ArrayList(0);
        InterfaceC3343xE.a.getClass();
        List listA = C3267wE.a();
        ArrayList arrayList = new ArrayList();
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            ((C3317wu) ((InterfaceC3343xE) it.next())).f();
        }
        this.i = arrayList;
    }

    public final void a(C0942Dy c0942Dy) {
        AbstractC0936Ds.c(c0942Dy, "<set-?>");
        this.f = c0942Dy;
    }

    public final void b(C0942Dy c0942Dy) {
        AbstractC0936Ds.c(c0942Dy, "<set-?>");
        this.e = c0942Dy;
    }

    public final List<C3321wx> c() {
        return this.g;
    }

    public final int d() {
        return this.b;
    }

    public final String e() {
        return this.c;
    }

    public final List<C1119Jy> f() {
        return this.d;
    }

    public final ArrayList g() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        return (AbstractC1200My) AbstractC2909rH.a(new C1119Jy(i, str, i2, enumC1364Sy), this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final AbstractC1254Oy b(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        b(c0942Dy);
        return c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final AbstractC1254Oy a(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        a(c0942Dy);
        return c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final AbstractC1520Xy b() {
        return (AbstractC1520Xy) AbstractC2909rH.a(new C1439Uy(), this.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final void a(C3321wx c3321wx) {
        AbstractC0936Ds.c(c3321wx, "annotation");
        this.g.add(c3321wx);
    }

    public final void a(AbstractC1029Gy abstractC1029Gy) {
        for (C1119Jy c1119Jy : this.d) {
            AbstractC1200My abstractC1200MyA = abstractC1029Gy.a(c1119Jy.b(), c1119Jy.d(), c1119Jy.c(), c1119Jy.f());
            if (abstractC1200MyA != null) {
                c1119Jy.a(abstractC1200MyA);
            }
        }
        C0942Dy c0942Dy = this.e;
        if (c0942Dy != null) {
            AbstractC1254Oy abstractC1254OyB = abstractC1029Gy.b(c0942Dy.e());
            if (abstractC1254OyB != null) {
                C0942Dy c0942Dy2 = this.e;
                if (c0942Dy2 != null) {
                    c0942Dy2.a(abstractC1254OyB);
                } else {
                    AbstractC0936Ds.a("underlyingType");
                    throw null;
                }
            }
            C0942Dy c0942Dy3 = this.f;
            if (c0942Dy3 != null) {
                AbstractC1254Oy abstractC1254OyA = abstractC1029Gy.a(c0942Dy3.e());
                if (abstractC1254OyA != null) {
                    C0942Dy c0942Dy4 = this.f;
                    if (c0942Dy4 != null) {
                        c0942Dy4.a(abstractC1254OyA);
                    } else {
                        AbstractC0936Ds.a("expandedType");
                        throw null;
                    }
                }
                Iterator it = this.g.iterator();
                while (it.hasNext()) {
                    abstractC1029Gy.a((C3321wx) it.next());
                }
                for (C1439Uy c1439Uy : this.h) {
                    AbstractC1520Xy abstractC1520XyB = abstractC1029Gy.b();
                    if (abstractC1520XyB != null) {
                        c1439Uy.a(abstractC1520XyB);
                    }
                }
                Iterator it2 = this.i.iterator();
                if (!it2.hasNext()) {
                    abstractC1029Gy.a();
                    return;
                } else {
                    AbstractC1288Qc.a(it2.next());
                    throw null;
                }
            }
            AbstractC0936Ds.a("expandedType");
            throw null;
        }
        AbstractC0936Ds.a("underlyingType");
        throw null;
    }
}
