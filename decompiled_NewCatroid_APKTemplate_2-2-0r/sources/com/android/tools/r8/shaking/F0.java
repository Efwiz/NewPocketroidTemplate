package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.EnumC0608b0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.Collection;
import java.util.Queue;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class F0 {
    final L a;
    final Queue b;

    private F0(L l, Queue queue) {
        this.a = l;
        this.b = queue;
    }

    public static F0 a(L l) {
        return new C3777p0(l);
    }

    public abstract void a(com.android.tools.r8.graph.Y0 y0, o5 o5Var);

    abstract void a(C0684j2 c0684j2, o5 o5Var);

    abstract void a(C0684j2 c0684j2, o5 o5Var, AbstractC3837z1 abstractC3837z1);

    abstract void a(InterfaceC0694k5 interfaceC0694k5, C0633d0 c0633d0, EnumC0608b0 enumC0608b0);

    public abstract void a(m5 m5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1);

    public abstract void a(m5 m5Var, o5 o5Var);

    public abstract void a(o5 o5Var);

    public abstract void a(C0729q2 c0729q2);

    public abstract void a(C0729q2 c0729q2, o5 o5Var, V0 v0, AbstractC3837z1 abstractC3837z1);

    abstract void a(C0729q2 c0729q2, L0 l0);

    public abstract void a(C0767v2 c0767v2, o5 o5Var);

    abstract void a(L0 l0, m5 m5Var);

    abstract void a(AbstractC3709e0 abstractC3709e0);

    abstract void a(AbstractC3837z1 abstractC3837z1, o5 o5Var);

    abstract void a(InterfaceC3880d interfaceC3880d);

    abstract boolean a(o5 o5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1);

    abstract C3772o0 b();

    public abstract void b(C0684j2 c0684j2, o5 o5Var);

    public abstract void b(m5 m5Var, o5 o5Var);

    abstract void b(C0729q2 c0729q2, L0 l0);

    public abstract void b(C0767v2 c0767v2, o5 o5Var);

    public abstract void c(C0684j2 c0684j2, o5 o5Var);

    public abstract void c(m5 m5Var, o5 o5Var);

    public abstract void c(C0729q2 c0729q2, L0 l0);

    public final boolean a() {
        return this.b.isEmpty();
    }

    final void a(Collection collection) {
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.F0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC3709e0) obj);
            }
        });
    }
}
