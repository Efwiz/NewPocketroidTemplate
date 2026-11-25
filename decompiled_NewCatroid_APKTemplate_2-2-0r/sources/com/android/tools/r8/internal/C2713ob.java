package com.android.tools.r8.internal;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ob, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2713ob implements Spliterator {
    final /* synthetic */ Spliterator a;
    final /* synthetic */ Function b;

    C2713ob(Spliterator spliterator, Function function) {
        this.a = spliterator;
        this.b = function;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, Function function, Object obj) {
        consumer.accept(function.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Consumer consumer, Function function, Object obj) {
        consumer.accept(function.apply(obj));
    }

    @Override // java.util.Spliterator
    public final int characteristics() {
        return this.a.characteristics() & (-262);
    }

    @Override // java.util.Spliterator
    public final long estimateSize() {
        return this.a.estimateSize();
    }

    @Override // java.util.Spliterator
    public final void forEachRemaining(final Consumer consumer) {
        Spliterator spliterator = this.a;
        final Function function = this.b;
        spliterator.forEachRemaining(new Consumer() { // from class: com.android.tools.r8.internal.ob$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2713ob.a(consumer, function, obj);
            }
        });
    }

    @Override // java.util.Spliterator
    public final boolean tryAdvance(final Consumer consumer) {
        Spliterator spliterator = this.a;
        final Function function = this.b;
        return spliterator.tryAdvance(new Consumer() { // from class: com.android.tools.r8.internal.ob$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2713ob.b(consumer, function, obj);
            }
        });
    }

    @Override // java.util.Spliterator
    public final Spliterator trySplit() {
        Spliterator spliteratorTrySplit = this.a.trySplit();
        if (spliteratorTrySplit != null) {
            return AbstractC3142ub.a(spliteratorTrySplit, this.b);
        }
        return null;
    }
}
