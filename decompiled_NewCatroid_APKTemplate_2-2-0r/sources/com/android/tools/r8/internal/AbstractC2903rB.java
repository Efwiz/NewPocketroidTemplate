package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2903rB {
    static final /* synthetic */ boolean a = true;

    public static AbstractC2744p a(C0702m c0702m, AbstractC1305Qs abstractC1305Qs) {
        C0684j2 c0684j2S2 = abstractC1305Qs.S2();
        C2974sB c2974sBX = c0702m.x();
        c2974sBX.getClass();
        if (c2974sBX.a(abstractC1305Qs.S2(), abstractC1305Qs.J2())) {
            return C1501Xf.e();
        }
        if (a || !c0702m.a().p4.n.contains(c0684j2S2)) {
            return C2198i10.e();
        }
        throw new AssertionError();
    }
}
