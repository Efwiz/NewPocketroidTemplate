package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2626nc extends AbstractC2151hN {
    static final /* synthetic */ boolean d = true;
    private final List a;
    private final com.android.tools.r8.utils.N b;
    private boolean c;

    private C2626nc(com.android.tools.r8.utils.N n, ArrayList arrayList) {
        this.c = false;
        this.a = arrayList;
        this.b = n;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(o5Var.getReference());
    }

    private boolean e() {
        return AbstractC2734ot.b(this.a, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.nc$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((C2511m3) obj).e();
            }
        });
    }

    private boolean f() {
        return this.b.a(new Predicate() { // from class: com.android.tools.r8.internal.nc$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C3215vW) obj).e();
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2151hN
    public final C2626nc a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2151hN
    public final void d() {
        if (!d && !this.c) {
            throw new AssertionError();
        }
    }

    final void a(Consumer consumer) {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            consumer.accept((C2511m3) it.next());
        }
        this.b.a(consumer);
    }

    public final void b(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        a(o5Var2.getReference(), new Consumer() { // from class: com.android.tools.r8.internal.nc$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2626nc.a(o5Var, (InterfaceC1997fN) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2151hN
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C2626nc a(final Function function) {
        com.android.tools.r8.utils.N n;
        ArrayList arrayList = new ArrayList(this.a.size());
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            arrayList.add((C2511m3) ((C2511m3) it.next()).a(function));
        }
        com.android.tools.r8.utils.N n2 = this.b;
        InterfaceC3910i3 interfaceC3910i3 = new InterfaceC3910i3() { // from class: com.android.tools.r8.internal.nc$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.InterfaceC3910i3
            public final Object apply(Object obj) {
                return C2626nc.a(function, (C3215vW) obj);
            }
        };
        if (n2.c()) {
            n = new com.android.tools.r8.utils.N(interfaceC3910i3.apply(n2.a()));
        } else {
            n = new com.android.tools.r8.utils.N();
        }
        return new C2626nc(n, arrayList);
    }

    @Override // com.android.tools.r8.internal.AbstractC2151hN
    public final void a(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        a(o5Var2.getReference(), new Consumer() { // from class: com.android.tools.r8.internal.nc$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2626nc.b(o5Var, (InterfaceC1997fN) obj);
            }
        });
    }

    C2626nc(AbstractC3104u3 abstractC3104u3, AbstractC3135uW abstractC3135uW) {
        this.c = false;
        this.a = new ArrayList();
        if (abstractC3104u3.c()) {
            Iterator it = abstractC3104u3.a().iterator();
            while (it.hasNext()) {
                this.a.add(new C2511m3((C2434l3) it.next()));
            }
            if (!d && this.a.isEmpty()) {
                throw new AssertionError();
            }
        }
        this.b = new com.android.tools.r8.utils.N(abstractC3135uW.c() ? null : new C3215vW(abstractC3135uW));
    }

    public final void a(AbstractC0748t0 abstractC0748t0, final com.android.tools.r8.graph.o5 o5Var) {
        abstractC0748t0.getClass();
        if (abstractC0748t0 instanceof com.android.tools.r8.graph.o5) {
            a(o5Var, abstractC0748t0.g0());
        } else {
            a(new Consumer() { // from class: com.android.tools.r8.internal.nc$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2626nc.a(o5Var, (AbstractC2069gN) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, AbstractC2069gN abstractC2069gN) {
        abstractC2069gN.a(o5Var, new C1609aN$$ExternalSyntheticLambda0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        InterfaceC1997fN interfaceC1997fNA = interfaceC1997fN.a(o5Var.getReference());
        C0729q2 c0729q2A = o5Var.a();
        interfaceC1997fNA.getClass();
        interfaceC1997fNA.a(c0729q2A.getReference());
    }

    final void a(final Consumer consumer, final C0729q2 c0729q2) {
        a(new Consumer() { // from class: com.android.tools.r8.internal.nc$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2626nc.a(c0729q2, consumer, (AbstractC2069gN) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0729q2 c0729q2, Consumer consumer, AbstractC2069gN abstractC2069gN) {
        abstractC2069gN.a(c0729q2.getType(), consumer);
    }

    @Override // com.android.tools.r8.internal.AbstractC2151hN
    public final void a(final C0684j2 c0684j2, final Consumer consumer) {
        a(new Consumer() { // from class: com.android.tools.r8.internal.nc$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC2069gN) obj).b(c0684j2, consumer);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2151hN
    public final void a(C0702m c0702m) {
        boolean z = d;
        if (!z && this.c) {
            throw new AssertionError();
        }
        if (e()) {
            if (!z && !e()) {
                throw new AssertionError();
            }
            ArrayList arrayList = new ArrayList(this.a.size());
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                arrayList.add((C2434l3) ((C2511m3) it.next()).b());
            }
            c0702m.a(new C3049tH(arrayList));
        }
        if (f()) {
            if (!d && !f()) {
                throw new AssertionError();
            }
            c0702m.a((AbstractC3135uW) ((C3215vW) this.b.a()).b());
        }
        this.c = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3215vW a(Function function, C3215vW c3215vW) {
        return (C3215vW) c3215vW.a(function);
    }

    @Override // com.android.tools.r8.internal.AbstractC2151hN
    public final AbstractC2151hN a(AbstractC3104u3 abstractC3104u3) {
        if (abstractC3104u3.c()) {
            Iterator it = abstractC3104u3.a().iterator();
            Iterator it2 = this.a.iterator();
            while (it2.hasNext()) {
                ((C2511m3) it2.next()).a = (C2434l3) it.next();
            }
        } else {
            boolean z = d;
            if (!z && !this.a.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !this.b.c()) {
                throw new AssertionError();
            }
        }
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2151hN
    public final AbstractC2151hN a(final AbstractC3135uW abstractC3135uW) {
        this.b.a(new Consumer() { // from class: com.android.tools.r8.internal.nc$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2626nc.a(abstractC3135uW, (C3215vW) obj);
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC3135uW abstractC3135uW, C3215vW c3215vW) {
        c3215vW.a = abstractC3135uW;
    }
}
