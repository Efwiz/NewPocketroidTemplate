package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1155Lg {
    static final /* synthetic */ boolean c = true;
    private final ConcurrentHashMap a = new ConcurrentHashMap();
    private final Set b = OU.a();

    public final void a(C0702m c0702m, C0729q2 c0729q2, AbstractC1476Wj abstractC1476Wj) {
        if (!c && this.a.containsKey(c0729q2.d)) {
            throw new AssertionError();
        }
        this.a.put(c0729q2.d, new C1129Kg(c0702m, c0729q2, abstractC1476Wj));
    }

    public final boolean b(C0767v2 c0767v2) {
        return !((C1129Kg) this.a.get(c0767v2)).d.isEmpty();
    }

    public final boolean c(C0767v2 c0767v2) {
        return this.a.containsKey(c0767v2);
    }

    public final void d(C0767v2 c0767v2) {
        this.a.remove(c0767v2);
    }

    public final boolean e() {
        return this.a.isEmpty();
    }

    public final void f() {
        for (C1129Kg c1129Kg : this.a.values()) {
            if (!c && c1129Kg.d == null) {
                throw new AssertionError();
            }
        }
    }

    public final AbstractC1048Hm c() {
        return AbstractC1048Hm.a(this.a.keySet());
    }

    public final void d() {
        this.a.clear();
    }

    public final void a(C0767v2 c0767v2, Set set) {
        ((C1129Kg) this.a.get(c0767v2)).d = set;
    }

    public final AbstractC1048Hm b() {
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        Iterator it = this.a.values().iterator();
        while (it.hasNext()) {
            c0862BmG.a(((C1129Kg) it.next()).a);
        }
        return c0862BmG.a();
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        this.b.add(o5Var.getReference());
    }

    public final C0729q2 a(C0767v2 c0767v2) {
        C1129Kg c1129Kg = (C1129Kg) this.a.get(c0767v2);
        if (c1129Kg == null) {
            return null;
        }
        return c1129Kg.a;
    }

    public final C1749cD a() {
        Iterator it = this.a.values().iterator();
        if (!c && !it.hasNext()) {
            throw new AssertionError();
        }
        C1749cD c1749cD = ((C1129Kg) it.next()).b;
        while (it.hasNext()) {
            c1749cD.a(((C1129Kg) it.next()).b);
        }
        c1749cD.a(this.b);
        return c1749cD;
    }

    public final void a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var) {
        C1129Kg c1129Kg = (C1129Kg) this.a.get(c0767v2);
        if (c1129Kg == null) {
            return;
        }
        c1129Kg.b.a(o5Var);
    }

    public final void a(C0729q2 c0729q2, com.android.tools.r8.graph.Y0 y0) {
        C1129Kg c1129Kg = (C1129Kg) this.a.get(c0729q2.getType());
        if (c1129Kg == null) {
            return;
        }
        c1129Kg.c.add(y0);
    }

    public final void a(final Consumer consumer) {
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Lg$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1155Lg.a(consumer, (C1129Kg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, C1129Kg c1129Kg) {
        consumer.accept(c1129Kg.a);
    }

    public final void a(final BiConsumer biConsumer) {
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Lg$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1155Lg.a(biConsumer, (C1129Kg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(BiConsumer biConsumer, C1129Kg c1129Kg) {
        biConsumer.accept(c1129Kg.a, c1129Kg.c);
    }
}
