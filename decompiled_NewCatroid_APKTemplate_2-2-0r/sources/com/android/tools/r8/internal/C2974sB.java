package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0644e4;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2974sB {
    private final AbstractC2178hm a;
    private final AbstractC1048Hm b;
    private final AbstractC1048Hm c;

    public C2974sB(C0702m c0702m) {
        C0722p1 c0722p1A = c0702m.a();
        this.a = a(c0702m, c0722p1A);
        this.b = b(c0722p1A);
        this.c = a(c0722p1A);
    }

    private static AbstractC2178hm a(final C0702m c0702m, C0722p1 c0722p1) {
        C1946em c1946emA = AbstractC2178hm.e().a(c0722p1.Z3.b, new BiPredicate() { // from class: com.android.tools.r8.internal.sB$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C2974sB.b((C0684j2) obj, (List) obj2);
            }
        }).a(c0722p1.f4.j, new BiPredicate() { // from class: com.android.tools.r8.internal.sB$$ExternalSyntheticLambda1
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C2974sB.a(c0702m, (C0684j2) obj, (List) obj2);
            }
        }).a(c0722p1.k4.e, new BiPredicate() { // from class: com.android.tools.r8.internal.sB$$ExternalSyntheticLambda2
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C2974sB.c((C0684j2) obj, (List) obj2);
            }
        }).a(c0722p1.k4.s, new BiPredicate() { // from class: com.android.tools.r8.internal.sB$$ExternalSyntheticLambda3
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C2974sB.b(c0702m, (C0684j2) obj, (List) obj2);
            }
        });
        final com.android.tools.r8.graph.Z1 z1 = c0722p1.X3;
        AbstractC1048Hm abstractC1048Hm = z1.u;
        BiPredicate biPredicate = new BiPredicate() { // from class: com.android.tools.r8.internal.sB$$ExternalSyntheticLambda4
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return z1.a((C0684j2) obj, (List) obj2);
            }
        };
        Iterator<E> it = abstractC1048Hm.iterator();
        while (it.hasNext()) {
            c1946emA.a(it.next(), biPredicate);
        }
        final com.android.tools.r8.graph.Z1 z2 = c0722p1.W3;
        AbstractC1048Hm abstractC1048Hm2 = z2.u;
        BiPredicate biPredicate2 = new BiPredicate() { // from class: com.android.tools.r8.internal.sB$$ExternalSyntheticLambda4
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return z2.a((C0684j2) obj, (List) obj2);
            }
        };
        Iterator<E> it2 = abstractC1048Hm2.iterator();
        while (it2.hasNext()) {
            c1946emA.a(it2.next(), biPredicate2);
        }
        return c1946emA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C0684j2 c0684j2, List list) {
        return ((S10) list.get(0)).P();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(C0684j2 c0684j2, List list) {
        return ((S10) list.get(1)).P();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C0702m c0702m, C0684j2 c0684j2, List list) {
        return !AbstractC0937Dt.a(c0702m, list);
    }

    private static AbstractC1048Hm b(C0722p1 c0722p1) {
        return AbstractC1048Hm.g().a(c0722p1.Y3.g).a(c0722p1.Z3.c).a(c0722p1.Z3.d).a(c0722p1.p4.a).a(c0722p1.a4.b).a(c0722p1.m4.c).a(c0722p1.r4.k).a(c0722p1.b4.b).a(c0722p1.c4.b).a(c0722p1.d4.b).a(c0722p1.v4.a).a(c0722p1.v4.b).a(c0722p1.i4.a).a(c0722p1.g4.g).a(c0722p1.g4.e).a(c0722p1.j4.b).a(c0722p1.X3.r).a(c0722p1.W3.r).a(c0722p1.k4.c).a(c0722p1.k4.r).a(c0722p1.k4.b).a(c0722p1.k4.t).a(c0722p1.k4.u).a((Set) c0722p1.p4.n).a(c0722p1.a()).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0702m c0702m, C0684j2 c0684j2, List list) {
        return !AbstractC0937Dt.a(c0702m, list);
    }

    private static AbstractC1048Hm a(C0722p1 c0722p1) {
        C0722p1.b bVar = c0722p1.g4;
        return AbstractC1048Hm.a(bVar.d, bVar.f, bVar.i);
    }

    public final void a(Consumer consumer) {
        this.b.forEach(consumer);
    }

    public final boolean a(C0684j2 c0684j2, List list) {
        if (!this.b.contains(c0684j2)) {
            AbstractC2178hm abstractC2178hm = this.a;
            Object objA = com.android.tools.r8.utils.H.a();
            Object obj = abstractC2178hm.get(c0684j2);
            if (obj != null) {
                objA = obj;
            }
            if (!((BiPredicate) objA).test(c0684j2, list)) {
                return false;
            }
        }
        return true;
    }

    public final boolean a(AbstractC1305Qs abstractC1305Qs, C0644e4 c0644e4) {
        return a(c0644e4.getReference(), abstractC1305Qs.J2()) || this.c.contains(c0644e4.getReference());
    }
}
