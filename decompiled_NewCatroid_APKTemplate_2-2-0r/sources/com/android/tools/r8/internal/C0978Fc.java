package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0415b0;
import com.android.tools.r8.dex.code.C0440g0;
import com.android.tools.r8.dex.code.C0445h0;
import com.android.tools.r8.dex.code.C0450i0;
import com.android.tools.r8.dex.code.C0455j0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Set;
import java.util.function.Function;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0978Fc extends AbstractC0824Ac {
    static final /* synthetic */ boolean j = true;
    public static final /* synthetic */ int k = 0;
    private final long i;

    public C0978Fc(S10 s10, long j2) {
        super(s10);
        if (!j && !s10.N() && !s10.b.B1()) {
            throw new AssertionError();
        }
        this.i = j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean B1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0978Fc E() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        if (j) {
            return 0;
        }
        throw new AssertionError("Const has no register arguments.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 15;
    }

    public final S10 J2() {
        return this.a;
    }

    public final boolean K2() {
        return !T2();
    }

    public final double L2() {
        if (j || H2() == EnumC1812d20.DOUBLE) {
            return Double.longBitsToDouble(this.i);
        }
        throw new AssertionError();
    }

    public final float M2() {
        if (j || H2() == EnumC1812d20.FLOAT) {
            return Float.intBitsToFloat((int) this.i);
        }
        throw new AssertionError();
    }

    public final int N2() {
        if (j || H2() == EnumC1812d20.INT || H2() == EnumC1812d20.OBJECT) {
            return (int) this.i;
        }
        throw new AssertionError();
    }

    public final long O2() {
        if (j || H2() == EnumC1812d20.LONG) {
            return this.i;
        }
        throw new AssertionError();
    }

    public long P2() {
        return this.i;
    }

    public final boolean Q2() {
        long j2 = this.i;
        return -32768 <= j2 && j2 <= 32767;
    }

    public final boolean R2() {
        long j2 = this.i;
        return -128 <= j2 && j2 <= 127;
    }

    public final boolean S2() {
        return H2() == EnumC1812d20.INT && N2() == 1;
    }

    public final boolean T2() {
        return this.i == 0;
    }

    public final boolean U2() {
        long j2 = -this.i;
        return -32768 <= j2 && j2 <= 32767;
    }

    public final boolean V2() {
        long j2 = -this.i;
        return -128 <= j2 && j2 <= 127;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        if (abstractC2412ko == this) {
            return true;
        }
        if (!abstractC2412ko.B1()) {
            return false;
        }
        C0978Fc c0978FcE = abstractC2412ko.E();
        return c0978FcE.H2() == H2() && c0978FcE.i == this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean j1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean r2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        if (this.a == null) {
            return super.toString() + " " + this.i + " (dead)";
        }
        return super.toString() + " " + this.i + " (" + a() + Constant.AFTER_QUTO;
    }

    public static C0978Fc a(C2409kl c2409kl, C0978Fc c0978Fc) {
        return a(new S10(c2409kl.e.a(), c0978Fc.a(), c0978Fc.j()), c0978Fc);
    }

    public static C0978Fc a(S10 s10, C0978Fc c0978Fc) {
        if (j || s10 != c0978Fc.b()) {
            return new C0978Fc(s10, c0978Fc.P2());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        if (!this.a.Y()) {
            c2322jf.b(this);
            return;
        }
        int iA = c2322jf.a(W0(), this.a);
        if (!H2().a()) {
            EnumC1812d20 enumC1812d20H2 = H2();
            enumC1812d20H2.getClass();
            if (enumC1812d20H2 != EnumC1812d20.INT && enumC1812d20H2 != EnumC1812d20.FLOAT) {
                if (!j && !H2().b()) {
                    throw new AssertionError();
                }
                long j2 = this.i;
                if (-32768 <= j2 && j2 <= 32767) {
                    c2322jf.a(this, new C0440g0(iA, (int) this.i));
                    return;
                }
                if ((281474976710655L & j2) == 0) {
                    c2322jf.a(this, new C0455j0(iA, (int) (this.i >>> 48)));
                    return;
                } else if (-2147483648L <= j2 && j2 <= 2147483647L) {
                    c2322jf.a(this, new C0445h0(iA, (int) this.i));
                    return;
                } else {
                    c2322jf.a(this, new C0450i0(iA, this.i));
                    return;
                }
            }
        }
        if (!j) {
            long j3 = this.i;
            if (-2147483648L > j3 || j3 > 2147483647L) {
                throw new AssertionError();
            }
        }
        if ((iA & 15) == iA) {
            long j4 = this.i;
            if (-8 <= j4 && j4 <= 7) {
                c2322jf.a(this, new com.android.tools.r8.dex.code.Y(iA, (int) this.i));
                return;
            }
        }
        long j5 = this.i;
        if (-32768 <= j5 && j5 <= 32767) {
            c2322jf.a(this, new com.android.tools.r8.dex.code.X(iA, (int) this.i));
        } else if ((WebSocketProtocol.PAYLOAD_SHORT_MAX & j5) == 0) {
            c2322jf.a(this, new C0415b0(iA, ((int) this.i) >>> 16));
        } else {
            c2322jf.a(this, new com.android.tools.r8.dex.code.Z(iA, (int) this.i));
        }
    }

    public final boolean a(EnumC2527mI enumC2527mI) {
        if (!j && enumC2527mI != EnumC2527mI.INT && enumC2527mI != EnumC2527mI.LONG) {
            throw new AssertionError();
        }
        if (enumC2527mI == EnumC2527mI.INT) {
            return N2() == -1;
        }
        return O2() == -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        if (H2().a()) {
            l6.a(new C1668b7(), this);
        } else {
            l6.a(new C1818d7(this.i, H2()), this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:48:0x0082  */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0089, code lost:
    
        if ((r20 & okhttp3.internal.ws.WebSocketProtocol.PAYLOAD_SHORT_MAX) == 0) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00c2, code lost:
    
        if (java.lang.Double.doubleToLongBits(r20) == java.lang.Double.doubleToLongBits(-0.0d)) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00e0, code lost:
    
        if (java.lang.Float.floatToIntBits(r20) == java.lang.Float.floatToIntBits(-0.0f)) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00e9, code lost:
    
        if (r20 != 1) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0104, code lost:
    
        if (r20 <= 127) goto L92;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int a(com.android.tools.r8.utils.H1 r18, com.android.tools.r8.internal.EnumC1812d20 r19, long r20) {
        /*
            Method dump skipped, instruction units count: 266
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C0978Fc.a(com.android.tools.r8.utils.H1, com.android.tools.r8.internal.d20, long):int");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        if (!j && !H2().a()) {
            throw new AssertionError();
        }
        c0702m.a();
        return C0722p1.S5;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final SA a(C2409kl c2409kl, Function function) {
        if (this.a.A()) {
            return C3106u5.c();
        }
        return new C0852Bc(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return a();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C0702m c0702m, C2433l20 c2433l20) {
        boolean z = j;
        if (!z) {
            super.a(c0702m, c2433l20);
        }
        if (!z && T2() && !a().H() && !a().E()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(Set set) {
        long j2 = this.i;
        return j2 == 0 || j2 == 1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return c0702m.b().a(this.i);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(this.i, H2());
    }
}
