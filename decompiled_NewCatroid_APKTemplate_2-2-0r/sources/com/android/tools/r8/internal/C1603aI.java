package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1603aI extends AbstractC1683bI {
    static final /* synthetic */ boolean f = true;
    private final com.android.tools.r8.graph.Y0 c;
    private final com.android.tools.r8.graph.Y0 d;
    private final List e;

    public C1603aI(C0702m c0702m, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1, List list) {
        super(c0702m, y1.e);
        this.c = y0;
        this.d = y1;
        this.e = list;
    }

    public final C0753u b() {
        C0722p1 c0722p1A = this.a.a();
        ArrayList arrayList = new ArrayList();
        C0767v2 c0767v2 = this.d.h;
        C2284j7 c2284j7A = C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.a(c0767v2)).a();
        AbstractC1683bI.b(arrayList);
        arrayList.add(c2284j7A);
        if (!f && this.c == null) {
            throw new AssertionError();
        }
        M7 m7 = new M7();
        arrayList.add(new O7(EnumC1812d20.a(c0767v2), 0));
        arrayList.add(new A7(this.c.e));
        arrayList.add(new C3039t7(EnumC1076Il.EQ, EnumC1812d20.INT, m7));
        arrayList.add(new O7(EnumC1812d20.a((char) c0767v2.e.e[0]), 0));
        arrayList.add(new M6(this.c.e, false));
        com.android.tools.r8.graph.Y0 y0 = this.c;
        arrayList.add(new C3413y7(y0, y0));
        arrayList.add(new C3109u8(EnumC1812d20.a(this.c.h)));
        arrayList.add(m7);
        arrayList.add(c2284j7A.clone());
        for (C0684j2 c0684j2 : this.e) {
            M7 m8 = new M7();
            C0767v2 c0767v2A = c0684j2.a(0, true);
            arrayList.add(new O7(EnumC1812d20.a((char) c0767v2.e.e[0]), 0));
            arrayList.add(new A7(c0767v2A));
            arrayList.add(new C3039t7(EnumC1076Il.EQ, EnumC1812d20.INT, m8));
            arrayList.add(new O7(EnumC1812d20.a((char) c0767v2.e.e[0]), 0));
            arrayList.add(new M6(c0767v2A, false));
            arrayList.add(new I7(184, c0684j2, false));
            arrayList.add(new C3109u8(EnumC1812d20.a(this.c.h)));
            arrayList.add(m8);
            arrayList.add(c2284j7A.clone());
        }
        arrayList.add(new V7(null, this.d.e));
        arrayList.add(D8.a(89));
        arrayList.add(new O7(EnumC1812d20.a((char) c0767v2.e.e[0]), 0));
        arrayList.add(new I7(183, c0722p1A.a(this.d.e, c0722p1A.b(c0722p1A.D1, c0767v2), c0722p1A.Y0), false));
        arrayList.add(new C3109u8(EnumC1812d20.a(this.d.e)));
        return a(arrayList);
    }
}
