package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class P3 {
    private AbstractC1154Lf a = AbstractC1154Lf.n();
    private B1 b = B1.E();
    private boolean c = false;

    public final void a(Q3 q3) {
        P3 p3A = a(q3.a).a(q3.b);
        p3A.c = p3A.c || q3.c;
    }

    public final void b() {
        this.c = true;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0016  */
    public final P3 a(AbstractC1154Lf abstractC1154Lf) {
        AbstractC1154Lf abstractC1154Lf2 = this.a;
        if (abstractC1154Lf2.equals(abstractC1154Lf)) {
            abstractC1154Lf = abstractC1154Lf2;
        } else if (!abstractC1154Lf2.m()) {
            if (abstractC1154Lf.m()) {
                abstractC1154Lf = abstractC1154Lf2;
            } else {
                abstractC1154Lf = AbstractC1154Lf.n();
            }
        }
        this.a = abstractC1154Lf;
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0016  */
    public final P3 a(B1 b1) {
        B1 b2 = this.b;
        if (b2.equals(b1)) {
            b1 = b2;
        } else if (!b2.isUnknown()) {
            if (b1.isUnknown()) {
                b1 = b2;
            } else {
                b1 = C2892r10.F();
            }
        }
        this.b = b1;
        return this;
    }

    public final Q3 a() {
        AbstractC1154Lf abstractC1154Lf = this.a;
        B1 b1 = this.b;
        boolean z = this.c;
        if (abstractC1154Lf.m() && b1.isUnknown() && !z) {
            return Q3.d;
        }
        return new Q3(abstractC1154Lf, b1, z);
    }
}
