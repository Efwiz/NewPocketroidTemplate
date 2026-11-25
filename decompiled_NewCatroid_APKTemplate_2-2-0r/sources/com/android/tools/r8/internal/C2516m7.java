package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import java.util.Optional;

/* JADX INFO: renamed from: com.android.tools.r8.internal.m7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2516m7 {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final C0753u b;
    private final com.android.tools.r8.graph.o5 c;
    private InterfaceC3107u6 d;
    private InterfaceC2829q7 e;
    private Optional f = Optional.empty();
    private boolean g;

    C2516m7(C0702m c0702m, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        this.a = c0702m;
        this.b = c0753u;
        this.c = o5Var;
    }

    public final C2516m7 a(AbstractC1476Wj abstractC1476Wj) {
        if (abstractC1476Wj != this.a.E()) {
            this.f = Optional.of(this.a.E().a(abstractC1476Wj, this.c.getReference()));
            this.g = this.c.e().B1() || this.a.E().d(abstractC1476Wj, this.c.getReference()).a().f();
        }
        return this;
    }

    public final C2516m7 a(InterfaceC3107u6 interfaceC3107u6) {
        this.d = interfaceC3107u6;
        return this;
    }

    public final C2516m7 a(InterfaceC2829q7 interfaceC2829q7) {
        this.e = interfaceC2829q7;
        return this;
    }

    public final C2674o7 a() {
        if (!h && this.e == null) {
            throw new AssertionError();
        }
        C0702m c0702m = this.a;
        C0753u c0753u = this.b;
        InterfaceC3107u6 c2756p7 = this.d;
        if (c2756p7 == null) {
            c2756p7 = new C2756p7(c0702m, c0753u, this.c, this.f);
        }
        return new C2674o7(c0702m, c0753u, c2756p7, this.e, this.c, this.f, this.g);
    }
}
