package com.android.tools.r8.internal;

import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3598b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1493Wx extends AbstractC1572Zx {
    private int b;
    private final ArrayList c = new ArrayList();
    private final ArrayList d = new ArrayList(0);
    private final ArrayList e;

    public C1493Wx(int i) {
        this.b = i;
        InterfaceC3343xE.a.getClass();
        List listA = C3267wE.a();
        ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listA));
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            arrayList.add(((C3317wu) ((InterfaceC3343xE) it.next())).b());
        }
        this.e = arrayList;
    }

    @Override // com.android.tools.r8.internal.AbstractC1572Zx
    public final AbstractC1338Ry a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return (AbstractC1338Ry) AbstractC2909rH.a(new C1284Py(i, str), this.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1572Zx
    public final AbstractC1520Xy b() {
        return (AbstractC1520Xy) AbstractC2909rH.a(new C1439Uy(), this.d);
    }

    public final int c() {
        return this.b;
    }

    public final List<C1284Py> d() {
        return this.c;
    }

    public final ArrayList e() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC1572Zx
    public final InterfaceC1546Yx a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        return (InterfaceC1546Yx) AbstractC2939rh.a(this.e, c2576my);
    }

    public final void a(AbstractC1572Zx abstractC1572Zx) {
        for (C1284Py c1284Py : this.c) {
            AbstractC1338Ry abstractC1338RyA = abstractC1572Zx.a(c1284Py.b(), c1284Py.c());
            if (abstractC1338RyA != null) {
                c1284Py.a(abstractC1338RyA);
            }
        }
        for (C1439Uy c1439Uy : this.d) {
            AbstractC1520Xy abstractC1520XyB = abstractC1572Zx.b();
            if (abstractC1520XyB != null) {
                c1439Uy.a(abstractC1520XyB);
            }
        }
        for (InterfaceC2653ny interfaceC2653ny : this.e) {
            ((AbstractC3598b) interfaceC2653ny).getClass();
            InterfaceC1546Yx interfaceC1546YxA = abstractC1572Zx.a(AbstractC3598b.a);
            if (interfaceC1546YxA != null) {
                ((C2186hu) interfaceC2653ny).a(interfaceC1546YxA);
            }
        }
        abstractC1572Zx.a();
    }
}
