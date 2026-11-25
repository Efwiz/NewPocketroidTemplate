package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.q2$$ExternalSyntheticLambda9;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class QG extends TF {
    static final /* synthetic */ boolean d = true;
    private final C0702m b;
    private final C0722p1 c;

    public QG(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        if (!d && !enumC0928Dk.a()) {
            throw new AssertionError();
        }
        this.b = c0702m;
        this.c = c0702m.a();
    }

    @Override // com.android.tools.r8.internal.TF
    public final /* bridge */ /* synthetic */ Collection a(C2688oE c2688oE, Object obj) {
        return a(c2688oE, (Set) obj);
    }

    public final Set b(Collection collection) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C2688oE c2688oE = (C2688oE) it.next();
            Iterator it2 = c2688oE.iterator();
            while (it2.hasNext()) {
                identityHashMap.put(((C0729q2) it2.next()).getType(), c2688oE);
            }
        }
        final Set setC = OU.c();
        com.android.tools.r8.utils.O3.d((Iterable) this.b.f().d()).a(new BiConsumer() { // from class: com.android.tools.r8.internal.QG$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(identityHashMap, setC, (C0729q2) obj, (com.android.tools.r8.utils.O3) obj2);
            }
        });
        return setC;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoConstructorCollisions";
    }

    @Override // com.android.tools.r8.internal.TF
    public final /* bridge */ /* synthetic */ Object a(Collection collection, ExecutorService executorService) {
        return b(collection);
    }

    public static List a(C2688oE c2688oE, final Set set) {
        C2688oE c2688oE2 = new C2688oE(AbstractC2734ot.c(c2688oE, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.QG$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return QG.a(set, (C0729q2) obj);
            }
        }));
        if (c2688oE2.m()) {
            return Collections.emptyList();
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(c2688oE2);
        return linkedList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, C0729q2 c0729q2) {
        return !set.contains(c0729q2.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, Set set, C0729q2 c0729q2, com.android.tools.r8.utils.O3 o3) {
        Iterable iterableB;
        if (map.containsKey(c0729q2.getType())) {
            iterableB = (Iterable) map.get(c0729q2.getType());
        } else {
            iterableB = com.android.tools.r8.utils.I1.b(c0729q2);
        }
        Set setC = OU.c();
        Iterator it = iterableB.iterator();
        while (it.hasNext()) {
            for (com.android.tools.r8.graph.W0 w0 : ((C0729q2) it.next()).b(new q2$$ExternalSyntheticLambda9())) {
                C0684j2 reference = w0.getReference();
                if (!setC.add(this.c.a(a(reference.B0(), map), a(reference.H0(), map), reference.C0()))) {
                    for (C0767v2 c0767v2 : w0.m1().a(this.c)) {
                        if (c0767v2.O0() && map.containsKey(c0767v2)) {
                            set.add(c0767v2);
                        }
                    }
                }
            }
        }
        o3.b(iterableB);
    }

    private C0737r2 a(C0737r2 c0737r2, final Map map) {
        return this.c.b(a(c0737r2.v0(), map), (C0767v2[]) com.android.tools.r8.utils.B.a(c0737r2.u0().a, new Function() { // from class: com.android.tools.r8.internal.QG$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(map, (C0767v2) obj);
            }
        }, C0767v2.g));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0767v2 a(C0767v2 c0767v2, Map map) {
        if (c0767v2.M0()) {
            C0767v2 c0767v2B = c0767v2.b(this.c);
            C0767v2 c0767v2A = a(c0767v2B, map);
            return c0767v2A == c0767v2B ? c0767v2 : c0767v2.a(this.c, c0767v2A);
        }
        if (c0767v2.O0()) {
            return !map.containsKey(c0767v2) ? c0767v2 : ((C0729q2) ((C2688oE) map.get(c0767v2)).b().getFirst()).getType();
        }
        if (d || c0767v2.W0() || c0767v2.Y0()) {
            return c0767v2;
        }
        throw new AssertionError();
    }
}
