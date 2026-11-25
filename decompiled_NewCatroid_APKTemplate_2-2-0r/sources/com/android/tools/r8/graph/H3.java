package com.android.tools.r8.graph;

import com.android.tools.r8.internal.CM;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H3 {
    private final C0702m a;
    private final Map b;

    /* JADX INFO: renamed from: $r8$lambda$wBZ5N9SAmxJLRX-vx8hIUeo_fgc, reason: not valid java name */
    public static /* synthetic */ ArrayList m514$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc() {
        return new ArrayList();
    }

    private H3(C0702m c0702m, IdentityHashMap identityHashMap) {
        this.a = c0702m;
        this.b = identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0) {
        return abstractC0727q0 != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Predicate predicate, Consumer consumer, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C == null || !predicate.test(abstractC0727q0C)) {
            return;
        }
        consumer.accept(abstractC0727q0C);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Predicate predicate, Consumer consumer, C0729q2 c0729q2) {
        if (predicate.test(c0729q2)) {
            consumer.accept(c0729q2);
        }
    }

    public static H3 a(final C0702m c0702m) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        for (final C0729q2 c0729q2 : ((C0680j) c0702m.f()).d()) {
            c0729q2.d(new Consumer() { // from class: com.android.tools.r8.graph.H3$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    H3.a(c0702m, identityHashMap, c0729q2, (C0767v2) obj);
                }
            });
        }
        return new H3(c0702m, identityHashMap);
    }

    public final void b(final Predicate predicate, final Consumer consumer, C0729q2 c0729q2) {
        a(c0729q2).forEach(new Consumer() { // from class: com.android.tools.r8.graph.H3$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                H3.c(predicate, consumer, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, Map map, C0729q2 c0729q2, C0767v2 c0767v2) {
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2));
        if (c0729q2A != null) {
            ((List) map.computeIfAbsent(c0729q2A, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.graph.H3$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    return H3.m514$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc();
                }
            }))).add(c0729q2);
        }
    }

    public final void a(final Consumer consumer, AbstractC0727q0 abstractC0727q0) {
        a(abstractC0727q0, new BiPredicate() { // from class: com.android.tools.r8.graph.H3$$ExternalSyntheticLambda3
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return H3.a((C0767v2) obj, (AbstractC0727q0) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.graph.H3$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.accept((AbstractC0727q0) obj2);
            }
        });
    }

    public final void a(AbstractC0727q0 abstractC0727q0, final BiPredicate biPredicate, final BiConsumer biConsumer) {
        abstractC0727q0.d(new Consumer() { // from class: com.android.tools.r8.graph.H3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(biPredicate, biConsumer, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(BiPredicate biPredicate, BiConsumer biConsumer, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (biPredicate.test(c0767v2, abstractC0727q0C)) {
            biConsumer.accept(c0767v2, abstractC0727q0C);
        }
    }

    public final void a(AbstractC0727q0 abstractC0727q0, final Predicate predicate, final Consumer consumer) {
        abstractC0727q0.d(new Consumer() { // from class: com.android.tools.r8.graph.H3$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(predicate, consumer, (C0767v2) obj);
            }
        });
    }

    public final void a(Consumer consumer, C0729q2 c0729q2) {
        a(CM.b(), consumer, c0729q2);
    }

    public final void a(final Predicate predicate, final Consumer consumer, C0729q2 c0729q2) {
        c0729q2.d(new Consumer() { // from class: com.android.tools.r8.graph.H3$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(predicate, consumer, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Predicate predicate, Consumer consumer, C0767v2 c0767v2) {
        C0729q2 c0729q2A = C0729q2.a(this.a.c(c0767v2));
        if (c0729q2A == null || !predicate.test(c0729q2A)) {
            return;
        }
        consumer.accept(c0729q2A);
    }

    public final List a(C0729q2 c0729q2) {
        return (List) this.b.getOrDefault(c0729q2, Collections.emptyList());
    }
}
