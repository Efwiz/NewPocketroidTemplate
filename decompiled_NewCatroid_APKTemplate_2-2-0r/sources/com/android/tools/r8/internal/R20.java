package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class R20 {
    public static final WN a(C3321wx c3321wx, C1648av c1648av) {
        AbstractC0936Ds.c(c3321wx, "<this>");
        AbstractC0936Ds.c(c1648av, "strings");
        WN wnL = XN.l();
        wnL.a(a(c1648av, c3321wx.b()));
        for (Map.Entry<String, AbstractC3467yx> entry : c3321wx.a().entrySet()) {
            String key = entry.getKey();
            AbstractC3467yx value = entry.getValue();
            QN qnM = VN.m();
            qnM.a(c1648av.a(key));
            UN unG = a(value, c1648av).g();
            if (!unG.a()) {
                throw new C1659b10();
            }
            qnM.a(unG);
            wnL.a(qnM);
        }
        return wnL;
    }

    public static final int a(C1648av c1648av, String str) {
        AbstractC0936Ds.c(c1648av, "<this>");
        AbstractC0936Ds.c(str, "name");
        if (AbstractC3068tY.a(str, false)) {
            String strSubstring = str.substring(1);
            AbstractC0936Ds.b(strSubstring, "this as java.lang.String).substring(startIndex)");
            return c1648av.a(strSubstring, true);
        }
        return c1648av.a(str, false);
    }

    public static final SN a(AbstractC3467yx abstractC3467yx, C1648av c1648av) {
        AbstractC0936Ds.c(abstractC3467yx, "<this>");
        AbstractC0936Ds.c(c1648av, "strings");
        SN snD = UN.D();
        if (abstractC3467yx instanceof C0847Ax) {
            snD.a(TN.b);
            snD.a(((C0847Ax) abstractC3467yx).a().byteValue());
        } else if (abstractC3467yx instanceof C0873Bx) {
            snD.a(TN.c);
            snD.a(((C0873Bx) abstractC3467yx).a().charValue());
        } else if (abstractC3467yx instanceof C1118Jx) {
            snD.a(TN.d);
            snD.a(((C1118Jx) abstractC3467yx).a().shortValue());
        } else if (abstractC3467yx instanceof C0999Fx) {
            snD.a(TN.e);
            snD.a(((C0999Fx) abstractC3467yx).a().intValue());
        } else if (abstractC3467yx instanceof C1089Ix) {
            snD.a(TN.f);
            snD.a(((C1089Ix) abstractC3467yx).a().longValue());
        } else if (abstractC3467yx instanceof C0973Ex) {
            snD.a(TN.g);
            snD.a(((C0973Ex) abstractC3467yx).a().floatValue());
        } else if (abstractC3467yx instanceof C0913Cx) {
            snD.a(TN.h);
            snD.a(((C0913Cx) abstractC3467yx).a().doubleValue());
        } else if (abstractC3467yx instanceof C3538zx) {
            snD.a(TN.i);
            snD.a(((C3538zx) abstractC3467yx).a().booleanValue() ? 1L : 0L);
        } else if (abstractC3467yx instanceof C1172Lx) {
            snD.a(TN.b);
            snD.a(((long) ((C1172Lx) abstractC3467yx).a()) & 255);
            snD.d(AbstractC3382xi.w.a(Boolean.TRUE));
        } else if (abstractC3467yx instanceof C1253Ox) {
            snD.a(TN.d);
            snD.a(((long) ((C1253Ox) abstractC3467yx).a()) & WebSocketProtocol.PAYLOAD_SHORT_MAX);
            snD.d(AbstractC3382xi.w.a(Boolean.TRUE));
        } else if (abstractC3467yx instanceof C1199Mx) {
            snD.a(TN.e);
            snD.a(((long) ((C1199Mx) abstractC3467yx).a()) & 4294967295L);
            snD.d(AbstractC3382xi.w.a(Boolean.TRUE));
        } else if (abstractC3467yx instanceof C1226Nx) {
            snD.a(TN.f);
            snD.a(((C1226Nx) abstractC3467yx).a());
            snD.d(AbstractC3382xi.w.a(Boolean.TRUE));
        } else if (abstractC3467yx instanceof C1146Kx) {
            snD.a(TN.j);
            snD.e(c1648av.a(((C1146Kx) abstractC3467yx).a()));
        } else if (abstractC3467yx instanceof C1028Gx) {
            snD.a(TN.k);
            C1028Gx c1028Gx = (C1028Gx) abstractC3467yx;
            snD.b(a(c1648av, c1028Gx.b()));
            snD.a(c1028Gx.a());
        } else if (abstractC3467yx instanceof C0941Dx) {
            snD.a(TN.l);
            C0941Dx c0941Dx = (C0941Dx) abstractC3467yx;
            snD.b(a(c1648av, c0941Dx.a()));
            snD.c(c1648av.a(c0941Dx.b()));
        } else if (abstractC3467yx instanceof C3398xx) {
            snD.a(TN.m);
            XN xnG = a(((C3398xx) abstractC3467yx).a(), c1648av).g();
            if (xnG.a()) {
                snD.a(xnG);
            } else {
                throw new C1659b10();
            }
        } else if (abstractC3467yx instanceof AbstractC3467yx.a) {
            snD.a(TN.n);
            Iterator<AbstractC3467yx> it = ((AbstractC3467yx.a) abstractC3467yx).a().iterator();
            while (it.hasNext()) {
                snD.a(a(it.next(), c1648av));
            }
        }
        return snD;
    }
}
