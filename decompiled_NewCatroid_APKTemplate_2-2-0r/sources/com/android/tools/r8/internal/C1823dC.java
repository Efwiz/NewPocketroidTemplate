package com.android.tools.r8.internal;

import java.util.function.Function;
import java.util.function.IntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1823dC extends SB {
    static final /* synthetic */ boolean c = true;
    private final S10[] a;
    private final int b;

    C1823dC(RB rb) {
        int iL;
        this.a = new S10[rb.c() + rb.a()];
        WB it = rb.iterator();
        while (it.hasNext()) {
            VB next = it.next();
            if (next.b() == 210) {
                iL = next.l() + rb.a();
                this.b = iL;
            }
        }
        iL = -1;
        this.b = iL;
    }

    @Override // com.android.tools.r8.internal.SB
    public final S10 a(Object obj, AbstractC2366kC abstractC2366kC) {
        int i;
        C2214iC c2214iC = (C2214iC) obj;
        if (!c2214iC.a()) {
            C2055gC c2055gC = (C2055gC) abstractC2366kC;
            boolean z = C2214iC.b;
            if (!z && c2214iC.a >= 0) {
                throw new AssertionError();
            }
            int i2 = c2214iC.a;
            int i3 = (Integer.MAX_VALUE & i2) >> 16;
            if (!z && i2 >= 0) {
                throw new AssertionError();
            }
            int i4 = i2 & 65535;
            if (!c && this.b == -1) {
                throw new AssertionError();
            }
            int i5 = this.b;
            int i6 = 0;
            while (true) {
                int[] iArr = c2055gC.a;
                if (i6 >= iArr.length) {
                    throw new C3405y10("Unexpectedly fell off the end of the phi table");
                }
                if (iArr[i6] == i3) {
                    i = i4 + i5;
                    break;
                }
                i5 += iArr[i6 + 1];
                i6 += 2;
            }
        } else {
            if (!C2214iC.b && !c2214iC.a()) {
                throw new AssertionError();
            }
            i = c2214iC.a;
        }
        S10 s10 = this.a[i];
        if (s10 != null) {
            return s10;
        }
        S10 s11 = new S10(i, AbstractC3472z00.f(), null);
        this.a[i] = s11;
        return s11;
    }

    @Override // com.android.tools.r8.internal.SB
    public final S10 a(int i, AbstractC3472z00 abstractC3472z00, Function function) {
        C2214iC c2214iC = new C2214iC(i);
        boolean z = c;
        if (!z && !c2214iC.a()) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.X x = (com.android.tools.r8.graph.X) function.apply(c2214iC);
        S10 s10 = this.a[i];
        if (s10 == null) {
            S10 s11 = new S10(i, abstractC3472z00, x);
            this.a[i] = s11;
            return s11;
        }
        s10.a(abstractC3472z00);
        if (x != null && !s10.A()) {
            s10.a(x);
        }
        if (z || x == s10.s()) {
            return s10;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.SB
    public final ML a(int i, IntFunction intFunction, AbstractC3472z00 abstractC3472z00, Function function, AbstractC2366kC abstractC2366kC) {
        C2055gC c2055gC = (C2055gC) abstractC2366kC;
        int i2 = this.b;
        int i3 = 0;
        while (true) {
            int[] iArr = c2055gC.a;
            if (i3 < iArr.length) {
                boolean z = c;
                if (!z && i2 > i) {
                    throw new AssertionError();
                }
                int i4 = iArr[i3];
                int i5 = iArr[i3 + 1];
                if (!z && i5 <= 0) {
                    throw new AssertionError();
                }
                int i6 = i5 + i2;
                if (i < i6) {
                    int i7 = i - i2;
                    if (i4 < 32768) {
                        boolean z2 = C1589a6.a;
                        if (!z2 && !C1589a6.a(i4)) {
                            throw new AssertionError();
                        }
                        int i8 = (i4 & 65535) << 16;
                        if (!z2 && !C1589a6.a(i7)) {
                            throw new AssertionError();
                        }
                        int i9 = (i7 & 65535) | Integer.MIN_VALUE | i8;
                        boolean z3 = C2214iC.b;
                        if (!z3 && i9 >= 0) {
                            throw new AssertionError();
                        }
                        C2214iC c2214iC = new C2214iC(i9);
                        if (!z3 && i9 >= 0) {
                            throw new AssertionError();
                        }
                        ML ml = new ML(i, (C3332x4) intFunction.apply((i9 & Integer.MAX_VALUE) >> 16), abstractC3472z00, (com.android.tools.r8.graph.X) function.apply(c2214iC), ML.a.a);
                        S10 s10 = this.a[i];
                        if (s10 != null) {
                            if (!z && s10.i()) {
                                throw new AssertionError();
                            }
                            s10.g(ml);
                        }
                        this.a[i] = ml;
                        return ml;
                    }
                    throw new Z00("No support for more than 15-bit block index.");
                }
                i3 += 2;
                i2 = i6;
            } else {
                throw new C3405y10("Unexpected fall off the end of the phi table");
            }
        }
    }
}
