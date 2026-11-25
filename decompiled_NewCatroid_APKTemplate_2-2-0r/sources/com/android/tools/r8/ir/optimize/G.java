package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1189Mn;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.InterfaceC2101gk;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class G implements InterfaceC2101gk {
    static final /* synthetic */ boolean a = true;

    G() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2101gk
    public final boolean a(Object obj, Object obj2) {
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) obj;
        AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) obj2;
        if (abstractC2412ko == abstractC2412ko2) {
            return true;
        }
        if (abstractC2412ko == null || abstractC2412ko2 == null || abstractC2412ko.getClass() != abstractC2412ko2.getClass() || ((abstractC2412ko instanceof C1189Mn) && abstractC2412ko.T0() != abstractC2412ko2.T0())) {
            return false;
        }
        return abstractC2412ko.b(abstractC2412ko2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2101gk
    public final int a(Object obj) {
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) obj;
        if (!a && !abstractC2412ko.j1()) {
            throw new AssertionError();
        }
        int iG2 = abstractC2412ko.G2();
        if (iG2 == 12) {
            return abstractC2412ko.A().K2().hashCode();
        }
        if (iG2 == 20) {
            return abstractC2412ko.J().J2().hashCode();
        }
        if (iG2 == 28 || iG2 == 59) {
            return abstractC2412ko.M().getField().hashCode();
        }
        if (iG2 == 15) {
            return (abstractC2412ko.H2().hashCode() * 13) + Long.hashCode(abstractC2412ko.E().P2());
        }
        if (iG2 == 16) {
            return abstractC2412ko.F().J2().hashCode();
        }
        throw new C3405y10();
    }
}
