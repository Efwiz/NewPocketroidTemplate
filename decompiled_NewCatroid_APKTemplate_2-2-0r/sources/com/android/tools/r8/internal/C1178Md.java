package com.android.tools.r8.internal;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Md, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1178Md extends EZ {
    public static final FZ c = new C1152Ld();
    private final DateFormat a = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat b = DateFormat.getDateTimeInstance(2, 2);

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) {
        Date date = (Date) obj;
        synchronized (this) {
            try {
                if (date == null) {
                    c1799cu.l();
                } else {
                    c1799cu.d(this.a.format(date));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        Date dateA;
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        String strR = c1486Wt.r();
        synchronized (this) {
            try {
                try {
                    try {
                        dateA = this.b.parse(strR);
                    } catch (ParseException unused) {
                        dateA = AbstractC3525zl.a(strR, new ParsePosition(0));
                    }
                } catch (ParseException unused2) {
                    dateA = this.a.parse(strR);
                }
            } catch (ParseException e) {
                throw new C1542Yt(strR, e);
            }
        }
        return dateA;
    }
}
