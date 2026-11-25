package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1561Zm extends AbstractC1453Vm {
    static final /* synthetic */ boolean h = true;
    private final com.android.tools.r8.graph.Y0 d;
    private final InterfaceC2415kr e;
    private final C0684j2 f;
    private final C3103u20 g;

    public C1561Zm(com.android.tools.r8.graph.Y0 y0, C2807pq c2807pq, C0684j2 c0684j2, C3103u20 c3103u20) {
        this.e = c2807pq;
        this.d = y0;
        this.g = c3103u20;
        this.f = c0684j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1453Vm
    public final C0753u a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C1013Gk c1013Gk) {
        C0684j2 c0684j2B;
        int iA = com.android.tools.r8.utils.I1.a(o5Var.D(), new Function() { // from class: com.android.tools.r8.internal.Zm$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((C0767v2) obj).J0());
            }
        });
        int i = iA + 1;
        int i2 = iA + 2;
        ArrayList arrayList = new ArrayList();
        InterfaceC3018sr it = this.e.keySet().iterator();
        int iJ0 = 1;
        int size = this.e.size() - com.android.tools.r8.utils.M.a(this.g == null);
        int[] iArr = new int[size];
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < size; i3++) {
            iArr[i3] = it.t();
            arrayList2.add(new M7());
        }
        M7 m7 = new M7();
        arrayList.add(new O7(EnumC1812d20.OBJECT, 0));
        for (C0767v2 c0767v2 : o5Var.D()) {
            arrayList.add(new O7(EnumC1812d20.a(c0767v2), iJ0));
            iJ0 += c0767v2.J0();
        }
        arrayList.add(new O7(EnumC1812d20.OBJECT, 0));
        com.android.tools.r8.graph.Y0 y0 = this.d;
        arrayList.add(new C3413y7(y0, y0));
        arrayList.add(new U8(U8.a.LOOKUP, m7, iArr, arrayList2));
        for (int i4 = 0; i4 < size; i4++) {
            C0684j2 c0684j2H = c1013Gk.h((C0684j2) this.e.get(iArr[i4]));
            arrayList.add((B7) arrayList2.get(i4));
            arrayList.add(a(o5Var, i));
            arrayList.add(new I7(183, c0684j2H, o5Var.a().isInterface()));
            if (o5Var.G().Y0()) {
                arrayList.add(new C3186v8());
            } else {
                arrayList.add(new C3109u8(EnumC1812d20.a(o5Var.G())));
            }
        }
        arrayList.add(m7);
        arrayList.add(a(o5Var, i));
        C3103u20 c3103u20 = this.g;
        if (c3103u20 == null) {
            InterfaceC2415kr interfaceC2415kr = this.e;
            c0684j2B = c1013Gk.h((C0684j2) interfaceC2415kr.get(interfaceC2415kr.d()));
        } else {
            C0684j2 c0684j2 = c3103u20.b;
            c1013Gk.getClass();
            c0684j2B = ((C0684j2) c1013Gk.a(c0684j2, o5Var.getReference(), EnumC1953et.f, (AbstractC1476Wj) null).b()).b(c1013Gk.b(AbstractC1476Wj.e(), this.g.a.B0()), c0702m.a());
        }
        arrayList.add(new I7(183, c0684j2B, o5Var.a().isInterface()));
        if (o5Var.G().Y0()) {
            arrayList.add(new C3186v8());
        } else {
            arrayList.add(new C3109u8(EnumC1812d20.a(o5Var.G())));
        }
        return new C1535Ym(this.f.B0(), i2, i, arrayList, c1013Gk);
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return "IncompleteVirtuallyMergedMethodCode";
    }

    private static C2284j7 a(com.android.tools.r8.graph.o5 o5Var, int i) {
        C2284j7.a aVarA = C2284j7.T().a(o5Var.e().h1());
        for (int i2 = 0; i2 < o5Var.e().h1(); i2++) {
            aVarA.a((InterfaceC3054tM) InterfaceC1348Si.a(o5Var.a(i2)));
        }
        for (int i3 = 0; i3 < o5Var.e().h1(); i3++) {
            aVarA.a((InterfaceC1348Si) InterfaceC1348Si.a(o5Var.a(i3)));
        }
        C2284j7 c2284j7A = aVarA.a();
        if (h || c2284j7A.W().size() == i) {
            return c2284j7A;
        }
        throw new AssertionError();
    }
}
