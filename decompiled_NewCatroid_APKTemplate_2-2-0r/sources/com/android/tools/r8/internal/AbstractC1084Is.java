package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0511u2;
import com.android.tools.r8.dex.code.C0516v2;
import com.android.tools.r8.dex.code.C0521w2;
import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Is, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1084Is extends AbstractC2412ko {
    static final /* synthetic */ boolean h = true;

    protected AbstractC1084Is(S10 s10, List list) {
        super(s10, list);
    }

    public static AbstractC1084Is a(EnumC1953et enumC1953et, AbstractC0594a1 abstractC0594a1, C0737r2 c0737r2, S10 s10, List list, boolean z) {
        switch (enumC1953et.ordinal()) {
            case 0:
                return new C1166Ls((C0684j2) abstractC0594a1, s10, list, z);
            case 1:
                return new C1248Os((C0684j2) abstractC0594a1, s10, list);
            case 2:
                return new C1721bt((C0684j2) abstractC0594a1, s10, list, z);
            case 3:
                return new C1798ct((C0684j2) abstractC0594a1, s10, list, z);
            case 4:
                return new C2115gt((C0684j2) abstractC0594a1, s10, list);
            case 5:
                return new C1385Ts((C0767v2) abstractC0594a1, s10, list);
            case 6:
                return new C1358Ss((C0767v2) abstractC0594a1, s10, list);
            case 7:
                throw new C3405y10("Use InvokeCustom constructor instead");
            case 8:
                return new C1433Us((C0684j2) abstractC0594a1, c0737r2, s10, list);
            default:
                throw new C3405y10("Unknown invoke type: " + enumC1953et);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        int i = 1;
        if (this.b.size() == 1 || P2() > 5) {
            return 65535;
        }
        if (this.b.size() == 0) {
            return 15;
        }
        S10 s10 = (S10) this.b.get(0);
        if (!s10.H()) {
            return 15;
        }
        while (i < this.b.size()) {
            S10 s11 = (S10) this.b.get(i);
            if (s10.t() != s11) {
                return 15;
            }
            i++;
            s10 = s11;
        }
        return 65535;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    public final List J2() {
        return this.b;
    }

    public final S10 K2() {
        return c(this.b.size() - 1);
    }

    public abstract C0767v2 L2();

    public abstract EnumC1953et M2();

    protected abstract String N2();

    public final boolean O2() {
        return !this.b.isEmpty();
    }

    public final int P2() {
        Iterator it = this.b.iterator();
        int iO = 0;
        while (it.hasNext()) {
            iO += ((S10) it.next()).n.O();
        }
        return iO;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String U0() {
        return "Invoke-" + N2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean U1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC1084Is X() {
        return this;
    }

    protected final int b(C2322jf c2322jf) {
        if (!h && !d(c2322jf)) {
            throw new AssertionError();
        }
        if (this.b.size() <= 0) {
            return 0;
        }
        return c2322jf.b(W0(), (S10) this.b.get(0));
    }

    protected final void c(C2322jf c2322jf) {
        S10 s10 = (S10) this.b.get(0);
        int iO = s10.n.O() + c2322jf.b(W0(), s10);
        for (int i = 1; i < this.b.size(); i++) {
            S10 s11 = (S10) this.b.get(i);
            if (!h && iO != c2322jf.b(W0(), s11)) {
                throw new AssertionError();
            }
            iO += s11.n.O();
        }
    }

    protected final boolean d(C2322jf c2322jf) {
        if (P2() > 5) {
            return true;
        }
        if (this.b.size() != 1) {
            if (this.b.size() != 0) {
                S10 s10 = (S10) this.b.get(0);
                if (s10.H()) {
                    int i = 1;
                    while (i < this.b.size()) {
                        S10 s11 = (S10) this.b.get(i);
                        if (s10.t() == s11) {
                            i++;
                            s10 = s11;
                        }
                    }
                }
            }
            return false;
        }
        if (!h) {
            c(c2322jf);
        }
        return (P2() + c2322jf.b(W0(), (S10) this.b.get(0))) - 1 > 15;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    public final S10 c(int i) {
        if (h || i < this.b.size()) {
            return (S10) this.b.get(i);
        }
        throw new AssertionError();
    }

    protected final int a(C2322jf c2322jf, int[] iArr) {
        if (!h && d(c2322jf)) {
            throw new AssertionError();
        }
        int i = 0;
        for (S10 s10 : this.b) {
            int iB = c2322jf.b(W0(), s10);
            if ((s10.n.O() + iB) - 1 > 15) {
                iB = c2322jf.a(W0(), s10);
            }
            if (!h && (s10.n.O() + iB) - 1 > 15) {
                throw new AssertionError();
            }
            int i2 = 0;
            while (i2 < s10.n.O()) {
                if (!h && i >= 5) {
                    throw new AssertionError();
                }
                iArr[i] = iB;
                i2++;
                i++;
                iB++;
            }
        }
        return i;
    }

    protected final void a(com.android.tools.r8.dex.code.B1 b1, C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 c0516v2;
        S10 s10 = this.a;
        if (s10 != null && s10.Y()) {
            AbstractC3472z00 abstractC3472z00W = this.a.w();
            int iA = c2322jf.a(W0(), this.a);
            if (abstractC3472z00W.K()) {
                c0516v2 = new C0511u2(iA);
            } else if (abstractC3472z00W.M()) {
                c0516v2 = new C0521w2(iA);
            } else if (abstractC3472z00W.I()) {
                c0516v2 = new C0516v2(iA);
            } else {
                throw new C3405y10("Unexpected result type " + H2());
            }
            c2322jf.a(this, b1, c0516v2);
            return;
        }
        c2322jf.a(this, b1);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, S10 s10) {
        if (!h && (s10 == null || !s10.w().I())) {
            throw new AssertionError();
        }
        S10 s11 = this.a;
        if (s11 == null) {
            return false;
        }
        AbstractC3472z00 abstractC3472z00W = s11.w();
        if (abstractC3472z00W.H()) {
            return false;
        }
        if (c0702m.f().i() && abstractC3472z00W.w() && s10.w().w() && c0702m.f().o().a(abstractC3472z00W.b().R(), s10.w().b().R())) {
            return false;
        }
        return abstractC3472z00W.I();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public AbstractC3472z00 a(C0702m c0702m) {
        C0767v2 c0767v2L2 = L2();
        if (!c0767v2L2.Y0()) {
            return AbstractC3472z00.a(c0767v2L2, WH.i(), (C0702m<?>) c0702m);
        }
        throw new C3405y10("void methods have no type.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(Set set) {
        return L2().N0();
    }
}
