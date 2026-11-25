package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C3011sk;
import com.android.tools.r8.shaking.C3733i;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class z0 extends C3617e0 {
    static final /* synthetic */ boolean i = true;
    private final Map g;
    private final Set h;

    z0(C0702m c0702m, C3011sk c3011sk, Set set) {
        super(c0702m);
        this.g = c3011sk;
        this.h = set;
    }

    @Override // com.android.tools.r8.naming.C3617e0, com.android.tools.r8.naming.InterfaceC3612c
    public final C0759u2 a(C0767v2 c0767v2, char[] cArr, I i2, final Predicate predicate) {
        boolean z = i;
        if (!z && this.g.containsKey(c0767v2)) {
            throw new AssertionError();
        }
        if (z || ((C3733i) this.e.f()).k(c0767v2)) {
            return super.a(c0767v2, cArr, i2, new Predicate() { // from class: com.android.tools.r8.naming.z0$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(predicate, (String) obj);
                }
            });
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.naming.C3617e0, com.android.tools.r8.naming.InterfaceC3612c
    public final C0759u2 b(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0D = ((C3733i) this.e.f()).d(c0767v2);
        if (abstractC0727q0D == null) {
            return c0767v2.e;
        }
        if (abstractC0727q0D.I1() && this.g.containsKey(c0767v2)) {
            return (C0759u2) this.g.get(c0767v2);
        }
        return (abstractC0727q0D.e0() && ((C3733i) this.e.f()).f(abstractC0727q0D.a0())) ? (C0759u2) this.g.get(c0767v2) : c0767v2.e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Predicate predicate, String str) {
        if (this.h.contains(str)) {
            return true;
        }
        return predicate.test(str);
    }

    @Override // com.android.tools.r8.naming.C3617e0, com.android.tools.r8.naming.InterfaceC3612c
    public final boolean a(C0767v2 c0767v2) {
        return this.g.containsKey(c0767v2);
    }
}
