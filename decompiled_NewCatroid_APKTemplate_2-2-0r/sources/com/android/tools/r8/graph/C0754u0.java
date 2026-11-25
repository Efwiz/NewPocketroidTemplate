package com.android.tools.r8.graph;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.origin.Origin;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.graph.u0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0754u0 extends AbstractC0727q0 implements P, T, p5, Supplier, com.android.tools.r8.utils.structural.x {
    static final /* synthetic */ boolean t = true;

    public C0754u0(C0767v2 c0767v2, ProgramResource.Kind kind, Origin origin, E e, C0767v2 c0767v3, C0800x2 c0800x2, C0759u2 c0759u2, W4 w4, List list, List list2, List list3, O2 o2, List list4, C0706m3.b bVar, C0658g0 c0658g0, T0[] t0Arr, T0[] t0Arr2, C0792w4.a aVar, boolean z) {
        super(c0759u2, c0800x2, e, c0767v3, c0767v2, t0Arr, t0Arr2, aVar, w4, list, list2, list3, o2, list4, bVar, c0658g0, origin, z);
        if (!t && kind != ProgramResource.Kind.CF) {
            throw new AssertionError("Invalid kind " + kind + " for class-path class " + c0767v2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Consumer consumer, W0 w0) {
        consumer.accept(new S(this, w0));
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    public final boolean I1() {
        return true;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final T N() {
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3) {
        consumer2.accept(this);
    }

    public final void d(final Consumer consumer, Predicate predicate) {
        this.k.b(new Consumer() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(consumer, (W0) obj);
            }
        }, predicate);
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0, com.android.tools.r8.graph.F
    public final C0754u0 m() {
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0754u0.a(zVar);
            }
        };
    }

    public final String toString() {
        return this.d.toString() + "(classpath class)";
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    final boolean a(C0702m c0702m, AbstractC0727q0 abstractC0727q0, Predicate predicate, Set set) {
        if (!set.add(getType()) || predicate.test(getType())) {
            return false;
        }
        if (!isInterface()) {
            return true;
        }
        c0702m.Y().getClass();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).getType();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).n1();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).f1();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).getAccessFlags();
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).m1();
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).h1();
            }
        }).f(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).i1();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).s0();
            }
        }).a(new Predicate() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0754u0.a((C0754u0) obj);
            }
        }).f(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).G0();
            }
        }).f(new Function() { // from class: com.android.tools.r8.graph.u0$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0754u0) obj).I0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0754u0 c0754u0) {
        return c0754u0.r == C0706m3.b.f();
    }
}
