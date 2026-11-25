package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.utils.A1;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.naming.t0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3663t0 extends AbstractC3647n0 {
    static final /* synthetic */ boolean f = true;
    private final C0702m d;
    private final AbstractC3649o0 e;

    public C3663t0(C0702m c0702m) {
        super(c0702m.a());
        this.d = c0702m;
        this.e = c0702m.z();
    }

    public static AbstractC3649o0 a(C0702m c0702m) {
        return !c0702m.t.a() ? c0702m.z() : new C3663t0(c0702m);
    }

    private void b(final String str) {
        this.d.t.a(new Consumer() { // from class: com.android.tools.r8.naming.t0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3663t0.a(str, (C0767v2) obj);
            }
        });
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 e(C0767v2 c0767v2) {
        C0702m c0702m = this.d;
        if (!c0702m.t.a(c0702m, c0767v2)) {
            return null;
        }
        C0702m c0702m2 = this.d;
        return c0702m2.t.b(c0702m2, c0767v2).e;
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    protected final C0759u2 a(C0767v2 c0767v2) {
        C0759u2 c0759u2;
        C0702m c0702m = this.d;
        if (c0702m.t.a(c0702m, c0767v2)) {
            C0702m c0702m2 = this.d;
            c0759u2 = c0702m2.t.b(c0702m2, c0767v2).e;
        } else {
            c0759u2 = null;
        }
        return c0759u2 != null ? c0759u2 : this.e.c(c0767v2);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(J3 j3, A1 a1) {
        C0759u2 c0759u2;
        C0767v2 c0767v2B = j3.b();
        C0702m c0702m = this.d;
        if (c0702m.t.a(c0702m, c0767v2B)) {
            C0702m c0702m2 = this.d;
            c0759u2 = c0702m2.t.b(c0702m2, c0767v2B).e;
        } else {
            c0759u2 = null;
        }
        if (c0759u2 != null) {
            return j3.c();
        }
        return this.e.a(j3, a1);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(C0684j2 c0684j2) {
        C0759u2 c0759u2;
        C0767v2 c0767v2 = c0684j2.e;
        C0702m c0702m = this.d;
        if (c0702m.t.a(c0702m, c0767v2)) {
            C0702m c0702m2 = this.d;
            c0759u2 = c0702m2.t.b(c0702m2, c0767v2).e;
        } else {
            c0759u2 = null;
        }
        if (c0759u2 != null) {
            return c0684j2.f;
        }
        return this.e.a(c0684j2);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(Y0 y0) {
        C0759u2 c0759u2;
        C0767v2 c0767v2 = y0.e;
        C0702m c0702m = this.d;
        if (c0702m.t.a(c0702m, c0767v2)) {
            C0702m c0702m2 = this.d;
            c0759u2 = c0702m2.t.b(c0702m2, c0767v2).e;
        } else {
            c0759u2 = null;
        }
        if (c0759u2 != null) {
            return y0.f;
        }
        return this.e.a(y0);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final String a(String str) {
        if (!f) {
            b(str);
        }
        return this.e.a(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, C0767v2 c0767v2) {
        if (!f && c0767v2.H0().equals(str)) {
            throw new AssertionError();
        }
    }
}
