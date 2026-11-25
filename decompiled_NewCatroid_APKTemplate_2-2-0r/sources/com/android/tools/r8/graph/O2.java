package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C1549Za;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.shaking.C3733i;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O2 {
    static final /* synthetic */ boolean c = true;
    public static final /* synthetic */ int d = 0;
    private C0767v2 a;
    private C0684j2 b;

    public O2(C0767v2 c0767v2) {
        this.a = c0767v2;
    }

    public final void a(C1549Za c1549Za, AbstractC3649o0 abstractC3649o0) {
        C0684j2 c0684j2 = this.b;
        if (c0684j2 != null) {
            c1549Za.a(abstractC3649o0.d(c0684j2.e), abstractC3649o0.a(this.b).toString(), this.b.h.a(abstractC3649o0));
        } else {
            c1549Za.a(abstractC3649o0.d(this.a), (String) null, (String) null);
        }
    }

    public C0684j2 b() {
        return this.b;
    }

    public final C0767v2 c() {
        C0684j2 c0684j2 = this.b;
        return c0684j2 != null ? c0684j2.B0() : this.a;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof O2) {
            O2 o2 = (O2) obj;
            if (this.a == o2.a && this.b == o2.b) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        if (!c) {
            if ((this.a == null) == (this.b == null)) {
                throw new AssertionError();
            }
        }
        return System.identityHashCode(this.b) + System.identityHashCode(this.a);
    }

    public final String toString() {
        C0767v2 c0767v2 = this.a;
        String strB1 = c0767v2 == null ? "null" : c0767v2.b1();
        C0684j2 c0684j2 = this.b;
        return "[enclosingClass: " + strB1 + ", enclosingMethod: " + (c0684j2 != null ? c0684j2.r0() : "null") + "]";
    }

    public O2(C0684j2 c0684j2) {
        this.b = c0684j2;
    }

    public final C0767v2 a() {
        return this.a;
    }

    public final void a(C0702m c0702m, com.android.tools.r8.dex.l0 l0Var) {
        C0767v2 c0767v2 = this.a;
        if (c0767v2 != null && l0Var.a(c0767v2)) {
            C0759u2 c0759u2C = c0702m.z().c(c0767v2);
            c0759u2C.getClass();
            l0Var.a(c0759u2C);
        }
        C0684j2 c0684j2 = this.b;
        if (c0684j2 == null || !c0684j2.b(c0702m, l0Var)) {
            return;
        }
        C0759u2 c0759u2A = c0702m.z().a(c0684j2);
        c0759u2A.getClass();
        l0Var.a(c0759u2A);
    }

    public final boolean a(C0702m c0702m) {
        if (this.b != null) {
            return ((C3733i) c0702m.f()).a((AbstractC0744s2) this.b);
        }
        if (this.a != null) {
            return ((C3733i) c0702m.f()).a((AbstractC0744s2) this.a);
        }
        return false;
    }
}
