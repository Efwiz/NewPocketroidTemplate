package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1015Gl extends AbstractC1954eu {
    static final /* synthetic */ boolean j = true;
    public static final /* synthetic */ int k = 0;
    private EnumC1076Il i;

    public C1015Gl(EnumC1076Il enumC1076Il, S10 s10) {
        super(s10);
        this.i = enumC1076Il;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return N2() ? 255 : 15;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        if (j) {
            return 0;
        }
        throw new AssertionError("If instructions define no values.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 25;
    }

    @Override // com.android.tools.r8.internal.AbstractC1954eu
    public C3332x4 J2() {
        boolean z = j;
        if (!z && h().m() != this) {
            throw new AssertionError();
        }
        List<C3332x4> listZ = h().z();
        if (z || listZ.size() >= 2) {
            return listZ.get(listZ.size() - 1);
        }
        throw new AssertionError();
    }

    public final C3332x4 K2() {
        boolean z = j;
        if (!z && h().m() != this) {
            throw new AssertionError();
        }
        List<C3332x4> listZ = h().z();
        if (z || listZ.size() >= 2) {
            return listZ.get(listZ.size() - 2);
        }
        throw new AssertionError();
    }

    public final EnumC1076Il L2() {
        return this.i;
    }

    public final void M2() {
        C3332x4 c3332x4K2 = K2();
        c(J2());
        b(c3332x4K2);
        this.i = this.i.b();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean N1() {
        return true;
    }

    public boolean N2() {
        return this.b.size() == 1;
    }

    public final S10 O2() {
        return (S10) this.b.get(0);
    }

    public final S10 P2() {
        if (j || !N2()) {
            return (S10) this.b.get(1);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1015Gl Q() {
        return this;
    }

    public final C3332x4 Q2() {
        boolean z = j;
        if (!z && !N2()) {
            throw new AssertionError();
        }
        if (z || ((S10) this.b.get(0)).d0().a()) {
            return c(1);
        }
        throw new AssertionError();
    }

    public final C3332x4 R2() {
        boolean z = j;
        if (!z && !N2()) {
            throw new AssertionError();
        }
        if (z || ((S10) this.b.get(0)).d0().a()) {
            return c(0);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1954eu
    public final void b(C3332x4 c3332x4) {
        List<C3332x4> listS = h().s();
        listS.set(listS.size() - 1, c3332x4);
    }

    public final void c(C3332x4 c3332x4) {
        boolean z = j;
        if (!z && h().m() != this) {
            throw new AssertionError();
        }
        List<C3332x4> listS = h().s();
        if (!z && listS.size() < 2) {
            throw new AssertionError();
        }
        listS.set(listS.size() - 2, c3332x4);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + " " + this.i + (N2() ? "Z" : " ") + " block " + K2().w() + " (fallthrough " + J2().w() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this);
    }

    public C1015Gl(EnumC1076Il enumC1076Il, List list) {
        super(list);
        this.i = enumC1076Il;
    }

    public final C3332x4 a(C0978Fc c0978Fc) {
        boolean z = j;
        if (!z && !N2()) {
            throw new AssertionError();
        }
        if (!z) {
            AbstractC3472z00 abstractC3472z00A = c0978Fc.a();
            EnumC1076Il enumC1076Il = this.i;
            abstractC3472z00A.getClass();
            if (!(abstractC3472z00A instanceof C1484Wr) && ((!(abstractC3472z00A instanceof C0858Bi) || (enumC1076Il != EnumC1076Il.EQ && enumC1076Il != EnumC1076Il.NE)) && (!abstractC3472z00A.I() || (enumC1076Il != EnumC1076Il.EQ && enumC1076Il != EnumC1076Il.NE)))) {
                throw new AssertionError();
            }
        }
        return c(Long.signum(c0978Fc.P2()));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.N1()) {
            return false;
        }
        C1015Gl c1015GlQ = abstractC2412ko.Q();
        return c1015GlQ.K2() == K2() && c1015GlQ.J2() == J2() && c1015GlQ.i == this.i;
    }

    public final C3332x4 c(int i) {
        if (!j && Integer.signum(i) != i) {
            throw new AssertionError();
        }
        switch (AbstractC0987Fl.a[this.i.ordinal()]) {
            case 1:
                return i == 0 ? K2() : J2();
            case 2:
                return i != 0 ? K2() : J2();
            case 3:
                return i >= 0 ? K2() : J2();
            case 4:
                return i > 0 ? K2() : J2();
            case 5:
                return i <= 0 ? K2() : J2();
            case 6:
                return i < 0 ? K2() : J2();
            default:
                throw new C3405y10("Unexpected condition type " + this.i);
        }
    }

    public final C3332x4 b(boolean z) {
        if (j || N2()) {
            return c(com.android.tools.r8.utils.M.a(z));
        }
        throw new AssertionError();
    }

    public final C3332x4 a(C0978Fc c0978Fc, C0978Fc c0978Fc2) {
        boolean z = j;
        if (!z && N2()) {
            throw new AssertionError();
        }
        if (!z && c0978Fc.H2() != c0978Fc2.H2()) {
            throw new AssertionError();
        }
        if (!z) {
            AbstractC3472z00 abstractC3472z00A = c0978Fc.a();
            EnumC1076Il enumC1076Il = this.i;
            abstractC3472z00A.getClass();
            if (!(abstractC3472z00A instanceof C1484Wr) && ((!(abstractC3472z00A instanceof C0858Bi) || (enumC1076Il != EnumC1076Il.EQ && enumC1076Il != EnumC1076Il.NE)) && (!abstractC3472z00A.I() || (enumC1076Il != EnumC1076Il.EQ && enumC1076Il != EnumC1076Il.NE)))) {
                throw new AssertionError();
            }
        }
        return c(Long.signum(c0978Fc.P2() - c0978Fc2.P2()));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        EnumC1812d20 enumC1812d20D0 = ((S10) this.b.get(0)).d0();
        if (this.b.size() == 1) {
            l6.a(new C3039t7(this.i, enumC1812d20D0, l6.a(K2())), this);
            return;
        }
        boolean z = j;
        if (!z && this.b.size() != 2) {
            throw new AssertionError();
        }
        if (!z && ((S10) this.b.get(0)).d0() != ((S10) this.b.get(1)).d0()) {
            throw new AssertionError();
        }
        l6.a(new C3185v7(this.i, enumC1812d20D0, l6.a(K2())), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        EnumC1812d20 enumC1812d20D0 = ((S10) this.b.get(0)).d0();
        if (this.b.size() == 1) {
            nb.a(this.i, enumC1812d20D0, (S10) this.b.get(0), K2());
            return;
        }
        boolean z = j;
        if (!z && this.b.size() != 2) {
            throw new AssertionError();
        }
        if (!z && ((S10) this.b.get(0)).d0() != ((S10) this.b.get(1)).d0()) {
            throw new AssertionError();
        }
        nb.a(this.i, enumC1812d20D0, this.b, K2());
    }
}
