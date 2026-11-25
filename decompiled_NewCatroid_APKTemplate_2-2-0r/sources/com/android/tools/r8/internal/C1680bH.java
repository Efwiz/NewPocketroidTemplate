package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1680bH extends UF {
    private final C0702m b;
    private final com.android.tools.r8.shaking.g4 c;
    private final C2537mR d = new C2537mR();

    public C1680bH(C0702m c0702m, com.android.tools.r8.shaking.g4 g4Var) {
        this.b = c0702m;
        this.c = g4Var;
    }

    @Override // com.android.tools.r8.internal.UF
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C0800x2 a(C0729q2 c0729q2) {
        C0800x2 c0800x2F1 = c0729q2.f1();
        C0767v2[] c0767v2Arr = (C0767v2[]) com.android.tools.r8.utils.B.a(c0800x2F1.a, new Predicate() { // from class: com.android.tools.r8.internal.bH$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        }, C0767v2.g);
        if (c0767v2Arr != c0800x2F1.a) {
            c0800x2F1 = C0800x2.a(c0767v2Arr);
        }
        C0767v2[] c0767v2Arr2 = c0800x2F1.a;
        if (c0767v2Arr2.length <= 1) {
            return c0800x2F1;
        }
        C0767v2[] c0767v2Arr3 = (C0767v2[]) c0767v2Arr2.clone();
        Arrays.sort(c0767v2Arr3);
        return new C0800x2(c0767v2Arr3);
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoIndirectRuntimeTypeChecks";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0767v2 c0767v2) {
        if (this.d.containsKey(c0767v2)) {
            return this.d.b(c0767v2);
        }
        AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2);
        if (abstractC0727q0C == null || !abstractC0727q0C.isInterface()) {
            this.d.a((Object) c0767v2, true);
            return true;
        }
        if (!abstractC0727q0C.e0()) {
            this.d.a((Object) c0767v2, true);
            return true;
        }
        com.android.tools.r8.shaking.g4 g4Var = this.c;
        if (g4Var == null || g4Var.a(abstractC0727q0C.a0())) {
            this.d.a((Object) c0767v2, true);
            return true;
        }
        if (abstractC0727q0C.p1() && abstractC0727q0C.Y0().i1().A() && abstractC0727q0C.Y().e(new Predicate() { // from class: com.android.tools.r8.internal.bH$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1680bH.a((com.android.tools.r8.graph.W0) obj);
            }
        })) {
            this.d.a((Object) c0767v2, true);
            return true;
        }
        Iterator<C0767v2> it = abstractC0727q0C.f1().iterator();
        while (it.hasNext()) {
            if (a(it.next())) {
                this.d.a((Object) c0767v2, true);
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.W0 w0) {
        return !w0.s1();
    }
}
