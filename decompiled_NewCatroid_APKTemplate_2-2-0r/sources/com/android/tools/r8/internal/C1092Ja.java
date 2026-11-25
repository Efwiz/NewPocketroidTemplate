package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.C3912j0;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ja, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1092Ja {
    private final C0702m a;
    private final C0722p1 b;
    private final C2688oE c;
    private final C0986Fk d;
    private final LinkedHashMap e = new LinkedHashMap();

    public C1092Ja(C0702m c0702m, C0986Fk c0986Fk, C2688oE c2688oE) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = c2688oE;
        this.d = c0986Fk;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.Y0 y0) {
        return this.c.f().a(y0) == null && !this.e.containsKey(y0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.T0 t0) {
        com.android.tools.r8.graph.Y0 reference = t0.getReference();
        com.android.tools.r8.graph.Y0 y0A = this.b.a(this.c.f().getType(), t0.getType(), t0.K0().toString(), new Predicate() { // from class: com.android.tools.r8.internal.Ja$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.Y0) obj);
            }
        });
        this.e.put(y0A, t0.a(this.a, y0A, C3912j0.b()));
        this.d.c(reference, y0A);
    }

    public final void a(C0729q2 c0729q2) {
        c0729q2.h(new Consumer() { // from class: com.android.tools.r8.internal.Ja$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.T0) obj);
            }
        });
    }

    public final void a() {
        this.c.f().c(this.e.values());
    }
}
