package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class WN extends AbstractC0859Bj {
    private int b;
    private int c;
    private List d = Collections.emptyList();

    private WN() {
    }

    static WN f() {
        return new WN();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        XN xnG = g();
        if (xnG.a()) {
            return xnG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new WN().a(g());
    }

    public final XN g() {
        XN xn = new XN(this, 0);
        int i = (this.b & 1) == 1 ? 1 : 0;
        xn.c = this.c;
        if ((this.b & 2) == 2) {
            this.d = Collections.unmodifiableList(this.d);
            this.b &= -3;
        }
        xn.d = this.d;
        xn.b = i;
        return xn;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final WN a(XN xn) {
        if (xn == XN.h()) {
            return this;
        }
        if (xn.j()) {
            a(xn.i());
        }
        if (!xn.d.isEmpty()) {
            if (this.d.isEmpty()) {
                this.d = xn.d;
                this.b &= -3;
            } else {
                if ((this.b & 2) != 2) {
                    this.d = new ArrayList(this.d);
                    this.b |= 2;
                }
                this.d.addAll(xn.d);
            }
        }
        a(e().b(xn.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        XN xn = null;
        try {
            try {
                a((XN) ((ON) XN.h).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                XN xn2 = (XN) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    xn = xn2;
                    if (xn != null) {
                        a(xn);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (xn != null) {
                a(xn);
            }
            throw th;
        }
    }

    public final void a(int i) {
        this.b |= 1;
        this.c = i;
    }

    public final void a(QN qn) {
        if ((this.b & 2) != 2) {
            this.d = new ArrayList(this.d);
            this.b |= 2;
        }
        this.d.add(qn.g());
    }
}
