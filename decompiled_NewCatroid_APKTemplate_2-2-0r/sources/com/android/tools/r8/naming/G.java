package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class G {
    static final /* synthetic */ boolean e = true;
    final AbstractC0727q0 a;
    final HashSet b = new HashSet();
    private final HashSet c = new HashSet();
    final /* synthetic */ H d;

    G(H h, AbstractC0727q0 abstractC0727q0) {
        this.d = h;
        this.a = abstractC0727q0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0759u2 c0759u2, W0 w0, C0767v2 c0767v2) {
        C3604a0 c3604a0C = this.d.e.c(c0767v2);
        c3604a0C.getClass();
        try {
            ((Z) c3604a0C.c(w0.getReference())).a(c0759u2, w0);
        } catch (AssertionError e2) {
            throw new RuntimeException(String.format("Assertion error when trying to reserve name '%s' for method '%s'", c0759u2, w0), e2);
        }
    }

    final C0759u2 a(final W0 w0) {
        C0759u2 c0759u2A;
        if (this.d.a.Y().D0().x() && (c0759u2A = this.d.e.a(this.a, w0)) != null) {
            return c0759u2A;
        }
        Function function = new Function() { // from class: com.android.tools.r8.naming.G$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(w0, (G) obj);
            }
        };
        Object objApply = function.apply(this);
        if (objApply == null && (objApply = a(function)) == null) {
            objApply = b(function);
        }
        if (((Boolean) objApply) == null) {
            return null;
        }
        return w0.K0();
    }

    final void c(final C0759u2 c0759u2, final W0 w0) {
        a(new Consumer() { // from class: com.android.tools.r8.naming.G$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c(c0759u2, w0, (G) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(final C0759u2 c0759u2, final W0 w0, G g) {
        g.c.forEach(new Consumer() { // from class: com.android.tools.r8.naming.G$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c0759u2, w0, (C0767v2) obj);
            }
        });
    }

    final boolean b(final C0759u2 c0759u2, final W0 w0) {
        Function function = new Function() { // from class: com.android.tools.r8.naming.G$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(c0759u2, w0, (G) obj);
            }
        };
        Object objApply = function.apply(this);
        if (objApply == null && (objApply = a(function)) == null) {
            objApply = b(function);
        }
        Boolean bool = (Boolean) objApply;
        return bool == null || bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean b(C0759u2 c0759u2, W0 w0, G g) {
        Iterator it = g.c.iterator();
        while (it.hasNext()) {
            if (!this.d.e.b((C0767v2) it.next()).a(c0759u2, w0.getReference())) {
                return Boolean.FALSE;
            }
        }
        return null;
    }

    private Object b(Function function) {
        for (C0767v2 c0767v2 : this.a.g.a) {
            G g = (G) this.d.g.get(c0767v2);
            if (g != null) {
                Object objApply = function.apply(g);
                if (objApply != null) {
                    return objApply;
                }
                Object objB = g.b(function);
                if (objB != null) {
                    return objB;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean a(W0 w0, G g) {
        Iterator it = g.c.iterator();
        while (it.hasNext()) {
            Set setD = this.d.e.c((C0767v2) it.next()).d(w0.getReference());
            if (!e && setD != null && setD.isEmpty()) {
                throw new AssertionError();
            }
            if (setD != null && setD.contains(w0.K0())) {
                return Boolean.TRUE;
            }
        }
        return null;
    }

    final void a(final C0759u2 c0759u2, final W0 w0) {
        a(new Consumer() { // from class: com.android.tools.r8.naming.G$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0759u2, w0, (G) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C0759u2 c0759u2, final W0 w0, G g) {
        g.c.forEach(new Consumer() { // from class: com.android.tools.r8.naming.G$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0759u2, w0, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0759u2 c0759u2, W0 w0, C0767v2 c0767v2) {
        this.d.e.b(c0767v2).a(c0759u2, w0);
    }

    final void a(final Consumer consumer) {
        Function function = new Function() { // from class: com.android.tools.r8.naming.G$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return G.a(consumer, (G) obj);
            }
        };
        if (function.apply(this) == null && a(function) == null) {
            b(function);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Consumer consumer, G g) {
        consumer.accept(g);
        return null;
    }

    private Object a(Function function) {
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            G g = (G) this.d.g.get((C0767v2) it.next());
            if (g != null) {
                Object objApply = function.apply(g);
                if (objApply != null) {
                    return objApply;
                }
                Object objA = g.a(function);
                if (objA != null) {
                    return objA;
                }
            }
        }
        return null;
    }
}
