package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.q2$$ExternalSyntheticLambda9;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1509Xn {
    static final /* synthetic */ boolean c = true;
    private final List a;
    private final Map b;

    private C1509Xn(List list, Map map) {
        this.a = list;
        this.b = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C0702m c0702m, final InterfaceC2802pl interfaceC2802pl, final C2688oE c2688oE, final Map map, final CR cr, final C0986Fk c0986Fk, final EnumC0928Dk enumC0928Dk, final CN cn, C0729q2 c0729q2) {
        c0729q2.d(new Consumer() { // from class: com.android.tools.r8.internal.Xn$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1509Xn.a(c0702m, interfaceC2802pl, c2688oE, map, cr, c0986Fk, enumC0928Dk, cn, (com.android.tools.r8.graph.o5) obj);
            }
        }, new q2$$ExternalSyntheticLambda9());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C0702m c0702m, InterfaceC2802pl interfaceC2802pl, C2688oE c2688oE, Map map, final CR cr, final C0986Fk c0986Fk, final EnumC0928Dk enumC0928Dk, CN cn, com.android.tools.r8.graph.o5 o5Var) {
        C1353Sn c1353SnA = AbstractC1300Qn.a(c0702m, interfaceC2802pl, c2688oE, o5Var);
        if (c1353SnA != null) {
            ((C1454Vn) map.computeIfAbsent(c1353SnA, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.Xn$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return C1509Xn.a(c0702m, cr, c0986Fk, enumC0928Dk);
                }
            }))).b(o5Var);
        } else {
            cn.add(o5Var);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1454Vn a(C0702m c0702m, CR cr, C0986Fk c0986Fk, EnumC0928Dk enumC0928Dk) {
        return new C1454Vn(c0702m, cr, c0986Fk, enumC0928Dk);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2688oE c2688oE, CN cn, Map map, C1353Sn c1353Sn, C1454Vn c1454Vn) {
        C1480Wn c1480WnA = c1454Vn.a(c2688oE, c1353Sn);
        if (c1480WnA.c() == 1) {
            cn.addAll(c1480WnA.b());
        } else {
            map.put(c1353Sn, c1480WnA);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, final C0702m c0702m, final CR cr, final C0986Fk c0986Fk, final EnumC0928Dk enumC0928Dk, com.android.tools.r8.graph.o5 o5Var) {
        ((C1454Vn) map.computeIfAbsent(o5Var.e().m1(), new Function() { // from class: com.android.tools.r8.internal.Xn$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1509Xn.a(c0702m, cr, c0986Fk, enumC0928Dk, (C0737r2) obj);
            }
        })).a(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1454Vn a(C0702m c0702m, CR cr, C0986Fk c0986Fk, EnumC0928Dk enumC0928Dk, C0737r2 c0737r2) {
        return new C1454Vn(c0702m, cr, c0986Fk, enumC0928Dk);
    }

    private static void a(C2688oE c2688oE) {
        c2688oE.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Xn$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1509Xn.a((C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0729q2 c0729q2) {
        if (!c && c0729q2.h2().iterator().hasNext()) {
            throw new AssertionError();
        }
    }

    public final void a(Consumer consumer) {
        this.a.forEach(consumer);
        this.b.values().forEach(consumer);
    }

    public static C1509Xn a(C0702m c0702m, final MR mr, final InterfaceC2802pl interfaceC2802pl, final C2688oE c2688oE, final C0986Fk c0986Fk, final EnumC0928Dk enumC0928Dk) {
        if (!c0702m.f().h()) {
            boolean z = c;
            if (!z && !c0702m.Y().O0().g()) {
                throw new AssertionError();
            }
            if (!z) {
                a(c2688oE);
            }
            return new C1509Xn(Collections.emptyList(), Collections.emptyMap());
        }
        final C0702m c0702mH0 = c0702m.h0();
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final BB bbD = CN.d();
        c2688oE.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Xn$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1509Xn.a(c0702mH0, interfaceC2802pl, c2688oE, linkedHashMap, mr, c0986Fk, enumC0928Dk, bbD, (C0729q2) obj);
            }
        });
        final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Xn$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1509Xn.a(c2688oE, bbD, linkedHashMap2, (C1353Sn) obj, (C1454Vn) obj2);
            }
        });
        ArrayList arrayList = new ArrayList();
        c0702m.Y().O0().getClass();
        final LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        bbD.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Xn$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1509Xn.a(linkedHashMap3, c0702mH0, mr, c0986Fk, enumC0928Dk, (com.android.tools.r8.graph.o5) obj);
            }
        });
        Iterator it = linkedHashMap3.values().iterator();
        while (it.hasNext()) {
            arrayList.addAll(((C1454Vn) it.next()).a(c2688oE));
        }
        arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.Xn$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((C1480Wn) obj).a());
            }
        }).reversed());
        return new C1509Xn(arrayList, linkedHashMap2);
    }
}
