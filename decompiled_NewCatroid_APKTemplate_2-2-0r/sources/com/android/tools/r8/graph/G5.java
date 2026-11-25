package com.android.tools.r8.graph;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class G5 extends G {
    private G5(C0702m c0702m, int i) {
        super(c0702m, i);
    }

    public static G5 a(C0702m c0702m) {
        return new G5(c0702m, 1);
    }

    final G b() {
        return this;
    }

    public static G5 b(C0702m c0702m) {
        return new G5(c0702m, 4);
    }

    public final G a() {
        this.e = true;
        return b();
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0043  */
    @Override // com.android.tools.r8.graph.G
    final void a(AbstractC0727q0 abstractC0727q0) {
        boolean zF0;
        boolean zE0;
        AbstractC0727q0 abstractC0727q0A;
        boolean zE1;
        if ((this.e && abstractC0727q0.isInterface()) || this.c.contains(abstractC0727q0)) {
            return;
        }
        int iB = AbstractC0403b.b(this.b);
        if (iB == 0) {
            zF0 = true;
        } else if (iB == 1) {
            zF0 = abstractC0727q0.f0();
        } else if (iB != 2) {
            if (iB != 3) {
                throw new C3405y10();
            }
            zF0 = abstractC0727q0.e0();
        } else if (abstractC0727q0.f0() || (abstractC0727q0 instanceof C0754u0)) {
            zF0 = true;
        } else {
            zF0 = false;
        }
        if (zF0) {
            this.d.addFirst(abstractC0727q0);
        }
        C0767v2 c0767v2 = abstractC0727q0.f;
        if (c0767v2 != null && (abstractC0727q0A = this.a.a(c0767v2)) != null) {
            int iB2 = AbstractC0403b.b(this.b);
            if (iB2 == 0 || iB2 == 1 || iB2 == 2) {
                zE1 = true;
            } else {
                if (iB2 != 3) {
                    throw new C3405y10();
                }
                zE1 = abstractC0727q0A.e0();
            }
            if (zE1) {
                a(abstractC0727q0A);
            }
        }
        if (this.e) {
            return;
        }
        for (C0767v2 c0767v3 : abstractC0727q0.g.a) {
            AbstractC0727q0 abstractC0727q0A2 = this.a.a(c0767v3);
            if (abstractC0727q0A2 != null) {
                int iB3 = AbstractC0403b.b(this.b);
                if (iB3 == 0 || iB3 == 1 || iB3 == 2) {
                    zE0 = true;
                } else {
                    if (iB3 != 3) {
                        throw new C3405y10();
                    }
                    zE0 = abstractC0727q0A2.e0();
                }
                if (zE0) {
                    a(abstractC0727q0A2);
                }
            }
        }
    }
}
