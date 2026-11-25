package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0942Dy extends AbstractC1254Oy {
    private int b;
    public AbstractC1438Ux c;
    private final ArrayList d;
    private C0942Dy e;
    private C0942Dy f;
    private C2742oy g;
    private final ArrayList h;

    public C0942Dy(int i) {
        super(0);
        this.b = i;
        this.d = new ArrayList(0);
        InterfaceC3343xE.a.getClass();
        List listA = C3267wE.a();
        ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listA));
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            arrayList.add(((C3317wu) ((InterfaceC3343xE) it.next())).g());
        }
        this.h = arrayList;
    }

    public final void a(AbstractC1254Oy abstractC1254Oy) {
        AbstractC1254Oy abstractC1254OyA;
        AbstractC1254Oy abstractC1254OyB;
        AbstractC1254Oy abstractC1254OyA2;
        AbstractC0936Ds.c(abstractC1254Oy, "visitor");
        AbstractC1438Ux abstractC1438Ux = this.c;
        if (abstractC1438Ux == null) {
            AbstractC0936Ds.a("classifier");
            throw null;
        }
        if (abstractC1438Ux instanceof C1391Tx) {
            abstractC1254Oy.a(((C1391Tx) abstractC1438Ux).a());
        } else if (abstractC1438Ux instanceof AbstractC1438Ux.b) {
            abstractC1254Oy.c(((AbstractC1438Ux.b) abstractC1438Ux).a());
        } else if (abstractC1438Ux instanceof AbstractC1438Ux.a) {
            abstractC1254Oy.b(((AbstractC1438Ux.a) abstractC1438Ux).a());
        }
        for (C1227Ny c1227Ny : this.d) {
            if (AbstractC0936Ds.a(c1227Ny, C1227Ny.c)) {
                abstractC1254Oy.b();
            } else {
                EnumC1364Sy enumC1364SyA = c1227Ny.a();
                C0942Dy c0942DyB = c1227Ny.b();
                if (enumC1364SyA == null || c0942DyB == null) {
                    throw new C1640an("Variance and type must be set for non-star type projection", null);
                }
                AbstractC1254Oy abstractC1254OyA3 = abstractC1254Oy.a(c0942DyB.b, enumC1364SyA);
                if (abstractC1254OyA3 != null) {
                    c0942DyB.a(abstractC1254OyA3);
                }
            }
        }
        C0942Dy c0942Dy = this.e;
        if (c0942Dy != null && (abstractC1254OyA2 = abstractC1254Oy.a(c0942Dy.b)) != null) {
            c0942Dy.a(abstractC1254OyA2);
        }
        C0942Dy c0942Dy2 = this.f;
        if (c0942Dy2 != null && (abstractC1254OyB = abstractC1254Oy.b(c0942Dy2.b)) != null) {
            c0942Dy2.a(abstractC1254OyB);
        }
        C2742oy c2742oy = this.g;
        if (c2742oy != null && (abstractC1254OyA = abstractC1254Oy.a(c2742oy.a().b, c2742oy.b())) != null) {
            c2742oy.a().a(abstractC1254OyA);
        }
        for (InterfaceC1090Iy interfaceC1090Iy : this.h) {
            ((com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m) interfaceC1090Iy).getClass();
            InterfaceC1090Iy interfaceC1090IyA = abstractC1254Oy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m.b);
            if (interfaceC1090IyA != null) {
                ((C1723bv) interfaceC1090Iy).a(interfaceC1090IyA);
            }
        }
        abstractC1254Oy.a();
    }

    public final void b(C0942Dy c0942Dy) {
        this.f = c0942Dy;
    }

    public final C0942Dy c() {
        return this.e;
    }

    public final List<C1227Ny> d() {
        return this.d;
    }

    public final int e() {
        return this.b;
    }

    public final C2742oy f() {
        return this.g;
    }

    public final C0942Dy g() {
        return this.f;
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final void b(String str) {
        AbstractC0936Ds.c(str, "name");
        this.c = new AbstractC1438Ux.a(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final void c(int i) {
        this.c = new AbstractC1438Ux.b(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final void b() {
        this.d.add(C1227Ny.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final AbstractC1254Oy b(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        this.f = c0942Dy;
        return c0942Dy;
    }

    public final void a(C0942Dy c0942Dy) {
        this.e = c0942Dy;
    }

    public final void a(C2742oy c2742oy) {
        this.g = c2742oy;
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final void a(String str) {
        AbstractC0936Ds.c(str, "name");
        this.c = new C1391Tx(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final AbstractC1254Oy a(int i, EnumC1364Sy enumC1364Sy) {
        C0942Dy c0942Dy = new C0942Dy(i);
        this.d.add(new C1227Ny(enumC1364Sy, c0942Dy));
        return c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final AbstractC1254Oy a(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        this.e = c0942Dy;
        return c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final AbstractC1254Oy a(int i, String str) {
        C0942Dy c0942Dy = new C0942Dy(i);
        this.g = new C2742oy(c0942Dy, str);
        return c0942Dy;
    }

    @Override // com.android.tools.r8.internal.AbstractC1254Oy
    public final InterfaceC1090Iy a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        return (InterfaceC1090Iy) AbstractC2939rh.a(this.h, c2576my);
    }
}
