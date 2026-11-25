package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.C1458Vr;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.InterfaceC0967Er;
import com.android.tools.r8.utils.H1;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class D {
    static final /* synthetic */ boolean b = true;
    private final C2732or a = new C2732or();

    public D(InterfaceC0967Er... interfaceC0967ErArr) {
        if (!b && interfaceC0967ErArr.length <= 0) {
            throw new AssertionError();
        }
        for (InterfaceC0967Er interfaceC0967Er : interfaceC0967ErArr) {
            if (!b && interfaceC0967Er.size() <= 0) {
                throw new AssertionError();
            }
            this.a.a(interfaceC0967Er);
        }
    }

    public final long a(H1 h1) {
        C2732or c2732or = this.a;
        long jI = (((long) c2732or.i(c2732or.size() - 1)) - ((long) this.a.i(0))) + 1;
        if (!C1458Vr.a(h1, jI)) {
            return -9223372036854775807L;
        }
        long j = h1.a() ? 12 : 3;
        long size = this.a.size();
        return ((h1.a() ? size * 8 : (size * 4) + 2) + j) - (C1458Vr.b(h1, jI) + ((long) (h1.a() ? 16 : 3)));
    }
}
