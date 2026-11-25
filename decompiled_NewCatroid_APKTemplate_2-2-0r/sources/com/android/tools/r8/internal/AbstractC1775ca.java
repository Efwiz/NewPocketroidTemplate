package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ca, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1775ca {
    public static int a(C0702m c0702m, C2409kl c2409kl) {
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        Set setC = OU.c();
        boolean z = false;
        for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
            if (abstractC2412ko.r1()) {
                C1736c3 c1736c3V = abstractC2412ko.v();
                if (!c1736c3V.J2().m().b(new c3$$ExternalSyntheticLambda1()) || c1736c3V.b(c0702m, o5VarH)) {
                    return 1;
                }
            } else if (abstractC2412ko.b2() || (abstractC2412ko instanceof AG) || abstractC2412ko.o2()) {
                if (abstractC2412ko.b(c0702m, o5VarH)) {
                    return 1;
                }
            } else if (abstractC2412ko.c()) {
                RW rwG0 = abstractC2412ko.G0();
                com.android.tools.r8.graph.T0 t0S = ((C3733i) c0702m.f()).c(rwG0.getField()).s();
                if (t0S == null || t0S.J0() != o5VarH.t() || abstractC2412ko.b(c0702m, o5VarH)) {
                    return 1;
                }
                setC.add(rwG0.value());
                z = true;
            } else if (abstractC2412ko.a(c0702m.a())) {
                if (abstractC2412ko.a(c0702m, o5VarH, C2332jo.c)) {
                    return 1;
                }
            } else if (abstractC2412ko.c(c0702m, o5VarH)) {
                return 1;
            }
        }
        if (z) {
            Objects.requireNonNull(setC);
            if (c2409kl.a((Consumer) new S10$$ExternalSyntheticLambda8(setC))) {
                return 1;
            }
        }
        if (setC.isEmpty()) {
            return 3;
        }
        return new C1662b20(c0702m, c2409kl).a(setC) ? 1 : 2;
    }
}
