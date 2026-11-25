package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.io.UTFDataFormatException;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2544mY {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final com.android.tools.r8.naming.C b;
    private final C1229Oa c;

    C2544mY(C0702m c0702m, com.android.tools.r8.naming.C c) {
        this.a = c0702m;
        this.b = c;
        this.c = AbstractC3472z00.b((C0702m<?>) c0702m, WH.b());
    }

    private static boolean a(C2409kl c2409kl) {
        B4 b4S = c2409kl.s();
        boolean z = false;
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            Iterator it = next.t().iterator();
            while (it.hasNext()) {
                C1620aY c1620aYI0 = ((C3332x4) it.next()).m().I0();
                if (c1620aYI0 != null) {
                    if (next == c1620aYI0.J2()) {
                        next.a(c2409kl).a(c2409kl, b4S).a(c2409kl).a(c2409kl, b4S);
                        z = true;
                        break;
                    }
                    z = true;
                }
            }
        }
        return z;
    }

    public final void b(C2409kl c2409kl) {
        AbstractC2470lY c2312jY;
        if (!c2409kl.t().I()) {
            if (!d && !AbstractC1699bX.a(c2409kl.r()).noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.mY$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((AbstractC2412ko) obj).y2();
                }
            })) {
                throw new AssertionError();
            }
            return;
        }
        if (a(c2409kl)) {
            Set setC = OU.c();
            B4 b4S = c2409kl.s();
            while (b4S.hasNext()) {
                C3332x4 next = b4S.next();
                C1620aY c1620aYI0 = next.m().I0();
                if (c1620aYI0 != null) {
                    try {
                        if (c1620aYI0.L2() < this.a.Y().Q) {
                            c2312jY = new C2312jY(this, c2409kl, b4S, next, c1620aYI0, setC);
                        } else {
                            if (this.a.Y().k1()) {
                                if (com.android.tools.r8.naming.D.a(this.a.a(), c1620aYI0.value())) {
                                    c2312jY = new C2312jY(this, c2409kl, b4S, next, c1620aYI0, setC);
                                }
                            }
                            c2312jY = new C2391kY(this, c2409kl, b4S, next, c1620aYI0, setC);
                        }
                        c2312jY.a();
                    } catch (UTFDataFormatException unused) {
                        throw new C3405y10();
                    }
                }
            }
            com.android.tools.r8.naming.C c = this.b;
            if (c != null) {
                c.a(c2409kl, setC);
            }
            if (!d && !c2409kl.b(this.a)) {
                throw new AssertionError();
            }
        }
    }
}
