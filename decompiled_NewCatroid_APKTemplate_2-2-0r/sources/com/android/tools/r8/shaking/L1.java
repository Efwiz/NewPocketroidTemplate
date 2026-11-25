package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.OU;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L1 {
    static final /* synthetic */ boolean f = true;
    private final Set a;
    private final Set b;
    private final Set c;
    private final Set d;
    private final boolean e;

    private L1(boolean z) {
        this.a = OU.c();
        this.b = OU.c();
        this.c = OU.c();
        this.d = OU.c();
        this.e = z;
    }

    public final void a(C0729q2 c0729q2) {
        this.a.add(c0729q2.getType());
    }

    public final void b(C0729q2 c0729q2) {
        d(c0729q2.getType());
    }

    public final void c(C0767v2 c0767v2) {
        this.a.add(c0767v2);
    }

    public final void d(C0767v2 c0767v2) {
        if (!f && this.d.contains(c0767v2)) {
            throw new AssertionError();
        }
        this.b.add(c0767v2);
    }

    public final boolean e(C0767v2 c0767v2) {
        return this.b.contains(c0767v2) || this.d.contains(c0767v2);
    }

    public final void b(C0767v2 c0767v2) {
        if (this.b.contains(c0767v2)) {
            return;
        }
        a(c0767v2);
    }

    public final Set c() {
        return this.b;
    }

    public final void a(C0684j2 c0684j2) {
        this.c.add(c0684j2);
    }

    public final void a(C0767v2 c0767v2) {
        if (!f && this.b.contains(c0767v2)) {
            throw new AssertionError();
        }
        this.d.add(c0767v2);
    }

    public final Consumer a(final com.android.tools.r8.synthesis.D d) {
        return new Consumer() { // from class: com.android.tools.r8.shaking.L1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(d, (C0767v2) obj);
            }
        };
    }

    public final N1 b() {
        boolean z = f;
        if (!z && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        if (z || this.b.isEmpty()) {
            return new N1(this.a, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.synthesis.D d, C0767v2 c0767v2) {
        if (!f && this.b.contains(c0767v2) && !d.f(c0767v2)) {
            throw new AssertionError();
        }
        b(c0767v2);
    }

    public final N1 a(N1 n1) {
        Set set = n1.a;
        if (f || this.a.isEmpty()) {
            return new N1(set, this.b, this.c, this.d, this.e);
        }
        throw new AssertionError();
    }

    public final N1 a() {
        return new N1(this.a, this.b, this.c, this.d, this.e);
    }
}
