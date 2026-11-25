package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0403b;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.c00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1731c00 extends EZ {
    C1731c00() {
    }

    public static void a(AbstractC1249Ot abstractC1249Ot, C1799cu c1799cu) throws IOException {
        if (abstractC1249Ot == null || (abstractC1249Ot instanceof C1306Qt)) {
            c1799cu.l();
            return;
        }
        boolean z = abstractC1249Ot instanceof C1434Ut;
        if (z) {
            if (!z) {
                throw new IllegalStateException("This is not a JSON Primitive.");
            }
            C1434Ut c1434Ut = (C1434Ut) abstractC1249Ot;
            if (c1434Ut.j()) {
                c1799cu.a(c1434Ut.g());
                return;
            } else if (c1434Ut.i()) {
                c1799cu.d(c1434Ut.a());
                return;
            } else {
                c1799cu.d(c1434Ut.f());
                return;
            }
        }
        if (abstractC1249Ot instanceof C1222Nt) {
            c1799cu.d();
            Iterator it = abstractC1249Ot.c().iterator();
            while (it.hasNext()) {
                a((AbstractC1249Ot) it.next(), c1799cu);
            }
            c1799cu.f();
            return;
        }
        if (!(abstractC1249Ot instanceof C1333Rt)) {
            throw new IllegalArgumentException("Couldn't write " + abstractC1249Ot.getClass());
        }
        c1799cu.e();
        for (Map.Entry entry : abstractC1249Ot.d().entrySet()) {
            c1799cu.b((String) entry.getKey());
            a((AbstractC1249Ot) entry.getValue(), c1799cu);
        }
        c1799cu.g();
    }

    public static AbstractC1249Ot b(C1486Wt c1486Wt) throws IOException {
        int iB = AbstractC0403b.b(c1486Wt.t());
        if (iB == 0) {
            C1222Nt c1222Nt = new C1222Nt();
            c1486Wt.c();
            while (c1486Wt.i()) {
                c1222Nt.a(b(c1486Wt));
            }
            c1486Wt.g();
            return c1222Nt;
        }
        if (iB == 2) {
            C1333Rt c1333Rt = new C1333Rt();
            c1486Wt.d();
            while (c1486Wt.i()) {
                c1333Rt.a(c1486Wt.p(), b(c1486Wt));
            }
            c1486Wt.h();
            return c1333Rt;
        }
        if (iB == 5) {
            return new C1434Ut(c1486Wt.r());
        }
        if (iB == 6) {
            return new C1434Ut((Number) new TA(c1486Wt.r()));
        }
        if (iB == 7) {
            return new C1434Ut(Boolean.valueOf(c1486Wt.l()));
        }
        if (iB != 8) {
            throw new IllegalArgumentException();
        }
        c1486Wt.q();
        return C1306Qt.a;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final /* bridge */ /* synthetic */ Object a(C1486Wt c1486Wt) {
        return b(c1486Wt);
    }

    @Override // com.android.tools.r8.internal.EZ
    public final /* bridge */ /* synthetic */ void a(C1799cu c1799cu, Object obj) throws IOException {
        a((AbstractC1249Ot) obj, c1799cu);
    }
}
