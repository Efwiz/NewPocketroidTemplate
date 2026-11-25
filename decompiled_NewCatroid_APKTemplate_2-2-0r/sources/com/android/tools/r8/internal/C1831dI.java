package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0452i2;
import com.android.tools.r8.dex.code.C0457j2;
import com.android.tools.r8.dex.code.C0462k2;
import com.android.tools.r8.dex.code.C0509u0;
import com.android.tools.r8.dex.code.C0514v0;
import com.android.tools.r8.dex.code.C0519w0;
import com.android.tools.r8.graph.C0702m;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1831dI extends AbstractC3328x10 {
    public final EnumC2527mI h;
    public final EnumC2527mI i;

    public C1831dI(EnumC2527mI enumC2527mI, EnumC2527mI enumC2527mI2, S10 s10, S10 s11) {
        super(s10, s11);
        this.h = enumC2527mI;
        this.i = enumC2527mI2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 52;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.q2()) {
            return false;
        }
        C1831dI c1831dIW0 = abstractC2412ko.w0();
        return c1831dIW0.h == this.h && c1831dIW0.i == this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean q2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1831dI w0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 c0457j2;
        int iA = c2322jf.a(W0(), J2());
        int iA2 = c2322jf.a(W0(), K2());
        int[] iArr = AbstractC1754cI.a;
        int i = iArr[this.h.ordinal()];
        if (i == 4) {
            int i2 = iArr[this.i.ordinal()];
            if (i2 == 5) {
                c0457j2 = new C0457j2(iA, iA2);
            } else if (i2 == 6) {
                c0457j2 = new C0452i2(iA, iA2);
            } else {
                if (i2 != 7) {
                    throw new C3405y10("Unexpected types " + this.h + ", " + this.i);
                }
                c0457j2 = new C0462k2(iA, iA2);
            }
        } else if (i == 5) {
            int i3 = iArr[this.i.ordinal()];
            if (i3 == 4) {
                c0457j2 = new com.android.tools.r8.dex.code.D0(iA, iA2);
            } else if (i3 == 6) {
                c0457j2 = new com.android.tools.r8.dex.code.B0(iA, iA2);
            } else {
                if (i3 != 7) {
                    throw new C3405y10("Unexpected types " + this.h + ", " + this.i);
                }
                c0457j2 = new com.android.tools.r8.dex.code.C0(iA, iA2);
            }
        } else if (i == 6) {
            int i4 = iArr[this.i.ordinal()];
            if (i4 == 4) {
                c0457j2 = new C0519w0(iA, iA2);
            } else if (i4 == 5) {
                c0457j2 = new C0509u0(iA, iA2);
            } else {
                if (i4 != 7) {
                    throw new C3405y10("Unexpected types " + this.h + ", " + this.i);
                }
                c0457j2 = new C0514v0(iA, iA2);
            }
        } else {
            if (i != 7) {
                throw new C3405y10("Unexpected types " + this.h + ", " + this.i);
            }
            switch (iArr[this.i.ordinal()]) {
                case 1:
                    c0457j2 = new com.android.tools.r8.dex.code.E1(iA, iA2);
                    break;
                case 2:
                    c0457j2 = new com.android.tools.r8.dex.code.F1(iA, iA2);
                    break;
                case 3:
                    c0457j2 = new com.android.tools.r8.dex.code.J1(iA, iA2);
                    break;
                case 4:
                    c0457j2 = new com.android.tools.r8.dex.code.I1(iA, iA2);
                    break;
                case 5:
                    c0457j2 = new com.android.tools.r8.dex.code.H1(iA, iA2);
                    break;
                case 6:
                    c0457j2 = new com.android.tools.r8.dex.code.G1(iA, iA2);
                    break;
                default:
                    throw new C3405y10("Unexpected types " + this.h + ", " + this.i);
            }
        }
        c2322jf.a(this, c0457j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC3328x10, com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return XM.a(this.i);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new C1591a8(this.h, this.i), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(this.h, this.i, K2());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(Set set) {
        return this.i == EnumC2527mI.BYTE && K2().b(set);
    }
}
