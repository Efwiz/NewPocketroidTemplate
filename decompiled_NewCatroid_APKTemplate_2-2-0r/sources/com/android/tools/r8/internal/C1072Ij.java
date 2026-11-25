package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ij, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1072Ij extends AbstractC2797pg {
    static final /* synthetic */ boolean c = true;
    final /* synthetic */ Set a;
    final /* synthetic */ C1132Kj b;

    C1072Ij(C1132Kj c1132Kj, Set set) {
        this.b = c1132Kj;
        this.a = set;
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.shaking.L l, final com.android.tools.r8.shaking.F0 f0, com.android.tools.r8.utils.y3 y3Var) {
        IdentityHashMap identityHashMap = this.b.d;
        final Set set = this.a;
        identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Ij$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(set, f0, (C0729q2) obj, (com.android.tools.r8.graph.o5) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, com.android.tools.r8.shaking.F0 f0, C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        if (set.add(c0729q2)) {
            C0729q2 c0729q2A = C0729q2.a(this.b.a.c(c0729q2.f));
            boolean z = c;
            if (!z && c0729q2A == null) {
                throw new AssertionError();
            }
            c0729q2A.e.O();
            C0767v2 c0767v2 = c0729q2A.d;
            VP vp = this.b.b;
            C0767v2 c0767v3 = vp.j;
            if (c0767v2 == c0767v3) {
                f0.b(c0767v3, o5Var);
                f0.b(this.b.b.r.a, o5Var);
            } else {
                if (!z && c0767v2 != vp.k) {
                    throw new AssertionError();
                }
                f0.b(vp.k, o5Var);
                f0.b(this.b.b.s.b, o5Var);
            }
            f0.a(this.b.b.a(o5Var.a()), o5Var);
        }
    }
}
