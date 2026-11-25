package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Q6 {
    public static Q6 a(C0702m c0702m) {
        ArrayList arrayList = new ArrayList();
        if (c0702m.Y().C1.d()) {
            C3276wN c3276wN = (c0702m.Y().C1.d() && c0702m.Y().C1.B()) ? new C3276wN(c0702m) : null;
            if (c3276wN != null) {
                arrayList.add(c3276wN);
            }
            C1035He c1035HeA = C1035He.a(c0702m);
            if (c1035HeA != null) {
                arrayList.add(c1035HeA);
            }
            arrayList.add(new C1317Re(c0702m));
        }
        EQ eqA = EQ.a(c0702m);
        if (eqA != null) {
            arrayList.add(eqA);
        }
        C2044g20 c2044g20A = C2044g20.a(c0702m);
        if (c2044g20A != null) {
            arrayList.add(c2044g20A);
        }
        return arrayList.isEmpty() ? new O6() : new P6(c0702m, arrayList);
    }

    public abstract void a(ExecutorService executorService, S6 s6);
}
