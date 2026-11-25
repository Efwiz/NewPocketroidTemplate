package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3888e1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0915Cz implements InterfaceC3680a0 {
    private static final List g;
    private static final C0915Cz h;
    private final int a;
    private final Integer b;
    private final C1464Vx c;
    private final C2759pA d;
    private final List e;
    private final List f;

    static {
        AbstractC1639am abstractC1639amI = AbstractC1639am.i();
        g = abstractC1639amI;
        h = new C0915Cz(0, 0, null, null, abstractC1639amI, abstractC1639amI);
    }

    private C0915Cz(int i, Integer num, C1464Vx c1464Vx, C2759pA c2759pA, AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2) {
        this.a = i;
        this.b = num;
        this.c = c1464Vx;
        this.d = c2759pA;
        this.e = abstractC1639am;
        this.f = abstractC1639am2;
    }

    static C0915Cz a(C2120gy c2120gy, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        return c2120gy == null ? h : new C0915Cz(c2120gy.f(), c2120gy.h(), c2120gy.e(), C2759pA.a(c0722p1, o2, c2120gy.i()), a(c2120gy.d(), c0722p1, o2), a(c2120gy.g(), c0722p1, o2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer b(C0915Cz c0915Cz) {
        Objects.requireNonNull(c0915Cz);
        return new Cz$$ExternalSyntheticLambda2(c0915Cz);
    }

    static AbstractC1639am a(ArrayList arrayList, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        if (arrayList.isEmpty()) {
            return (AbstractC1639am) g;
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            c1507XlG.a(a((C2120gy) it.next(), c0722p1, o2));
        }
        return c1507XlG.a();
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        if (this == h) {
            return;
        }
        C2759pA c2759pA = this.d;
        if (c2759pA != null) {
            c2759pA.a(q0);
        }
        C3888e1.a(this.e, new Function() { // from class: com.android.tools.r8.internal.Cz$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0915Cz.a((C0915Cz) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.f, new Function() { // from class: com.android.tools.r8.internal.Cz$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0915Cz.b((C0915Cz) obj);
            }
        }, (Object) q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C0915Cz c0915Cz) {
        Objects.requireNonNull(c0915Cz);
        return new Cz$$ExternalSyntheticLambda2(c0915Cz);
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        if (this == h) {
            return false;
        }
        final C2120gy c2120gy = (C2120gy) AbstractC1745cA.a(consumer, new C2120gy());
        c2120gy.b(this.a);
        c2120gy.a(this.b);
        C1464Vx c1464Vx = this.c;
        if (c1464Vx != null) {
            c2120gy.a(c1464Vx);
        }
        return AbstractC1745cA.a(c0702m, this.f, c2120gy.g(), new Cz$$ExternalSyntheticLambda5()) | AbstractC1745cA.a(c0702m, this.d, new Consumer() { // from class: com.android.tools.r8.internal.Cz$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2120gy.a((C0942Dy) obj);
            }
        }, new Cz$$ExternalSyntheticLambda4()) | AbstractC1745cA.a(c0702m, this.e, c2120gy.d(), new Cz$$ExternalSyntheticLambda5());
    }
}
