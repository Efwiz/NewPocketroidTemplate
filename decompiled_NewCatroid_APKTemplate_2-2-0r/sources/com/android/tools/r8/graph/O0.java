package com.android.tools.r8.graph;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class O0 extends AbstractC0594a1 {
    static final /* synthetic */ boolean b = true;
    private C0658g0 a;

    public O0(C0658g0 c0658g0) {
        if (!b && c0658g0 == null) {
            throw new AssertionError("Should use DexAnnotationSet.THE_EMPTY_ANNOTATIONS_SET");
        }
        this.a = c0658g0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0633d0 a(BiFunction biFunction, C0633d0 c0633d0) {
        return (C0633d0) biFunction.apply(c0633d0, EnumC0608b0.a(getReference()));
    }

    public static Stream b(Stream stream, final Function function) {
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.graph.O0$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((O0) obj).D0();
            }
        };
        return stream.filter(predicate).map(new Function() { // from class: com.android.tools.r8.graph.O0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return O0.b(function, (O0) obj);
            }
        });
    }

    public boolean A0() {
        return false;
    }

    public boolean B0() {
        return this instanceof T0;
    }

    public C0767v2 C() {
        return A0() ? t0().d : v0().J0();
    }

    public boolean C0() {
        return false;
    }

    public boolean D0() {
        return false;
    }

    public abstract boolean E0();

    public abstract boolean F0();

    public void M() {
        this.a = C0658g0.t0();
    }

    public C0729q2 a0() {
        return null;
    }

    public boolean e0() {
        return false;
    }

    public abstract AbstractC0657g getAccessFlags();

    public abstract AbstractC0744s2 getReference();

    public C0658g0 s0() {
        return this.a;
    }

    public AbstractC0727q0 t0() {
        return null;
    }

    public T0 u0() {
        return null;
    }

    public U0 v0() {
        return null;
    }

    public W0 w0() {
        return null;
    }

    public final void x0() {
        this.a = C0658g0.t0();
    }

    public final boolean y0() {
        return !s0().isEmpty();
    }

    public boolean z0() {
        return y0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object b(Function function, O0 o0) {
        return function.apply(o0.w0());
    }

    public final void a(C0658g0 c0658g0) {
        this.a = c0658g0;
    }

    public final void a(Predicate predicate) {
        this.a = s0().a(predicate);
    }

    public void a(final BiFunction biFunction) {
        this.a = s0().a(new Function() { // from class: com.android.tools.r8.graph.O0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(biFunction, (C0633d0) obj);
            }
        });
    }

    public static Stream a(Stream stream, final Function function) {
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.graph.O0$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((O0) obj).B0();
            }
        };
        return stream.filter(predicate).map(new Function() { // from class: com.android.tools.r8.graph.O0$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return O0.a(function, (O0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Function function, O0 o0) {
        return function.apply(o0.u0());
    }
}
