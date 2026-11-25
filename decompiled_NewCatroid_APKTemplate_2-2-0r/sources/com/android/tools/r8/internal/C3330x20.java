package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3330x20 extends AbstractC2550me {
    private final IdentityHashMap f;
    private final Set g;
    private final IdentityHashMap h;

    public C3330x20(C0702m c0702m, com.android.tools.r8.graph.H3 h3) {
        super(c0702m, h3);
        this.f = new IdentityHashMap();
        this.g = OU.c();
        this.h = new IdentityHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.h.put(o5Var2.getReference(), o5Var.getReference());
    }

    private HashMap l(C0729q2 c0729q2) {
        final HashMap map = new HashMap();
        com.android.tools.r8.graph.H3 h3 = this.b;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.x20$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(map, (C0729q2) obj);
            }
        };
        h3.getClass();
        h3.a(CM.b(), consumer, c0729q2);
        c0729q2.h(new Consumer() { // from class: com.android.tools.r8.internal.x20$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3330x20.a(map, (com.android.tools.r8.graph.o5) obj);
            }
        }, CM.b());
        return map;
    }

    public final void a(Collection collection, C2 c2) {
        a(collection);
        c2.a(this.g);
        c2.a(this.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void i(final C0729q2 c0729q2) {
        final Map map = (Map) this.f.remove(c0729q2);
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.x20$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(map, c0729q2, (com.android.tools.r8.graph.o5) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.h(consumer, CM.b());
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void k(C0729q2 c0729q2) {
        this.f.put(c0729q2, l(c0729q2));
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void a(Consumer consumer, C0729q2 c0729q2) {
        List listA = this.b.a(c0729q2);
        if (listA.isEmpty()) {
            if (this.a.Y().n1.r0 || c0729q2.x1() || c0729q2.isInterface() || !this.a.a(c0729q2).b((com.android.tools.r8.shaking.K0) this.a.Y())) {
                return;
            }
            c0729q2.getAccessFlags().t();
            return;
        }
        listA.forEach(consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final Map map, C0729q2 c0729q2) {
        ((Map) this.f.get(c0729q2)).forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.x20$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3330x20.a(map, (AbstractC0717o2) obj, (C3255w20) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, AbstractC0717o2 abstractC0717o2, final C3255w20 c3255w20) {
        map.computeIfAbsent(abstractC0717o2, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.x20$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return C3330x20.a(c3255w20);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C3255w20 a(C3255w20 c3255w20) {
        return new C3255w20(c3255w20.b, c3255w20);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Map map, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0717o2 abstractC0717o2Z = o5Var.z();
        if (map.containsKey(abstractC0717o2Z)) {
            ((C3255w20) map.get(abstractC0717o2Z)).a.a(o5Var);
        } else {
            map.put(abstractC0717o2Z, new C3255w20(o5Var, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, C0729q2 c0729q2, final com.android.tools.r8.graph.o5 o5Var) {
        C3255w20 c3255w20 = (C3255w20) map.remove(o5Var.z());
        a(o5Var, c3255w20);
        if (o5Var.b(c3255w20.b)) {
            if (!c0729q2.isInterface() && c3255w20.c.isEmpty()) {
                this.g.add(o5Var.getReference());
                return;
            }
            final com.android.tools.r8.graph.o5 o5Var2 = null;
            com.android.tools.r8.graph.o5 o5Var3 = c3255w20.b.getAccessFlags().W() ? null : c3255w20.b;
            Iterator it = c3255w20.c.iterator();
            while (true) {
                if (it.hasNext()) {
                    com.android.tools.r8.graph.o5 o5Var4 = (com.android.tools.r8.graph.o5) it.next();
                    if (!o5Var4.getAccessFlags().W()) {
                        if (o5Var3 != null) {
                            break;
                        } else {
                            o5Var3 = o5Var4;
                        }
                    }
                } else {
                    if (!C3255w20.d && o5Var3 != null && o5Var3.getAccessFlags().W()) {
                        throw new AssertionError();
                    }
                    o5Var2 = o5Var3;
                    break;
                }
            }
            if (o5Var2 != null && !c3255w20.b.a().isInterface()) {
                c3255w20.a(new Consumer() { // from class: com.android.tools.r8.internal.x20$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(o5Var2, (com.android.tools.r8.graph.o5) obj);
                    }
                });
                if (o5Var2.a().isInterface()) {
                    return;
                }
                this.g.add(o5Var2.getReference());
                return;
            }
            c3255w20.a(new Consumer() { // from class: com.android.tools.r8.internal.x20$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(o5Var, (com.android.tools.r8.graph.o5) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        this.h.put(o5Var2.getReference(), o5Var.getReference());
    }

    private void a(com.android.tools.r8.graph.o5 o5Var, C3255w20 c3255w20) {
        if (this.a.Y().n1.q0 || o5Var.a().isInterface() || o5Var.getAccessFlags().W() || !c3255w20.c.isEmpty() || !this.a.a(o5Var).b((com.android.tools.r8.shaking.K0) this.a.Y())) {
            return;
        }
        o5Var.getAccessFlags().t();
    }
}
