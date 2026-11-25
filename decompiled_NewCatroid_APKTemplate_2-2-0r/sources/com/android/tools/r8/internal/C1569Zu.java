package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1569Zu {
    private static final C2867qh a;
    public static final /* synthetic */ int b = 0;

    static {
        C2867qh c2867qhA = C2867qh.a();
        c2867qhA.a(AbstractC1543Yu.a);
        c2867qhA.a(AbstractC1543Yu.b);
        c2867qhA.a(AbstractC1543Yu.c);
        c2867qhA.a(AbstractC1543Yu.d);
        c2867qhA.a(AbstractC1543Yu.e);
        c2867qhA.a(AbstractC1543Yu.f);
        c2867qhA.a(AbstractC1543Yu.g);
        c2867qhA.a(AbstractC1543Yu.h);
        c2867qhA.a(AbstractC1543Yu.i);
        c2867qhA.a(AbstractC1543Yu.j);
        c2867qhA.a(AbstractC1543Yu.k);
        c2867qhA.a(AbstractC1543Yu.l);
        c2867qhA.a(AbstractC1543Yu.m);
        c2867qhA.a(AbstractC1543Yu.n);
        a = c2867qhA;
    }

    private C1569Zu() {
    }

    public static C2419ku a(FO fo, InterfaceC2600nG interfaceC2600nG, H00 h00) {
        String strA;
        AbstractC0936Ds.c(fo, "proto");
        AbstractC0936Ds.c(interfaceC2600nG, "nameResolver");
        AbstractC0936Ds.c(h00, "typeTable");
        C1012Gj c1012Gj = AbstractC1543Yu.d;
        AbstractC0936Ds.b(c1012Gj, "propertySignature");
        C1307Qu c1307Qu = (C1307Qu) AbstractC3057tP.a(fo, c1012Gj);
        if (c1307Qu == null) {
            return null;
        }
        C1143Ku c1143KuI = c1307Qu.n() ? c1307Qu.i() : null;
        if (c1143KuI == null) {
            return null;
        }
        int iP = (c1143KuI == null || !c1143KuI.k()) ? fo.p() : c1143KuI.i();
        if (c1143KuI == null || !c1143KuI.j()) {
            WO woA = YP.a(fo, h00);
            strA = woA.B() ? AbstractC2712oa.a(interfaceC2600nG.c(woA.n())) : null;
            if (strA == null) {
                return null;
            }
        } else {
            strA = interfaceC2600nG.a(c1143KuI.h());
        }
        return new C2419ku(interfaceC2600nG.a(iP), strA);
    }

    public static final C2772pL b(String[] strArr, String[] strArr2) {
        AbstractC0936Ds.c(strArr2, "strings");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Z4.a(strArr));
        C2867qh c2867qh = a;
        C1516Xu c1516Xu = (C1516Xu) ((AbstractC1732c1) C1516Xu.h).a(byteArrayInputStream, c2867qh);
        AbstractC0936Ds.b(c1516Xu, "parseDelimitedFrom(this, EXTENSION_REGISTRY)");
        return new C2772pL(new C0970Eu(c1516Xu, strArr2), (C3354xO) ((AbstractC1732c1) C3354xO.v).a((InputStream) byteArrayInputStream, c2867qh));
    }

    public static final C2772pL c(String[] strArr, String[] strArr2) {
        AbstractC0936Ds.c(strArr2, "strings");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Z4.a(strArr));
        C2867qh c2867qh = a;
        C1516Xu c1516Xu = (C1516Xu) ((AbstractC1732c1) C1516Xu.h).a(byteArrayInputStream, c2867qh);
        AbstractC0936Ds.b(c1516Xu, "parseDelimitedFrom(this, EXTENSION_REGISTRY)");
        return new C2772pL(new C0970Eu(c1516Xu, strArr2), (CO) ((AbstractC1732c1) CO.l).a((InputStream) byteArrayInputStream, c2867qh));
    }

    public static final C2772pL a(String[] strArr, String[] strArr2) {
        AbstractC0936Ds.c(strArr2, "strings");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Z4.a(strArr));
        C2867qh c2867qh = a;
        C1516Xu c1516Xu = (C1516Xu) ((AbstractC1732c1) C1516Xu.h).a(byteArrayInputStream, c2867qh);
        AbstractC0936Ds.b(c1516Xu, "parseDelimitedFrom(this, EXTENSION_REGISTRY)");
        return new C2772pL(new C0970Eu(c1516Xu, strArr2), (C1761cO) ((AbstractC1732c1) C1761cO.K).a((InputStream) byteArrayInputStream, c2867qh));
    }

    public static C2495lu a(C3354xO c3354xO, InterfaceC2600nG interfaceC2600nG, H00 h00) {
        WO woA;
        WO woA2;
        String strConcat;
        AbstractC0936Ds.c(c3354xO, "proto");
        AbstractC0936Ds.c(interfaceC2600nG, "nameResolver");
        AbstractC0936Ds.c(h00, "typeTable");
        C1012Gj c1012Gj = AbstractC1543Yu.b;
        AbstractC0936Ds.b(c1012Gj, "methodSignature");
        C1223Nu c1223Nu = (C1223Nu) AbstractC3057tP.a(c3354xO, c1012Gj);
        int iP = (c1223Nu == null || !c1223Nu.k()) ? c3354xO.p() : c1223Nu.i();
        if (c1223Nu != null && c1223Nu.j()) {
            strConcat = interfaceC2600nG.a(c1223Nu.h());
        } else {
            if (c3354xO.D()) {
                woA = c3354xO.r();
            } else {
                woA = c3354xO.E() ? h00.a(c3354xO.s()) : null;
            }
            List listA = AbstractC0823Ab.a(woA);
            List<C2303jP> listX = c3354xO.x();
            AbstractC0936Ds.b(listX, "proto.valueParameterList");
            ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listX));
            for (C2303jP c2303jP : listX) {
                AbstractC0936Ds.b(c2303jP, "it");
                arrayList.add(YP.a(c2303jP, h00));
            }
            ArrayList<WO> arrayListA = AbstractC0823Ab.a(arrayList, listA);
            ArrayList arrayList2 = new ArrayList(AbstractC0823Ab.a((Iterable) arrayListA));
            for (WO wo : arrayListA) {
                String strA = wo.B() ? AbstractC2712oa.a(interfaceC2600nG.c(wo.n())) : null;
                if (strA == null) {
                    return null;
                }
                arrayList2.add(strA);
            }
            if (c3354xO.F()) {
                woA2 = c3354xO.t();
                AbstractC0936Ds.b(woA2, "returnType");
            } else {
                if (!c3354xO.G()) {
                    throw new IllegalStateException("No returnType in ProtoBuf.Function".toString());
                }
                woA2 = h00.a(c3354xO.u());
            }
            String strA2 = woA2.B() ? AbstractC2712oa.a(interfaceC2600nG.c(woA2.n())) : null;
            if (strA2 == null) {
                return null;
            }
            strConcat = AbstractC0823Ab.a(arrayList2, "", "(", Constant.AFTER_QUTO, null, 56).concat(strA2);
        }
        return new C2495lu(interfaceC2600nG.a(iP), strConcat);
    }

    public static C2495lu a(C1998fO c1998fO, InterfaceC2600nG interfaceC2600nG, H00 h00) {
        String strA;
        String strA2;
        AbstractC0936Ds.c(c1998fO, "proto");
        AbstractC0936Ds.c(interfaceC2600nG, "nameResolver");
        AbstractC0936Ds.c(h00, "typeTable");
        C1012Gj c1012Gj = AbstractC1543Yu.a;
        AbstractC0936Ds.b(c1012Gj, "constructorSignature");
        C1223Nu c1223Nu = (C1223Nu) AbstractC3057tP.a(c1998fO, c1012Gj);
        if (c1223Nu != null && c1223Nu.k()) {
            strA = interfaceC2600nG.a(c1223Nu.i());
        } else {
            strA = "<init>";
        }
        if (c1223Nu != null && c1223Nu.j()) {
            strA2 = interfaceC2600nG.a(c1223Nu.h());
        } else {
            List<C2303jP> listM = c1998fO.m();
            AbstractC0936Ds.b(listM, "proto.valueParameterList");
            ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listM));
            for (C2303jP c2303jP : listM) {
                AbstractC0936Ds.b(c2303jP, "it");
                WO woA = YP.a(c2303jP, h00);
                String strA3 = woA.B() ? AbstractC2712oa.a(interfaceC2600nG.c(woA.n())) : null;
                if (strA3 == null) {
                    return null;
                }
                arrayList.add(strA3);
            }
            strA2 = AbstractC0823Ab.a(arrayList, "", "(", ")V", null, 56);
        }
        return new C2495lu(strA, strA2);
    }
}
