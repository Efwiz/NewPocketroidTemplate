package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3984w2;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1104Jj {
    private final C0702m a;
    private final VP b;
    private final C3984w2 c;
    private final Set d;
    private final Set e;
    private final Set f;
    private final Set g;

    C1104Jj(C0702m c0702m, C3984w2 c3984w2, Set set, Set set2, Set set3, Set set4) {
        this.a = c0702m;
        this.b = c0702m.Z().h;
        this.c = c3984w2;
        this.d = set;
        this.e = set2;
        this.f = set3;
        this.g = set4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.c.a(new Predicate() { // from class: com.android.tools.r8.internal.Jj$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0767v2 c0767v2) {
        return ((C0680j) this.a.f()).b(c0767v2, this.b.j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.B5 b5) {
        com.android.tools.r8.graph.W0 w0E;
        Iterator<C0767v2> it = b5.j(this.b.i).iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2A0 = this.a.c(it.next()).a0();
            if (c0729q2A0 != null && (w0E = c0729q2A0.e(new Predicate() { // from class: com.android.tools.r8.internal.Jj$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a((com.android.tools.r8.graph.W0) obj);
                }
            })) != null) {
                this.g.add(w0E.getReference());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(com.android.tools.r8.graph.W0 w0) {
        return w0.getReference().f == this.b.z;
    }
}
