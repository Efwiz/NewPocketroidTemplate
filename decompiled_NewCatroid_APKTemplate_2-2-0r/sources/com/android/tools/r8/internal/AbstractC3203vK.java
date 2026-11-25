package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0798x1;
import com.android.tools.r8.shaking.C3733i;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vK, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3203vK {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof AG) || abstractC2412ko.o2() || abstractC2412ko.b2();
    }

    public static AbstractC3121uK b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        if (!a && s10.m() != s10) {
            throw new AssertionError();
        }
        if (!s10.b(new Predicate() { // from class: com.android.tools.r8.internal.vK$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC3203vK.a((AbstractC2412ko) obj);
            }
        })) {
            return s10.b(new IW$$ExternalSyntheticLambda0()) ? a(c0702m, o5Var, s10) : C2096gg.f();
        }
        B1 b1A = s10.b.a(c0702m, o5Var);
        b1A.getClass();
        return b1A instanceof GW ? b1A.r().t() : C2096gg.f();
    }

    private static AbstractC3121uK a(final C0702m c0702m, final com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        final C1166Ls c1166LsA = AbstractC2871ql.a(c0702m.a(), s10.b.t0().b());
        if (c1166LsA == null) {
            return C2096gg.f();
        }
        AbstractC0748t0 abstractC0748t0H = c1166LsA.h(c0702m, o5Var);
        if (abstractC0748t0H == null) {
            return C2096gg.f();
        }
        AbstractC1049Hn abstractC1049HnB = abstractC0748t0H.e().i1().a(c1166LsA).b();
        if (abstractC1049HnB.b()) {
            return C2096gg.f();
        }
        final C3052tK c3052tK = new C3052tK();
        abstractC1049HnB.a((com.android.tools.r8.graph.Q0) c0702m, new BiConsumer() { // from class: com.android.tools.r8.internal.vK$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AbstractC3203vK.a(c0702m, c1166LsA, c3052tK, o5Var, (com.android.tools.r8.graph.T0) obj, (InterfaceC0989Fn) obj2);
            }
        });
        return c3052tK.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, C1166Ls c1166Ls, C3052tK c3052tK, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.T0 t0, InterfaceC0989Fn interfaceC0989Fn) {
        if (!((C3733i) c0702m.f()).d(t0)) {
            C0798x1 c0798x1 = c0702m.a().r4;
            com.android.tools.r8.graph.Y0 reference = t0.getReference();
            if (reference != c0798x1.a && reference != c0798x1.b) {
                return;
            }
        }
        if (interfaceC0989Fn.h()) {
            c3052tK.a(t0, c1166Ls.c(interfaceC0989Fn.f().b()).a(c0702m, o5Var));
        } else if (interfaceC0989Fn.g()) {
            c3052tK.a(t0, interfaceC0989Fn.j());
        }
    }
}
