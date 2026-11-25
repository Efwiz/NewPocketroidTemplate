package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3247vy extends AbstractC3468yy implements InterfaceC1877dy {
    private final ArrayList b = new ArrayList();
    private final ArrayList c = new ArrayList();
    private final ArrayList d = new ArrayList(0);
    private final ArrayList e;

    public C3247vy() {
        InterfaceC3343xE.a.getClass();
        List listA = C3267wE.a();
        ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listA));
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            arrayList.add(((C3317wu) ((InterfaceC3343xE) it.next())).d());
        }
        this.e = arrayList;
    }

    public final void a(AbstractC2696oL abstractC2696oL) {
        for (C2815py c2815py : this.b) {
            c2815py.a(abstractC2696oL.a(c2815py.f(), c2815py.g()));
        }
        for (C3539zy c3539zy : this.c) {
            c3539zy.a(abstractC2696oL.a(c3539zy.d(), c3539zy.e(), c3539zy.i(), c3539zy.f()));
        }
        for (C0974Ey c0974Ey : this.d) {
            c0974Ey.a(abstractC2696oL.b(c0974Ey.d(), c0974Ey.e()));
        }
        for (InterfaceC3399xy interfaceC3399xy : this.e) {
            ((com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k) interfaceC3399xy).getClass();
            InterfaceC3399xy interfaceC3399xyA = abstractC2696oL.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k.b);
            if (interfaceC3399xyA != null) {
                ((C1025Gu) interfaceC3399xy).a(interfaceC3399xyA);
            }
        }
        abstractC2696oL.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1877dy
    public final List b() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1877dy
    public final List c() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC1029Gy b(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return (AbstractC1029Gy) AbstractC2909rH.a(new C0974Ey(i, str), this.d);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1877dy
    public final List a() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC3025sy a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return (AbstractC3025sy) AbstractC2909rH.a(new C2815py(i, str), this.b);
    }

    @Override // com.android.tools.r8.internal.AbstractC1960ey
    public final AbstractC0914Cy a(int i, int i2, int i3, String str) {
        AbstractC0936Ds.c(str, "name");
        return (AbstractC0914Cy) AbstractC2909rH.a(new C3539zy(i, i2, i3, str), this.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC3468yy
    public final InterfaceC3399xy a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        return (InterfaceC3399xy) AbstractC2939rh.a(this.e, c2576my);
    }
}
