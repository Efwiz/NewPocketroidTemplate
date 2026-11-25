package com.android.tools.r8.utils;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.j0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3912j0 {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Object obj) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Object obj, Object obj2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Object obj) {
    }

    public static <T> InterfaceC3905h3<T, RuntimeException> c() {
        return new InterfaceC3905h3() { // from class: com.android.tools.r8.utils.j0$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                C3912j0.b(obj);
            }
        };
    }

    public static <T> Consumer<T> b() {
        return new Consumer() { // from class: com.android.tools.r8.utils.j0$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3912j0.a(obj);
            }
        };
    }

    public static Consumer a(final Consumer consumer, final Set set) {
        return new Consumer() { // from class: com.android.tools.r8.utils.j0$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3912j0.a(set, consumer, obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, Consumer consumer, Object obj) {
        if (set.add(obj)) {
            consumer.accept(obj);
        }
    }

    public static BiConsumer a(final BiFunction biFunction, final Consumer consumer) {
        return new BiConsumer() { // from class: com.android.tools.r8.utils.j0$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3912j0.a(consumer, biFunction, obj, obj2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, BiFunction biFunction, Object obj, Object obj2) {
        consumer.accept(biFunction.apply(obj, obj2));
    }

    public static BiConsumer a() {
        return new BiConsumer() { // from class: com.android.tools.r8.utils.j0$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3912j0.a(obj, obj2);
            }
        };
    }
}
