package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class HO extends AbstractC0859Bj {
    private int b;
    private List c = Collections.emptyList();

    private HO() {
    }

    static HO f() {
        return new HO();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        MO moG = g();
        if (moG.a()) {
            return moG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new HO().a(g());
    }

    public final MO g() {
        MO mo = new MO(this, 0);
        if ((this.b & 1) == 1) {
            this.c = Collections.unmodifiableList(this.c);
            this.b &= -2;
        }
        mo.b = this.c;
        return mo;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final HO a(MO mo) {
        if (mo == MO.g()) {
            return this;
        }
        if (!mo.b.isEmpty()) {
            if (this.c.isEmpty()) {
                this.c = mo.b;
                this.b &= -2;
            } else {
                if ((this.b & 1) != 1) {
                    this.c = new ArrayList(this.c);
                    this.b |= 1;
                }
                this.c.addAll(mo.b);
            }
        }
        a(e().b(mo.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        MO mo = null;
        try {
            try {
                a((MO) ((GO) MO.f).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                MO mo2 = (MO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    mo = mo2;
                    if (mo != null) {
                        a(mo);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (mo != null) {
                a(mo);
            }
            throw th;
        }
    }
}
