package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2787pa {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final InterfaceC2802pl b;
    private final EnumC0928Dk c;
    private final C2688oE d;

    public C2787pa(C0702m c0702m, InterfaceC2802pl interfaceC2802pl, C2688oE c2688oE, EnumC0928Dk enumC0928Dk) {
        this.a = c0702m;
        this.b = interfaceC2802pl;
        this.d = c2688oE;
        this.c = enumC0928Dk;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0073  */
    public final C2858qa a(C0986Fk c0986Fk) {
        List list;
        List listEmptyList;
        if (this.a.f().h()) {
            LinkedHashMap linkedHashMapA = a();
            if (linkedHashMapA.isEmpty()) {
                listEmptyList = Collections.emptyList();
            } else {
                ArrayList arrayList = new ArrayList(linkedHashMapA.size());
                Iterator it = linkedHashMapA.values().iterator();
                while (it.hasNext()) {
                    arrayList.add(((C3034t20) it.next()).a(this.a.h0(), this.d));
                }
                list = arrayList;
            }
            if (list.stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.internal.pa$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C2787pa.a((C3180v20) obj);
                }
            })) {
                if (e && !this.c.b()) {
                    throw new AssertionError();
                }
                C0722p1 c0722p1A = this.a.a();
                C2688oE c2688oE = this.d;
                c2688oE.a(c0722p1A.a(c2688oE.f().getType(), c0722p1A.A1, "$r8$classId"));
            }
            return new C2858qa(this.a, this.b, this.c, c0986Fk, this.d, list);
        }
        if (!e && !a().isEmpty()) {
            throw new AssertionError();
        }
        listEmptyList = Collections.emptyList();
        list = listEmptyList;
        if (list.stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.internal.pa$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2787pa.a((C3180v20) obj);
            }
        })) {
            if (e) {
            }
            C0722p1 c0722p1A2 = this.a.a();
            C2688oE c2688oE2 = this.d;
            c2688oE2.a(c0722p1A2.a(c2688oE2.f().getType(), c0722p1A2.A1, "$r8$classId"));
        }
        return new C2858qa(this.a, this.b, this.c, c0986Fk, this.d, list);
    }

    private LinkedHashMap a() {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.internal.pa$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2787pa.a(linkedHashMap, (C0729q2) obj);
            }
        });
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final Map map, C0729q2 c0729q2) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.pa$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2787pa.a(map, (com.android.tools.r8.graph.o5) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.h(consumer, CM.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, com.android.tools.r8.graph.o5 o5Var) {
        ((C3034t20) map.computeIfAbsent(o5Var.getReference().J0(), new Function() { // from class: com.android.tools.r8.internal.pa$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2787pa.a((AbstractC0717o2) obj);
            }
        })).a(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3034t20 a(AbstractC0717o2 abstractC0717o2) {
        return new C3034t20();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3180v20 c3180v20) {
        return !c3180v20.c();
    }
}
