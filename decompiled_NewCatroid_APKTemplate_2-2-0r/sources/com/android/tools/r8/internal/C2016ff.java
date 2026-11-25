package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0477n2;
import com.android.tools.r8.dex.code.C0482o2;
import com.android.tools.r8.dex.code.C0492q2;
import com.android.tools.r8.dex.code.C0496r2;
import com.android.tools.r8.dex.code.C0501s2;
import com.android.tools.r8.dex.code.C0506t2;
import com.android.tools.r8.dex.code.C0526x2;
import com.android.tools.r8.dex.code.C0534y2;
import com.android.tools.r8.dex.code.C0539z2;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ff, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2016ff extends AbstractC1939ef {
    static final /* synthetic */ boolean f = true;
    private int e;

    public C2016ff(C3492zF c3492zF) {
        super(c3492zF);
        this.e = -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final void a(C2322jf c2322jf, ArrayList arrayList) {
        com.android.tools.r8.dex.code.B1 c0501s2;
        C3492zF c3492zF = (C3492zF) ((AbstractC1939ef) this).a;
        AbstractC3472z00 abstractC3472z00A = c3492zF.a();
        int iB = c2322jf.d.b(((C3492zF) ((AbstractC1939ef) this).a).K2(), ((C3492zF) ((AbstractC1939ef) this).a).W0());
        int iA = c2322jf.d.a(((C3492zF) ((AbstractC1939ef) this).a).J2(), ((C3492zF) ((AbstractC1939ef) this).a).W0());
        int i = this.e;
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    throw new C3405y10("Unexpected size: " + this.e);
                }
                if (abstractC3472z00A.K()) {
                    c0501s2 = new C0477n2(iA, iB);
                } else if (abstractC3472z00A.M()) {
                    c0501s2 = new C0526x2(iA, iB);
                } else {
                    if (!abstractC3472z00A.I()) {
                        throw new C3405y10("Unexpected type: " + c3492zF.H2());
                    }
                    c0501s2 = new C0496r2(iA, iB);
                }
            } else if (abstractC3472z00A.K()) {
                c0501s2 = new C0492q2(iA, iB);
            } else if (abstractC3472z00A.M()) {
                c0501s2 = new C0539z2(iA, iB);
            } else {
                if (!abstractC3472z00A.I()) {
                    throw new C3405y10("Unexpected type: " + c3492zF.H2());
                }
                c0501s2 = new C0506t2(iA, iB);
            }
        } else if (iB == iA) {
            c0501s2 = new com.android.tools.r8.dex.code.R2();
        } else if (abstractC3472z00A.K()) {
            c0501s2 = new C0482o2(iA, iB);
        } else if (abstractC3472z00A.M()) {
            c0501s2 = new C0534y2(iA, iB);
        } else {
            if (!abstractC3472z00A.I()) {
                throw new C3405y10("Unexpected type: " + c3492zF.H2());
            }
            c0501s2 = new C0501s2(iA, iB);
        }
        c0501s2.g(super.c());
        arrayList.add(c0501s2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int d() {
        if (f || this.e > 0) {
            return this.e;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int e() {
        if (f) {
            return 3;
        }
        new C0477n2(0, 0);
        return 3;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int f() {
        if (f) {
            return 1;
        }
        new com.android.tools.r8.dex.code.R2();
        new C0482o2(0, 0);
        return 1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int a(C2322jf c2322jf) {
        int iB = c2322jf.d.b(((C3492zF) ((AbstractC1939ef) this).a).K2(), ((C3492zF) ((AbstractC1939ef) this).a).W0());
        int iA = c2322jf.d.a(((C3492zF) ((AbstractC1939ef) this).a).J2(), ((C3492zF) ((AbstractC1939ef) this).a).W0());
        if (iB == iA) {
            this.e = 1;
        } else if (iB <= 15 && iA <= 15) {
            this.e = 1;
        } else if (iA <= 255) {
            this.e = 2;
        } else {
            this.e = 3;
        }
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final AbstractC2412ko a() {
        return ((AbstractC1939ef) this).a;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final boolean a(AbstractC1939ef abstractC1939ef, C2322jf c2322jf) {
        if (!(abstractC1939ef instanceof C2016ff)) {
            return false;
        }
        C2016ff c2016ff = (C2016ff) abstractC1939ef;
        if (c2322jf.d.b(((C3492zF) ((AbstractC1939ef) this).a).K2(), ((C3492zF) ((AbstractC1939ef) this).a).W0()) == c2322jf.d.b(((C3492zF) ((AbstractC1939ef) c2016ff).a).K2(), ((C3492zF) ((AbstractC1939ef) c2016ff).a).W0())) {
            return c2322jf.d.a(((C3492zF) ((AbstractC1939ef) this).a).J2(), ((C3492zF) ((AbstractC1939ef) this).a).W0()) == c2322jf.d.a(((C3492zF) ((AbstractC1939ef) c2016ff).a).J2(), ((C3492zF) ((AbstractC1939ef) c2016ff).a).W0());
        }
        return false;
    }
}
