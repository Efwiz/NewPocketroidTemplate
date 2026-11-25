package com.android.tools.r8.internal;

import com.android.tools.r8.utils.AbstractC3934m3;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1923eQ extends AbstractC3934m3 {
    private int a = 0;
    final /* synthetic */ InterfaceC2488lo b;
    final /* synthetic */ S10 c;

    C1923eQ(InterfaceC2488lo interfaceC2488lo, S10 s10) {
        this.b = interfaceC2488lo;
        this.c = s10;
    }

    @Override // com.android.tools.r8.utils.AbstractC3934m3
    public final boolean a() {
        while (this.b.hasNext()) {
            AbstractC2412ko abstractC2412koO = this.b.o();
            if (abstractC2412koO.r1() && abstractC2412koO.v().J2().m() == this.c) {
                return true;
            }
            if (abstractC2412koO.g2()) {
                return false;
            }
            this.b.next();
        }
        return false;
    }

    @Override // com.android.tools.r8.utils.AbstractC3934m3
    public final Object b() throws C2000fQ {
        if (!a()) {
            throw new NoSuchElementException();
        }
        C1736c3 c1736c3V = this.b.next().v();
        S10 s10M = c1736c3V.K2().m();
        if (!s10M.i() && s10M.b.B1()) {
            int iN2 = s10M.b.E().N2();
            int i = this.a;
            if (iN2 == i) {
                this.a = i + 1;
                return c1736c3V.value().m();
            }
        }
        throw new C2000fQ();
    }
}
