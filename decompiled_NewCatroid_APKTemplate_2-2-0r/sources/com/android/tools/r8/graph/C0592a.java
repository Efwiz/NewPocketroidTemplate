package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CN;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.a, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0592a extends AbstractC0632d {
    static final /* synthetic */ boolean b = true;
    private final Map a;

    public C0592a() {
        this(new IdentityHashMap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CN e(Y0 y0) {
        return CN.b();
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    public final C0592a a() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final int b() {
        if (this.a.size() == 1) {
            return ((CN) this.a.values().iterator().next()).size();
        }
        throw new C3405y10("Should only be querying the number of access contexts after flattening");
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    public final boolean c() {
        return this.a.isEmpty();
    }

    public final Map d() {
        return this.a;
    }

    public C0592a(IdentityHashMap identityHashMap) {
        this.a = identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CN c(Y0 y0) {
        return CN.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CN d(Y0 y0) {
        return CN.b();
    }

    final void a(final Consumer consumer, final Predicate predicate) {
        Map map = this.a;
        if (map != null) {
            map.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C0592a.a(predicate, consumer, (Y0) obj, (CN) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, Consumer consumer, Y0 y0, CN cn) {
        if (predicate.test(y0)) {
            consumer.accept(y0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CN b(Y0 y0) {
        return CN.b();
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    public final boolean b(Predicate predicate) {
        Iterator it = this.a.values().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((CN) it.next()).iterator();
            while (it2.hasNext()) {
                if (!predicate.test((o5) it2.next())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final void a(Consumer consumer) {
        CN cnB = CN.b();
        Map map = this.a;
        if (map != null) {
            Iterator it = map.values().iterator();
            while (it.hasNext()) {
                for (o5 o5Var : (CN) it.next()) {
                    if (cnB.add(o5Var)) {
                        consumer.accept(o5Var);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0632d
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C0592a a(final Q0 q0, final AbstractC1476Wj abstractC1476Wj) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0592a.a(identityHashMap, abstractC1476Wj, q0, (Y0) obj, (CN) obj2);
            }
        });
        return new C0592a(identityHashMap);
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    final void a(final Y0 y0) {
        Map map = this.a;
        if (map != null) {
            final CN cn = (CN) map.computeIfAbsent(y0, new Function() { // from class: com.android.tools.r8.graph.a$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C0592a.b((Y0) obj);
                }
            });
            this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C0592a.a(y0, cn, (Y0) obj, (CN) obj2);
                }
            });
            this.a.clear();
            if (!cn.isEmpty()) {
                this.a.put(y0, cn);
            }
            if (!b && this.a.size() > 1) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Map map, Y0 y0, CN cn) {
        ((CN) map.computeIfAbsent(y0, new Function() { // from class: com.android.tools.r8.graph.a$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0592a.c((Y0) obj);
            }
        })).a(cn);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Y0 y0, CN cn, Y0 y1, CN cn2) {
        if (y1 != y0) {
            cn.a(cn2);
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    public final boolean a(Predicate predicate) {
        Iterator it = this.a.values().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((CN) it.next()).iterator();
            while (it2.hasNext()) {
                if (predicate.test((o5) it2.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    public final boolean a(W0 w0) {
        Iterator it = this.a.values().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((CN) it.next()).iterator();
            while (it2.hasNext()) {
                if (((o5) it2.next()).e() != w0) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean a(Y0 y0, o5 o5Var) {
        return ((CN) this.a.computeIfAbsent(y0, new Function() { // from class: com.android.tools.r8.graph.a$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0592a.d((Y0) obj);
            }
        })).add(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, AbstractC1476Wj abstractC1476Wj, Q0 q0, Y0 y0, CN cn) {
        CN cn2 = (CN) map.computeIfAbsent(abstractC1476Wj.c(y0), new Function() { // from class: com.android.tools.r8.graph.a$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0592a.e((Y0) obj);
            }
        });
        Iterator it = cn.iterator();
        while (it.hasNext()) {
            o5 o5Var = (o5) it.next();
            o5 o5VarA = abstractC1476Wj.a(o5Var, q0);
            if (!b && o5VarA == null) {
                throw new AssertionError("Unable to map context: " + o5Var.w());
            }
            cn2.add(o5VarA);
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0632d
    public final AbstractC0632d a(AbstractC0632d abstractC0632d) {
        if (abstractC0632d.c()) {
            return this;
        }
        if (abstractC0632d instanceof C0625c) {
            return abstractC0632d;
        }
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0592a.a(identityHashMap, (Y0) obj, (CN) obj2);
            }
        });
        abstractC0632d.a().a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.a$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0592a.b(identityHashMap, (Y0) obj, (CN) obj2);
            }
        });
        return new C0592a(identityHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, Y0 y0, CN cn) {
        map.put(y0, CN.b(cn));
    }
}
