package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.utils.C3888e1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Iz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1091Iz implements InterfaceC1894eA {
    private final int a;
    private final String b;
    private final List c;
    public final C2759pA d;
    private final C2759pA e;
    private final List f;
    private final C1148Kz g;
    private final C2973sA h;
    private final C3111uA i;
    private final C3540zz j;
    private final boolean k;
    private final List l;

    private C1091Iz(int i, String str, C2759pA c2759pA, C2759pA c2759pA2, AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2, C1148Kz c1148Kz, C2973sA c2973sA, C3111uA c3111uA, C3540zz c3540zz, boolean z, List list) {
        this.a = i;
        this.b = str;
        this.d = c2759pA;
        this.e = c2759pA2;
        this.c = abstractC1639am;
        this.f = abstractC1639am2;
        this.g = c1148Kz;
        this.h = c2973sA;
        this.i = c3111uA;
        this.j = c3540zz;
        this.k = z;
        this.l = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C2815py c2815py, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        AbstractC0936Ds.c(c2815py, "<this>");
        ((C2338ju) c2815py.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f.b)).b(jVar);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final C1091Iz b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final boolean l() {
        return true;
    }

    public final boolean u() {
        return this.k;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C2815py c2815py, String str) {
        if (str != null) {
            AbstractC0936Ds.c(c2815py, "<this>");
            ((C2338ju) c2815py.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.f.b)).b(str);
        }
    }

    static C1091Iz a(C2815py c2815py, final C0722p1 c0722p1, final com.android.tools.r8.utils.O2 o2, boolean z) {
        boolean z2;
        AbstractC1639am abstractC1639amA = C3042tA.a(c2815py.k(), c0722p1, o2);
        Iterator it = abstractC1639amA.iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            }
            C3042tA c3042tA = (C3042tA) it.next();
            c3042tA.getClass();
            if (AbstractC3382xi.r.b(c3042tA.b).booleanValue()) {
                z2 = true;
                break;
            }
        }
        boolean z3 = z2;
        int iF = c2815py.f();
        String strG = c2815py.g();
        C2759pA c2759pAA = C2759pA.a(c0722p1, o2, c2815py.i());
        C2759pA c2759pAA2 = C2759pA.a(c0722p1, o2, c2815py.h());
        AbstractC1639am abstractC1639amA2 = C2832qA.a(c2815py.j(), c0722p1, o2);
        C1148Kz c1148KzA = z ? C1148Kz.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c2815py), c0722p1) : null;
        String strA = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c2815py);
        return new C1091Iz(iF, strG, c2759pAA, c2759pAA2, abstractC1639amA, abstractC1639amA2, c1148KzA, strA != null ? C2973sA.a(strA, c0722p1, strA) : null, C3111uA.a(c2815py.l()), C3540zz.a(c2815py.e(), c0722p1, o2), z3, com.android.tools.r8.utils.V1.a((Collection) c2815py.d(), new Function() { // from class: com.android.tools.r8.internal.Iz$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2759pA.a(c0722p1, o2, (C0942Dy) obj);
            }
        }));
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        return a(consumer, (com.android.tools.r8.graph.W0) null, c0702m);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x002a  */
    final boolean a(Consumer consumer, com.android.tools.r8.graph.W0 w0, C0702m c0702m) {
        String string;
        boolean z;
        String str = this.b;
        if (w0 != null) {
            String string2 = w0.getReference().f.toString();
            string = c0702m.z().a(w0.getReference()).toString();
            if (string2.equals(string)) {
                string = str;
                z = false;
            } else {
                z = true;
            }
        } else {
            string = str;
            z = false;
        }
        final C2815py c2815py = (C2815py) AbstractC1745cA.a(consumer, new C2815py(this.a, string));
        boolean zA = z | this.d.a(new Consumer() { // from class: com.android.tools.r8.internal.Iz$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2815py.b((C0942Dy) obj);
            }
        }, c0702m) | AbstractC1745cA.a(c0702m, this.c, c2815py.k(), new Iz$$ExternalSyntheticLambda13()) | AbstractC1745cA.a(c0702m, this.f, c2815py.j(), new Iz$$ExternalSyntheticLambda14()) | AbstractC1745cA.a(c0702m, this.l, c2815py.d(), new Cz$$ExternalSyntheticLambda4()) | AbstractC1745cA.a(c0702m, this.e, new Consumer() { // from class: com.android.tools.r8.internal.Iz$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2815py.a((C0942Dy) obj);
            }
        }, new Cz$$ExternalSyntheticLambda4());
        C3111uA c3111uA = this.i;
        ArrayList arrayListL = c2815py.l();
        Objects.requireNonNull(arrayListL);
        boolean zA2 = zA | c3111uA.a(new Iz$$ExternalSyntheticLambda2(arrayListL));
        C1148Kz c1148Kz = this.g;
        if (c1148Kz != null) {
            zA2 |= c1148Kz.a(new Consumer() { // from class: com.android.tools.r8.internal.Iz$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1091Iz.a(c2815py, (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j) obj);
                }
            }, w0, c0702m);
        }
        C2973sA c2973sA = this.h;
        if (c2973sA != null) {
            zA2 |= c2973sA.a(new Consumer() { // from class: com.android.tools.r8.internal.Iz$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1091Iz.a(c2815py, (String) obj);
                }
            }, c0702m, (String) null);
        }
        return this.j.a(new Consumer() { // from class: com.android.tools.r8.internal.Iz$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2815py.a((C1653ay) obj);
            }
        }, c0702m) | zA2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C3042tA c3042tA) {
        Objects.requireNonNull(c3042tA);
        return new Iz$$ExternalSyntheticLambda6(c3042tA);
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C3888e1.a(this.c, new Function() { // from class: com.android.tools.r8.internal.Iz$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1091Iz.a((C3042tA) obj);
            }
        }, (Object) q0);
        this.d.a(q0);
        C2759pA c2759pA = this.e;
        if (c2759pA != null) {
            c2759pA.a(q0);
        }
        C3888e1.a(this.f, new Function() { // from class: com.android.tools.r8.internal.Iz$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1091Iz.a((C2832qA) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.l, new Function() { // from class: com.android.tools.r8.internal.Iz$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1091Iz.a((C2759pA) obj);
            }
        }, (Object) q0);
        C1148Kz c1148Kz = this.g;
        if (c1148Kz != null) {
            c1148Kz.a(q0);
        }
        C2973sA c2973sA = this.h;
        if (c2973sA != null) {
            c2973sA.a(q0);
        }
        this.j.a(q0);
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
