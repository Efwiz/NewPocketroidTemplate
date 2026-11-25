package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class QK {
    static final /* synthetic */ boolean d = true;
    private AbstractC1476Wj b;
    private final ConcurrentHashMap a = new ConcurrentHashMap();
    private ConcurrentHashMap c = new ConcurrentHashMap();

    /* JADX INFO: renamed from: $r8$lambda$wBZ5N9SAmxJLRX-vx8hIUeo_fgc, reason: not valid java name */
    public static /* synthetic */ ArrayList m1080$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc() {
        return new ArrayList();
    }

    public QK(AbstractC1476Wj abstractC1476Wj) {
        this.b = abstractC1476Wj;
    }

    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!d && c0702m.E() != this.b) {
            throw new AssertionError();
        }
        this.c.remove(o5Var.getReference());
    }

    public final CN b(C0702m c0702m) {
        CN cnB = CN.b();
        for (List list : a(c0702m).values()) {
            if (list.size() >= c0702m.Y().T.d) {
                cnB.addAll(list);
            }
        }
        return cnB;
    }

    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, ArrayList arrayList) {
        if (!d && c0702m.E() != this.b) {
            throw new AssertionError();
        }
        if (arrayList.isEmpty()) {
            this.c.remove(o5Var.getReference());
            return;
        }
        ConcurrentHashMap concurrentHashMap = this.c;
        C0684j2 reference = o5Var.getReference();
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add((ZK) this.a.computeIfAbsent((ZK) it.next(), Function.identity()));
        }
        concurrentHashMap.put(reference, arrayList2);
    }

    public final void a(final AbstractC1476Wj abstractC1476Wj) {
        if (abstractC1476Wj == this.b) {
            return;
        }
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(this.c.size());
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.QK$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(abstractC1476Wj, concurrentHashMap, (C0684j2) obj, (List) obj2);
            }
        });
        this.c = concurrentHashMap;
        this.b = abstractC1476Wj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC1476Wj abstractC1476Wj, Map map, C0684j2 c0684j2, List list) {
        C0684j2 c0684j2C = abstractC1476Wj.c(this.b, c0684j2);
        if (!d && map.containsKey(c0684j2C)) {
            throw new AssertionError();
        }
        List listA = a(list, abstractC1476Wj);
        if (listA.isEmpty()) {
            return;
        }
        map.put(c0684j2C, listA);
    }

    private List a(List list, final AbstractC1476Wj abstractC1476Wj) {
        if (d || abstractC1476Wj != this.b) {
            return com.android.tools.r8.utils.V1.a(list, new Function() { // from class: com.android.tools.r8.internal.QK$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return QK.a(abstractC1476Wj, (ZK) obj);
                }
            }, list);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ZK a(AbstractC1476Wj abstractC1476Wj, ZK zk) {
        return zk.b(abstractC1476Wj);
    }

    private HashMap a(final C0702m c0702m) {
        final HashMap map = new HashMap();
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.QK$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0702m, map, (C0684j2) obj, (List) obj2);
            }
        });
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0702m c0702m, Map map, C0684j2 c0684j2, List list) {
        C0684j2 c0684j2C = c0702m.E().c(this.b, c0684j2);
        com.android.tools.r8.graph.o5 o5VarA = c0684j2C.a(C0729q2.a(c0702m.c(c0684j2C.B0())));
        if (o5VarA == null) {
            if (!d) {
                throw new AssertionError();
            }
        } else {
            if (!d && o5VarA.B().u()) {
                throw new AssertionError();
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((List) map.computeIfAbsent((ZK) it.next(), com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.QK$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return QK.m1080$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc();
                    }
                }))).add(o5VarA);
            }
        }
    }
}
