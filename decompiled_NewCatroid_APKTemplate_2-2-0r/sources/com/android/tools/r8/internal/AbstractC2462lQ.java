package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0403b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2462lQ {
    /* JADX WARN: Code duplicated, block: B:110:0x02fc  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r4v5 */
    public static void a(C1761cO c1761cO, C1283Px c1283Px, C0970Eu c0970Eu) {
        C2668o20 c2668o20;
        EnumC1364Sy enumC1364Sy;
        C1940eg c1940eg = C1940eg.a;
        AbstractC0936Ds.c(c1761cO, "<this>");
        AbstractC0936Ds.c(c0970Eu, "strings");
        C2075gP c2075gPD = c1761cO.D();
        AbstractC0936Ds.b(c2075gPD, "typeTable");
        H00 h00 = new H00(c2075gPD);
        int i = C2668o20.c;
        C2919rP c2919rPF = c1761cO.F();
        AbstractC0936Ds.b(c2919rPF, "versionRequirementTable");
        int i2 = 0;
        if (c2919rPF.h() == 0) {
            c2668o20 = C2668o20.b;
        } else {
            List listI = c2919rPF.i();
            AbstractC0936Ds.b(listI, "table.requirementList");
            c2668o20 = new C2668o20(listI, i2);
        }
        C2234iQ c2234iQ = new C2234iQ(c0970Eu, h00, c2668o20, c1940eg, 8);
        List listC = c1761cO.C();
        AbstractC0936Ds.b(listC, "typeParameterList");
        C2234iQ c2234iQA = c2234iQ.a(listC);
        c1283Px.c(c1761cO.q(), c2234iQA.a(c1761cO.r()));
        for (C1840dP c1840dP : c1761cO.C()) {
            AbstractC0936Ds.b(c1840dP, "typeParameter");
            EnumC1762cP enumC1762cPQ = c1840dP.q();
            AbstractC0936Ds.a(enumC1762cPQ);
            int iOrdinal = enumC1762cPQ.ordinal();
            if (iOrdinal == 0) {
                enumC1364Sy = EnumC1364Sy.b;
            } else if (iOrdinal == 1) {
                enumC1364Sy = EnumC1364Sy.c;
            } else {
                if (iOrdinal != 2) {
                    throw new C2692oH();
                }
                enumC1364Sy = EnumC1364Sy.a;
            }
            a(c1283Px.a(c1840dP.n() ? 1 : 0, c2234iQA.b(c1840dP.m()), c1840dP.l(), enumC1364Sy), c1840dP, c2234iQA);
        }
        H00 h00C = c2234iQA.c();
        AbstractC0936Ds.c(h00C, "typeTable");
        List<WO> listA = c1761cO.A();
        if (listA.isEmpty()) {
            listA = null;
        }
        if (listA == null) {
            List<Integer> listZ = c1761cO.z();
            AbstractC0936Ds.b(listZ, "supertypeIdList");
            ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listZ));
            for (Integer num : listZ) {
                AbstractC0936Ds.b(num, "it");
                arrayList.add(h00C.a(num.intValue()));
            }
            listA = arrayList;
        }
        for (WO wo : listA) {
            a(c1283Px.e(a(wo)), wo, c2234iQA);
        }
        for (C1998fO c1998fO : c1761cO.l()) {
            AbstractC1572Zx abstractC1572ZxB = c1283Px.b(c1998fO.l());
            for (C2303jP c2303jP : c1998fO.m()) {
                AbstractC1338Ry abstractC1338RyA = abstractC1572ZxB.a(c2303jP.l(), c2234iQA.b(c2303jP.m()));
                if (abstractC1338RyA != null) {
                    a(abstractC1338RyA, c2303jP, c2234iQA);
                }
            }
            for (Integer num2 : c1998fO.n()) {
                AbstractC1520Xy abstractC1520XyB = abstractC1572ZxB.b();
                if (abstractC1520XyB != null) {
                    AbstractC0936Ds.b(num2, "versionRequirement");
                    a(num2.intValue(), abstractC1520XyB, c2234iQA);
                }
            }
            Iterator it = c2234iQA.a().iterator();
            while (it.hasNext()) {
                ((C3317wu) ((InterfaceC3343xE) it.next())).a(abstractC1572ZxB, c1998fO, c2234iQA);
            }
            abstractC1572ZxB.a();
        }
        List listS = c1761cO.s();
        AbstractC0936Ds.b(listS, "functionList");
        List listX = c1761cO.x();
        AbstractC0936Ds.b(listX, "propertyList");
        List listB = c1761cO.B();
        AbstractC0936Ds.b(listB, "typeAliasList");
        a(c1283Px, listS, listX, listB, c2234iQA);
        if (c1761cO.G()) {
            c1283Px.c(c2234iQA.b(c1761cO.k()));
        }
        for (Integer num3 : c1761cO.w()) {
            AbstractC0936Ds.b(num3, "nestedClassName");
            c1283Px.f(c2234iQA.b(num3.intValue()));
        }
        for (C2846qO c2846qO : c1761cO.p()) {
            if (!c2846qO.m()) {
                throw new C1640an("No name for EnumEntry", null);
            }
            c1283Px.d(c2234iQA.b(c2846qO.l()));
        }
        for (Integer num4 : c1761cO.y()) {
            AbstractC0936Ds.b(num4, "sealedSubclassFqName");
            c1283Px.g(c2234iQA.a(num4.intValue()));
        }
        if (c1761cO.J()) {
            c1283Px.e(c2234iQA.b(c1761cO.t()));
        }
        H00 h00C2 = c2234iQA.c();
        AbstractC0936Ds.c(h00C2, "typeTable");
        WO woU = c1761cO.K() ? c1761cO.u() : c1761cO.L() ? h00C2.a(c1761cO.v()) : null;
        if (woU == null) {
            if (c1761cO.J()) {
                List listX2 = c1761cO.x();
                AbstractC0936Ds.b(listX2, "propertyList");
                Iterator it2 = listX2.iterator();
                Object obj = null;
                while (true) {
                    if (!it2.hasNext()) {
                        if (i2 == 0) {
                            break;
                        } else {
                            break;
                        }
                    }
                    Object next = it2.next();
                    FO fo = (FO) next;
                    AbstractC0936Ds.b(fo, "it");
                    H00 h00C3 = c2234iQA.c();
                    AbstractC0936Ds.c(h00C3, "typeTable");
                    if ((fo.D() ? fo.r() : fo.E() ? h00C3.a(fo.s()) : null) == null && AbstractC0936Ds.a((Object) c2234iQA.b(fo.p()), (Object) c2234iQA.b(c1761cO.t()))) {
                        if (i2 == 0) {
                            i2 = 1;
                            obj = next;
                        }
                    }
                    obj = null;
                    break;
                }
                FO fo2 = (FO) obj;
                if (fo2 != null) {
                    woU = YP.a(fo2, c2234iQA.c());
                } else {
                    woU = null;
                }
            } else {
                woU = null;
            }
        }
        if (woU != null) {
            a(c1283Px.d((woU.t() ? 1 : 0) + (woU.p() << 1)), woU, c2234iQA);
        }
        H00 h00C4 = c2234iQA.c();
        AbstractC0936Ds.c(h00C4, "typeTable");
        List listN = c1761cO.n();
        ?? arrayList2 = listN.isEmpty() ? 0 : listN;
        if (arrayList2 == 0) {
            List<Integer> listM = c1761cO.m();
            AbstractC0936Ds.b(listM, "contextReceiverTypeIdList");
            arrayList2 = new ArrayList(AbstractC0823Ab.a((Iterable) listM));
            for (Integer num5 : listM) {
                AbstractC0936Ds.b(num5, "it");
                arrayList2.add(h00C4.a(num5.intValue()));
            }
        }
        for (WO wo2 : arrayList2) {
            a(c1283Px.c(a(wo2)), wo2, c2234iQA);
        }
        for (Integer num6 : c1761cO.E()) {
            AbstractC1520Xy abstractC1520XyS = c1283Px.s();
            AbstractC0936Ds.b(num6, "versionRequirement");
            a(num6.intValue(), abstractC1520XyS, c2234iQA);
        }
        Iterator it3 = c2234iQA.a().iterator();
        while (it3.hasNext()) {
            ((C3317wu) ((InterfaceC3343xE) it3.next())).a(c1283Px, c1761cO, c2234iQA);
        }
        c1283Px.e();
    }

    public static final int b(FO fo) {
        if (fo.H()) {
            return fo.v();
        }
        int iN = fo.n();
        Boolean boolB = AbstractC3382xi.c.b(iN);
        AbstractC0936Ds.b(boolB, "HAS_ANNOTATIONS.get(flags)");
        return AbstractC3382xi.a(boolB.booleanValue(), (EnumC2988sP) AbstractC3382xi.d.a(iN), (EnumC3502zO) AbstractC3382xi.e.a(iN));
    }

    public static void a(CO co, C3247vy c3247vy, C0970Eu c0970Eu) {
        C2668o20 c2668o20;
        C1940eg c1940eg = C1940eg.a;
        AbstractC0936Ds.c(co, "<this>");
        AbstractC0936Ds.c(c0970Eu, "strings");
        C2075gP c2075gPO = co.o();
        AbstractC0936Ds.b(c2075gPO, "typeTable");
        H00 h00 = new H00(c2075gPO);
        int i = C2668o20.c;
        C2919rP c2919rPP = co.p();
        AbstractC0936Ds.b(c2919rPP, "versionRequirementTable");
        if (c2919rPP.h() == 0) {
            c2668o20 = C2668o20.b;
        } else {
            List listI = c2919rPP.i();
            AbstractC0936Ds.b(listI, "table.requirementList");
            c2668o20 = new C2668o20(listI, 0);
        }
        C2234iQ c2234iQ = new C2234iQ(c0970Eu, h00, c2668o20, c1940eg, 8);
        List listL = co.l();
        AbstractC0936Ds.b(listL, "functionList");
        List listM = co.m();
        AbstractC0936Ds.b(listM, "propertyList");
        List listN = co.n();
        AbstractC0936Ds.b(listN, "typeAliasList");
        a(c3247vy, listL, listM, listN, c2234iQ);
        Iterator it = c2234iQ.a().iterator();
        while (it.hasNext()) {
            ((C3317wu) ((InterfaceC3343xE) it.next())).a(c3247vy, co, c2234iQ);
        }
        c3247vy.e();
    }

    private static final void a(AbstractC1960ey abstractC1960ey, List list, List list2, List list3, C2234iQ c2234iQ) {
        WO woA;
        WO woA2;
        EnumC1364Sy enumC1364Sy;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C3354xO c3354xO = (C3354xO) it.next();
            AbstractC3025sy abstractC3025syA = abstractC1960ey.a(c3354xO.o(), c2234iQ.b(c3354xO.p()));
            if (abstractC3025syA != null) {
                a(abstractC3025syA, c3354xO, c2234iQ);
            }
        }
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            FO fo = (FO) it2.next();
            AbstractC0914Cy abstractC0914CyA = abstractC1960ey.a(fo.n(), a(fo), b(fo), c2234iQ.b(fo.p()));
            if (abstractC0914CyA != null) {
                a(abstractC0914CyA, fo, c2234iQ);
            }
        }
        Iterator it3 = list3.iterator();
        while (it3.hasNext()) {
            ZO zo = (ZO) it3.next();
            AbstractC1029Gy abstractC1029GyB = abstractC1960ey.b(zo.o(), c2234iQ.b(zo.p()));
            if (abstractC1029GyB != null) {
                List listQ = zo.q();
                AbstractC0936Ds.b(listQ, "typeParameterList");
                C2234iQ c2234iQA = c2234iQ.a(listQ);
                for (C1840dP c1840dP : zo.q()) {
                    AbstractC0936Ds.b(c1840dP, "typeParameter");
                    EnumC1762cP enumC1762cPQ = c1840dP.q();
                    AbstractC0936Ds.a(enumC1762cPQ);
                    int iOrdinal = enumC1762cPQ.ordinal();
                    if (iOrdinal == 0) {
                        enumC1364Sy = EnumC1364Sy.b;
                    } else if (iOrdinal != 1) {
                        if (iOrdinal != 2) {
                            throw new C2692oH();
                        }
                        enumC1364Sy = EnumC1364Sy.a;
                    } else {
                        enumC1364Sy = EnumC1364Sy.c;
                    }
                    boolean zN = c1840dP.n();
                    AbstractC1200My abstractC1200MyA = abstractC1029GyB.a(zN ? 1 : 0, c2234iQA.b(c1840dP.m()), c1840dP.l(), enumC1364Sy);
                    if (abstractC1200MyA != null) {
                        a(abstractC1200MyA, c1840dP, c2234iQA);
                    }
                }
                H00 h00C = c2234iQA.c();
                AbstractC0936Ds.c(h00C, "typeTable");
                if (zo.y()) {
                    woA = zo.r();
                    AbstractC0936Ds.b(woA, "underlyingType");
                } else {
                    if (!zo.z()) {
                        throw new IllegalStateException("No underlyingType in ProtoBuf.TypeAlias".toString());
                    }
                    woA = h00C.a(zo.s());
                }
                AbstractC1254Oy abstractC1254OyB = abstractC1029GyB.b(a(woA));
                if (abstractC1254OyB != null) {
                    a(abstractC1254OyB, woA, c2234iQA);
                }
                H00 h00C2 = c2234iQA.c();
                AbstractC0936Ds.c(h00C2, "typeTable");
                if (zo.u()) {
                    woA2 = zo.m();
                    AbstractC0936Ds.b(woA2, "expandedType");
                } else {
                    if (!zo.v()) {
                        throw new IllegalStateException("No expandedType in ProtoBuf.TypeAlias".toString());
                    }
                    woA2 = h00C2.a(zo.n());
                }
                AbstractC1254Oy abstractC1254OyA = abstractC1029GyB.a(a(woA2));
                if (abstractC1254OyA != null) {
                    a(abstractC1254OyA, woA2, c2234iQA);
                }
                for (XN xn : zo.k()) {
                    AbstractC0936Ds.b(xn, "annotation");
                    abstractC1029GyB.a(AbstractC2382kQ.a(xn, c2234iQA.b()));
                }
                for (Integer num : zo.t()) {
                    AbstractC1520Xy abstractC1520XyB = abstractC1029GyB.b();
                    if (abstractC1520XyB != null) {
                        AbstractC0936Ds.b(num, "versionRequirement");
                        a(num.intValue(), abstractC1520XyB, c2234iQA);
                    }
                }
                Iterator it4 = c2234iQA.a().iterator();
                while (it4.hasNext()) {
                    ((C3317wu) ((InterfaceC3343xE) it4.next())).a(abstractC1029GyB, zo, c2234iQA);
                }
                abstractC1029GyB.a();
            }
        }
    }

    public static final void a(C3354xO c3354xO, C3095ty c3095ty, C0970Eu c0970Eu) {
        AbstractC0936Ds.c(c3354xO, "<this>");
        AbstractC0936Ds.c(c0970Eu, "strings");
        C2075gP c2075gPW = c3354xO.w();
        AbstractC0936Ds.b(c2075gPW, "typeTable");
        C2234iQ c2234iQ = new C2234iQ(c0970Eu, new H00(c2075gPW), C2668o20.b, (List) null, 24);
        a(c3095ty.a(c3354xO.o(), c2234iQ.b(c3354xO.p())), c3354xO, c2234iQ);
        c3095ty.a();
    }

    private static final void a(AbstractC3025sy abstractC3025sy, C3354xO c3354xO, C2234iQ c2234iQ) {
        WO woA;
        WO woA2;
        AbstractC1804cy abstractC1804cyA;
        EnumC2344jy enumC2344jy;
        EnumC2269iy enumC2269iy;
        AbstractC2191hy abstractC2191hyA;
        EnumC1364Sy enumC1364Sy;
        List listV = c3354xO.v();
        AbstractC0936Ds.b(listV, "typeParameterList");
        C2234iQ c2234iQA = c2234iQ.a(listV);
        for (C1840dP c1840dP : c3354xO.v()) {
            AbstractC0936Ds.b(c1840dP, "typeParameter");
            EnumC1762cP enumC1762cPQ = c1840dP.q();
            AbstractC0936Ds.a(enumC1762cPQ);
            int iOrdinal = enumC1762cPQ.ordinal();
            if (iOrdinal == 0) {
                enumC1364Sy = EnumC1364Sy.b;
            } else if (iOrdinal != 1) {
                if (iOrdinal != 2) {
                    throw new C2692oH();
                }
                enumC1364Sy = EnumC1364Sy.a;
            } else {
                enumC1364Sy = EnumC1364Sy.c;
            }
            AbstractC1200My abstractC1200MyA = abstractC3025sy.a(c1840dP.n() ? 1 : 0, c2234iQA.b(c1840dP.m()), c1840dP.l(), enumC1364Sy);
            if (abstractC1200MyA != null) {
                a(abstractC1200MyA, c1840dP, c2234iQA);
            }
        }
        H00 h00C = c2234iQA.c();
        AbstractC0936Ds.c(h00C, "typeTable");
        if (c3354xO.D()) {
            woA = c3354xO.r();
        } else {
            woA = c3354xO.E() ? h00C.a(c3354xO.s()) : null;
        }
        if (woA != null) {
            AbstractC1254Oy abstractC1254OyB = abstractC3025sy.b((woA.t() ? 1 : 0) + (woA.p() << 1));
            if (abstractC1254OyB != null) {
                a(abstractC1254OyB, woA, c2234iQA);
            }
        }
        H00 h00C2 = c2234iQA.c();
        AbstractC0936Ds.c(h00C2, "typeTable");
        List<WO> listL = c3354xO.l();
        if (listL.isEmpty()) {
            listL = null;
        }
        if (listL == null) {
            List<Integer> listK = c3354xO.k();
            AbstractC0936Ds.b(listK, "contextReceiverTypeIdList");
            ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listK));
            for (Integer num : listK) {
                AbstractC0936Ds.b(num, "it");
                arrayList.add(h00C2.a(num.intValue()));
            }
            listL = arrayList;
        }
        for (WO wo : listL) {
            AbstractC1254Oy abstractC1254OyA = abstractC3025sy.a(a(wo));
            if (abstractC1254OyA != null) {
                a(abstractC1254OyA, wo, c2234iQA);
            }
        }
        for (C2303jP c2303jP : c3354xO.x()) {
            AbstractC1338Ry abstractC1338RyA = abstractC3025sy.a(c2303jP.l(), c2234iQA.b(c2303jP.m()));
            if (abstractC1338RyA != null) {
                a(abstractC1338RyA, c2303jP, c2234iQA);
            }
        }
        H00 h00C3 = c2234iQA.c();
        AbstractC0936Ds.c(h00C3, "typeTable");
        if (c3354xO.F()) {
            woA2 = c3354xO.t();
            AbstractC0936Ds.b(woA2, "returnType");
        } else {
            if (!c3354xO.G()) {
                throw new IllegalStateException("No returnType in ProtoBuf.Function".toString());
            }
            woA2 = h00C3.a(c3354xO.u());
        }
        AbstractC1254Oy abstractC1254OyC = abstractC3025sy.c(a(woA2));
        if (abstractC1254OyC != null) {
            a(abstractC1254OyC, woA2, c2234iQA);
        }
        if (c3354xO.z() && (abstractC1804cyA = abstractC3025sy.a()) != null) {
            C2232iO c2232iOM = c3354xO.m();
            AbstractC0936Ds.b(c2232iOM, "contract");
            for (C2608nO c2608nO : c2232iOM.h()) {
                if (c2608nO.m()) {
                    EnumC2460lO enumC2460lOJ = c2608nO.j();
                    AbstractC0936Ds.a(enumC2460lOJ);
                    int iOrdinal2 = enumC2460lOJ.ordinal();
                    if (iOrdinal2 == 0) {
                        enumC2344jy = EnumC2344jy.RETURNS_CONSTANT;
                    } else if (iOrdinal2 != 1) {
                        if (iOrdinal2 != 2) {
                            throw new C2692oH();
                        }
                        enumC2344jy = EnumC2344jy.RETURNS_NOT_NULL;
                    } else {
                        enumC2344jy = EnumC2344jy.CALLS;
                    }
                    if (c2608nO.n()) {
                        EnumC2534mO enumC2534mOK = c2608nO.k();
                        AbstractC0936Ds.a(enumC2534mOK);
                        int iOrdinal3 = enumC2534mOK.ordinal();
                        if (iOrdinal3 == 0) {
                            enumC2269iy = EnumC2269iy.AT_MOST_ONCE;
                        } else if (iOrdinal3 != 1) {
                            if (iOrdinal3 != 2) {
                                throw new C2692oH();
                            }
                            enumC2269iy = EnumC2269iy.AT_LEAST_ONCE;
                        } else {
                            enumC2269iy = EnumC2269iy.EXACTLY_ONCE;
                        }
                    } else {
                        enumC2269iy = null;
                    }
                    AbstractC2425ky abstractC2425kyA = abstractC1804cyA.a(enumC2344jy, enumC2269iy);
                    if (abstractC2425kyA != null) {
                        for (C3125uO c3125uO : c2608nO.i()) {
                            AbstractC2191hy abstractC2191hyB = abstractC2425kyA.b();
                            if (abstractC2191hyB != null) {
                                AbstractC0936Ds.b(c3125uO, "constructorArgument");
                                a(c3125uO, abstractC2191hyB, c2234iQA);
                            }
                        }
                        if (c2608nO.l() && (abstractC2191hyA = abstractC2425kyA.a()) != null) {
                            C3125uO c3125uOG = c2608nO.g();
                            AbstractC0936Ds.b(c3125uOG, "conclusionOfConditionalEffect");
                            a(c3125uOG, abstractC2191hyA, c2234iQA);
                        }
                        abstractC2425kyA.c();
                    }
                }
            }
            abstractC1804cyA.a();
        }
        for (Integer num2 : c3354xO.y()) {
            AbstractC1520Xy abstractC1520XyC = abstractC3025sy.c();
            if (abstractC1520XyC != null) {
                AbstractC0936Ds.b(num2, "versionRequirement");
                a(num2.intValue(), abstractC1520XyC, c2234iQA);
            }
        }
        Iterator it = c2234iQA.a().iterator();
        while (it.hasNext()) {
            ((C3317wu) ((InterfaceC3343xE) it.next())).a(abstractC3025sy, c3354xO, c2234iQA);
        }
        abstractC3025sy.b();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r4v4 */
    public static final void a(AbstractC0914Cy abstractC0914Cy, FO fo, C2234iQ c2234iQ) {
        WO woA;
        EnumC1364Sy enumC1364Sy;
        AbstractC0936Ds.c(c2234iQ, "outer");
        List listX = fo.x();
        AbstractC0936Ds.b(listX, "typeParameterList");
        C2234iQ c2234iQA = c2234iQ.a(listX);
        for (C1840dP c1840dP : fo.x()) {
            AbstractC0936Ds.b(c1840dP, "typeParameter");
            EnumC1762cP enumC1762cPQ = c1840dP.q();
            AbstractC0936Ds.a(enumC1762cPQ);
            int iOrdinal = enumC1762cPQ.ordinal();
            if (iOrdinal == 0) {
                enumC1364Sy = EnumC1364Sy.b;
            } else if (iOrdinal != 1) {
                if (iOrdinal != 2) {
                    throw new C2692oH();
                }
                enumC1364Sy = EnumC1364Sy.a;
            } else {
                enumC1364Sy = EnumC1364Sy.c;
            }
            AbstractC1200My abstractC1200MyA = abstractC0914Cy.a(c1840dP.n() ? 1 : 0, c2234iQA.b(c1840dP.m()), c1840dP.l(), enumC1364Sy);
            if (abstractC1200MyA != null) {
                a(abstractC1200MyA, c1840dP, c2234iQA);
            }
        }
        H00 h00C = c2234iQA.c();
        AbstractC0936Ds.c(h00C, "typeTable");
        if (fo.D()) {
            woA = fo.r();
        } else {
            woA = fo.E() ? h00C.a(fo.s()) : null;
        }
        if (woA != null) {
            AbstractC1254Oy abstractC1254OyB = abstractC0914Cy.b((woA.t() ? 1 : 0) + (woA.p() << 1));
            if (abstractC1254OyB != null) {
                a(abstractC1254OyB, woA, c2234iQA);
            }
        }
        H00 h00C2 = c2234iQA.c();
        AbstractC0936Ds.c(h00C2, "typeTable");
        List listL = fo.l();
        ?? arrayList = listL.isEmpty() ? 0 : listL;
        if (arrayList == 0) {
            List<Integer> listK = fo.k();
            AbstractC0936Ds.b(listK, "contextReceiverTypeIdList");
            arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listK));
            for (Integer num : listK) {
                AbstractC0936Ds.b(num, "it");
                arrayList.add(h00C2.a(num.intValue()));
            }
        }
        for (WO wo : arrayList) {
            AbstractC1254Oy abstractC1254OyA = abstractC0914Cy.a(a(wo));
            if (abstractC1254OyA != null) {
                a(abstractC1254OyA, wo, c2234iQA);
            }
        }
        if (fo.I()) {
            C2303jP c2303jPW = fo.w();
            AbstractC1338Ry abstractC1338RyA = abstractC0914Cy.a(c2303jPW.l(), c2234iQA.b(c2303jPW.m()));
            if (abstractC1338RyA != null) {
                a(abstractC1338RyA, c2303jPW, c2234iQA);
            }
        }
        WO woA2 = YP.a(fo, c2234iQA.c());
        AbstractC1254Oy abstractC1254OyC = abstractC0914Cy.c(a(woA2));
        if (abstractC1254OyC != null) {
            a(abstractC1254OyC, woA2, c2234iQA);
        }
        for (Integer num2 : fo.y()) {
            AbstractC1520Xy abstractC1520XyB = abstractC0914Cy.b();
            if (abstractC1520XyB != null) {
                AbstractC0936Ds.b(num2, "versionRequirement");
                a(num2.intValue(), abstractC1520XyB, c2234iQA);
            }
        }
        Iterator it = c2234iQA.a().iterator();
        while (it.hasNext()) {
            ((C3317wu) ((InterfaceC3343xE) it.next())).a(abstractC0914Cy, fo, c2234iQA);
        }
        abstractC0914Cy.a();
    }

    private static final void a(AbstractC1338Ry abstractC1338Ry, C2303jP c2303jP, C2234iQ c2234iQ) {
        WO woA;
        WO woA2 = YP.a(c2303jP, c2234iQ.c());
        AbstractC1254Oy abstractC1254OyA = abstractC1338Ry.a(a(woA2));
        if (abstractC1254OyA != null) {
            a(abstractC1254OyA, woA2, c2234iQ);
        }
        H00 h00C = c2234iQ.c();
        AbstractC0936Ds.c(h00C, "typeTable");
        if (c2303jP.v()) {
            woA = c2303jP.p();
        } else {
            woA = c2303jP.w() ? h00C.a(c2303jP.q()) : null;
        }
        if (woA != null) {
            AbstractC1254Oy abstractC1254OyB = abstractC1338Ry.b((woA.t() ? 1 : 0) + (woA.p() << 1));
            if (abstractC1254OyB != null) {
                a(abstractC1254OyB, woA, c2234iQ);
            }
        }
        Iterator it = c2234iQ.a().iterator();
        while (it.hasNext()) {
            ((C3317wu) ((InterfaceC3343xE) it.next())).a(abstractC1338Ry, c2303jP, c2234iQ);
        }
        abstractC1338Ry.a();
    }

    private static final void a(AbstractC1200My abstractC1200My, C1840dP c1840dP, C2234iQ c2234iQ) {
        H00 h00C = c2234iQ.c();
        AbstractC0936Ds.c(h00C, "typeTable");
        List<WO> listP = c1840dP.p();
        if (listP.isEmpty()) {
            listP = null;
        }
        if (listP == null) {
            List<Integer> listO = c1840dP.o();
            AbstractC0936Ds.b(listO, "upperBoundIdList");
            ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listO));
            for (Integer num : listO) {
                AbstractC0936Ds.b(num, "it");
                arrayList.add(h00C.a(num.intValue()));
            }
            listP = arrayList;
        }
        for (WO wo : listP) {
            AbstractC1254Oy abstractC1254OyA = abstractC1200My.a(a(wo));
            if (abstractC1254OyA != null) {
                a(abstractC1254OyA, wo, c2234iQ);
            }
        }
        Iterator it = c2234iQ.a().iterator();
        while (it.hasNext()) {
            ((C3317wu) ((InterfaceC3343xE) it.next())).a(abstractC1200My, c1840dP, c2234iQ);
        }
        abstractC1200My.a();
    }

    private static final void a(AbstractC1254Oy abstractC1254Oy, WO wo, C2234iQ c2234iQ) {
        WO woA;
        WO woA2;
        WO woA3;
        EnumC1364Sy enumC1364Sy;
        WO woA4;
        if (wo.B()) {
            abstractC1254Oy.a(c2234iQ.a(wo.n()));
        } else if (wo.J()) {
            abstractC1254Oy.b(c2234iQ.a(wo.w()));
        } else if (wo.K()) {
            abstractC1254Oy.c(wo.x());
        } else if (wo.L()) {
            Integer numC = c2234iQ.c(wo.y());
            if (numC != null) {
                abstractC1254Oy.c(numC.intValue());
            } else {
                throw new C1640an("No type parameter id for " + c2234iQ.b(wo.y()), null);
            }
        } else {
            throw new C1640an("No classifier (class, type alias or type parameter) recorded for Type", null);
        }
        for (UO uo : wo.m()) {
            TO toH = uo.h();
            AbstractC0936Ds.a(toH);
            int iOrdinal = toH.ordinal();
            if (iOrdinal == 0) {
                enumC1364Sy = EnumC1364Sy.b;
            } else if (iOrdinal == 1) {
                enumC1364Sy = EnumC1364Sy.c;
            } else if (iOrdinal == 2) {
                enumC1364Sy = EnumC1364Sy.a;
            } else {
                if (iOrdinal != 3) {
                    throw new C2692oH();
                }
                enumC1364Sy = null;
            }
            if (enumC1364Sy != null) {
                H00 h00C = c2234iQ.c();
                AbstractC0936Ds.c(h00C, "typeTable");
                if (uo.l()) {
                    woA4 = uo.i();
                } else {
                    woA4 = uo.m() ? h00C.a(uo.j()) : null;
                }
                if (woA4 != null) {
                    AbstractC1254Oy abstractC1254OyA = abstractC1254Oy.a((woA4.t() ? 1 : 0) + (woA4.p() << 1), enumC1364Sy);
                    if (abstractC1254OyA != null) {
                        a(abstractC1254OyA, woA4, c2234iQ);
                    }
                } else {
                    throw new C1640an("No type argument for non-STAR projection in Type", null);
                }
            } else {
                abstractC1254Oy.b();
            }
        }
        H00 h00C2 = c2234iQ.c();
        AbstractC0936Ds.c(h00C2, "typeTable");
        if (wo.z()) {
            woA = wo.k();
        } else {
            woA = wo.A() ? h00C2.a(wo.l()) : null;
        }
        if (woA != null) {
            AbstractC1254Oy abstractC1254OyA2 = abstractC1254Oy.a((woA.t() ? 1 : 0) + (woA.p() << 1));
            if (abstractC1254OyA2 != null) {
                a(abstractC1254OyA2, woA, c2234iQ);
            }
        }
        H00 h00C3 = c2234iQ.c();
        AbstractC0936Ds.c(h00C3, "typeTable");
        if (wo.H()) {
            woA2 = wo.u();
        } else {
            woA2 = wo.I() ? h00C3.a(wo.v()) : null;
        }
        if (woA2 != null) {
            AbstractC1254Oy abstractC1254OyB = abstractC1254Oy.b((woA2.t() ? 1 : 0) + (woA2.p() << 1));
            if (abstractC1254OyB != null) {
                a(abstractC1254OyB, woA2, c2234iQ);
            }
        }
        H00 h00C4 = c2234iQ.c();
        AbstractC0936Ds.c(h00C4, "typeTable");
        if (wo.E()) {
            woA3 = wo.r();
        } else {
            woA3 = wo.F() ? h00C4.a(wo.s()) : null;
        }
        if (woA3 != null) {
            AbstractC1254Oy abstractC1254OyA3 = abstractC1254Oy.a((woA3.t() ? 1 : 0) + (woA3.p() << 1), wo.D() ? c2234iQ.b(wo.q()) : null);
            if (abstractC1254OyA3 != null) {
                a(abstractC1254OyA3, woA3, c2234iQ);
            }
        }
        Iterator it = c2234iQ.a().iterator();
        while (it.hasNext()) {
            ((C3317wu) ((InterfaceC3343xE) it.next())).a(abstractC1254Oy, wo, c2234iQ);
        }
        abstractC1254Oy.a();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x008c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:23:0x008e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:24:0x0090  */
    /* JADX WARN: Code duplicated, block: B:25:0x0092  */
    /* JADX WARN: Code duplicated, block: B:27:0x0098  */
    /* JADX WARN: Code duplicated, block: B:28:0x009a  */
    /* JADX WARN: Code duplicated, block: B:31:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:32:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:35:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:36:0x00bc  */
    private static final void a(int i, AbstractC1520Xy abstractC1520Xy, C2234iQ c2234iQ) {
        C2510m20 c2510m20;
        C2510m20 c2510m21;
        int iOrdinal;
        int i2;
        Integer numValueOf;
        String strA;
        C2584n20 c2584n20;
        EnumC1494Wy enumC1494Wy;
        EnumC1465Vy enumC1465Vy;
        InterfaceC2600nG interfaceC2600nGB = c2234iQ.b();
        C2668o20 c2668o20D = c2234iQ.d();
        AbstractC0936Ds.c(interfaceC2600nGB, "nameResolver");
        AbstractC0936Ds.c(c2668o20D, "table");
        C2700oP c2700oPA = c2668o20D.a(i);
        if (c2700oPA == null) {
            c2584n20 = null;
        } else {
            Integer numValueOf2 = c2700oPA.q() ? Integer.valueOf(c2700oPA.k()) : null;
            Integer numValueOf3 = c2700oPA.r() ? Integer.valueOf(c2700oPA.l()) : null;
            if (numValueOf3 != null) {
                c2510m20 = new C2510m20(numValueOf3.intValue() & 255, (numValueOf3.intValue() >> 8) & 255, (numValueOf3.intValue() >> 16) & 255);
            } else {
                if (numValueOf2 != null) {
                    c2510m21 = new C2510m20(numValueOf2.intValue() & 7, (numValueOf2.intValue() >> 3) & 15, (numValueOf2.intValue() >> 7) & 127);
                } else {
                    c2510m20 = C2510m20.d;
                }
                EnumC2535mP enumC2535mPI = c2700oPA.i();
                AbstractC0936Ds.a(enumC2535mPI);
                iOrdinal = enumC2535mPI.ordinal();
                if (iOrdinal != 0) {
                    i2 = 1;
                } else if (iOrdinal != 1) {
                    i2 = 2;
                } else {
                    if (iOrdinal == 2) {
                        throw new C2692oH();
                    }
                    i2 = 3;
                }
                if (c2700oPA.n()) {
                    numValueOf = Integer.valueOf(c2700oPA.h());
                } else {
                    numValueOf = null;
                }
                if (c2700oPA.p()) {
                    strA = interfaceC2600nGB.a(c2700oPA.j());
                } else {
                    strA = null;
                }
                EnumC2609nP enumC2609nPM = c2700oPA.m();
                AbstractC0936Ds.b(enumC2609nPM, "info.versionKind");
                c2584n20 = new C2584n20(c2510m21, enumC2609nPM, i2, numValueOf, strA);
            }
            c2510m21 = c2510m20;
            EnumC2535mP enumC2535mPI2 = c2700oPA.i();
            AbstractC0936Ds.a(enumC2535mPI2);
            iOrdinal = enumC2535mPI2.ordinal();
            if (iOrdinal != 0) {
                i2 = 1;
            } else if (iOrdinal != 1) {
                i2 = 2;
            } else {
                if (iOrdinal == 2) {
                    throw new C2692oH();
                }
                i2 = 3;
            }
            if (c2700oPA.n()) {
                numValueOf = Integer.valueOf(c2700oPA.h());
            } else {
                numValueOf = null;
            }
            if (c2700oPA.p()) {
                strA = interfaceC2600nGB.a(c2700oPA.j());
            } else {
                strA = null;
            }
            EnumC2609nP enumC2609nPM2 = c2700oPA.m();
            AbstractC0936Ds.b(enumC2609nPM2, "info.versionKind");
            c2584n20 = new C2584n20(c2510m21, enumC2609nPM2, i2, numValueOf, strA);
        }
        if (c2584n20 != null) {
            int iOrdinal2 = c2584n20.b().ordinal();
            if (iOrdinal2 == 0) {
                enumC1494Wy = EnumC1494Wy.a;
            } else if (iOrdinal2 == 1) {
                enumC1494Wy = EnumC1494Wy.b;
            } else {
                if (iOrdinal2 != 2) {
                    throw new C2692oH();
                }
                enumC1494Wy = EnumC1494Wy.c;
            }
            int iB = AbstractC0403b.b(c2584n20.c());
            if (iB == 0) {
                enumC1465Vy = EnumC1465Vy.a;
            } else if (iB == 1) {
                enumC1465Vy = EnumC1465Vy.b;
            } else {
                if (iB != 2) {
                    throw new C2692oH();
                }
                enumC1465Vy = EnumC1465Vy.c;
            }
            abstractC1520Xy.a(enumC1494Wy, enumC1465Vy, c2584n20.a(), c2584n20.d());
            C2510m20 c2510m20E = c2584n20.e();
            abstractC1520Xy.a(c2510m20E.a(), c2510m20E.b(), c2510m20E.c());
            abstractC1520Xy.a();
            return;
        }
        throw new C1640an("No VersionRequirement with the given id in the table", null);
    }

    private static final void a(C3125uO c3125uO, AbstractC2191hy abstractC2191hy, C2234iQ c2234iQ) {
        Boolean bool;
        WO woA = null;
        abstractC2191hy.a(c3125uO.j(), c3125uO.s() ? Integer.valueOf(c3125uO.n()) : null);
        if (c3125uO.o()) {
            EnumC3056tO enumC3056tOH = c3125uO.h();
            AbstractC0936Ds.a(enumC3056tOH);
            int iOrdinal = enumC3056tOH.ordinal();
            if (iOrdinal == 0) {
                bool = Boolean.TRUE;
            } else if (iOrdinal == 1) {
                bool = Boolean.FALSE;
            } else {
                if (iOrdinal != 2) {
                    throw new C2692oH();
                }
                bool = null;
            }
            abstractC2191hy.a(bool);
        }
        H00 h00C = c2234iQ.c();
        AbstractC0936Ds.c(h00C, "typeTable");
        if (c3125uO.q()) {
            woA = c3125uO.k();
        } else if (c3125uO.r()) {
            woA = h00C.a(c3125uO.l());
        }
        if (woA != null) {
            AbstractC1254Oy abstractC1254OyA = abstractC2191hy.a((woA.t() ? 1 : 0) + (woA.p() << 1));
            if (abstractC1254OyA != null) {
                a(abstractC1254OyA, woA, c2234iQ);
            }
        }
        for (C3125uO c3125uO2 : c3125uO.g()) {
            AbstractC2191hy abstractC2191hyA = abstractC2191hy.a();
            if (abstractC2191hyA != null) {
                AbstractC0936Ds.b(c3125uO2, "andArgument");
                a(c3125uO2, abstractC2191hyA, c2234iQ);
            }
        }
        for (C3125uO c3125uO3 : c3125uO.m()) {
            AbstractC2191hy abstractC2191hyC = abstractC2191hy.c();
            if (abstractC2191hyC != null) {
                AbstractC0936Ds.b(c3125uO3, "orArgument");
                a(c3125uO3, abstractC2191hyC, c2234iQ);
            }
        }
        abstractC2191hy.b();
    }

    private static final int a(WO wo) {
        boolean zT = wo.t();
        return (zT ? 1 : 0) + (wo.p() << 1);
    }

    public static final int a(FO fo) {
        if (fo.A()) {
            return fo.o();
        }
        int iN = fo.n();
        Boolean boolB = AbstractC3382xi.c.b(iN);
        AbstractC0936Ds.b(boolB, "HAS_ANNOTATIONS.get(flags)");
        return AbstractC3382xi.a(boolB.booleanValue(), (EnumC2988sP) AbstractC3382xi.d.a(iN), (EnumC3502zO) AbstractC3382xi.e.a(iN));
    }
}
