package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.C1248Os;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2561ml;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.p0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3578p0 {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C0767v2 c0767v2, C0767v2 c0767v3, com.android.tools.r8.graph.Q0 q0) {
        AbstractC0727q0 abstractC0727q0C;
        if (c0767v2 == c0767v3) {
            return true;
        }
        AbstractC0727q0 abstractC0727q0C2 = q0.c(c0767v2);
        return abstractC0727q0C2 != null && abstractC0727q0C2.D1() && (abstractC0727q0C = q0.c(c0767v3)) != null && abstractC0727q0C2.g1() == abstractC0727q0C.g1();
    }

    public static void a(C0702m c0702m, o5 o5Var, C2409kl c2409kl) {
        c2409kl.getClass();
        C2561ml c2561ml = new C2561ml(c2409kl);
        if (!a && c2409kl.h().a() == o5Var.a()) {
            throw new AssertionError();
        }
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            if (abstractC2412ko.W1()) {
                C1166Ls c1166LsZ = abstractC2412ko.Z();
                C0684j2 c0684j2S2 = c1166LsZ.S2();
                c0702m.getClass();
                W0 w0C = c0684j2S2.c(c0702m.c(c0684j2S2.e));
                if (w0C != null && !w0C.C1()) {
                    if (!a && !w0C.L1()) {
                        throw new AssertionError();
                    }
                    if (c1166LsZ.R2()) {
                        c2561ml.a(new C1248Os(c0684j2S2, c1166LsZ.b(), c1166LsZ.J2()), (Set) null);
                    } else {
                        c2561ml.a(new C2115gt(c0684j2S2, c1166LsZ.b(), c1166LsZ.J2()), (Set) null);
                    }
                }
            } else if ((abstractC2412ko instanceof C1248Os) || abstractC2412ko.f2()) {
                AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
                C0684j2 c0684j2S3 = abstractC1305QsB0.S2();
                if (c0684j2S3.e == o5Var.t()) {
                    c0702m.getClass();
                    W0 w0C2 = c0684j2S3.c(c0702m.c(c0684j2S3.e));
                    if (w0C2 != null && w0C2.L1()) {
                        c2561ml.a(new C1166Ls(c0684j2S3, abstractC1305QsB0.b(), abstractC1305QsB0.J2(), o5Var.a().isInterface()), (Set) null);
                    }
                }
            }
        }
    }
}
