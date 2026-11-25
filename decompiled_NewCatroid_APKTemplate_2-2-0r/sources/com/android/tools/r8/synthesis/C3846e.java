package com.android.tools.r8.synthesis;

import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.O0;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.DH;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.utils.C3970u0;
import java.util.Comparator;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.e, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3846e implements Comparable {
    static final /* synthetic */ boolean e = true;
    private final C0767v2 a;
    private final C0767v2 b;
    private final Origin c;
    private final FeatureSplit d;

    private C3846e(C0767v2 c0767v2, C0767v2 c0767v3, Origin origin, FeatureSplit featureSplit) {
        this.a = c0767v2;
        this.b = c0767v3;
        this.c = origin;
        this.d = featureSplit;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static C3846e a(com.android.tools.r8.graph.T t) {
        O0 o0 = (O0) t;
        return new C3846e(o0.C(), o0.C(), ((AbstractC0727q0) t).c, FeatureSplit.BASE);
    }

    final C0767v2 c() {
        return this.a;
    }

    public final boolean d() {
        return this.a != this.b;
    }

    public final String toString() {
        String str;
        C0767v2 c0767v2 = this.a;
        if (this.d.isBase()) {
            str = "";
        } else {
            str = ", feature:" + this.d;
        }
        return "SynthesizingContext{" + c0767v2 + str + "}";
    }

    final Origin b() {
        return this.c;
    }

    static C3846e a(C0767v2 c0767v2) {
        return new C3846e(c0767v2, c0767v2, com.android.tools.r8.origin.c.a(), FeatureSplit.BASE);
    }

    static C3846e a(C0729q2 c0729q2, FeatureSplit featureSplit) {
        return new C3846e(c0729q2.getType(), c0729q2.getType(), c0729q2.c, featureSplit);
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final int compareTo(C3846e c3846e) {
        return Comparator.comparing(new Function() { // from class: com.android.tools.r8.synthesis.e$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C3846e) obj).c();
            }
        }).thenComparing(new Function() { // from class: com.android.tools.r8.synthesis.e$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C3846e) obj).b;
            }
        }).compare(this, c3846e);
    }

    final C0767v2 a(boolean z) {
        if (z) {
            return this.b;
        }
        return this.a;
    }

    final FeatureSplit a() {
        return this.d;
    }

    final C3846e a(DH dh) {
        C0767v2 c0767v2 = this.b;
        dh.getClass();
        C0767v2 c0767v2A = dh.a(AbstractC1476Wj.e(), c0767v2);
        C0767v2 c0767v2A2 = dh.a(AbstractC1476Wj.e(), this.a);
        return (c0767v2A == this.b && c0767v2A2 == this.a) ? this : new C3846e(c0767v2A2, c0767v2A, this.c, this.d);
    }

    final void a(C0702m c0702m, C0767v2 c0767v2) {
        if (c0702m.Y().U0()) {
            if (!e && !c0767v2.r0().startsWith(this.a.r0())) {
                throw new AssertionError();
            }
            C0767v2 c0767v2A = c0702m.t.a(this.a);
            if (c0767v2A == null) {
                return;
            }
            String strF = C3970u0.f(this.a.b1());
            c0702m.t.a(c0767v2, c0702m.a().e(C3970u0.l(C3970u0.f(c0767v2A.b1()) + C3970u0.f(c0767v2.b1()).substring(strF.length()))));
        }
    }

    final boolean a(N1 n1) {
        return n1.a(this.b);
    }

    static C3846e a(C0729q2 c0729q2, C0767v2 c0767v2, C0702m c0702m) {
        FeatureSplit featureSplitA;
        if (c0702m.f().h()) {
            C0702m c0702mH0 = c0702m.h0();
            C1121Ka c1121KaP = ((C0680j) c0702mH0.f()).p();
            c1121KaP.getClass();
            featureSplitA = c1121KaP.a(c0729q2.getType(), c0702mH0.Y(), c0702mH0.C(), c0702mH0.D());
        } else {
            featureSplitA = FeatureSplit.BASE;
        }
        return new C3846e(c0767v2, c0729q2.d, c0729q2.c, featureSplitA);
    }
}
