package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC2251ih;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C2318jb;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.T4;
import com.android.tools.r8.utils.A1;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.x, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3593x extends AbstractC2251ih {
    static final /* synthetic */ boolean b = true;
    private final A1 a;

    private C3593x(A1 a1) {
        this.a = a1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final boolean a(Object obj, Object obj2) {
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) obj;
        AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) obj2;
        abstractC2412ko.getClass();
        if (((abstractC2412ko instanceof C2318jb) && this.a.i()) || !abstractC2412ko.b(abstractC2412ko2)) {
            return false;
        }
        if (abstractC2412ko.v1() && abstractC2412ko.x().K2()) {
            S10 s10 = (S10) abstractC2412ko.h1().get(0);
            S10 s11 = (S10) abstractC2412ko.h1().get(1);
            S10 s12 = (S10) abstractC2412ko2.h1().get(0);
            S10 s13 = (S10) abstractC2412ko2.h1().get(1);
            if ((!a(s10, s12) || !a(s11, s13)) && (!a(s10, s13) || !a(s11, s12))) {
                return false;
            }
        } else {
            if (!b && abstractC2412ko.h1().size() != abstractC2412ko2.h1().size()) {
                throw new AssertionError();
            }
            for (int i = 0; i < abstractC2412ko.h1().size(); i++) {
                if (!a((S10) abstractC2412ko.h1().get(i), (S10) abstractC2412ko2.h1().get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        int iHashCode;
        int iHashCode2;
        int iHashCode3;
        int iHashCode4;
        int iHashCode5;
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) obj;
        int iHashCode6 = abstractC2412ko.getClass().hashCode();
        if (abstractC2412ko.v1()) {
            T4 t4X = abstractC2412ko.x();
            S10 s10 = (S10) ((ArrayList) abstractC2412ko.h1()).get(0);
            S10 s11 = (S10) ((ArrayList) abstractC2412ko.h1()).get(1);
            if (t4X.K2()) {
                int i = iHashCode6 * 29;
                if (s10.J()) {
                    iHashCode4 = Long.hashCode(s10.b.E().P2());
                } else {
                    iHashCode4 = s10.hashCode();
                }
                if (s11.J()) {
                    iHashCode5 = Long.hashCode(s11.b.E().P2());
                } else {
                    iHashCode5 = s11.hashCode();
                }
                return (iHashCode4 * iHashCode5) + i + iHashCode6;
            }
            int i2 = iHashCode6 * 29;
            if (s10.J()) {
                iHashCode2 = Long.hashCode(s10.b.E().P2());
            } else {
                iHashCode2 = s10.hashCode();
            }
            int i3 = i2 + iHashCode2 + iHashCode6;
            int i4 = i3 * 29;
            if (s11.J()) {
                iHashCode3 = Long.hashCode(s11.b.E().P2());
            } else {
                iHashCode3 = s11.hashCode();
            }
            return i4 + iHashCode3 + i3;
        }
        for (S10 s12 : (ArrayList) abstractC2412ko.h1()) {
            int i5 = iHashCode6 * 29;
            if (s12.J()) {
                iHashCode = Long.hashCode(s12.b.E().P2());
            } else {
                iHashCode = s12.hashCode();
            }
            iHashCode6 += i5 + iHashCode;
        }
        return iHashCode6;
    }

    private static boolean a(S10 s10, S10 s11) {
        if (s10.equals(s11)) {
            return true;
        }
        if (s10.J() && s11.J()) {
            return s10.b.b(s11.b);
        }
        return false;
    }
}
