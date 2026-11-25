package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C1229Oa;
import com.android.tools.r8.internal.C1969f3;
import com.android.tools.r8.internal.InterfaceC3010sj;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.graph.y2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0809y2 {
    static final /* synthetic */ boolean a = true;

    public static C0767v2 a(Iterable iterable, final C0702m c0702m) {
        return a(c0702m, AbstractC3472z00.a((Iterable<AbstractC3472z00>) AbstractC2734ot.a(iterable, new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.y2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC0809y2.a(c0702m, (C0767v2) obj);
            }
        }), (C0702m<?>) c0702m));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3472z00 a(C0702m c0702m, C0767v2 c0767v2) {
        return c0767v2.c((C0702m<?>) c0702m);
    }

    public static C0767v2 a(C0702m c0702m, AbstractC3472z00 abstractC3472z00) {
        C0722p1 c0722p1A = c0702m.a();
        if (abstractC3472z00.H()) {
            return abstractC3472z00.c().a(c0722p1A);
        }
        if (abstractC3472z00.r()) {
            C1969f3 c1969f3A = abstractC3472z00.a();
            C0767v2 c0767v2A = a(c0702m, c1969f3A.R());
            int iU = c1969f3A.U();
            C0759u2 c0759u2 = c0767v2A.e;
            byte[] bArr = new byte[c0759u2.e.length + iU];
            Arrays.fill(bArr, 0, iU, (byte) 91);
            byte[] bArr2 = c0759u2.e;
            System.arraycopy(bArr2, 0, bArr, iU, bArr2.length);
            return c0722p1A.c(c0722p1A.b(c0759u2.d + iU, bArr));
        }
        if (!a && !abstractC3472z00.w()) {
            throw new AssertionError();
        }
        C1229Oa c1229OaB = abstractC3472z00.b();
        if (c1229OaB.R() != c0722p1A.Z1) {
            return c1229OaB.R();
        }
        if (c1229OaB.S().e()) {
            return c1229OaB.S().d();
        }
        return c0722p1A.Z1;
    }
}
