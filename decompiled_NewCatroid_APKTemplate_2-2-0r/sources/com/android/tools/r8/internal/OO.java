package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class OO extends AbstractC0859Bj {
    private int b;
    private YA c = XA.b;

    private OO() {
    }

    static OO f() {
        return new OO();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        PO poG = g();
        if (poG.a()) {
            return poG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new OO().a(g());
    }

    public final PO g() {
        PO po = new PO(this, 0);
        if ((this.b & 1) == 1) {
            this.c = this.c.h();
            this.b &= -2;
        }
        po.b = this.c;
        return po;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final OO a(PO po) {
        if (po == PO.g()) {
            return this;
        }
        if (!po.b.isEmpty()) {
            if (this.c.isEmpty()) {
                this.c = po.b;
                this.b &= -2;
            } else {
                if ((this.b & 1) != 1) {
                    this.c = new XA(this.c);
                    this.b |= 1;
                }
                this.c.addAll(po.b);
            }
        }
        a(e().b(po.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        PO po = null;
        try {
            try {
                a((PO) ((NO) PO.f).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                PO po2 = (PO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    po = po2;
                    if (po != null) {
                        a(po);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (po != null) {
                a(po);
            }
            throw th;
        }
    }
}
