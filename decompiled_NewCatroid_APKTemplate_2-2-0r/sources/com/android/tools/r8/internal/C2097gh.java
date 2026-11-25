package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2097gh extends QY {
    private final com.android.tools.r8.graph.Y0 c;

    public C2097gh(C0702m c0702m, com.android.tools.r8.graph.Y0 y0, C0767v2 c0767v2) {
        super(c0702m, c0767v2);
        this.c = y0;
    }

    public final C0753u b() {
        C0767v2 c0767v2B0 = this.c.B0();
        InterfaceC1348Si[] interfaceC1348SiArr = {InterfaceC1348Si.a(c0767v2B0), InterfaceC1348Si.a(this.a.a().Z1)};
        M7 m7 = new M7();
        M7 m8 = new M7();
        ArrayList arrayList = new ArrayList();
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        arrayList.add(new O7(enumC1812d20, 0));
        com.android.tools.r8.graph.Y0 y0 = this.c;
        arrayList.add(new C3413y7(y0, y0));
        arrayList.add(new O7(enumC1812d20, 1));
        arrayList.add(new A7(c0767v2B0));
        EnumC1076Il enumC1076Il = EnumC1076Il.EQ;
        EnumC1812d20 enumC1812d21 = EnumC1812d20.INT;
        arrayList.add(new C3039t7(enumC1076Il, enumC1812d21, m7));
        arrayList.add(new O7(enumC1812d20, 1));
        arrayList.add(new M6(c0767v2B0, false));
        com.android.tools.r8.graph.Y0 y1 = this.c;
        arrayList.add(new C3413y7(y1, y1));
        arrayList.add(new C2899r7(m8));
        arrayList.add(m7);
        arrayList.add(new C2284j7(new C2413kp(new int[]{0, 1}, interfaceC1348SiArr, 0), new ArrayDeque(Arrays.asList(InterfaceC1348Si.a(this.c.h)))));
        arrayList.add(new O7(enumC1812d20, 1));
        arrayList.add(m8);
        arrayList.add(new C2284j7(new C2413kp(new int[]{0, 1}, interfaceC1348SiArr, 0), new ArrayDeque(Arrays.asList(InterfaceC1348Si.a(this.c.h), InterfaceC1348Si.a(this.a.a().Z1)))));
        arrayList.add(new I7(182, this.a.a().g4.d, false));
        arrayList.add(new C3109u8(enumC1812d21));
        return a(arrayList);
    }
}
