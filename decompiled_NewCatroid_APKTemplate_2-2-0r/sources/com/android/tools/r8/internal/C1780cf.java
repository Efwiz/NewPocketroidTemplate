package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0421c1;
import com.android.tools.r8.dex.code.C0426d1;
import com.android.tools.r8.dex.code.C0431e1;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1780cf extends AbstractC1939ef {
    static final /* synthetic */ boolean f = true;
    private int e;

    public C1780cf(C1296Qj c1296Qj) {
        super(c1296Qj);
        this.e = -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final void a(C2322jf c2322jf, ArrayList arrayList) {
        com.android.tools.r8.dex.code.B1 c0431e1;
        C1296Qj c1296Qj = (C1296Qj) ((AbstractC1939ef) this).a;
        int iC = c2322jf.c(c1296Qj).c();
        AbstractC1939ef abstractC1939efA = c2322jf.a(c1296Qj.K2());
        int iC2 = abstractC1939efA.c() - iC;
        if (iC2 < 0) {
            c2322jf.p = true;
        }
        C1616aU c1616aUB0 = abstractC1939efA.a().B0();
        if (c1616aUB0 != null && this.e == abstractC1939efA.d() && c1616aUB0.Y0().o()) {
            com.android.tools.r8.dex.code.J jB = c1616aUB0.b(c2322jf);
            jB.g(c());
            arrayList.add(jB);
            return;
        }
        int i = this.e;
        if (i == iC2) {
            for (int i2 = 0; i2 < this.e; i2++) {
                com.android.tools.r8.dex.code.R2 r2 = new com.android.tools.r8.dex.code.R2();
                r2.g(c() + i2);
                arrayList.add(r2);
            }
            return;
        }
        if (i == 1) {
            if (!f && iC2 == 0) {
                throw new AssertionError();
            }
            c0431e1 = new C0431e1(iC2);
        } else if (i != 2) {
            if (i != 3) {
                throw new C3405y10("Unexpected size for goto instruction: " + this.e);
            }
            c0431e1 = new C0426d1(iC2);
        } else if (iC2 == 0) {
            arrayList.add(new com.android.tools.r8.dex.code.R2());
            c0431e1 = new C0431e1(-1);
        } else {
            c0431e1 = new C0421c1(iC2);
        }
        c0431e1.g(c());
        arrayList.add(c0431e1);
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
        return !f ? 3 : 3;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int f() {
        if (f) {
            return 1;
        }
        new C0431e1(42);
        return 1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int a(C2322jf c2322jf) {
        int iC;
        boolean z = f;
        if (!z && this.e >= 0) {
            throw new AssertionError();
        }
        C1296Qj c1296Qj = (C1296Qj) ((AbstractC1939ef) this).a;
        AbstractC1939ef abstractC1939efA = c2322jf.a(c1296Qj.K2());
        if (c1296Qj == abstractC1939efA.a()) {
            this.e = 2;
            return 2;
        }
        int iB = b();
        int iB2 = abstractC1939efA.b();
        if (iB2 < iB) {
            iC = c() - abstractC1939efA.c();
        } else {
            iC = (iB2 - (iB - c())) - c();
        }
        if (iC <= 127) {
            this.e = 1;
        } else if (iC <= 32767) {
            this.e = 2;
        } else {
            this.e = 3;
        }
        if (abstractC1939efA.a().u2() && abstractC1939efA.a().Y0().o()) {
            if (!z && (abstractC1939efA instanceof C1553Ze)) {
                throw new AssertionError();
            }
            this.e = Math.min(abstractC1939efA.d(), this.e);
        }
        if (z || this.e != 0) {
            return this.e;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final boolean a(AbstractC1939ef abstractC1939ef, C2322jf c2322jf) {
        return abstractC1939ef instanceof C1780cf;
    }
}
