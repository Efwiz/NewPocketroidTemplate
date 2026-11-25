package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2069gN {
    static final /* synthetic */ boolean f = true;
    protected InterfaceC1882e1 a;
    final ConcurrentHashMap b = new ConcurrentHashMap();
    final ConcurrentHashMap c = new ConcurrentHashMap();
    private final Set d = OU.a();
    private final C1917eN e = new C1917eN();

    protected AbstractC2069gN(InterfaceC1882e1 interfaceC1882e1) {
        this.a = interfaceC1882e1;
    }

    public abstract AbstractC2069gN a();

    public abstract InterfaceC2125h1 a(C0684j2 c0684j2);

    public final void a(C0767v2 c0767v2, Consumer consumer) {
        if (this.a.a(c0767v2) || this.b.containsKey(c0767v2)) {
            consumer.accept(new C1608aN(this));
        }
    }

    public abstract InterfaceC1965f1 b(C0767v2 c0767v2);

    public final void b(C0684j2 c0684j2, Consumer consumer) {
        InterfaceC2197i1 interfaceC2197i1B = this.a.b(c0684j2);
        if (interfaceC2197i1B != null) {
            consumer.accept(new C1688bN(this, interfaceC2197i1B));
        } else if (this.c.containsKey(c0684j2)) {
            consumer.accept(new C1759cN(this, c0684j2));
        }
    }

    public abstract InterfaceC1809d1 c();

    public abstract Comparator d();

    public final boolean e() {
        return (this.b.isEmpty() && this.c.isEmpty()) ? false : true;
    }

    public abstract AbstractC2069gN f();

    public final void a(C0767v2 c0767v2) {
        if (this.a.a(c0767v2)) {
            return;
        }
        this.b.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.internal.gN$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C0767v2) obj);
            }
        });
    }

    public final void a(AbstractC0748t0 abstractC0748t0, Consumer consumer) {
        a(abstractC0748t0.getReference(), consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC2069gN a(C0684j2 c0684j2, Consumer consumer) {
        InterfaceC2125h1 interfaceC2125h1 = (InterfaceC2125h1) this.c.computeIfAbsent(c0684j2, new Function() { // from class: com.android.tools.r8.internal.gN$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0684j2) obj);
            }
        });
        synchronized (interfaceC2125h1) {
            consumer.accept(interfaceC2125h1);
        }
        return f();
    }

    final void a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var) {
        boolean z = f;
        if (!z && !this.a.a(c0684j2) && !this.c.containsKey(c0684j2)) {
            throw new AssertionError();
        }
        if (!z && !this.c.containsKey(o5Var.getReference())) {
            throw new AssertionError();
        }
        this.d.add(c0684j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(InterfaceC1809d1 interfaceC1809d1, InterfaceC2197i1 interfaceC2197i1) {
        if (this.d.contains(interfaceC2197i1.c())) {
            return;
        }
        InterfaceC2125h1 interfaceC2125h1 = (InterfaceC2125h1) this.c.remove(interfaceC2197i1.c());
        if (interfaceC2125h1 != null) {
            interfaceC1809d1.a(interfaceC2125h1.a(interfaceC2197i1).d());
        } else {
            interfaceC1809d1.a(interfaceC2197i1);
        }
    }

    public final InterfaceC1882e1 b() {
        if (!e()) {
            if (!f && !this.d.isEmpty()) {
                throw new AssertionError();
            }
            return this.a;
        }
        if (!f && !this.e.a()) {
            throw new AssertionError();
        }
        this.e.a(this.c);
        final InterfaceC1809d1 interfaceC1809d1C = c();
        InterfaceC1882e1 interfaceC1882e1 = this.a;
        Objects.requireNonNull(interfaceC1809d1C);
        interfaceC1882e1.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.gN$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                interfaceC1809d1C.a((InterfaceC2041g1) obj);
            }
        }, new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.gN$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a(interfaceC1809d1C, (InterfaceC2197i1) obj);
            }
        });
        final ArrayList arrayList = new ArrayList(this.c.size() + this.b.size());
        this.b.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.gN$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC2069gN.a(arrayList, (InterfaceC1965f1) obj);
            }
        });
        this.c.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.gN$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC2069gN.a(arrayList, (InterfaceC2125h1) obj);
            }
        });
        arrayList.sort(d());
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.gN$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC1809d1C.a((InterfaceC2275j1) obj);
            }
        });
        return interfaceC1809d1C.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, InterfaceC1965f1 interfaceC1965f1) {
        list.add(interfaceC1965f1.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, InterfaceC2125h1 interfaceC2125h1) {
        list.add(interfaceC2125h1.d());
    }

    public final AbstractC2069gN a(final Function function) {
        final AbstractC2069gN abstractC2069gNA = a();
        if (!f && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        abstractC2069gNA.b.putAll(this.b);
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.gN$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AbstractC2069gN.a(function, abstractC2069gNA, (C0684j2) obj, (InterfaceC2125h1) obj2);
            }
        });
        return abstractC2069gNA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Function function, AbstractC2069gN abstractC2069gN, C0684j2 c0684j2, InterfaceC2125h1 interfaceC2125h1) {
        C0684j2 c0684j3 = (C0684j2) function.apply(c0684j2);
        InterfaceC2125h1 interfaceC2125h2 = (InterfaceC2125h1) abstractC2069gN.c.put(c0684j3, interfaceC2125h1.a(c0684j3));
        if (!f && interfaceC2125h2 != null) {
            throw new AssertionError();
        }
    }
}
