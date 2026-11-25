package com.android.tools.r8.internal;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class QV extends EZ {
    public static final FZ b = new PV();
    private final SimpleDateFormat a = new SimpleDateFormat("MMM d, yyyy");

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) {
        synchronized (this) {
            if (c1486Wt.t() == 9) {
                c1486Wt.q();
                return null;
            }
            try {
                return new Date(this.a.parse(c1486Wt.r()).getTime());
            } catch (ParseException e) {
                throw new C1542Yt(e);
            }
        }
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) {
        Date date = (Date) obj;
        synchronized (this) {
            c1799cu.d(date == null ? null : this.a.format((java.util.Date) date));
        }
    }
}
