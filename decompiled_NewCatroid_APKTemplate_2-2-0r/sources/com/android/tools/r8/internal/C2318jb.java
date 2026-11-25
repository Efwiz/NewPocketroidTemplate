package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2318jb extends T4 {
    static final /* synthetic */ boolean j = true;
    private final EnumC2246ib i;

    public C2318jb(EnumC2527mI enumC2527mI, EnumC2246ib enumC2246ib, S10 s10, S10 s11, S10 s12) {
        super(enumC2527mI, s10, s11, s12);
        this.i = enumC2246ib;
    }

    @Override // com.android.tools.r8.internal.T4, com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.T4, com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 11;
    }

    @Override // com.android.tools.r8.internal.T4
    public final boolean K2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C2318jb) && abstractC2412ko.z().i == this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("jb (");
        int i = AbstractC2167hb.a[this.h.ordinal()];
        if (i == 1) {
            sb.append("double, ");
            sb.append(a(this.i));
        } else if (i == 2) {
            sb.append("float, ");
            sb.append(a(this.i));
        } else {
            if (i != 3) {
                throw new C3405y10("Unexpected type " + this.h);
            }
            sb.append("long");
        }
        sb.append(Constant.AFTER_QUTO);
        for (int length = sb.length(); length < 20; length++) {
            sb.append(" ");
        }
        S10 s10 = this.a;
        if (s10 != null) {
            sb.append(s10);
            sb.append(" <- ");
        }
        com.android.tools.r8.utils.Y2.a(sb, this.b, ", ", com.android.tools.r8.utils.Y2.a.NONE);
        return sb.toString();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C2318jb z() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 u;
        int iA = c2322jf.a(W0(), this.a);
        int iA2 = c2322jf.a(W0(), L2());
        int iA3 = c2322jf.a(W0(), N2());
        int i = AbstractC2167hb.a[this.h.ordinal()];
        if (i == 1) {
            boolean z = j;
            if (!z && this.i == EnumC2246ib.NONE) {
                throw new AssertionError();
            }
            EnumC2246ib enumC2246ib = this.i;
            if (enumC2246ib == EnumC2246ib.GT) {
                u = new com.android.tools.r8.dex.code.S(iA, iA2, iA3);
            } else {
                if (!z && enumC2246ib != EnumC2246ib.LT) {
                    throw new AssertionError();
                }
                u = new com.android.tools.r8.dex.code.U(iA, iA2, iA3);
            }
        } else if (i == 2) {
            boolean z2 = j;
            if (!z2 && this.i == EnumC2246ib.NONE) {
                throw new AssertionError();
            }
            EnumC2246ib enumC2246ib2 = this.i;
            if (enumC2246ib2 == EnumC2246ib.GT) {
                u = new com.android.tools.r8.dex.code.T(iA, iA2, iA3);
            } else {
                if (!z2 && enumC2246ib2 != EnumC2246ib.LT) {
                    throw new AssertionError();
                }
                u = new com.android.tools.r8.dex.code.V(iA, iA2, iA3);
            }
        } else {
            if (i != 3) {
                throw new C3405y10("Unexpected type " + this.h);
            }
            if (!j && this.i != EnumC2246ib.NONE) {
                throw new AssertionError();
            }
            u = new com.android.tools.r8.dex.code.Q(iA, iA2, iA3);
        }
        c2322jf.a(this, u);
    }

    private static String a(EnumC2246ib enumC2246ib) {
        int i = AbstractC2167hb.b[enumC2246ib.ordinal()];
        if (i == 1) {
            return "none";
        }
        if (i == 2) {
            return "gt";
        }
        if (i == 3) {
            return "lt";
        }
        throw new C3405y10("Unexpected bias " + enumC2246ib);
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:36:0x00a3  */
    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final SA a(C2409kl c2409kl, Function function) {
        int iSignum;
        SA sa = (SA) function.apply(L2());
        SA sa2 = (SA) function.apply(N2());
        sa.getClass();
        boolean z = sa instanceof C0852Bc;
        if (z) {
            sa2.getClass();
            if (sa2 instanceof C0852Bc) {
                C0978Fc c0978FcC = sa.a().c();
                C0978Fc c0978FcC2 = sa2.a().c();
                EnumC2527mI enumC2527mI = this.h;
                if (enumC2527mI == EnumC2527mI.LONG) {
                    iSignum = Integer.signum(Long.compare(c0978FcC.O2(), c0978FcC2.O2()));
                } else if (enumC2527mI == EnumC2527mI.FLOAT) {
                    float fM2 = c0978FcC.M2();
                    float fM3 = c0978FcC2.M2();
                    if (!Float.isNaN(fM2) && !Float.isNaN(fM3)) {
                        iSignum = (int) Math.signum(fM2 - fM3);
                    } else if (this.i == EnumC2246ib.GT) {
                        iSignum = 1;
                    } else {
                        iSignum = -1;
                    }
                } else {
                    if (!j && enumC2527mI != EnumC2527mI.DOUBLE) {
                        throw new AssertionError();
                    }
                    double dL2 = c0978FcC.L2();
                    double dL3 = c0978FcC2.L2();
                    if (!Double.isNaN(dL2) && !Double.isNaN(dL3)) {
                        iSignum = (int) Math.signum(dL2 - dL3);
                    } else if (this.i == EnumC2246ib.GT) {
                        iSignum = 1;
                    } else {
                        iSignum = -1;
                    }
                }
                return new C0852Bc(new C0978Fc(c2409kl.a(j(), AbstractC3472z00.k()), iSignum));
            }
        }
        boolean z2 = sa instanceof C1004Gc;
        if (z2) {
            sa2.getClass();
            if (sa2 instanceof C0852Bc) {
                return a(c2409kl, sa.b().c().x(), sa2.a().c().b().x());
            }
        }
        if (z) {
            sa2.getClass();
            if (sa2 instanceof C1004Gc) {
                return a(c2409kl, sa.a().c().b().x(), sa2.b().c().x());
            }
        }
        if (z2) {
            sa2.getClass();
            if (sa2 instanceof C1004Gc) {
                return a(c2409kl, sa.b().c().x(), sa2.b().c().x());
            }
        }
        return C3106u5.c();
    }

    private SA a(C2409kl c2409kl, com.android.tools.r8.utils.W1 w1, com.android.tools.r8.utils.W1 w2) {
        if (w1.a(w2)) {
            return C3106u5.c();
        }
        return new C0852Bc(new C0978Fc(c2409kl.a(j(), AbstractC3472z00.k()), Integer.signum(Long.compare(w1.b(), w2.b()))));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new U6(this.i, this.h), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(this.h, this.i, L2(), N2());
    }

    @Override // com.android.tools.r8.internal.T4, com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.k();
    }
}
