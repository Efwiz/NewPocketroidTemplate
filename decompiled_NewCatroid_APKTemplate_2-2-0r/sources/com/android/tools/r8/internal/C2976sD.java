package com.android.tools.r8.internal;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2976sD extends EZ {
    private final EZ a;
    private final EZ b;
    private final InterfaceC1833dK c;
    final /* synthetic */ C3045tD d;

    public C2976sD(C3045tD c3045tD, C1862dk c1862dk, Type type, EZ ez, Type type2, EZ ez2, InterfaceC1833dK interfaceC1833dK) {
        this.d = c3045tD;
        this.a = new GZ(c1862dk, ez, type);
        this.b = new GZ(c1862dk, ez2, type2);
        this.c = interfaceC1833dK;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        String strF;
        Map map = (Map) obj;
        if (map == null) {
            c1799cu.l();
            return;
        }
        if (!this.d.b) {
            c1799cu.e();
            for (Map.Entry entry : map.entrySet()) {
                c1799cu.b(String.valueOf(entry.getKey()));
                this.b.a(c1799cu, entry.getValue());
            }
            c1799cu.g();
            return;
        }
        ArrayList arrayList = new ArrayList(map.size());
        ArrayList arrayList2 = new ArrayList(map.size());
        int i = 0;
        boolean z = false;
        for (Map.Entry entry2 : map.entrySet()) {
            EZ ez = this.a;
            Object key = entry2.getKey();
            ez.getClass();
            try {
                C1722bu c1722bu = new C1722bu();
                ez.a(c1722bu, key);
                AbstractC1249Ot abstractC1249OtO = c1722bu.o();
                arrayList.add(abstractC1249OtO);
                arrayList2.add(entry2.getValue());
                abstractC1249OtO.getClass();
                z |= (abstractC1249OtO instanceof C1222Nt) || (abstractC1249OtO instanceof C1333Rt);
            } catch (IOException e) {
                throw new C1279Pt(e);
            }
        }
        if (z) {
            c1799cu.d();
            while (i < arrayList.size()) {
                c1799cu.d();
                AbstractC2961s00.A.a(c1799cu, (AbstractC1249Ot) arrayList.get(i));
                this.b.a(c1799cu, arrayList2.get(i));
                c1799cu.f();
                i++;
            }
            c1799cu.f();
            return;
        }
        c1799cu.e();
        while (i < arrayList.size()) {
            AbstractC1249Ot abstractC1249Ot = (AbstractC1249Ot) arrayList.get(i);
            abstractC1249Ot.getClass();
            boolean z2 = abstractC1249Ot instanceof C1434Ut;
            if (z2) {
                if (!z2) {
                    throw new IllegalStateException("This is not a JSON Primitive.");
                }
                C1434Ut c1434Ut = (C1434Ut) abstractC1249Ot;
                if (c1434Ut.j()) {
                    strF = String.valueOf(c1434Ut.g());
                } else if (c1434Ut.i()) {
                    strF = Boolean.toString(c1434Ut.a());
                } else {
                    if (!c1434Ut.k()) {
                        throw new AssertionError();
                    }
                    strF = c1434Ut.f();
                }
            } else {
                if (!(abstractC1249Ot instanceof C1306Qt)) {
                    throw new AssertionError();
                }
                strF = "null";
            }
            c1799cu.b(strF);
            this.b.a(c1799cu, arrayList2.get(i));
            i++;
        }
        c1799cu.g();
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        int iT = c1486Wt.t();
        if (iT == 9) {
            c1486Wt.q();
            return null;
        }
        Map map = (Map) this.c.a();
        if (iT == 1) {
            c1486Wt.c();
            while (c1486Wt.i()) {
                c1486Wt.c();
                Object objA = this.a.a(c1486Wt);
                if (map.put(objA, this.b.a(c1486Wt)) == null) {
                    c1486Wt.g();
                } else {
                    throw new C1542Yt("duplicate key: " + objA);
                }
            }
            c1486Wt.g();
        } else {
            c1486Wt.d();
            while (c1486Wt.i()) {
                AbstractC1515Xt.a.a(c1486Wt);
                Object objA2 = this.a.a(c1486Wt);
                if (map.put(objA2, this.b.a(c1486Wt)) != null) {
                    throw new C1542Yt("duplicate key: " + objA2);
                }
            }
            c1486Wt.h();
        }
        return map;
    }
}
