package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.utils.C3888e1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2212iA implements InterfaceC1001Fz, InterfaceC1894eA {
    private final int a;
    private final int b;
    private final int c;
    private final String d;
    private final C2759pA e;
    private final C2759pA f;
    private final C3042tA g;
    private final List h;
    private final C3111uA i;
    private final int j;
    private final C1120Jz k;
    private final C1148Kz l;
    private final C1148Kz m;
    private final C1148Kz n;
    private final C1148Kz o;
    private final List p;

    private C2212iA(int i, int i2, int i3, String str, C2759pA c2759pA, C2759pA c2759pA2, C3042tA c3042tA, AbstractC1639am abstractC1639am, C3111uA c3111uA, int i4, C1120Jz c1120Jz, C1148Kz c1148Kz, C1148Kz c1148Kz2, C1148Kz c1148Kz3, C1148Kz c1148Kz4, List list) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = str;
        this.e = c2759pA;
        this.f = c2759pA2;
        this.g = c3042tA;
        this.h = abstractC1639am;
        this.i = c3111uA;
        this.j = i4;
        this.k = c1120Jz;
        this.l = c1148Kz;
        this.m = c1148Kz2;
        this.n = c1148Kz3;
        this.o = c1148Kz4;
        this.p = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C3539zy c3539zy, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e eVar) {
        AbstractC0936Ds.c(c3539zy, "<this>");
        AbstractC2263iu.a(c3539zy).a(eVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(C3539zy c3539zy, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        AbstractC0936Ds.c(c3539zy, "<this>");
        AbstractC2263iu.a(c3539zy).d(jVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(C3539zy c3539zy, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        AbstractC0936Ds.c(c3539zy, "<this>");
        AbstractC2263iu.a(c3539zy).e(jVar);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final boolean d() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final C2212iA o() {
        return this;
    }

    public final C1120Jz u() {
        return this.k;
    }

    public final C1148Kz v() {
        return this.l;
    }

    public final C1148Kz w() {
        return this.m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(C3539zy c3539zy, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        AbstractC0936Ds.c(c3539zy, "<this>");
        AbstractC2263iu.a(c3539zy).f(jVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C3539zy c3539zy, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        AbstractC0936Ds.c(c3539zy, "<this>");
        AbstractC2263iu.a(c3539zy).c(jVar);
    }

    public static C2212iA a(C3539zy c3539zy, final C0722p1 c0722p1, final com.android.tools.r8.utils.O2 o2) {
        return new C2212iA(c3539zy.d(), c3539zy.e(), c3539zy.i(), c3539zy.f(), C2759pA.a(c0722p1, o2, c3539zy.h()), C2759pA.a(c0722p1, o2, c3539zy.g()), C3042tA.a(c3539zy.j(), c0722p1, o2), C2832qA.a(c3539zy.k(), c0722p1, o2), C3111uA.a(c3539zy.l()), com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.c(c3539zy), C1120Jz.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c3539zy), c0722p1), C1148Kz.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c3539zy), c0722p1), C1148Kz.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.d(c3539zy), c0722p1), C1148Kz.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.e(c3539zy), c0722p1), C1148Kz.a(AbstractC2263iu.a(c3539zy).h(), c0722p1), com.android.tools.r8.utils.V1.a((Collection) c3539zy.c(), new Function() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2759pA.a(c0722p1, o2, (C0942Dy) obj);
            }
        }));
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        return a(consumer, null, null, null, c0702m);
    }

    final boolean a(Consumer consumer, com.android.tools.r8.graph.T0 t0, com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0 w1, C0702m c0702m) {
        final C3539zy c3539zy = (C3539zy) AbstractC1745cA.a(consumer, new C3539zy(this.a, this.b, this.c, this.d));
        boolean zA = AbstractC1745cA.a(c0702m, this.e, new Consumer() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c3539zy.b((C0942Dy) obj);
            }
        }, new Cz$$ExternalSyntheticLambda4()) | AbstractC1745cA.a(c0702m, this.f, new Consumer() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c3539zy.a((C0942Dy) obj);
            }
        }, new Cz$$ExternalSyntheticLambda4()) | AbstractC1745cA.a(c0702m, this.g, new Consumer() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c3539zy.a((C1284Py) obj);
            }
        }, new Iz$$ExternalSyntheticLambda13()) | AbstractC1745cA.a(c0702m, this.h, c3539zy.k(), new Iz$$ExternalSyntheticLambda14()) | AbstractC1745cA.a(c0702m, this.p, c3539zy.c(), new Cz$$ExternalSyntheticLambda4());
        C3111uA c3111uA = this.i;
        ArrayList arrayListL = c3539zy.l();
        Objects.requireNonNull(arrayListL);
        boolean zA2 = zA | c3111uA.a(new Iz$$ExternalSyntheticLambda2(arrayListL));
        C1120Jz c1120Jz = this.k;
        if (c1120Jz != null) {
            zA2 |= c1120Jz.a(new Consumer() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2212iA.a(c3539zy, (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e) obj);
                }
            }, t0, c0702m);
        }
        C1148Kz c1148Kz = this.l;
        if (c1148Kz != null) {
            zA2 |= c1148Kz.a(new Consumer() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2212iA.a(c3539zy, (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j) obj);
                }
            }, w0, c0702m);
        }
        C1148Kz c1148Kz2 = this.m;
        if (c1148Kz2 != null) {
            zA2 |= c1148Kz2.a(new Consumer() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2212iA.b(c3539zy, (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j) obj);
                }
            }, w1, c0702m);
        }
        AbstractC2263iu.a(c3539zy).a(this.j);
        return AbstractC1745cA.a(c0702m, this.o, new Consumer() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2212iA.d(c3539zy, (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j) obj);
            }
        }, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return Boolean.valueOf(((C1148Kz) obj).a((Consumer) obj2, (C0702m) obj3));
            }
        }) | AbstractC1745cA.a(c0702m, this.n, new Consumer() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2212iA.c(c3539zy, (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j) obj);
            }
        }, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return Boolean.valueOf(((C1148Kz) obj).a((Consumer) obj2, (C0702m) obj3));
            }
        }) | zA2;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C2759pA c2759pA = this.e;
        if (c2759pA != null) {
            c2759pA.a(q0);
        }
        C2759pA c2759pA2 = this.f;
        if (c2759pA2 != null) {
            c2759pA2.a(q0);
        }
        C3042tA c3042tA = this.g;
        if (c3042tA != null) {
            c3042tA.a(q0);
        }
        C3888e1.a(this.h, new Function() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2212iA.a((C2832qA) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.p, new Function() { // from class: com.android.tools.r8.internal.iA$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2212iA.a((C2759pA) obj);
            }
        }, (Object) q0);
        C1120Jz c1120Jz = this.k;
        if (c1120Jz != null) {
            c1120Jz.a(q0);
        }
        C1148Kz c1148Kz = this.l;
        if (c1148Kz != null) {
            c1148Kz.a(q0);
        }
        C1148Kz c1148Kz2 = this.m;
        if (c1148Kz2 != null) {
            c1148Kz2.a(q0);
        }
        C1148Kz c1148Kz3 = this.n;
        if (c1148Kz3 != null) {
            c1148Kz3.a(q0);
        }
        C1148Kz c1148Kz4 = this.o;
        if (c1148Kz4 != null) {
            c1148Kz4.a(q0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2832qA c2832qA) {
        Objects.requireNonNull(c2832qA);
        return new Iz$$ExternalSyntheticLambda11(c2832qA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2759pA c2759pA) {
        Objects.requireNonNull(c2759pA);
        return new Iz$$ExternalSyntheticLambda0(c2759pA);
    }
}
