package com.android.tools.r8.internal;

import com.beust.jcommander.Parameters;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class WK extends AbstractC1757cL {
    private final EnumC2527mI c;

    private WK(EnumC1686bL enumC1686bL, EnumC2527mI enumC2527mI) {
        super(enumC1686bL);
        this.c = enumC2527mI;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final boolean a(AbstractC1476Wj abstractC1476Wj) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final String b() {
        return this.a.name() + Parameters.DEFAULT_OPTION_PREFIXES + this.c.name();
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int d() {
        return 2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final boolean equals(Object obj) {
        if (!(obj instanceof WK)) {
            return false;
        }
        WK wk = (WK) obj;
        return wk.a.equals(this.a) && wk.c.equals(this.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int hashCode() {
        return this.c.ordinal() + (this.a.ordinal() * 7);
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int a(AbstractC1757cL abstractC1757cL) {
        if (!(abstractC1757cL instanceof WK)) {
            return this.a.compareTo(abstractC1757cL.a);
        }
        WK wk = (WK) abstractC1757cL;
        int iCompareTo = this.a.compareTo(wk.a);
        return iCompareTo != 0 ? iCompareTo : this.c.compareTo(wk.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final String a() {
        return "";
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int a(C2102gl c2102gl, ZK zk, int i) {
        AbstractC2412ko h1;
        ArrayList arrayList = new ArrayList(2);
        int i2 = 0;
        while (i2 < 2) {
            int i3 = i + 1;
            int iIntValue = ((Integer) zk.b.get(i)).intValue();
            if (iIntValue == -1) {
                iIntValue = zk.a();
            }
            arrayList.add(c2102gl.b(iIntValue, EnumC1968f20.a(this.c)));
            i2++;
            i = i3;
        }
        S10 s10A = c2102gl.a(zk.a(), XM.a(this.c), 2);
        int iOrdinal = this.a.ordinal();
        if (iOrdinal == 0) {
            h1 = new H1(this.c, s10A, (S10) arrayList.get(0), (S10) arrayList.get(1));
        } else if (iOrdinal == 1) {
            h1 = new C3218vY(this.c, s10A, (S10) arrayList.get(0), (S10) arrayList.get(1));
        } else if (iOrdinal == 2) {
            h1 = new CF(this.c, s10A, (S10) arrayList.get(0), (S10) arrayList.get(1));
        } else if (iOrdinal == 3) {
            h1 = new C3379xf(this.c, s10A, (S10) arrayList.get(0), (S10) arrayList.get(1));
        } else {
            if (iOrdinal != 4) {
                throw new C3405y10("Invalid binary operation type: " + this.a);
            }
            h1 = new US(this.c, s10A, (S10) arrayList.get(0), (S10) arrayList.get(1));
        }
        c2102gl.a(h1);
        return i;
    }
}
