package com.android.tools.r8.internal;

import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3597a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Px, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1283Px extends AbstractC1363Sx implements InterfaceC1877dy {
    public String c;
    private String j;
    private String n;
    private C0942Dy o;
    private final ArrayList r;
    private int b = AbstractC3452yi.a(new C3079ti[0]);
    private final ArrayList d = new ArrayList(0);
    private final ArrayList e = new ArrayList(1);
    private final ArrayList f = new ArrayList();
    private final ArrayList g = new ArrayList();
    private final ArrayList h = new ArrayList(0);
    private final ArrayList i = new ArrayList(1);
    private final ArrayList k = new ArrayList(0);
    private final ArrayList l = new ArrayList(0);
    private final ArrayList m = new ArrayList(0);
    private final ArrayList p = new ArrayList(0);
    private final ArrayList q = new ArrayList(0);

    public C1283Px() {
        InterfaceC3343xE.a.getClass();
        List listA = C3267wE.a();
        ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listA));
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            arrayList.add(((C3317wu) ((InterfaceC3343xE) it.next())).a());
        }
        this.r = arrayList;
    }

    public final void a(int i) {
        this.b = i;
    }

    public final void b(String str) {
        AbstractC0936Ds.c(str, "<set-?>");
        this.c = str;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1877dy
    public final List c() {
        return this.f;
    }

    public final void d(String str) {
        AbstractC0936Ds.c(str, "name");
        this.l.add(str);
    }

    public final AbstractC1254Oy e(int i) {
        return (AbstractC1254Oy) AbstractC2909rH.a(new C0942Dy(i), this.e);
    }

    public final String f() {
        return this.j;
    }

    public final List<C1493Wx> g() {
        return this.i;
    }

    public final ArrayList h() {
        return this.p;
    }

    public final List<String> i() {
        return this.l;
    }

    public final int j() {
        return this.b;
    }

    public final String k() {
        return this.n;
    }

    public final C0942Dy l() {
        return this.o;
    }

    public final String m() {
        String str = this.c;
        if (str != null) {
            return str;
        }
        AbstractC0936Ds.a("name");
        throw null;
    }

    public final List<String> n() {
        return this.k;
    }

    public final List<String> o() {
        return this.m;
    }

    public final List<C0942Dy> p() {
        return this.e;
    }

    public final List<C1119Jy> q() {
        return this.d;
    }

    public final ArrayList r() {
        return this.q;
    }

    public final AbstractC1520Xy s() {
        return (AbstractC1520Xy) AbstractC2909rH.a(new C1439Uy(), this.q);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1877dy
    public final List a() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1877dy
    public final List b() {
        return this.h;
    }

    public final void c(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        this.b = i;
        b(str);
    }

    public final AbstractC1254Oy d(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        this.o = c0942Dy;
        return c0942Dy;
    }

    public final void e(String str) {
        AbstractC0936Ds.c(str, "name");
        this.n = str;
    }

    public final void f(String str) {
        AbstractC0936Ds.c(str, "name");
        this.k.add(str);
    }

    public final void g(String str) {
        AbstractC0936Ds.c(str, "name");
        this.m.add(str);
    }

    public final void a(String str) {
        this.n = str;
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC1029Gy b(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return (AbstractC1029Gy) AbstractC2909rH.a(new C0974Ey(i, str), this.h);
    }

    public final void a(C0942Dy c0942Dy) {
        this.o = c0942Dy;
    }

    public final void c(String str) {
        AbstractC0936Ds.c(str, "name");
        this.j = str;
    }

    public final AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        return (AbstractC1200My) AbstractC2909rH.a(new C1119Jy(i, str, i2, enumC1364Sy), this.d);
    }

    public final AbstractC1254Oy c(int i) {
        return (AbstractC1254Oy) AbstractC2909rH.a(new C0942Dy(i), this.p);
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC3025sy a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return (AbstractC3025sy) AbstractC2909rH.a(new C2815py(i, str), this.f);
    }

    public final AbstractC1572Zx b(int i) {
        return (AbstractC1572Zx) AbstractC2909rH.a(new C1493Wx(i), this.i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC0914Cy a(int i, int i2, int i3, String str) {
        AbstractC0936Ds.c(str, "name");
        return (AbstractC0914Cy) AbstractC2909rH.a(new C3539zy(i, i2, i3, str), this.g);
    }

    @Override // com.android.tools.r8.internal.AbstractC1363Sx
    public final InterfaceC1337Rx a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        return (InterfaceC1337Rx) AbstractC2939rh.a(this.r, c2576my);
    }

    public final void a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.p pVar) {
        pVar.c(this.b, m());
        for (C1119Jy c1119Jy : this.d) {
            c1119Jy.a(pVar.a(c1119Jy.b(), c1119Jy.d(), c1119Jy.c(), c1119Jy.f()));
        }
        for (C0942Dy c0942Dy : this.e) {
            c0942Dy.a(pVar.d(c0942Dy.e()));
        }
        for (C2815py c2815py : this.f) {
            c2815py.a(pVar.a(c2815py.f(), c2815py.g()));
        }
        for (C3539zy c3539zy : this.g) {
            c3539zy.a(pVar.a(c3539zy.d(), c3539zy.e(), c3539zy.i(), c3539zy.f()));
        }
        for (C0974Ey c0974Ey : this.h) {
            c0974Ey.a(pVar.b(c0974Ey.d(), c0974Ey.e()));
        }
        for (C1493Wx c1493Wx : this.i) {
            c1493Wx.a(pVar.a(c1493Wx.c()));
        }
        String str = this.j;
        if (str != null) {
            pVar.a(str);
        }
        Iterator it = this.k.iterator();
        while (it.hasNext()) {
            pVar.d((String) it.next());
        }
        Iterator it2 = this.l.iterator();
        while (it2.hasNext()) {
            pVar.b((String) it2.next());
        }
        Iterator it3 = this.m.iterator();
        while (it3.hasNext()) {
            pVar.e((String) it3.next());
        }
        String str2 = this.n;
        if (str2 != null) {
            pVar.c(str2);
        }
        C0942Dy c0942Dy2 = this.o;
        if (c0942Dy2 != null) {
            c0942Dy2.a(pVar.c(c0942Dy2.e()));
        }
        for (C0942Dy c0942Dy3 : this.p) {
            c0942Dy3.a(pVar.b(c0942Dy3.e()));
        }
        Iterator it4 = this.q.iterator();
        while (it4.hasNext()) {
            ((C1439Uy) it4.next()).a(pVar.h());
        }
        for (InterfaceC1337Rx interfaceC1337Rx : this.r) {
            ((AbstractC3597a) interfaceC1337Rx).getClass();
            InterfaceC1337Rx interfaceC1337RxA = pVar.a(AbstractC3597a.b);
            if (interfaceC1337RxA != null) {
                ((C2031fu) interfaceC1337Rx).a(interfaceC1337RxA);
            }
        }
        pVar.e();
    }
}
