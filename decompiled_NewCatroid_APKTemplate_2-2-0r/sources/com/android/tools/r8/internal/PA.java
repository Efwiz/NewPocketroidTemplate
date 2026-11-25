package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.C3932m1;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class PA {
    static final /* synthetic */ boolean a = true;

    private static C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        byte b = c0767v2.e.e[0];
        if (b != 70 && b != 83 && b != 90 && b != 73 && b != 74) {
            switch (b) {
                case 66:
                case 67:
                case 68:
                    break;
                default:
                    throw new C3405y10("Invalid primitive type descriptor: " + c0767v2);
            }
        }
        return c0722p1.c(c0767v2);
    }

    private static void b(C0767v2 c0767v2, C0767v2 c0767v3, boolean z, C1507Xl c1507Xl, C0722p1 c0722p1) {
        C0767v2 c0767v2A;
        C0767v2 c0767v2A2;
        if (c0767v2 == c0767v3) {
            return;
        }
        boolean zW0 = c0767v2.W0();
        boolean zW1 = c0767v3.W0();
        if (zW0 && zW1) {
            a(c0767v2, c0767v3, c1507Xl);
            return;
        }
        if (zW1) {
            if (c0767v2 == c0722p1.Z1) {
                c0767v2A2 = a(c0722p1, c0767v3);
                c1507Xl.a(new M6(c0767v2A2));
            } else {
                c0767v2A2 = c0767v2;
            }
            C0767v2 c0767v2E = c0722p1.e(c0767v2A2);
            if (c0767v2E != null) {
                c1507Xl.a(new I7(182, c0722p1.f(c0767v2A2), false));
                a(c0767v2E, c0767v3, c1507Xl);
                return;
            }
        }
        if (zW0 && (c0767v3 == (c0767v2A = a(c0722p1, c0767v2)) || c0767v3 == c0722p1.Z1 || c0767v3 == c0722p1.u5 || c0767v3 == c0722p1.x5 || (c0767v2A != c0722p1.v1 && c0767v2A != c0722p1.x1 && c0767v3 == c0722p1.U1))) {
            c1507Xl.a(new I7(184, c0722p1.b(c0767v2A), false));
            return;
        }
        if (c0767v2.M0() && (c0767v3 == c0722p1.Z1 || c0767v3.M0())) {
            return;
        }
        if ((!c0767v2.O0() || !c0767v3.O0()) && (c0767v2 != c0722p1.Z1 || !c0767v3.M0())) {
            throw new C3405y10("Unexpected type adjustment from " + c0767v2.r0() + " to " + c0767v3);
        }
        if (!z || c0767v3 == c0722p1.Z1) {
            return;
        }
        c1507Xl.a(new M6(c0767v3));
    }

    private static boolean a(C0722p1 c0722p1, C0767v2 c0767v2, C0767v2 c0767v3) {
        if (c0767v2 == c0767v3) {
            return true;
        }
        if (c0767v2.M0()) {
            return c0767v3 == c0722p1.Z1 || c0767v3.M0();
        }
        if (c0767v3.M0()) {
            return c0767v2 == c0722p1.Z1;
        }
        if (c0767v2.W0()) {
            if (c0767v3.W0()) {
                return a(c0767v2.e.e[0], c0767v3.e.e[0]);
            }
            C0767v2 c0767v2A = a(c0722p1, c0767v2);
            if (c0767v3 == c0767v2A || c0767v3 == c0722p1.Z1 || c0767v3 == c0722p1.u5 || c0767v3 == c0722p1.x5) {
                return true;
            }
            return (c0767v2A == c0722p1.O1 || c0767v2A == c0722p1.M1 || c0767v3.e != c0722p1.K) ? false : true;
        }
        if (!c0767v3.W0()) {
            return c0767v2.O0() && c0767v3.O0();
        }
        if (c0767v2 == c0722p1.Z1) {
            return true;
        }
        C0767v2 c0767v2E = c0722p1.e(c0767v2);
        return c0767v2E != null && a(c0767v2E.e.e[0], c0767v3.e.e[0]);
    }

    private static boolean a(byte b, byte b2) {
        if (b == b2) {
            return true;
        }
        if (b == 70) {
            return b2 == 68;
        }
        if (b != 83) {
            if (b != 90) {
                if (b == 73) {
                    return b2 == 74 || b2 == 70 || b2 == 68;
                }
                if (b == 74) {
                    return b2 == 70 || b2 == 68;
                }
                switch (b) {
                    case 66:
                        return b2 == 83 || b2 == 73 || b2 == 74 || b2 == 70 || b2 == 68;
                    case 67:
                        break;
                    case 68:
                        break;
                    default:
                        throw new C3405y10("Invalid primitive type descriptor: " + ((int) b));
                }
            }
            return false;
        }
        return b2 == 73 || b2 == 74 || b2 == 70 || b2 == 68;
    }

    public static C0753u a(HA ha, C0684j2 c0684j2, MA ma) {
        C0767v2 c0767v2;
        int iMax;
        int i;
        boolean z;
        HA ha2 = ha;
        C0722p1 c0722p1A = ha2.a.a();
        GA ga = ha2.i;
        if (ga instanceof DA) {
            C0767v2 c0767v3 = ((DA) ga).f;
            C0684j2 c0684j2A = c0722p1A.a(c0767v3, c0722p1A.b(c0722p1A.D1, new C0767v2[0]), c0722p1A.Y0);
            int iC = 1;
            for (C0767v2 c0767v4 : c0684j2.h.f.a) {
                iC += EnumC1812d20.a(c0767v4).c();
            }
            return new C0753u(c0684j2.e, 2, iC, AbstractC1639am.a(new V7(null, c0767v3), new D8(D8.a.Dup), new I7(183, c0684j2A, false), new V8()), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
        }
        C0684j2 c0684j3 = ga.a;
        JA ja = ha2.d;
        C0767v2[] c0767v2Arr = ja.i.a;
        C0737r2 c0737r2 = ja.d;
        C0767v2[] c0767v2Arr2 = c0737r2.f.a;
        C0767v2 c0767v5 = c0737r2.e;
        C0737r2 c0737r3 = ja.e;
        C0767v2[] c0767v2Arr3 = c0737r3.f.a;
        C0767v2 c0767v6 = c0737r3.e;
        boolean z2 = c0684j3.f == c0722p1A.Y0;
        boolean z3 = a;
        if (!z3 && z2 && ga.b != EnumC1953et.c) {
            throw new AssertionError();
        }
        EnumC1953et enumC1953et = ga.b;
        EnumC1953et enumC1953et2 = EnumC1953et.g;
        boolean z4 = enumC1953et == enumC1953et2 || enumC1953et == EnumC1953et.d || (enumC1953et == EnumC1953et.c && !z2);
        ArrayList arrayList = new ArrayList();
        if (z4) {
            arrayList.add(c0684j3.e);
        }
        arrayList.addAll(AbstractC2763pC.a(c0684j3.h.f.a));
        C0767v2 c0767v7 = c0684j3.h.e;
        if (z3) {
            c0767v2 = c0767v7;
        } else {
            EnumC1953et enumC1953et3 = ga.b;
            c0767v2 = c0767v7;
            if (enumC1953et3 != EnumC1953et.e && enumC1953et3 != enumC1953et2 && enumC1953et3 != EnumC1953et.c && enumC1953et3 != EnumC1953et.d) {
                throw new AssertionError();
            }
        }
        if (!z3) {
            C0767v2 c0767v8 = z2 ? ga.a.e : c0767v2;
            ArrayList arrayList2 = new ArrayList();
            arrayList2.addAll(AbstractC2763pC.a(c0767v2Arr));
            arrayList2.addAll(AbstractC2763pC.a(c0767v2Arr3));
            int size = arrayList2.size();
            if (size != arrayList.size() && !z3) {
                throw new AssertionError();
            }
            int i2 = 0;
            while (i2 < size) {
                ArrayList arrayList3 = arrayList2;
                if (!a(c0722p1A, (C0767v2) arrayList2.get(i2), (C0767v2) arrayList.get(i2)) && !a) {
                    throw new AssertionError();
                }
                i2++;
                arrayList2 = arrayList3;
            }
            if (!c0767v6.Y0() && !a(c0722p1A, c0767v8, c0767v6) && !a) {
                throw new AssertionError();
            }
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        if (z2) {
            c1507XlG.a(new V7(null, c0684j3.e));
            c1507XlG.a(new D8(D8.a.Dup));
            iMax = 2;
        } else {
            iMax = 0;
        }
        int length = c0767v2Arr.length;
        int i3 = 0;
        while (i3 < length) {
            com.android.tools.r8.graph.Y0 y0A = ha2.a(i3);
            EnumC1812d20 enumC1812d20A = EnumC1812d20.a(y0A.h);
            c1507XlG.a(new O7(EnumC1812d20.OBJECT, 0));
            c1507XlG.a(new C3413y7(y0A, y0A));
            iMax += enumC1812d20A.c();
            i3++;
            ha2 = ha;
            c0767v5 = c0767v5;
            z2 = z2;
        }
        C0767v2 c0767v9 = c0767v5;
        boolean z5 = z2;
        int iA = 1;
        for (int i4 = 0; i4 < c0767v2Arr2.length; i4++) {
            EnumC1812d20 enumC1812d20A2 = EnumC1812d20.a(c0684j2.G0().a[i4]);
            c1507XlG.a(new O7(enumC1812d20A2, iA));
            iA += enumC1812d20A2.c();
            C0767v2 c0767v10 = (C0767v2) arrayList.get(i4 + length);
            C0767v2 c0767v11 = c0767v2Arr2[i4];
            C0767v2 c0767v12 = c0767v2Arr3[i4];
            if (c0767v11 == c0767v12) {
                z = false;
            } else {
                if (!a && !JA.a(c0722p1A, c0767v12, c0767v11)) {
                    throw new AssertionError();
                }
                z = false;
                c1507XlG.a(new M6(c0767v12, false));
            }
            iMax += a(c0767v12, c0767v10, z, c1507XlG, c0722p1A);
        }
        I7 i7 = new I7(ga.b.a(), c0684j3, ga.c);
        if (ga instanceof EA) {
            C3932m1 c3932m1 = new C3932m1();
            C3932m1 c3932m2 = new C3932m1();
            Collection collectionA = ma.a(i7, new PA$$ExternalSyntheticLambda0(c3932m1), new PA$$ExternalSyntheticLambda1(c3932m2));
            if (collectionA != null) {
                c1507XlG.a((Iterable) collectionA);
                iA += c3932m1.a();
                iMax += c3932m2.a();
            } else {
                c1507XlG.a(i7);
            }
        } else {
            c1507XlG.a(i7);
        }
        int i5 = iA;
        C0767v2 c0767v2I0 = c0684j3.I0();
        if (!c0767v2I0.Y0()) {
            EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
            iMax = Math.max(iMax, EnumC1812d20.a((char) c0767v2I0.e.e[0]).c());
        }
        if (c0767v6.Y0()) {
            if (!c0767v2I0.Y0()) {
                c1507XlG.a(new D8(c0767v2I0.Z0() ? D8.a.Pop2 : D8.a.Pop));
            }
            c1507XlG.a(new C3186v8());
            i = iMax;
        } else {
            boolean z6 = a;
            if (!z6 && !z5 && c0767v2I0.Y0()) {
                throw new AssertionError();
            }
            if (z5) {
                c0767v2I0 = c0684j3.e;
            }
            if (!z6 && !JA.a(c0722p1A, c0767v6, c0767v9)) {
                throw new AssertionError();
            }
            int iMax2 = Math.max(iMax, a(c0767v2I0, c0767v6, true, c1507XlG, c0722p1A));
            EnumC1812d20 enumC1812d21 = EnumC1812d20.OBJECT;
            c1507XlG.a(new C3109u8(EnumC1812d20.a((char) c0767v6.e.e[0])));
            i = iMax2;
        }
        return new C0753u(c0684j2.e, i, i5, c1507XlG.a(), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    private static int a(C0767v2 c0767v2, C0767v2 c0767v3, boolean z, C1507Xl c1507Xl, C0722p1 c0722p1) {
        b(c0767v2, c0767v3, z, c1507Xl, c0722p1);
        if (c0767v2 == c0767v3) {
            EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
            return EnumC1812d20.a((char) c0767v2.e.e[0]).c();
        }
        C0767v2 c0767v2E = c0722p1.e(c0767v2);
        EnumC1812d20 enumC1812d21 = EnumC1812d20.OBJECT;
        return Math.max(EnumC1812d20.a((char) c0767v2.e.e[0]).c(), Math.max(c0767v2E == null ? 0 : EnumC1812d20.a((char) c0767v2E.e.e[0]).c(), EnumC1812d20.a((char) c0767v3.e.e[0]).c()));
    }

    private static void a(C0767v2 c0767v2, C0767v2 c0767v3, C1507Xl c1507Xl) {
        boolean z = a;
        if (!z && (!c0767v2.W0() || !c0767v3.W0())) {
            throw new AssertionError();
        }
        if (c0767v2 == c0767v3) {
            return;
        }
        EnumC2527mI enumC2527mIA = EnumC2527mI.a(c0767v2);
        EnumC2527mI enumC2527mIA2 = EnumC2527mI.a(c0767v3);
        if (enumC2527mIA != null && enumC2527mIA2 != null) {
            if (!z && enumC2527mIA == enumC2527mIA2) {
                throw new AssertionError();
            }
            int i = OA.a[enumC2527mIA2.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            if (i == 5) {
                                if (enumC2527mIA != EnumC2527mI.FLOAT && enumC2527mIA != EnumC2527mI.LONG) {
                                    enumC2527mIA = EnumC2527mI.INT;
                                }
                                c1507Xl.a(new C1591a8(enumC2527mIA, enumC2527mIA2));
                                return;
                            }
                        } else if (enumC2527mIA != EnumC2527mI.DOUBLE) {
                            EnumC2527mI enumC2527mI = EnumC2527mI.LONG;
                            if (enumC2527mIA != enumC2527mI) {
                                enumC2527mI = EnumC2527mI.INT;
                            }
                            c1507Xl.a(new C1591a8(enumC2527mI, enumC2527mIA2));
                            return;
                        }
                    } else if (enumC2527mIA != EnumC2527mI.FLOAT && enumC2527mIA != EnumC2527mI.DOUBLE) {
                        c1507Xl.a(new C1591a8(EnumC2527mI.INT, enumC2527mIA2));
                        return;
                    }
                } else if (enumC2527mIA == EnumC2527mI.BYTE || enumC2527mIA == EnumC2527mI.CHAR || enumC2527mIA == EnumC2527mI.SHORT) {
                    return;
                }
            } else if (enumC2527mIA == EnumC2527mI.BYTE) {
                c1507Xl.a(new C1591a8(EnumC2527mI.INT, enumC2527mIA2));
                return;
            }
        }
        throw new C3405y10("Type " + c0767v2.r0() + " cannot be converted to " + c0767v3.r0() + " via primitive widening conversion.");
    }
}
