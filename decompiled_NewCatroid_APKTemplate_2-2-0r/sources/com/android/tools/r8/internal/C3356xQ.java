package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3356xQ extends QY {
    private final C0684j2 c;

    public C3356xQ(C0702m c0702m, C0684j2 c0684j2, C0767v2 c0767v2) {
        super(c0702m, c0767v2);
        this.c = c0684j2;
    }

    public final C0753u b() {
        C0722p1 c0722p1A = this.a.a();
        ArrayList arrayList = new ArrayList();
        M7 m7 = new M7();
        EnumC1812d20 enumC1812d20A = EnumC1812d20.a(a());
        EnumC1812d20 enumC1812d20A2 = EnumC1812d20.a(c0722p1A.Z1);
        arrayList.add(new O7(enumC1812d20A, 0));
        arrayList.add(new I7(182, c0722p1A.g4.e, false));
        arrayList.add(new O7(enumC1812d20A2, 1));
        arrayList.add(new I7(182, c0722p1A.g4.e, false));
        arrayList.add(new C3185v7(EnumC1076Il.EQ, EnumC1812d20.OBJECT, m7));
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        arrayList.add(new C1818d7(0L, enumC1812d20));
        arrayList.add(new C3109u8(enumC1812d20));
        arrayList.add(m7);
        arrayList.add(C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.a(a())).a((InterfaceC1348Si) InterfaceC1348Si.a(this.a.a().Z1)).a());
        arrayList.add(new O7(enumC1812d20A, 0));
        arrayList.add(new I7(183, this.c, false));
        arrayList.add(new O7(enumC1812d20A2, 1));
        arrayList.add(new M6(a(), true));
        arrayList.add(new I7(183, this.c, false));
        arrayList.add(new I7(184, c0722p1A.J4.b, false));
        arrayList.add(new C3109u8(enumC1812d20));
        return a(arrayList);
    }
}
