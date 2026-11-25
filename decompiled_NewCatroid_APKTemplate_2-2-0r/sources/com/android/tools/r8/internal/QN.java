package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class QN extends AbstractC0859Bj {
    private int b;
    private int c;
    private UN d = UN.k();

    private QN() {
    }

    static QN f() {
        return new QN();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        VN vn = new VN(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        vn.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        vn.d = this.d;
        vn.b = i2;
        if (vn.a()) {
            return vn;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        QN qn = new QN();
        VN vn = new VN(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        vn.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        vn.d = this.d;
        vn.b = i2;
        return qn.a(vn);
    }

    public final VN g() {
        VN vn = new VN(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        vn.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        vn.d = this.d;
        vn.b = i2;
        if (vn.a()) {
            return vn;
        }
        throw new C1659b10();
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final QN a(VN vn) {
        if (vn == VN.g()) {
            return this;
        }
        if (vn.j()) {
            a(vn.h());
        }
        if (vn.k()) {
            UN unI = vn.i();
            if ((this.b & 2) != 2 || this.d == UN.k()) {
                this.d = unI;
            } else {
                this.d = SN.f().a(this.d).a(unI).g();
            }
            this.b |= 2;
        }
        a(e().b(vn.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        VN vn = null;
        try {
            try {
                a((VN) ((PN) VN.h).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                VN vn2 = (VN) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    vn = vn2;
                    if (vn != null) {
                        a(vn);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (vn != null) {
                a(vn);
            }
            throw th;
        }
    }

    public final void a(int i) {
        this.b |= 1;
        this.c = i;
    }

    public final void a(UN un) {
        this.d = un;
        this.b |= 2;
    }
}
