package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0722p1;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Y0 extends AbstractC3722g1 {
    private static final Y0 j;
    private static final Y0 k;
    static final /* synthetic */ boolean l = true;
    private final boolean h;
    private final boolean i;

    private Y0(W0 w0) {
        super(w0);
        this.h = w0.p();
        this.i = w0.o();
    }

    public static boolean a(C0722p1 c0722p1, com.android.tools.r8.utils.A1 a1, Function function, Function function2) {
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) function.apply(c0722p1.t3);
        return abstractC0727q0 == null || abstractC0727q0.I1() || !((Y0) function2.apply(abstractC0727q0.a0())).d((K0) a1);
    }

    public static Y0 i() {
        return k;
    }

    public static X0 p() {
        return k.o();
    }

    public static Y0 q() {
        return j;
    }

    public final boolean e(K0 k0) {
        return ((com.android.tools.r8.utils.A1) k0).n1() && this.h;
    }

    final W0 j() {
        return new W0(this);
    }

    final boolean k() {
        return this.i;
    }

    final boolean l() {
        return this.h;
    }

    public final boolean m() {
        return this.i;
    }

    public final boolean n() {
        return equals(j);
    }

    public final X0 o() {
        if (l || !equals(j)) {
            return new X0(this);
        }
        throw new AssertionError();
    }

    static {
        W0IA w0ia = null;
        W0 w0Q = new W0().m();
        w0Q.i = false;
        j = (Y0) w0Q.a();
        W0 w0 = (W0) new W0().l();
        w0.j = false;
        w0.i = true;
        k = (Y0) w0.a();
    }
}
