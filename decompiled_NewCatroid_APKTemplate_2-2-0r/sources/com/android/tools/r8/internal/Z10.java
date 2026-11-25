package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Z10 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C2409kl b;
    private final IdentityHashMap c = new IdentityHashMap();

    public Z10(C0702m c0702m, C2409kl c2409kl) {
        this.a = c0702m;
        this.b = c2409kl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(S10 s10) {
        return this.c.get(s10) == Y10.a;
    }

    public final boolean a(C3332x4 c3332x4) {
        return AbstractC2734ot.b(c3332x4.x(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.Z10$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a((ML) obj);
            }
        });
    }

    private S10 a(com.android.tools.r8.utils.O3 o3, com.android.tools.r8.utils.K k) {
        X10 x10 = new X10();
        while (o3.c()) {
            S10 s10 = (S10) o3.f();
            if (!d && x10.a.containsKey(s10)) {
                throw new AssertionError();
            }
            Y10 y10 = (Y10) this.c.get(s10);
            if (y10 != null) {
                if (y10 != Y10.a) {
                    a(s10, x10);
                    return s10;
                }
            } else {
                if (s10.z()) {
                    b(s10, x10);
                    a(s10, x10);
                    return s10;
                }
                LinkedHashSet<S10> linkedHashSet = new LinkedHashSet(s10.g0());
                for (AbstractC2412ko abstractC2412ko : s10.h0()) {
                    com.android.tools.r8.ir.optimize.L lA = abstractC2412ko.a(this.a, this.b);
                    if (lA.d()) {
                        b(s10, x10);
                        a(s10, x10);
                        return s10;
                    }
                    if (lA.c()) {
                        lA.b().forEach(new S10$$ExternalSyntheticLambda8(linkedHashSet));
                    }
                    if (abstractC2412ko.d1()) {
                        linkedHashSet.add(abstractC2412ko.b());
                    }
                }
                Iterator it = linkedHashSet.iterator();
                while (it.hasNext()) {
                    S10 s11 = (S10) it.next();
                    Object obj = this.c.get(s11);
                    Y10 y11 = Y10.b;
                    if (obj == y11) {
                        b(s10, x10);
                        a(s10, x10);
                        return s10;
                    }
                    if (!d && this.c.get(s11) == y11) {
                        throw new AssertionError();
                    }
                    if (s11 == s10 || this.c.containsKey(s11)) {
                        it.remove();
                    }
                }
                if (linkedHashSet.isEmpty()) {
                    com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(s10);
                    while (o3H.c()) {
                        S10 s12 = (S10) o3H.f();
                        IdentityHashMap identityHashMap = this.c;
                        Y10 y12 = Y10.a;
                        Y10 y13 = (Y10) identityHashMap.put(s12, y12);
                        if (!d && y13 != null && y13 != y12) {
                            throw new AssertionError();
                        }
                        x10.b(s12);
                        for (S10 s13 : x10.a(s12)) {
                            if (!x10.a.containsKey(s13)) {
                                o3H.c(s13);
                            }
                        }
                    }
                } else {
                    for (S10 s14 : linkedHashSet) {
                        x10.a(s10, s14);
                        k.b(o3.e(s14));
                    }
                    o3.a((Iterable) linkedHashSet);
                }
            }
        }
        return null;
    }

    private void b(S10 s10, X10 x10) {
        x10.b(s10);
        IdentityHashMap identityHashMap = this.c;
        Y10 y10 = Y10.b;
        Y10 y11 = (Y10) identityHashMap.put(s10, y10);
        if (!d && y11 != null && y11 != y10) {
            throw new AssertionError();
        }
    }

    private void a(S10 s10, X10 x10) {
        com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(s10);
        while (o3H.c()) {
            for (S10 s11 : x10.a((S10) o3H.f())) {
                b(s11, x10);
                o3H.c(s11);
            }
        }
    }

    public final boolean a(S10 s10) {
        if (!s10.S()) {
            return true;
        }
        com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(s10);
        com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K();
        S10 s10A = a(o3H, k);
        boolean zIsNull = Objects.isNull(s10A);
        if (s10A == null) {
            if (k.e()) {
                for (S10 s11 : o3H.b()) {
                    IdentityHashMap identityHashMap = this.c;
                    Y10 y10 = Y10.a;
                    Y10 y11 = (Y10) identityHashMap.put(s11, y10);
                    if (!d && y11 != null && y11 != y10) {
                        throw new AssertionError();
                    }
                }
            } else if (!d && !o3H.b().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.Z10$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.b((S10) obj);
                }
            })) {
                throw new AssertionError();
            }
        }
        return zIsNull;
    }
}
