package com.android.tools.r8.internal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.slf4j.Marker;

/* JADX INFO: renamed from: com.android.tools.r8.internal.h00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2124h00 implements FZ {
    final /* synthetic */ Class a = Calendar.class;
    final /* synthetic */ Class b = GregorianCalendar.class;
    final /* synthetic */ EZ c;

    C2124h00(EZ ez) {
        this.c = ez;
    }

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        Class clsA = i00.a();
        if (clsA == this.a || clsA == this.b) {
            return this.c;
        }
        return null;
    }

    public final String toString() {
        return "Factory[type=" + this.a.getName() + Marker.ANY_NON_NULL_MARKER + this.b.getName() + ",adapter=" + this.c + "]";
    }
}
