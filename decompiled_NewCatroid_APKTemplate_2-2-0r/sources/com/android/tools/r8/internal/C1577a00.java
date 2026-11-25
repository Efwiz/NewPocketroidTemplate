package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* JADX INFO: renamed from: com.android.tools.r8.internal.a00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1577a00 extends EZ {
    C1577a00() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        Calendar calendar = (Calendar) obj;
        if (calendar == null) {
            c1799cu.l();
            return;
        }
        c1799cu.e();
        c1799cu.b("year");
        c1799cu.a(calendar.get(1));
        c1799cu.b("month");
        c1799cu.a(calendar.get(2));
        c1799cu.b("dayOfMonth");
        c1799cu.a(calendar.get(5));
        c1799cu.b("hourOfDay");
        c1799cu.a(calendar.get(11));
        c1799cu.b("minute");
        c1799cu.a(calendar.get(12));
        c1799cu.b("second");
        c1799cu.a(calendar.get(13));
        c1799cu.g();
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        c1486Wt.d();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (c1486Wt.t() != 4) {
            String strP = c1486Wt.p();
            int iN = c1486Wt.n();
            if ("year".equals(strP)) {
                i = iN;
            } else if ("month".equals(strP)) {
                i2 = iN;
            } else if ("dayOfMonth".equals(strP)) {
                i3 = iN;
            } else if ("hourOfDay".equals(strP)) {
                i4 = iN;
            } else if ("minute".equals(strP)) {
                i5 = iN;
            } else if ("second".equals(strP)) {
                i6 = iN;
            }
        }
        c1486Wt.h();
        return new GregorianCalendar(i, i2, i3, i4, i5, i6);
    }
}
