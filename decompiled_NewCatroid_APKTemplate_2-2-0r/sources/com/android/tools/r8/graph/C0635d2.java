package com.android.tools.r8.graph;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.internal.AbstractC1699bX;
import com.android.tools.r8.origin.Origin;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.graph.d2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0635d2 extends AbstractC0727q0 implements InterfaceC0630c4, T, Supplier<C0635d2> {
    static final /* synthetic */ boolean t = true;
    public static final /* synthetic */ int u = 0;

    public C0635d2(C0767v2 c0767v2, ProgramResource.Kind kind, Origin origin, E e, C0767v2 c0767v3, C0800x2 c0800x2, C0759u2 c0759u2, W4 w4, List list, List list2, List list3, O2 o2, List list4, C0706m3.b bVar, C0658g0 c0658g0, T0[] t0Arr, T0[] t0Arr2, C0792w4.a aVar, boolean z) {
        super(c0759u2, c0800x2, e, c0767v3, c0767v2, t0Arr, t0Arr2, aVar, w4, list, list2, list3, o2, list4, bVar, c0658g0, origin, z);
        boolean z2 = t;
        if (!z2 && !AbstractC1699bX.a(M1()).allMatch(new Predicate() { // from class: com.android.tools.r8.graph.d2$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0635d2.d((W0) obj);
            }
        })) {
            throw new AssertionError();
        }
        if (!z2 && !Arrays.stream(t0Arr).allMatch(new Predicate() { // from class: com.android.tools.r8.graph.d2$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0635d2.e((T0) obj);
            }
        })) {
            throw new AssertionError();
        }
        if (!z2 && !Arrays.stream(t0Arr2).allMatch(new Predicate() { // from class: com.android.tools.r8.graph.d2$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0635d2.e((T0) obj);
            }
        })) {
            throw new AssertionError();
        }
        for (T0 t0 : t0Arr) {
            t0.R0();
        }
        if (!t && kind != ProgramResource.Kind.CF) {
            throw new AssertionError("Invalid kind " + kind + " for library-path class " + c0767v2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(W0 w0) {
        boolean z = t;
        if (!z && w0.u1()) {
            throw new AssertionError();
        }
        if (!z && w0.L1()) {
            throw new AssertionError();
        }
        if (z || !w0.q1()) {
            return true;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean e(T0 t0) {
        boolean z = t;
        if (!z && t0.O0()) {
            throw new AssertionError();
        }
        if (!z && t0.E0() && t0.Z0()) {
            throw new AssertionError();
        }
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    public final boolean I1() {
        return true;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final T N() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3) {
        consumer3.accept(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0, com.android.tools.r8.graph.F
    public final C0635d2 d0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0, com.android.tools.r8.graph.F
    public final boolean f0() {
        return true;
    }

    @Override // java.util.function.Supplier
    public final C0635d2 get() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final String r0() {
        return this.d.r0() + "(library class)";
    }

    public final String toString() {
        return this.d.toString() + "(library class)";
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    final boolean a(C0702m c0702m, AbstractC0727q0 abstractC0727q0, Predicate predicate, Set set) {
        if (!set.add(getType()) || predicate.test(getType())) {
            return false;
        }
        if (isInterface()) {
            return c0702m.Y().r;
        }
        return !c0702m.a().Q5.contains(this.d);
    }
}
