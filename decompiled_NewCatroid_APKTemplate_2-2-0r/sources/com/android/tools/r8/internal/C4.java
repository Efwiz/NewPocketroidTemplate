package com.android.tools.r8.internal;

import com.android.tools.r8.utils.C3988x2;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class C4 {
    public static void a(C2409kl c2409kl, com.android.tools.r8.utils.A1 a1) {
        a(c2409kl, AbstractC1639am.a(new VS(), new BF(), new TW()), a1);
        a(c2409kl, AbstractC1639am.a(new VW(), new TW(), new C3113uC(), new C1127Kf(), new UW()), a1);
    }

    private static void a(C2409kl c2409kl, AbstractC1639am abstractC1639am, com.android.tools.r8.utils.A1 a1) {
        B4 b4A = c2409kl.a(c2409kl.d.size());
        int i = 0;
        while (b4A.hasPrevious()) {
            C3332x4 c3332x4Previous = b4A.previous();
            final C3486zB c3486zB = new C3486zB(c2409kl, c3332x4Previous, c3332x4Previous.p().size());
            while (true) {
                boolean z = false;
                while (true) {
                    Iterator it = abstractC1639am.iterator();
                    while (it.hasNext()) {
                        D4 d4 = (D4) it.next();
                        boolean zA = d4.a(c3486zB);
                        if (zA) {
                            d4.a();
                        }
                        z |= zA;
                    }
                    if (c3486zB.hasPrevious()) {
                        int i2 = a1.n1.a0;
                        if (i2 != -1) {
                            if (i > i2) {
                                throw new C1367Tb("Too many iterations in BasicBlockMuncher");
                            }
                            i++;
                        }
                        c3486zB.previous();
                    }
                }
                if (z) {
                    c3486zB = new C3486zB(c2409kl, c3332x4Previous, c3332x4Previous.p().size());
                }
            }
            if (com.android.tools.r8.utils.L1.a(b4A, C3988x2.a(new Predicate() { // from class: com.android.tools.r8.internal.C4$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return c3486zB.b((C3332x4) obj);
                }
            })) != null) {
                b4A.next();
            }
        }
    }
}
