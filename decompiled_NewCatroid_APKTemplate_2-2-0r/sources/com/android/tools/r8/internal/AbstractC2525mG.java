package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2525mG {
    public C3141ua a() {
        return null;
    }

    public C1294Qh b() {
        return null;
    }

    public KQ c() {
        return null;
    }

    public boolean d() {
        return false;
    }

    public abstract boolean e();

    public abstract boolean f();

    public final C0759u2 a(AbstractC0744s2 abstractC0744s2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        AbstractC0744s2 abstractC0744s2A = abstractC1476Wj.a(abstractC0744s2);
        if (e()) {
            if (this instanceof C1294Qh) {
                C1294Qh c1294QhB = b();
                abstractC0744s2A.t0();
                c1294QhB.getClass();
                throw new C3405y10();
            }
            if (this instanceof C3141ua) {
                return a().a(abstractC0744s2A.w0(), c0702m, abstractC3649o0);
            }
            if (d()) {
                return c().a(abstractC0744s2A.w0(), c0702m, abstractC1476Wj, abstractC3649o0);
            }
        }
        return abstractC3649o0.a(abstractC0744s2A, c0702m.a());
    }
}
