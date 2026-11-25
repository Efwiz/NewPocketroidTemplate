package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.g1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3722g1 {
    private final boolean a;
    private final boolean b;
    private final boolean c;
    private final boolean d;
    private final boolean e;
    private final boolean f;
    private final boolean g;

    private AbstractC3722g1(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        this.a = z;
        this.b = z2;
        this.c = z3;
        this.d = z4;
        this.e = z5;
        this.f = z6;
        this.g = z7;
    }

    public static AbstractC3716f1 a(AbstractC0744s2 abstractC0744s2) {
        return (AbstractC3716f1) abstractC0744s2.a(new Function() { // from class: com.android.tools.r8.shaking.g1$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3722g1.a((C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.g1$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3722g1.a((com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.g1$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3722g1.a((C0684j2) obj);
            }
        });
    }

    final boolean b() {
        return this.b;
    }

    final boolean c() {
        return this.g;
    }

    final boolean d() {
        return this.c;
    }

    final boolean e() {
        return this.d;
    }

    final boolean f() {
        return this.e;
    }

    final boolean g() {
        return this.f;
    }

    public final boolean h() {
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3716f1 a(C0767v2 c0767v2) {
        return Y0.p();
    }

    public final boolean b(K0 k0) {
        return ((com.android.tools.r8.utils.A1) k0).l1() && this.d;
    }

    public final boolean c(K0 k0) {
        return (b(k0) && d(k0)) ? false : true;
    }

    public final boolean d(K0 k0) {
        return ((com.android.tools.r8.utils.A1) k0).o1() && this.e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3716f1 a(com.android.tools.r8.graph.Y0 y0) {
        return C3704d1.p();
    }

    public final boolean c(com.android.tools.r8.utils.A1 a1) {
        if (a1.c1()) {
            return (a1.S0 || c((K0) a1)) ? false : true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3716f1 a(C0684j2 c0684j2) {
        return C3762m1.x();
    }

    public final boolean a(K0 k0) {
        return ((com.android.tools.r8.utils.A1) k0).k1() && this.c;
    }

    public final boolean b(com.android.tools.r8.utils.A1 a1) {
        return !a1.S0 && this.b;
    }

    public final boolean d(com.android.tools.r8.utils.A1 a1) {
        if (a1.a1()) {
            return !a1.S0 && this.f;
        }
        return true;
    }

    final boolean a() {
        return this.a;
    }

    AbstractC3722g1(AbstractC3710e1 abstractC3710e1) {
        this(abstractC3710e1.e(), abstractC3710e1.f(), abstractC3710e1.h(), abstractC3710e1.i(), abstractC3710e1.j(), abstractC3710e1.k(), abstractC3710e1.g());
    }

    public final boolean a(com.android.tools.r8.utils.A1 a1) {
        return a1.D0() != null && a1.D0().A() && this.a;
    }
}
