package com.android.tools.r8.internal;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1932eZ extends EZ {
    public static final FZ b = new C1850dZ();
    private final SimpleDateFormat a = new SimpleDateFormat("hh:mm:ss a");

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) {
        synchronized (this) {
            if (c1486Wt.t() == 9) {
                c1486Wt.q();
                return null;
            }
            try {
                return new Time(this.a.parse(c1486Wt.r()).getTime());
            } catch (ParseException e) {
                throw new C1542Yt(e);
            }
        }
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) {
        Time time = (Time) obj;
        synchronized (this) {
            c1799cu.d(time == null ? null : this.a.format((Date) time));
        }
    }
}
