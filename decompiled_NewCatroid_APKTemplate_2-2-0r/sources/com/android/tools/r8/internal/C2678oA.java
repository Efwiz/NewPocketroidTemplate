package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3888e1;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2678oA implements InterfaceC3680a0 {
    static final /* synthetic */ boolean h = true;
    private final int a;
    private final String b;
    private final C2759pA c;
    private final C2759pA d;
    private final List e;
    private final List f;
    private final C3111uA g;

    private C2678oA(int i, String str, C2759pA c2759pA, C2759pA c2759pA2, AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2, C3111uA c3111uA) {
        this.a = i;
        this.b = str;
        boolean z = h;
        if (!z && c2759pA == null) {
            throw new AssertionError();
        }
        if (!z && c2759pA2 == null) {
            throw new AssertionError();
        }
        this.c = c2759pA;
        this.d = c2759pA2;
        this.e = abstractC1639am;
        this.f = abstractC1639am2;
        this.g = c3111uA;
    }

    public static C2678oA a(C0974Ey c0974Ey, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        return new C2678oA(c0974Ey.d(), c0974Ey.e(), C2759pA.a(c0722p1, o2, c0974Ey.e), C2759pA.a(c0722p1, o2, c0974Ey.f), C2832qA.a(c0974Ey.f(), c0722p1, o2), C2577mz.a(c0722p1, c0974Ey.c()), C3111uA.a(c0974Ey.g()));
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        final C0974Ey c0974Ey = (C0974Ey) AbstractC1745cA.a(consumer, new C0974Ey(this.a, this.b));
        boolean zA = AbstractC1745cA.a(c0702m, this.f, c0974Ey.c(), new C2681oA$$ExternalSyntheticLambda2()) | this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.oA$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0974Ey.b((C0942Dy) obj);
            }
        }, c0702m) | this.d.a(new Consumer() { // from class: com.android.tools.r8.internal.oA$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0974Ey.a((C0942Dy) obj);
            }
        }, c0702m) | AbstractC1745cA.a(c0702m, this.e, c0974Ey.f(), new Iz$$ExternalSyntheticLambda14());
        C3111uA c3111uA = this.g;
        ArrayList arrayListG = c0974Ey.g();
        Objects.requireNonNull(arrayListG);
        return c3111uA.a(new Iz$$ExternalSyntheticLambda2(arrayListG)) | zA;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.c.a(q0);
        this.d.a(q0);
        C3888e1.a(this.e, new Function() { // from class: com.android.tools.r8.internal.oA$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2678oA.a((C2832qA) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.f, new Function() { // from class: com.android.tools.r8.internal.oA$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2678oA.a((C2577mz) obj);
            }
        }, (Object) q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2832qA c2832qA) {
        Objects.requireNonNull(c2832qA);
        return new Iz$$ExternalSyntheticLambda11(c2832qA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2577mz c2577mz) {
        Objects.requireNonNull(c2577mz);
        return new C2682oA$$ExternalSyntheticLambda3(c2577mz);
    }
}
