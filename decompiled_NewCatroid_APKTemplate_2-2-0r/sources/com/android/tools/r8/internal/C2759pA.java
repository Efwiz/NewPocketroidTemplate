package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3888e1;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2759pA implements InterfaceC3680a0 {
    private static final List i = AbstractC1639am.i();
    private final int a;
    private final AbstractC3323wz b;
    private final C2759pA c;
    private final C2759pA d;
    private final List e;
    private final List f;
    private final C1061Hz g;
    private final boolean h;

    C2759pA(int i2, AbstractC3323wz abstractC3323wz, C2759pA c2759pA, C2759pA c2759pA2, AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2, C1061Hz c1061Hz, boolean z) {
        this.a = i2;
        this.b = abstractC3323wz;
        this.c = c2759pA;
        this.d = c2759pA2;
        this.e = abstractC1639am;
        this.f = abstractC1639am2;
        this.g = c1061Hz;
        this.h = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static C2759pA a(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, C0942Dy c0942Dy) {
        List listA;
        if (c0942Dy == null) {
            return null;
        }
        int iE = c0942Dy.e();
        AbstractC3323wz abstractC3323wzA = AbstractC3323wz.a(c0942Dy.c, c0722p1, o2);
        C2759pA c2759pAA = a(c0722p1, o2, c0942Dy.c());
        C2759pA c2759pAA2 = a(c0722p1, o2, c0942Dy.g());
        List<C1227Ny> listD = c0942Dy.d();
        if (listD.isEmpty()) {
            listA = i;
        } else {
            C1507Xl c1507XlG = AbstractC1639am.g();
            Iterator<C1227Ny> it = listD.iterator();
            while (it.hasNext()) {
                c1507XlG.a(C2902rA.a(it.next(), c0722p1, o2));
            }
            listA = c1507XlG.a();
        }
        return new C2759pA(iE, abstractC3323wzA, c2759pAA, c2759pAA2, (AbstractC1639am) listA, C2577mz.a(c0722p1, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c0942Dy)), C1061Hz.a(c0942Dy.f(), c0722p1, o2), com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c0942Dy));
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        final C0942Dy c0942Dy = (C0942Dy) AbstractC1745cA.a(consumer, new C0942Dy(this.a));
        boolean zA = this.b.a(c0942Dy, c0702m) | AbstractC1745cA.a(c0702m, this.c, new Consumer() { // from class: com.android.tools.r8.internal.pA$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0942Dy.a((C0942Dy) obj);
            }
        }, new Cz$$ExternalSyntheticLambda4()) | AbstractC1745cA.a(c0702m, this.d, new Consumer() { // from class: com.android.tools.r8.internal.pA$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0942Dy.b((C0942Dy) obj);
            }
        }, new Cz$$ExternalSyntheticLambda4()) | AbstractC1745cA.a(c0702m, this.e, c0942Dy.d(), new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.pA$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return Boolean.valueOf(((C2902rA) obj).a((Consumer) obj2, (C0702m) obj3));
            }
        }) | this.g.a(new Consumer() { // from class: com.android.tools.r8.internal.pA$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0942Dy.a((C2742oy) obj);
            }
        }, c0702m);
        if (this.f.isEmpty() && !this.h) {
            return zA;
        }
        boolean zA2 = AbstractC1745cA.a(c0702m, this.f, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c0942Dy), new C2681oA$$ExternalSyntheticLambda2()) | zA;
        ((C1723bv) c0942Dy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m.b)).b(this.h);
        return zA2;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.b.a(q0);
        C2759pA c2759pA = this.c;
        if (c2759pA != null) {
            c2759pA.a(q0);
        }
        C2759pA c2759pA2 = this.d;
        if (c2759pA2 != null) {
            c2759pA2.a(q0);
        }
        C3888e1.a(this.e, new Function() { // from class: com.android.tools.r8.internal.pA$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2759pA.a((C2902rA) obj);
            }
        }, (Object) q0);
        this.g.a(q0);
        C3888e1.a(this.f, new Function() { // from class: com.android.tools.r8.internal.pA$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2759pA.a((C2577mz) obj);
            }
        }, (Object) q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(final C2902rA c2902rA) {
        Objects.requireNonNull(c2902rA);
        return new Consumer() { // from class: com.android.tools.r8.internal.pA$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2902rA.a((com.android.tools.r8.graph.Q0) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2577mz c2577mz) {
        Objects.requireNonNull(c2577mz);
        return new C2682oA$$ExternalSyntheticLambda3(c2577mz);
    }

    public final C0767v2 a(AbstractC1476Wj abstractC1476Wj) {
        return this.b.a(abstractC1476Wj);
    }
}
