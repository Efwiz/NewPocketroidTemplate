package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2382kQ {
    public static final C3321wx a(XN xn, InterfaceC2600nG interfaceC2600nG) {
        AbstractC0936Ds.c(interfaceC2600nG, "strings");
        int i = xn.i();
        String strC = interfaceC2600nG.c(i);
        if (interfaceC2600nG.b(i)) {
            strC = "." + strC;
        }
        List<VN> listG = xn.g();
        AbstractC0936Ds.b(listG, "argumentList");
        ArrayList arrayList = new ArrayList();
        for (VN vn : listG) {
            UN unI = vn.i();
            AbstractC0936Ds.b(unI, "argument.value");
            AbstractC3467yx abstractC3467yxA = a(unI, interfaceC2600nG);
            C2772pL c2772pL = abstractC3467yxA != null ? new C2772pL(interfaceC2600nG.a(vn.h()), abstractC3467yxA) : null;
            if (c2772pL != null) {
                arrayList.add(c2772pL);
            }
        }
        return new C3321wx(strC, SD.a(arrayList));
    }

    public static final AbstractC3467yx a(UN un, InterfaceC2600nG interfaceC2600nG) {
        AbstractC3467yx c0847Ax;
        AbstractC3467yx c1146Kx;
        AbstractC3467yx c1028Gx;
        AbstractC0936Ds.c(interfaceC2600nG, "strings");
        Boolean boolB = AbstractC3382xi.w.b(un.n());
        AbstractC0936Ds.b(boolB, "Flags.IS_UNSIGNED[flags]");
        if (boolB.booleanValue()) {
            TN tnR = un.r();
            int i = tnR != null ? AbstractC2304jQ.a[tnR.ordinal()] : -1;
            if (i == 1) {
                return new C1172Lx((byte) un.p(), 0);
            }
            if (i == 2) {
                return new C1253Ox((short) un.p(), 0);
            }
            if (i == 3) {
                return new C1199Mx((int) un.p(), 0);
            }
            if (i != 4) {
                throw new IllegalStateException(("Cannot read value of unsigned type: " + un.r()).toString());
            }
            return new C1226Nx(un.p(), 0);
        }
        TN tnR2 = un.r();
        switch (tnR2 != null ? AbstractC2304jQ.a[tnR2.ordinal()] : -1) {
            case -1:
                return null;
            case 0:
            default:
                throw new C2692oH();
            case 1:
                c0847Ax = new C0847Ax((byte) un.p());
                return c0847Ax;
            case 2:
                c0847Ax = new C1118Jx((short) un.p());
                return c0847Ax;
            case 3:
                c0847Ax = new C0999Fx((int) un.p());
                return c0847Ax;
            case 4:
                c0847Ax = new C1089Ix(un.p());
                return c0847Ax;
            case 5:
                c0847Ax = new C0873Bx((char) un.p());
                return c0847Ax;
            case 6:
                c0847Ax = new C0973Ex(un.o());
                return c0847Ax;
            case 7:
                c0847Ax = new C0913Cx(un.l());
                return c0847Ax;
            case 8:
                c0847Ax = new C3538zx(un.p() != 0);
                return c0847Ax;
            case 9:
                c1146Kx = new C1146Kx(interfaceC2600nG.a(un.q()));
                return c1146Kx;
            case 10:
                int iJ = un.j();
                String strC = interfaceC2600nG.c(iJ);
                if (interfaceC2600nG.b(iJ)) {
                    strC = "." + strC;
                }
                c1028Gx = new C1028Gx(strC, un.h());
                return c1028Gx;
            case 11:
                int iJ2 = un.j();
                String strC2 = interfaceC2600nG.c(iJ2);
                if (interfaceC2600nG.b(iJ2)) {
                    strC2 = "." + strC2;
                }
                c1028Gx = new C0941Dx(strC2, interfaceC2600nG.a(un.m()));
                return c1028Gx;
            case 12:
                XN xnG = un.g();
                AbstractC0936Ds.b(xnG, "annotation");
                c1146Kx = new C3398xx(a(xnG, interfaceC2600nG));
                return c1146Kx;
            case 13:
                List<UN> listI = un.i();
                AbstractC0936Ds.b(listI, "arrayElementList");
                ArrayList arrayList = new ArrayList();
                for (UN un2 : listI) {
                    AbstractC0936Ds.b(un2, "it");
                    AbstractC3467yx abstractC3467yxA = a(un2, interfaceC2600nG);
                    if (abstractC3467yxA != null) {
                        arrayList.add(abstractC3467yxA);
                    }
                }
                return new AbstractC3467yx.a(arrayList);
        }
    }
}
