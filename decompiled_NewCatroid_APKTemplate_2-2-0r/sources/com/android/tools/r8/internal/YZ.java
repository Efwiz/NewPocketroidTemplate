package com.android.tools.r8.internal;

import java.sql.Timestamp;
import java.util.Date;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class YZ extends EZ {
    final /* synthetic */ EZ a;

    YZ(EZ ez) {
        this.a = ez;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) {
        this.a.a(c1799cu, (Timestamp) obj);
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) {
        Date date = (Date) this.a.a(c1486Wt);
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }
}
