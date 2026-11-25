package com.android.tools.r8.dex;

import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.C2363kB;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class l0 implements L {
    static final /* synthetic */ boolean n = true;
    private final C0702m a;
    private final q0 b;
    private final C2363kB c;
    private final LinkedHashSet d;
    private final LinkedHashSet e;
    private final LinkedHashSet f;
    private final LinkedHashSet g;
    private final LinkedHashSet h;
    private final LinkedHashSet i;
    private final LinkedHashSet j;
    private final LinkedHashSet k;
    private final k0 l;
    C0729q2 m;

    private l0(q0 q0Var, C0702m c0702m) {
        this.d = new LinkedHashSet();
        this.e = new LinkedHashSet();
        this.f = new LinkedHashSet();
        this.g = new LinkedHashSet();
        this.h = new LinkedHashSet();
        this.i = new LinkedHashSet();
        this.j = new LinkedHashSet();
        this.k = new LinkedHashSet();
        this.m = null;
        this.a = c0702m;
        this.b = q0Var;
        this.c = new C2363kB(c0702m, 0);
        c0702m.Y().n1.getClass();
        this.l = new k0();
    }

    private boolean a(AbstractC0594a1 abstractC0594a1, LinkedHashSet linkedHashSet, Set set, boolean z) {
        if (set.contains(abstractC0594a1)) {
            return false;
        }
        boolean zAdd = linkedHashSet.add(abstractC0594a1);
        if (n || !zAdd || !z || this.d.contains(this.m)) {
            return zAdd;
        }
        throw new AssertionError();
    }

    public final boolean a(C0729q2 c0729q2) {
        if (!n && this.m != null) {
            throw new AssertionError();
        }
        this.m = c0729q2;
        return a(c0729q2, this.d, this.b.a, true);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(Y0 y0) {
        if (n || this.m != null) {
            return a(y0, this.e, this.b.e, true);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0684j2 c0684j2) {
        if (n || this.m != null) {
            return a(c0684j2, this.f, this.b.d, true);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0759u2 c0759u2) {
        if (this.m != null || n || c0759u2.b("~~")) {
            return a(c0759u2, this.i, this.b.f, false);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0737r2 c0737r2) {
        if (n || this.m != null) {
            return a(c0737r2, this.h, this.b.b, true);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0767v2 c0767v2) {
        boolean z = n;
        if (!z && this.m == null) {
            throw new AssertionError();
        }
        if (!z) {
            int i = com.android.tools.r8.synthesis.L.W;
            com.android.tools.r8.synthesis.L.a(c0767v2.b1());
        }
        return a(c0767v2, this.g, this.b.c, true);
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0721p0 c0721p0) {
        if (n || this.m != null) {
            return a(c0721p0, this.j, this.b.g, true);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.dex.L
    public final boolean a(C0699l2 c0699l2) {
        if (n || this.m != null) {
            return a(c0699l2, this.k, this.b.h, true);
        }
        throw new AssertionError();
    }

    private static void a(LinkedHashSet linkedHashSet, final Function function) {
        linkedHashSet.forEach(new Consumer() { // from class: com.android.tools.r8.dex.l0$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                l0.a(function, (AbstractC0594a1) obj);
            }
        });
        linkedHashSet.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Function function, AbstractC0594a1 abstractC0594a1) {
        boolean zBooleanValue = ((Boolean) function.apply(abstractC0594a1)).booleanValue();
        if (!n && !zBooleanValue) {
            throw new AssertionError();
        }
    }

    final void a() {
        LinkedHashSet linkedHashSet = this.d;
        final q0 q0Var = this.b;
        Objects.requireNonNull(q0Var);
        a(linkedHashSet, new Function() { // from class: com.android.tools.r8.dex.l0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(q0Var.a((C0729q2) obj));
            }
        });
        LinkedHashSet linkedHashSet2 = this.e;
        final q0 q0Var2 = this.b;
        Objects.requireNonNull(q0Var2);
        a(linkedHashSet2, new Function() { // from class: com.android.tools.r8.dex.l0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(q0Var2.a((Y0) obj));
            }
        });
        LinkedHashSet linkedHashSet3 = this.f;
        final q0 q0Var3 = this.b;
        Objects.requireNonNull(q0Var3);
        a(linkedHashSet3, new Function() { // from class: com.android.tools.r8.dex.l0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(q0Var3.a((C0684j2) obj));
            }
        });
        LinkedHashSet linkedHashSet4 = this.h;
        final q0 q0Var4 = this.b;
        Objects.requireNonNull(q0Var4);
        a(linkedHashSet4, new Function() { // from class: com.android.tools.r8.dex.l0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(q0Var4.a((C0737r2) obj));
            }
        });
        LinkedHashSet linkedHashSet5 = this.g;
        final q0 q0Var5 = this.b;
        Objects.requireNonNull(q0Var5);
        a(linkedHashSet5, new Function() { // from class: com.android.tools.r8.dex.l0$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(q0Var5.a((C0767v2) obj));
            }
        });
        LinkedHashSet linkedHashSet6 = this.i;
        final q0 q0Var6 = this.b;
        Objects.requireNonNull(q0Var6);
        a(linkedHashSet6, new Function() { // from class: com.android.tools.r8.dex.l0$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(q0Var6.a((C0759u2) obj));
            }
        });
        LinkedHashSet linkedHashSet7 = this.j;
        final q0 q0Var7 = this.b;
        Objects.requireNonNull(q0Var7);
        a(linkedHashSet7, new Function() { // from class: com.android.tools.r8.dex.l0$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(q0Var7.a((C0721p0) obj));
            }
        });
        LinkedHashSet linkedHashSet8 = this.k;
        final q0 q0Var8 = this.b;
        Objects.requireNonNull(q0Var8);
        a(linkedHashSet8, new Function() { // from class: com.android.tools.r8.dex.l0$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(q0Var8.a((C0699l2) obj));
            }
        });
        this.a.Y().n1.getClass();
    }
}
