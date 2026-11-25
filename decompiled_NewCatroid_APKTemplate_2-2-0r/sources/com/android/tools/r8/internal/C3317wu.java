package com.android.tools.r8.internal;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3597a;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3598b;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3317wu implements InterfaceC3343xE {
    public void a(AbstractC1029Gy abstractC1029Gy, ZO zo, C2234iQ c2234iQ) {
        AbstractC0936Ds.c(abstractC1029Gy, RegisterSpec.PREFIX);
        AbstractC0936Ds.c(zo, "proto");
        AbstractC0936Ds.c(c2234iQ, "c");
    }

    public InterfaceC1519Xx b() {
        return new C2186hu();
    }

    public InterfaceC2887qy c() {
        return new C2338ju();
    }

    public InterfaceC3322wy d() {
        return new C1025Gu();
    }

    public InterfaceC0848Ay e() {
        return new C1056Hu();
    }

    public InterfaceC1000Fy f() {
        return null;
    }

    public InterfaceC1060Hy g() {
        return new C1723bv();
    }

    public InterfaceC1147Ky h() {
        return new C1800cv();
    }

    public InterfaceC1311Qy i() {
        return null;
    }

    public void a(AbstractC1338Ry abstractC1338Ry, C2303jP c2303jP, C2234iQ c2234iQ) {
        AbstractC0936Ds.c(abstractC1338Ry, RegisterSpec.PREFIX);
        AbstractC0936Ds.c(c2303jP, "proto");
        AbstractC0936Ds.c(c2234iQ, "c");
    }

    public static final C1223Nu a(C3317wu c3317wu, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.g gVar, Q20 q20) {
        c3317wu.getClass();
        C1196Mu c1196MuM = C1223Nu.m();
        c1196MuM.b(q20.a(gVar.c()));
        c1196MuM.a(q20.a(gVar.b()));
        C1223Nu c1223NuG = c1196MuM.g();
        if (c1223NuG.a()) {
            return c1223NuG;
        }
        throw new C1659b10();
    }

    public void a(AbstractC1363Sx abstractC1363Sx, C1761cO c1761cO, C2234iQ c2234iQ) {
        String strB;
        AbstractC0936Ds.c(abstractC1363Sx, RegisterSpec.PREFIX);
        AbstractC0936Ds.c(c1761cO, "proto");
        AbstractC0936Ds.c(c2234iQ, "c");
        InterfaceC1337Rx interfaceC1337RxA = abstractC1363Sx.a(AbstractC3597a.b);
        AbstractC3597a abstractC3597a = interfaceC1337RxA instanceof AbstractC3597a ? (AbstractC3597a) interfaceC1337RxA : null;
        if (abstractC3597a == null) {
            return;
        }
        C1012Gj c1012Gj = AbstractC1543Yu.k;
        AbstractC0936Ds.b(c1012Gj, "anonymousObjectOriginName");
        Integer num = (Integer) AbstractC3057tP.a(c1761cO, c1012Gj);
        if (num != null) {
            abstractC3597a.b(c2234iQ.b(num.intValue()));
        }
        for (FO fo : (List) c1761cO.a(AbstractC1543Yu.j)) {
            AbstractC0914Cy abstractC0914CyA = abstractC3597a.a(fo.n(), AbstractC2462lQ.a(fo), AbstractC2462lQ.b(fo), c2234iQ.b(fo.p()));
            if (abstractC0914CyA != null) {
                AbstractC2462lQ.a(abstractC0914CyA, fo, c2234iQ);
            }
        }
        C1012Gj c1012Gj2 = AbstractC1543Yu.i;
        AbstractC0936Ds.b(c1012Gj2, "classModuleName");
        Integer num2 = (Integer) AbstractC3057tP.a(c1761cO, c1012Gj2);
        if (num2 == null || (strB = c2234iQ.b(num2.intValue())) == null) {
            strB = "main";
        }
        abstractC3597a.a(strB);
        C1012Gj c1012Gj3 = AbstractC1543Yu.l;
        AbstractC0936Ds.b(c1012Gj3, "jvmClassFlags");
        Integer num3 = (Integer) AbstractC3057tP.a(c1761cO, c1012Gj3);
        if (num3 != null) {
            abstractC3597a.a(num3.intValue());
        }
        abstractC3597a.b();
    }

    public void a(AbstractC3468yy abstractC3468yy, CO co, C2234iQ c2234iQ) {
        String strB;
        AbstractC0936Ds.c(abstractC3468yy, RegisterSpec.PREFIX);
        AbstractC0936Ds.c(co, "proto");
        AbstractC0936Ds.c(c2234iQ, "c");
        InterfaceC3399xy interfaceC3399xyA = abstractC3468yy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k.b);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k kVar = interfaceC3399xyA instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k ? (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k) interfaceC3399xyA : null;
        if (kVar == null) {
            return;
        }
        for (FO fo : (List) co.a(AbstractC1543Yu.n)) {
            AbstractC0914Cy abstractC0914CyA = kVar.a(fo.n(), AbstractC2462lQ.a(fo), AbstractC2462lQ.b(fo), c2234iQ.b(fo.p()));
            if (abstractC0914CyA != null) {
                AbstractC2462lQ.a(abstractC0914CyA, fo, c2234iQ);
            }
        }
        C1012Gj c1012Gj = AbstractC1543Yu.m;
        AbstractC0936Ds.b(c1012Gj, "packageModuleName");
        Integer num = (Integer) AbstractC3057tP.a(co, c1012Gj);
        if (num == null || (strB = c2234iQ.b(num.intValue())) == null) {
            strB = "main";
        }
        kVar.a(strB);
        kVar.b();
    }

    public void a(AbstractC3025sy abstractC3025sy, C3354xO c3354xO, C2234iQ c2234iQ) {
        AbstractC0936Ds.c(abstractC3025sy, RegisterSpec.PREFIX);
        AbstractC0936Ds.c(c3354xO, "proto");
        AbstractC0936Ds.c(c2234iQ, "c");
        InterfaceC2956ry interfaceC2956ryA = abstractC3025sy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f.b);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f fVar = interfaceC2956ryA instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f ? (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f) interfaceC2956ryA : null;
        if (fVar == null) {
            return;
        }
        int i = C1569Zu.b;
        C2495lu c2495luA = C1569Zu.a(c3354xO, c2234iQ.b(), c2234iQ.c());
        fVar.a(c2495luA != null ? new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(c2495luA.c(), c2495luA.b()) : null);
        C1012Gj c1012Gj = AbstractC1543Yu.c;
        AbstractC0936Ds.b(c1012Gj, "lambdaClassOriginName");
        Integer num = (Integer) AbstractC3057tP.a(c3354xO, c1012Gj);
        if (num != null) {
            fVar.a(c2234iQ.b(num.intValue()));
        }
        fVar.a();
    }

    public void a(AbstractC0914Cy abstractC0914Cy, FO fo, C2234iQ c2234iQ) {
        AbstractC0936Ds.c(abstractC0914Cy, RegisterSpec.PREFIX);
        AbstractC0936Ds.c(fo, "proto");
        AbstractC0936Ds.c(c2234iQ, "c");
        InterfaceC0874By interfaceC0874ByA = abstractC0914Cy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l.b);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l lVar = interfaceC0874ByA instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l ? (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l) interfaceC0874ByA : null;
        if (lVar == null) {
            return;
        }
        int i = C1569Zu.b;
        C2419ku c2419kuA = C1569Zu.a(fo, c2234iQ.b(), c2234iQ.c());
        C1012Gj c1012Gj = AbstractC1543Yu.d;
        AbstractC0936Ds.b(c1012Gj, "propertySignature");
        C1307Qu c1307Qu = (C1307Qu) AbstractC3057tP.a(fo, c1012Gj);
        C1223Nu c1223NuJ = (c1307Qu == null || !c1307Qu.o()) ? null : c1307Qu.j();
        C1223Nu c1223NuK = (c1307Qu == null || !c1307Qu.p()) ? null : c1307Qu.k();
        Object objA = fo.a(AbstractC1543Yu.e);
        AbstractC0936Ds.b(objA, "proto.getExtension(JvmProtoBuf.flags)");
        lVar.a(((Number) objA).intValue(), c2419kuA != null ? new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e(c2419kuA.c(), c2419kuA.b()) : null, c1223NuJ != null ? new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(c2234iQ.b(c1223NuJ.i()), c2234iQ.b(c1223NuJ.h())) : null, c1223NuK != null ? new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(c2234iQ.b(c1223NuK.i()), c2234iQ.b(c1223NuK.h())) : null);
        C1223Nu c1223NuL = (c1307Qu == null || !c1307Qu.q()) ? null : c1307Qu.l();
        lVar.a(c1223NuL != null ? new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(c2234iQ.b(c1223NuL.i()), c2234iQ.b(c1223NuL.h())) : null);
        C1223Nu c1223NuH = (c1307Qu == null || !c1307Qu.m()) ? null : c1307Qu.h();
        lVar.b(c1223NuH != null ? new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(c2234iQ.b(c1223NuH.i()), c2234iQ.b(c1223NuH.h())) : null);
        lVar.b();
    }

    public void a(AbstractC1572Zx abstractC1572Zx, C1998fO c1998fO, C2234iQ c2234iQ) {
        AbstractC0936Ds.c(abstractC1572Zx, RegisterSpec.PREFIX);
        AbstractC0936Ds.c(c1998fO, "proto");
        AbstractC0936Ds.c(c2234iQ, "c");
        InterfaceC1546Yx interfaceC1546YxA = abstractC1572Zx.a(AbstractC3598b.a);
        AbstractC3598b abstractC3598b = interfaceC1546YxA instanceof AbstractC3598b ? (AbstractC3598b) interfaceC1546YxA : null;
        if (abstractC3598b == null) {
            return;
        }
        int i = C1569Zu.b;
        C2495lu c2495luA = C1569Zu.a(c1998fO, c2234iQ.b(), c2234iQ.c());
        abstractC3598b.a(c2495luA != null ? new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(c2495luA.c(), c2495luA.b()) : null);
    }

    public void a(AbstractC1200My abstractC1200My, C1840dP c1840dP, C2234iQ c2234iQ) {
        AbstractC0936Ds.c(abstractC1200My, RegisterSpec.PREFIX);
        AbstractC0936Ds.c(c1840dP, "proto");
        AbstractC0936Ds.c(c2234iQ, "c");
        InterfaceC1173Ly interfaceC1173LyA = abstractC1200My.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n.b);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n nVar = interfaceC1173LyA instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n ? (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n) interfaceC1173LyA : null;
        if (nVar == null) {
            return;
        }
        for (XN xn : (List) c1840dP.a(AbstractC1543Yu.h)) {
            AbstractC0936Ds.b(xn, "annotation");
            nVar.a(AbstractC2382kQ.a(xn, c2234iQ.b()));
        }
        nVar.a();
    }

    public void a(AbstractC1254Oy abstractC1254Oy, WO wo, C2234iQ c2234iQ) {
        AbstractC0936Ds.c(abstractC1254Oy, RegisterSpec.PREFIX);
        AbstractC0936Ds.c(wo, "proto");
        AbstractC0936Ds.c(c2234iQ, "c");
        InterfaceC1090Iy interfaceC1090IyA = abstractC1254Oy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m.b);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m mVar = interfaceC1090IyA instanceof com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m ? (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m) interfaceC1090IyA : null;
        if (mVar == null) {
            return;
        }
        Object objA = wo.a(AbstractC1543Yu.g);
        AbstractC0936Ds.b(objA, "proto.getExtension(JvmProtoBuf.isRaw)");
        mVar.a(((Boolean) objA).booleanValue());
        for (XN xn : (List) wo.a(AbstractC1543Yu.f)) {
            AbstractC0936Ds.b(xn, "annotation");
            mVar.a(AbstractC2382kQ.a(xn, c2234iQ.b()));
        }
        mVar.a();
    }

    public InterfaceC1337Rx a(C2576my c2576my, C1610aO c1610aO, Q20 q20) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC0936Ds.c(c1610aO, "proto");
        AbstractC0936Ds.c(q20, "c");
        if (AbstractC0936Ds.a(c2576my, AbstractC3597a.b)) {
            return new C2735ou(c1610aO, q20);
        }
        return null;
    }

    public InterfaceC3399xy a(C2576my c2576my, BO bo, Q20 q20) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC0936Ds.c(bo, "proto");
        AbstractC0936Ds.c(q20, "c");
        if (AbstractC0936Ds.a(c2576my, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k.b)) {
            return new C3021su(q20, bo);
        }
        return null;
    }

    public InterfaceC2956ry a(C2576my c2576my, C3285wO c3285wO, Q20 q20) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC0936Ds.c(c3285wO, "proto");
        AbstractC0936Ds.c(q20, "c");
        if (AbstractC0936Ds.a(c2576my, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f.b)) {
            return new C2882qu(c3285wO, this, q20);
        }
        return null;
    }

    public InterfaceC0874By a(C2576my c2576my, EO eo, Q20 q20) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC0936Ds.c(eo, "proto");
        AbstractC0936Ds.c(q20, "c");
        if (AbstractC0936Ds.a(c2576my, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.l.b)) {
            return new C3091tu(this, q20, eo);
        }
        return null;
    }

    public InterfaceC1546Yx a(C2576my c2576my, C1921eO c1921eO, Q20 q20) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC0936Ds.c(c1921eO, "proto");
        AbstractC0936Ds.c(q20, "c");
        if (AbstractC0936Ds.a(c2576my, AbstractC3598b.a)) {
            return new C2811pu(c1921eO, this, q20);
        }
        return null;
    }

    public InterfaceC1173Ly a(C2576my c2576my, C1691bP c1691bP, Q20 q20) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC0936Ds.c(c1691bP, "proto");
        AbstractC0936Ds.c(q20, "c");
        if (AbstractC0936Ds.a(c2576my, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n.b)) {
            return new C3242vu(c1691bP, q20);
        }
        return null;
    }

    public InterfaceC1090Iy a(C2576my c2576my, VO vo, Q20 q20) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC0936Ds.c(vo, "proto");
        AbstractC0936Ds.c(q20, "c");
        if (AbstractC0936Ds.a(c2576my, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m.b)) {
            return new C3168uu(vo, q20);
        }
        return null;
    }

    public InterfaceC1310Qx a() {
        return new C2031fu();
    }
}
