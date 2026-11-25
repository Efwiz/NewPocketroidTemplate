package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.utils.A1;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O0 extends AbstractC3647n0 {
    static final /* synthetic */ boolean f = true;
    private final C0722p1 d;
    private final AbstractC3649o0 e;

    public O0(C0702m c0702m) {
        super(c0702m.a());
        this.d = c0702m.a();
        this.e = c0702m.z();
    }

    public static AbstractC3649o0 a(C0702m c0702m) {
        return (!c0702m.Y().C1() || c0702m.f().d(c0702m.a().a2) == null) ? c0702m.z() : new O0(c0702m);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final boolean b() {
        return this.e.b();
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 e(C0767v2 c0767v2) {
        return this.e.e(c0767v2);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    protected final C0759u2 a(C0767v2 c0767v2) {
        C0722p1 c0722p1 = this.d;
        C0759u2 c0759u2 = c0767v2 == c0722p1.a2 ? c0722p1.b2.e : null;
        return c0759u2 != null ? c0759u2 : this.e.c(c0767v2);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(J3 j3, A1 a1) {
        if (!f) {
            C0767v2 c0767v2B = j3.b();
            C0722p1 c0722p1 = this.d;
            if ((c0767v2B == c0722p1.a2 ? c0722p1.b2.e : null) != null) {
                throw new AssertionError();
            }
        }
        return this.e.a(j3, a1);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(C0684j2 c0684j2) {
        return this.e.a(c0684j2);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(Y0 y0) {
        return this.e.a(y0);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final String a(String str) {
        return this.e.a(str);
    }
}
