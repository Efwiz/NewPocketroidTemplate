package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1119Jy extends AbstractC1200My {
    private int b;
    private String c;
    private int d;
    private EnumC1364Sy e;
    private final ArrayList f;
    private final ArrayList g;

    public C1119Jy(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        this.b = i;
        this.c = str;
        this.d = i2;
        this.e = enumC1364Sy;
        this.f = new ArrayList(1);
        InterfaceC3343xE.a.getClass();
        List listA = C3267wE.a();
        ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listA));
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            arrayList.add(((C3317wu) ((InterfaceC3343xE) it.next())).h());
        }
        this.g = arrayList;
    }

    @Override // com.android.tools.r8.internal.AbstractC1200My
    public final AbstractC1254Oy a(int i) {
        return (AbstractC1254Oy) AbstractC2909rH.a(new C0942Dy(i), this.f);
    }

    public final int b() {
        return this.b;
    }

    public final int c() {
        return this.d;
    }

    public final String d() {
        return this.c;
    }

    public final List<C0942Dy> e() {
        return this.f;
    }

    public final EnumC1364Sy f() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC1200My
    public final InterfaceC1173Ly a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        return (InterfaceC1173Ly) AbstractC2939rh.a(this.g, c2576my);
    }

    public final void a(AbstractC1200My abstractC1200My) {
        for (C0942Dy c0942Dy : this.f) {
            AbstractC1254Oy abstractC1254OyA = abstractC1200My.a(c0942Dy.e());
            if (abstractC1254OyA != null) {
                c0942Dy.a(abstractC1254OyA);
            }
        }
        for (InterfaceC2653ny interfaceC2653ny : this.g) {
            ((com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n) interfaceC2653ny).getClass();
            InterfaceC1173Ly interfaceC1173LyA = abstractC1200My.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n.b);
            if (interfaceC1173LyA != null) {
                ((C1800cv) interfaceC2653ny).a(interfaceC1173LyA);
            }
        }
        abstractC1200My.a();
    }
}
