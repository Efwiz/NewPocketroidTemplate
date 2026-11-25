package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.q2$$ExternalSyntheticLambda28;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class VG extends SF {
    static final /* synthetic */ boolean d = true;
    private final C0702m b;
    private final C0767v2 c;

    public VG(C0702m c0702m) {
        this.b = c0702m;
        this.c = c0702m.a().Z1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C2631nf b(C0729q2 c0729q2) {
        if (!d && !c0729q2.isInterface()) {
            throw new AssertionError();
        }
        C2631nf c2631nfE = C2631nf.e();
        com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        o3E.c(c0729q2);
        while (o3E.c()) {
            C0729q2 c0729q3 = (C0729q2) o3E.f();
            if (!d && !c0729q3.isInterface()) {
                throw new AssertionError();
            }
            a(c2631nfE, c0729q3);
            Iterator<C0767v2> it = c0729q3.f1().iterator();
            while (it.hasNext()) {
                C0767v2 next = it.next();
                C0702m c0702m = this.b;
                c0702m.getClass();
                C0729q2 c0729q2A = C0729q2.a(next == c0729q3.d ? c0729q3 : c0702m.a(next));
                if (c0729q2A != null && c0729q2A.isInterface()) {
                    o3E.c(c0729q2A);
                }
            }
        }
        return c2631nfE;
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            C2631nf c2631nf = (C2631nf) (c2688oE.l() ? new Function() { // from class: com.android.tools.r8.internal.VG$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.b((C0729q2) obj);
                }
            } : new Function() { // from class: com.android.tools.r8.internal.VG$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a((C0729q2) obj);
                }
            }).apply(c0729q2);
            Iterator it2 = linkedHashMap.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    C2688oE c2688oE2 = new C2688oE();
                    c2688oE2.a(c0729q2);
                    linkedHashMap.put(c2688oE2, c2631nf);
                    break;
                }
                Map.Entry entry = (Map.Entry) it2.next();
                C2688oE c2688oE3 = (C2688oE) entry.getKey();
                C2631nf c2631nf2 = (C2631nf) entry.getValue();
                if (!c2631nf2.a(c2631nf.keySet())) {
                    c2688oE3.a(c0729q2);
                    break;
                }
                Iterator it3 = c2631nf2.b(c2631nf.keySet()).iterator();
                if (it3.hasNext()) {
                    AbstractC0717o2 abstractC0717o2 = (AbstractC0717o2) it3.next();
                    if (c2631nf2.get(abstractC0717o2) == c2631nf.get(abstractC0717o2) && c2631nf2.get(abstractC0717o2) != this.c) {
                        c2688oE3.a(c0729q2);
                        break;
                    }
                }
            }
        }
        Set setKeySet = linkedHashMap.keySet();
        LinkedList linkedList = new LinkedList();
        AbstractC2734ot.a((Iterable) setKeySet, (Collection) linkedList);
        return WL.a(linkedList);
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoDefaultInterfaceMethodMerging";
    }

    private void a(final C2631nf c2631nf, final C0729q2 c0729q2) {
        c0729q2.h(new Consumer() { // from class: com.android.tools.r8.internal.VG$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2631nf, c0729q2, (com.android.tools.r8.graph.o5) obj);
            }
        }, new q2$$ExternalSyntheticLambda28());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2631nf c2631nf, final C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        c2631nf.a(o5Var.e(), c0729q2.getType(), new BiFunction() { // from class: com.android.tools.r8.internal.VG$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(c0729q2, (C0767v2) obj, (C0767v2) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0767v2 a(C0729q2 c0729q2, C0767v2 c0767v2, C0767v2 c0767v3) {
        return c0767v3 == c0729q2.getType() ? c0767v3 : this.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C2631nf a(C0729q2 c0729q2) {
        if (!d && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        C2631nf c2631nfE = C2631nf.e();
        com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        Iterator<C0767v2> it = c0729q2.f1().iterator();
        while (it.hasNext()) {
            C0767v2 next = it.next();
            C0702m c0702m = this.b;
            c0702m.getClass();
            C0729q2 c0729q2A = C0729q2.a(next == c0729q2.d ? c0729q2 : c0702m.a(next));
            if (c0729q2A != null && c0729q2A.isInterface()) {
                o3E.c(c0729q2A);
            }
        }
        while (o3E.c()) {
            C0729q2 c0729q3 = (C0729q2) o3E.f();
            if (!d && !c0729q3.isInterface()) {
                throw new AssertionError();
            }
            a(c2631nfE, c0729q3);
            Iterator<C0767v2> it2 = c0729q3.f1().iterator();
            while (it2.hasNext()) {
                C0767v2 next2 = it2.next();
                C0702m c0702m2 = this.b;
                c0702m2.getClass();
                C0729q2 c0729q2A2 = C0729q2.a(next2 == c0729q3.d ? c0729q3 : c0702m2.a(next2));
                if (c0729q2A2 != null && c0729q2A2.isInterface()) {
                    o3E.c(c0729q2A2);
                }
            }
        }
        return c2631nfE;
    }
}
