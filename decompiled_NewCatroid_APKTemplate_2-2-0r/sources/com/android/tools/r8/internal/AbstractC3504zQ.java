package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.position.Position;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayDeque;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3504zQ {
    public static C0753u a(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        C1818d7 c1818d7 = new C1818d7(31L, enumC1812d20);
        EnumC1812d20 enumC1812d21 = EnumC1812d20.OBJECT;
        O7 o7 = new O7(enumC1812d21, 1);
        I7 i7 = new I7(184, c0722p1.a(c0722p1.e("Ljava/util/Arrays;"), c0722p1.b(c0722p1.A1, c0722p1.e("[Ljava/lang/Object;")), c0722p1.c("hashCode")), false);
        C3481z6.a aVar = C3481z6.a.Mul;
        EnumC2527mI enumC2527mI = EnumC2527mI.INT;
        return new C0753u(c0767v2, 2, 2, AbstractC1639am.a(m7, c1818d7, o7, i7, new C3481z6(aVar, enumC2527mI), new O7(enumC1812d21, 0), new I7(182, c0722p1.a(c0722p1.Z1, c0722p1.b(c0722p1.A1, new C0767v2[0]), c0722p1.c("hashCode")), false), new C3481z6(C3481z6.a.Add, enumC2527mI), new C3109u8(enumC1812d20), m8), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    public static C0753u b(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        M7 m11 = new M7();
        M7 m12 = new M7();
        M7 m13 = new M7();
        M7 m14 = new M7();
        M7 m15 = new M7();
        M7 m16 = new M7();
        M7 m17 = new M7();
        M7 m18 = new M7();
        M7 m19 = new M7();
        M7 m20 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        O7 o7 = new O7(enumC1812d20, 2);
        I7 i7 = new I7(182, c0722p1.a(c0722p1.X1, c0722p1.b(c0722p1.A1, new C0767v2[0]), c0722p1.c("length")), false);
        EnumC1076Il enumC1076Il = EnumC1076Il.NE;
        EnumC1812d20 enumC1812d21 = EnumC1812d20.INT;
        C3039t7 c3039t7 = new C3039t7(enumC1076Il, enumC1812d21, m8);
        C1818d7 c1818d7 = new C1818d7(0L, enumC1812d21);
        W7 w7 = new W7(c0722p1.e("[Ljava/lang/String;"));
        C2899r7 c2899r7 = new C2899r7(m9);
        C2284j7 c2284j7 = new C2284j7(new C2413kp(new int[]{0, 1, 2}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("[Ljava/lang/Object;")), InterfaceC1348Si.b(c0722p1.m2), InterfaceC1348Si.b(c0722p1.X1)}, 0));
        O7 o8 = new O7(enumC1812d20, 2);
        C1891e7 c1891e7 = new C1891e7(c0722p1.c(";"));
        I7 i8 = new I7(182, c0722p1.a(c0722p1.X1, c0722p1.b(c0722p1.e("[Ljava/lang/String;"), c0722p1.X1), c0722p1.c("split")), false);
        C2413kp c2413kp = new C2413kp(new int[]{0, 1, 2}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("[Ljava/lang/Object;")), InterfaceC1348Si.b(c0722p1.m2), InterfaceC1348Si.b(c0722p1.X1)}, 0);
        C2945rn[] c2945rnArr = {InterfaceC1348Si.b(c0722p1.e("[Ljava/lang/String;"))};
        C0767v2 c0767v3 = c0722p1.v2;
        C0767v2[] c0767v2Arr = {c0722p1.X1};
        C0767v2 c0767v4 = c0722p1.v2;
        C0767v2[] c0767v2Arr2 = {c0722p1.X1};
        D8.a aVar = D8.a.Pop;
        InterfaceC1348Si[] interfaceC1348SiArr = {InterfaceC1348Si.b(c0722p1.e("[Ljava/lang/Object;")), InterfaceC1348Si.b(c0722p1.m2), InterfaceC1348Si.b(c0722p1.X1), InterfaceC1348Si.b(c0722p1.e("[Ljava/lang/String;")), InterfaceC1348Si.b(c0722p1.v2), InterfaceC1348Si.h()};
        EnumC2523mE enumC2523mE = EnumC2523mE.OBJECT;
        C0767v2 c0767v5 = c0722p1.v2;
        C0767v2[] c0767v2Arr3 = {c0722p1.X1};
        C0767v2 c0767v6 = c0722p1.v2;
        C0767v2[] c0767v2Arr4 = {c0722p1.X1};
        C0767v2 c0767v7 = c0722p1.v2;
        C0767v2[] c0767v2Arr5 = {c0722p1.Z1};
        C0767v2 c0767v8 = c0722p1.v2;
        C0767v2[] c0767v2Arr6 = {c0722p1.X1};
        InterfaceC1348Si[] interfaceC1348SiArr2 = {InterfaceC1348Si.b(c0722p1.e("[Ljava/lang/Object;")), InterfaceC1348Si.b(c0722p1.m2), InterfaceC1348Si.b(c0722p1.X1), InterfaceC1348Si.b(c0722p1.e("[Ljava/lang/String;")), InterfaceC1348Si.b(c0722p1.v2), InterfaceC1348Si.h()};
        InterfaceC1348Si[] interfaceC1348SiArr3 = {InterfaceC1348Si.b(c0722p1.e("[Ljava/lang/Object;")), InterfaceC1348Si.b(c0722p1.m2), InterfaceC1348Si.b(c0722p1.X1), InterfaceC1348Si.b(c0722p1.e("[Ljava/lang/String;")), InterfaceC1348Si.b(c0722p1.v2)};
        C0767v2 c0767v9 = c0722p1.v2;
        return new C0753u(c0767v2, 3, 6, AbstractC1639am.a(m7, o7, i7, c3039t7, c1818d7, w7, c2899r7, m8, c2284j7, o8, c1891e7, i8, m9, new C2284j7(c2413kp, new ArrayDeque(Arrays.asList(c2945rnArr))), new R8(enumC1812d20, 3), m10, new V7(null, c0722p1.v2), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.v2, c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new R8(enumC1812d20, 4), m11, new O7(enumC1812d20, 4), new O7(enumC1812d20, 1), new I7(182, c0722p1.a(c0722p1.m2, c0722p1.b(c0722p1.X1, new C0767v2[0]), c0722p1.c("getSimpleName")), false), new I7(182, c0722p1.a(c0767v3, c0722p1.b(c0767v3, c0767v2Arr), c0722p1.c("append")), false), new C1891e7(c0722p1.c("[")), new I7(182, c0722p1.a(c0767v4, c0722p1.b(c0767v4, c0767v2Arr2), c0722p1.c("append")), false), new D8(aVar), m12, new C1818d7(0L, enumC1812d21), new R8(enumC1812d21, 5), m13, new C2284j7(new C2413kp(new int[]{0, 1, 2, 3, 4, 5}, interfaceC1348SiArr, 0)), new O7(enumC1812d21, 5), new O7(enumC1812d20, 3), new A6(), new C3185v7(EnumC1076Il.GE, enumC1812d21, m18), m14, new O7(enumC1812d20, 4), new O7(enumC1812d20, 3), new O7(enumC1812d21, 5), new B6(enumC2523mE), new I7(182, c0722p1.a(c0767v5, c0722p1.b(c0767v5, c0767v2Arr3), c0722p1.c("append")), false), new C1891e7(c0722p1.c(ContainerUtils.KEY_VALUE_DELIMITER)), new I7(182, c0722p1.a(c0767v6, c0722p1.b(c0767v6, c0767v2Arr4), c0722p1.c("append")), false), new O7(enumC1812d20, 0), new O7(enumC1812d21, 5), new B6(enumC2523mE), new I7(182, c0722p1.a(c0767v7, c0722p1.b(c0767v7, c0767v2Arr5), c0722p1.c("append")), false), new D8(aVar), m15, new O7(enumC1812d21, 5), new O7(enumC1812d20, 3), new A6(), new C1818d7(1L, enumC1812d21), new C3481z6(C3481z6.a.Sub, EnumC2527mI.INT), new C3185v7(EnumC1076Il.EQ, enumC1812d21, m17), m16, new O7(enumC1812d20, 4), new C1891e7(c0722p1.c(", ")), new I7(182, c0722p1.a(c0767v8, c0722p1.b(c0767v8, c0767v2Arr6), c0722p1.c("append")), false), new D8(aVar), m17, new C2284j7(new C2413kp(new int[]{0, 1, 2, 3, 4, 5}, interfaceC1348SiArr2, 0)), new C3260w7(5, 1), new C2899r7(m13), m18, new C2284j7(new C2413kp(new int[]{0, 1, 2, 3, 4}, interfaceC1348SiArr3, 0)), new O7(enumC1812d20, 4), new C1891e7(c0722p1.c("]")), new I7(182, c0722p1.a(c0767v9, c0722p1.b(c0767v9, c0722p1.X1), c0722p1.c("append")), false), new D8(aVar), m19, new O7(enumC1812d20, 4), new I7(182, c0722p1.a(c0722p1.v2, c0722p1.b(c0722p1.X1, new C0767v2[0]), c0722p1.c("toString")), false), new C3109u8(enumC1812d20), m20), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }
}
