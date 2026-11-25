package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2079gT extends AbstractC3424yG {
    static final /* synthetic */ boolean p = true;
    private final G4 n;
    private final Map o;

    private C2079gT(C0702m c0702m, Q4 q4, Q4 q5, C3011sk c3011sk, HashMap map) {
        super(c0702m, q4, q5, c3011sk);
        this.n = c3011sk;
        this.o = map;
    }

    @Override // com.android.tools.r8.internal.DH, com.android.tools.r8.internal.AbstractC1476Wj
    public final String a(String str) {
        return (String) this.o.getOrDefault(k().a(str), str);
    }

    @Override // com.android.tools.r8.internal.AbstractC3424yG, com.android.tools.r8.internal.AbstractC1476Wj
    public final C0767v2 a(C0767v2 c0767v2) {
        return k().a((C0767v2) this.n.g().getOrDefault(c0767v2, c0767v2));
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean a(AbstractC0744s2 abstractC0744s2, AbstractC0744s2 abstractC0744s3) {
        if (abstractC0744s2 == abstractC0744s3) {
            if (p) {
                return false;
            }
            throw new AssertionError("The from and to references should not be equal");
        }
        super.a(abstractC0744s2, abstractC0744s3);
        return ((Boolean) AbstractC0744s2.a(abstractC0744s2, abstractC0744s3, new BiFunction() { // from class: com.android.tools.r8.internal.gT$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Boolean.valueOf(this.f$0.a((C0767v2) obj, (C0767v2) obj2));
            }
        }, new BiFunction() { // from class: com.android.tools.r8.internal.gT$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Boolean.valueOf(this.f$0.a((AbstractC0642e2) obj, (AbstractC0642e2) obj2));
            }
        }, new BiFunction() { // from class: com.android.tools.r8.internal.gT$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Boolean.valueOf(this.f$0.a((AbstractC0642e2) obj, (AbstractC0642e2) obj2));
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0767v2 c0767v2, C0767v2 c0767v3) {
        return c0767v2 == c0767v3 || this.n.get(c0767v2) == c0767v3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(AbstractC0642e2 abstractC0642e2, AbstractC0642e2 abstractC0642e3) {
        if (a(abstractC0642e2.B0(), abstractC0642e3.B0())) {
            return com.android.tools.r8.utils.I1.a(new BiPredicate() { // from class: com.android.tools.r8.internal.gT$$ExternalSyntheticLambda0
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return this.f$0.a((C0767v2) obj, (C0767v2) obj2);
                }
            }, abstractC0642e2.a(j()), abstractC0642e3.a(j()));
        }
        return false;
    }
}
