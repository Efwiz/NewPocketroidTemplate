package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.AbstractC2763pC;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class F implements Comparable {
    private final HashSet a = new HashSet();
    private final HashMap b = new HashMap();
    private final ArrayList c = new ArrayList();
    final /* synthetic */ H d;

    F(H h) {
        this.d = h;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set b(W0 w0) {
        return new HashSet();
    }

    final void a(W0 w0, G g) {
        ((Set) this.b.computeIfAbsent(w0, new Function() { // from class: com.android.tools.r8.naming.F$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return F.a((W0) obj);
            }
        })).add(g);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return ((F) obj).b.size() - this.b.size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(W0 w0) {
        return new HashSet();
    }

    final void b(final C0759u2 c0759u2) {
        a(new BiConsumer() { // from class: com.android.tools.r8.naming.F$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b(c0759u2, (W0) obj, (G) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C0759u2 c0759u2, W0 w0, G g) {
        C0759u2 c0759u2A = g.a(w0);
        if (c0759u2A != null) {
            g.c(c0759u2A, w0);
            this.d.e.a(c0759u2A, w0);
        } else {
            g.c(c0759u2, w0);
            this.d.e.a(c0759u2, w0);
        }
    }

    final void a(F f) {
        this.a.addAll(f.a);
        this.c.addAll(f.c);
        for (W0 w0 : f.b.keySet()) {
            ((Set) this.b.computeIfAbsent(w0, new Function() { // from class: com.android.tools.r8.naming.F$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return F.b((W0) obj);
                }
            })).addAll((Collection) f.b.get(w0));
        }
    }

    final C0759u2 a() {
        C0759u2 c0759u2 = null;
        if (this.b.isEmpty()) {
            return null;
        }
        ArrayList<W0> arrayListA = AbstractC2763pC.a(this.b.keySet());
        arrayListA.sort(new Comparator() { // from class: com.android.tools.r8.naming.F$$ExternalSyntheticLambda4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return F.a((W0) obj, (W0) obj2);
            }
        });
        for (W0 w0 : arrayListA) {
            Iterator it = ((Set) this.b.get(w0)).iterator();
            while (it.hasNext()) {
                C0759u2 c0759u2A = ((G) it.next()).a(w0);
                if (c0759u2A == w0.K0()) {
                    return w0.K0();
                }
                if (c0759u2A != null) {
                    c0759u2 = c0759u2A;
                }
            }
        }
        return c0759u2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(W0 w0, W0 w1) {
        return w0.getReference().compareTo(w1.getReference());
    }

    final boolean a(final C0759u2 c0759u2) {
        Object objApply;
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.naming.F$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return F.a(c0759u2, (W0) obj, (G) obj2);
            }
        };
        Iterator it = this.b.entrySet().iterator();
        loop0: while (true) {
            if (!it.hasNext()) {
                objApply = null;
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            Iterator it2 = ((Set) entry.getValue()).iterator();
            while (it2.hasNext()) {
                objApply = biFunction.apply((W0) entry.getKey(), (G) it2.next());
                if (objApply != null) {
                    break loop0;
                }
            }
        }
        Boolean bool = (Boolean) objApply;
        return bool == null || bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(C0759u2 c0759u2, W0 w0, G g) {
        if (g.b(c0759u2, w0)) {
            return null;
        }
        return Boolean.FALSE;
    }

    final void a(final C0759u2 c0759u2, final U u) {
        a(new BiConsumer() { // from class: com.android.tools.r8.naming.F$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                F.a(c0759u2, u, (W0) obj, (G) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0759u2 c0759u2, U u, W0 w0, G g) {
        g.a(c0759u2, w0);
        u.a(c0759u2, w0);
    }

    final void a(final BiConsumer biConsumer) {
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.naming.F$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return F.a(biConsumer, (W0) obj, (G) obj2);
            }
        };
        for (Map.Entry entry : this.b.entrySet()) {
            Iterator it = ((Set) entry.getValue()).iterator();
            while (it.hasNext()) {
                if (biFunction.apply((W0) entry.getKey(), (G) it.next()) != null) {
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(BiConsumer biConsumer, W0 w0, G g) {
        biConsumer.accept(w0, g);
        return null;
    }
}
