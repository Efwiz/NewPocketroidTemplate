package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0809y2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3912j0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ma, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2546ma implements InterfaceC2621na {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final C2688oE b;
    private final C0986Fk c;
    private com.android.tools.r8.graph.T0 d;
    private final Set e;

    private C2546ma(C0702m c0702m, C0986Fk c0986Fk, C2688oE c2688oE) {
        this.e = OU.c();
        this.a = c0702m;
        this.b = c2688oE;
        this.c = c0986Fk;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(com.android.tools.r8.graph.T0 t0) {
        return !t0.f.q();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(com.android.tools.r8.graph.T0 t0) {
        return !t0.f.h();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2621na
    public final void a(com.android.tools.r8.graph.T0 t0) {
        this.d = t0;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2621na
    public final com.android.tools.r8.graph.T0[] a() {
        if (!f && !this.b.i()) {
            throw new AssertionError();
        }
        final ArrayList arrayList = new ArrayList();
        com.android.tools.r8.graph.T0 t0 = this.d;
        if (t0 != null) {
            arrayList.add(t0);
            this.e.add(this.d.getReference());
        }
        this.b.c().a(new BiConsumer() { // from class: com.android.tools.r8.internal.ma$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(arrayList, (Set) obj, (com.android.tools.r8.graph.T0) obj2);
            }
        });
        return (com.android.tools.r8.graph.T0[]) arrayList.toArray(com.android.tools.r8.graph.T0.m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, Set set, com.android.tools.r8.graph.T0 t0) {
        com.android.tools.r8.graph.T0 t0A = a(t0, set);
        list.add(t0A);
        this.e.add(t0A.getReference());
    }

    private com.android.tools.r8.graph.T0 a(com.android.tools.r8.graph.T0 t0, Set set) {
        com.android.tools.r8.graph.T0 t0A;
        a(set, t0);
        if (a(t0, (Iterable) set)) {
            t0A = t0.a(this.a, t0.getReference().c(this.a.a(), AbstractC0809y2.a(AbstractC2734ot.a((Iterable) AbstractC0984Fi.a(com.android.tools.r8.utils.I1.b(t0), set), new InterfaceC3010sj() { // from class: com.android.tools.r8.internal.ma$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((com.android.tools.r8.graph.T0) obj).getType();
                }
            }), this.a)), C3912j0.b());
        } else {
            t0A = t0;
        }
        if (this.e.contains(t0A.getReference())) {
            C0702m c0702m = this.a;
            C0722p1 c0722p1A = c0702m.a();
            C0767v2 c0767v2J0 = t0A.J0();
            C0767v2 type = t0A.getType();
            String string = t0A.K0().toString();
            final Set set2 = this.e;
            Objects.requireNonNull(set2);
            t0A = t0.a(c0702m, c0722p1A.a(c0767v2J0, type, string, new Predicate() { // from class: com.android.tools.r8.internal.ma$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return set2.contains((com.android.tools.r8.graph.Y0) obj);
                }
            }.negate()), C3912j0.b());
        }
        this.c.a(AbstractC2734ot.a((Iterable) AbstractC0984Fi.a(set, com.android.tools.r8.utils.I1.b(t0)), new InterfaceC3010sj() { // from class: com.android.tools.r8.internal.ma$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((com.android.tools.r8.graph.T0) obj).getReference();
            }
        }), t0A.getReference(), t0.getReference());
        return t0A;
    }

    private static void a(Collection collection, com.android.tools.r8.graph.T0 t0) {
        if (t0.f.q() && AbstractC2734ot.b(collection, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.ma$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C2546ma.b((com.android.tools.r8.graph.T0) obj);
            }
        })) {
            t0.getAccessFlags().b();
        }
        if (t0.f.h() && AbstractC2734ot.b(collection, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.ma$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C2546ma.c((com.android.tools.r8.graph.T0) obj);
            }
        })) {
            t0.getAccessFlags().a();
        }
    }

    private static boolean a(final com.android.tools.r8.graph.T0 t0, Iterable iterable) {
        return AbstractC2734ot.b(iterable, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.ma$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C2546ma.a(t0, (com.android.tools.r8.graph.T0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.T0 t0, com.android.tools.r8.graph.T0 t1) {
        return t1.getType() != t0.getType();
    }
}
