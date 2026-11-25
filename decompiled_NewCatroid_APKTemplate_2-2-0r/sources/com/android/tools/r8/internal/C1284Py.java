package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Py, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1284Py extends AbstractC1338Ry {
    private int b;
    private String c;
    public C0942Dy d;
    private C0942Dy e;
    private final ArrayList f;

    public C1284Py(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        this.b = i;
        this.c = str;
        InterfaceC3343xE.a.getClass();
        List listA = C3267wE.a();
        ArrayList arrayList = new ArrayList();
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            ((C3317wu) ((InterfaceC3343xE) it.next())).i();
        }
        this.f = arrayList;
    }

    public final void a(C0942Dy c0942Dy) {
        AbstractC0936Ds.c(c0942Dy, "<set-?>");
        this.d = c0942Dy;
    }

    public final int b() {
        return this.b;
    }

    public final String c() {
        return this.c;
    }

    public final C0942Dy d() {
        C0942Dy c0942Dy = this.d;
        if (c0942Dy != null) {
            return c0942Dy;
        }
        AbstractC0936Ds.a(ModuleXmlParser.TYPE);
        throw null;
    }

    public final C0942Dy e() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC1338Ry
    public final AbstractC1254Oy a(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        a(c0942Dy);
        return c0942Dy;
    }

    public final void b(C0942Dy c0942Dy) {
        this.e = c0942Dy;
    }

    public final void a(AbstractC1338Ry abstractC1338Ry) {
        AbstractC1254Oy abstractC1254OyB;
        AbstractC1254Oy abstractC1254OyA = abstractC1338Ry.a(d().e());
        if (abstractC1254OyA != null) {
            d().a(abstractC1254OyA);
        }
        C0942Dy c0942Dy = this.e;
        if (c0942Dy != null && (abstractC1254OyB = abstractC1338Ry.b(c0942Dy.e())) != null) {
            c0942Dy.a(abstractC1254OyB);
        }
        Iterator it = this.f.iterator();
        if (!it.hasNext()) {
            abstractC1338Ry.a();
        } else {
            AbstractC1288Qc.a(it.next());
            throw null;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1338Ry
    public final AbstractC1254Oy b(int i) {
        C0942Dy c0942Dy = new C0942Dy(i);
        this.e = c0942Dy;
        return c0942Dy;
    }
}
