package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0593a0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S1 {
    private static final S1 b = new S1(Collections.emptyMap());
    static final /* synthetic */ boolean c = true;
    private final Map a;

    public S1() {
        this(new ConcurrentHashMap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC0744s2 a(AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, c0767v2);
        if (!c0767v2A.W0()) {
            return c0767v2A;
        }
        boolean z = c;
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        if (z || c0767v2A.R0()) {
            return null;
        }
        throw new AssertionError();
    }

    private S1(Map map) {
        this.a = map;
    }

    public final boolean b() {
        return this.a.isEmpty();
    }

    public static S1 a() {
        return b;
    }

    public final void a(final com.android.tools.r8.graph.Q0 q0, final BiConsumer biConsumer, final BiConsumer biConsumer2, final BiConsumer biConsumer3) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda12
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                S1.a(q0, biConsumer, biConsumer2, biConsumer3, (AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.Q0 q0, final BiConsumer biConsumer, final BiConsumer biConsumer2, final BiConsumer biConsumer3, AbstractC0744s2 abstractC0744s2, final AbstractC3716f1 abstractC3716f1) {
        final C0729q2 c0729q2A = C0729q2.a(q0.c(abstractC0744s2.C()));
        if (c0729q2A != null) {
            abstractC0744s2.a(new Consumer() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    S1.a(biConsumer, c0729q2A, abstractC3716f1, (C0767v2) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    S1.a(c0729q2A, biConsumer2, abstractC3716f1, (com.android.tools.r8.graph.Y0) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    S1.a(c0729q2A, biConsumer3, abstractC3716f1, (C0684j2) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, C0729q2 c0729q2, AbstractC3716f1 abstractC3716f1, C0767v2 c0767v2) {
        biConsumer.accept(c0729q2, abstractC3716f1.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0729q2 c0729q2, BiConsumer biConsumer, AbstractC3716f1 abstractC3716f1, com.android.tools.r8.graph.Y0 y0) {
        m5 m5VarD = c0729q2.d(y0);
        if (m5VarD != null) {
            biConsumer.accept(m5VarD, abstractC3716f1.b());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0729q2 c0729q2, BiConsumer biConsumer, AbstractC3716f1 abstractC3716f1, C0684j2 c0684j2) {
        o5 o5VarF = c0729q2.f(c0684j2);
        if (o5VarF != null) {
            biConsumer.accept(o5VarF, abstractC3716f1.c());
        }
    }

    public final void a(final BiPredicate biPredicate, final BiConsumer biConsumer) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                S1.a(biPredicate, biConsumer, (AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiPredicate biPredicate, BiConsumer biConsumer, AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        if (biPredicate.test(abstractC0744s2, abstractC3716f1)) {
            biConsumer.accept(abstractC0744s2, abstractC3716f1);
        }
    }

    public final AbstractC3716f1 a(AbstractC0744s2 abstractC0744s2, C3754l1 c3754l1) {
        return (AbstractC3716f1) this.a.getOrDefault(abstractC0744s2, c3754l1);
    }

    public final AbstractC3716f1 a(final AbstractC0744s2 abstractC0744s2) {
        return (AbstractC3716f1) this.a.computeIfAbsent(abstractC0744s2, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return AbstractC3722g1.a(abstractC0744s2);
            }
        }));
    }

    public final boolean a(AbstractC0744s2 abstractC0744s2, Predicate predicate) {
        AbstractC3716f1 abstractC3716f1 = (AbstractC3716f1) this.a.get(abstractC0744s2);
        return abstractC3716f1 != null && predicate.test(abstractC3716f1);
    }

    public final void a(S1 s1) {
        s1.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        });
    }

    public final void a(AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        a(abstractC0744s2).a(abstractC3716f1);
    }

    public final void a(final com.android.tools.r8.graph.Q0 q0, final L l) {
        com.android.tools.r8.utils.Z1.a(this.a, new BiPredicate() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda10
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return S1.a(q0, l, (AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(final com.android.tools.r8.graph.Q0 q0, L l, AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        if (!c && abstractC3716f1.j()) {
            throw new AssertionError();
        }
        InterfaceC0694k5 interfaceC0694k5 = (InterfaceC0694k5) abstractC0744s2.a(new Function() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return S1.a(q0, (C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return S1.a(q0, (com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return S1.a(q0, (C0684j2) obj);
            }
        });
        return interfaceC0694k5 == null || !l.a((InterfaceC0593a0) interfaceC0694k5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ InterfaceC0694k5 a(com.android.tools.r8.graph.Q0 q0, C0767v2 c0767v2) {
        return C0729q2.a(q0.c(c0767v2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ InterfaceC0694k5 a(com.android.tools.r8.graph.Q0 q0, com.android.tools.r8.graph.Y0 y0) {
        return y0.a(C0729q2.a(q0.c(y0.B0())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ InterfaceC0694k5 a(com.android.tools.r8.graph.Q0 q0, C0684j2 c0684j2) {
        return c0684j2.a(C0729q2.a(q0.c(c0684j2.B0())));
    }

    public final void a(final u5 u5Var) {
        Set setKeySet = this.a.keySet();
        Objects.requireNonNull(u5Var);
        setKeySet.removeIf(new Predicate() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return u5Var.a((AbstractC0744s2) obj);
            }
        });
    }

    public final X0 a(C0767v2 c0767v2) {
        return (X0) this.a.remove(c0767v2);
    }

    public final C3698c1 a(com.android.tools.r8.graph.Y0 y0) {
        return (C3698c1) this.a.remove(y0);
    }

    public final C3754l1 a(C0684j2 c0684j2) {
        return (C3754l1) this.a.remove(c0684j2);
    }

    public final S1 a(final AbstractC1476Wj abstractC1476Wj) {
        final S1 s1 = new S1();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                S1.a(abstractC1476Wj, s1, (AbstractC0744s2) obj, (AbstractC3716f1) obj2);
            }
        });
        return s1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final AbstractC1476Wj abstractC1476Wj, S1 s1, AbstractC0744s2 abstractC0744s2, AbstractC3716f1 abstractC3716f1) {
        Function function = new Function() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return S1.a(abstractC1476Wj, (C0767v2) obj);
            }
        };
        Objects.requireNonNull(abstractC1476Wj);
        AbstractC0744s2 abstractC0744s3 = (AbstractC0744s2) abstractC0744s2.a(function, new Function() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return abstractC1476Wj.b((com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.S1$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return abstractC1476Wj.c((C0684j2) obj);
            }
        });
        if (abstractC0744s3 != null) {
            s1.a(abstractC0744s3).a(abstractC3716f1);
        }
    }
}
