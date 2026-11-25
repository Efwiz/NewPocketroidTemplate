package org.jetbrains.kotlin.one.util.streamex;

import java.util.ArrayDeque;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
final class OrderedCancellableSpliterator<T, A> extends Internals.CloneableSpliterator<A, OrderedCancellableSpliterator<T, A>> {
    private final BiConsumer<A, ? super T> accumulator;
    private final Predicate<A> cancelPredicate;
    private final BinaryOperator<A> combiner;
    private volatile boolean localCancelled;
    private final Object lock = new Object();
    private A payload;
    private volatile OrderedCancellableSpliterator<T, A> prefix;
    private Spliterator<T> source;
    private volatile OrderedCancellableSpliterator<T, A> suffix;
    private final Supplier<A> supplier;

    OrderedCancellableSpliterator(Spliterator<T> spliterator, Supplier<A> supplier, BiConsumer<A, ? super T> biConsumer, BinaryOperator<A> binaryOperator, Predicate<A> predicate) {
        this.source = spliterator;
        this.supplier = supplier;
        this.accumulator = biConsumer;
        this.combiner = binaryOperator;
        this.cancelPredicate = predicate;
    }

    @Override // java.util.Spliterator
    public boolean tryAdvance(Consumer<? super A> consumer) {
        Spliterator<T> spliterator = this.source;
        if (spliterator == null || this.localCancelled) {
            this.source = null;
            return false;
        }
        final A aPollFirst = this.supplier.get();
        try {
            spliterator.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.one.util.streamex.OrderedCancellableSpliterator$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m7368x4a32e7b0(aPollFirst, obj);
                }
            });
            while (true) {
                if (this.prefix == null && this.suffix == null) {
                    consumer.accept(aPollFirst);
                    return true;
                }
                ArrayDeque arrayDeque = new ArrayDeque();
                arrayDeque.offer(aPollFirst);
                synchronized (this.lock) {
                    if (this.localCancelled) {
                        return false;
                    }
                    OrderedCancellableSpliterator<T, A> orderedCancellableSpliterator = this.prefix;
                    while (orderedCancellableSpliterator != null) {
                        A a = orderedCancellableSpliterator.payload;
                        if (a == null) {
                            break;
                        }
                        arrayDeque.offerFirst(a);
                        orderedCancellableSpliterator = orderedCancellableSpliterator.prefix;
                    }
                    this.prefix = orderedCancellableSpliterator;
                    if (orderedCancellableSpliterator != null) {
                        orderedCancellableSpliterator.suffix = this;
                    }
                    OrderedCancellableSpliterator<T, A> orderedCancellableSpliterator2 = this.suffix;
                    while (orderedCancellableSpliterator2 != null) {
                        A a2 = orderedCancellableSpliterator2.payload;
                        if (a2 == null) {
                            break;
                        }
                        arrayDeque.offerLast(a2);
                        orderedCancellableSpliterator2 = orderedCancellableSpliterator2.suffix;
                    }
                    this.suffix = orderedCancellableSpliterator2;
                    if (orderedCancellableSpliterator2 != null) {
                        orderedCancellableSpliterator2.prefix = this;
                    }
                    if (arrayDeque.size() == 1) {
                        if (this.prefix == null && this.suffix == null) {
                            consumer.accept(aPollFirst);
                            return true;
                        }
                        this.payload = (A) aPollFirst;
                        return false;
                    }
                }
                aPollFirst = arrayDeque.pollFirst();
                while (!arrayDeque.isEmpty()) {
                    aPollFirst = (A) this.combiner.apply((A) aPollFirst, arrayDeque.pollFirst());
                    if (this.cancelPredicate.test((A) aPollFirst)) {
                        cancelSuffix();
                    }
                }
            }
        } catch (Internals.CancelException unused) {
            if (this.localCancelled) {
                return false;
            }
        }
        this.source = null;
    }

    /* JADX INFO: renamed from: lambda$tryAdvance$0$org-jetbrains-kotlin-one-util-streamex-OrderedCancellableSpliterator, reason: not valid java name */
    /* synthetic */ void m7368x4a32e7b0(Object obj, Object obj2) {
        this.accumulator.accept(obj, obj2);
        if (this.cancelPredicate.test(obj)) {
            cancelSuffix();
            throw new Internals.CancelException();
        }
        if (this.localCancelled) {
            throw new Internals.CancelException();
        }
    }

    private void cancelSuffix() {
        if (this.suffix == null) {
            return;
        }
        synchronized (this.lock) {
            for (OrderedCancellableSpliterator<T, A> orderedCancellableSpliterator = this.suffix; orderedCancellableSpliterator != null && !orderedCancellableSpliterator.localCancelled; orderedCancellableSpliterator = orderedCancellableSpliterator.suffix) {
                orderedCancellableSpliterator.prefix = null;
                orderedCancellableSpliterator.localCancelled = true;
            }
            this.suffix = null;
        }
    }

    @Override // java.util.Spliterator
    public void forEachRemaining(Consumer<? super A> consumer) {
        tryAdvance(consumer);
    }

    @Override // java.util.Spliterator
    public Spliterator<A> trySplit() {
        OrderedCancellableSpliterator<T, A> orderedCancellableSpliteratorDoClone;
        if (this.source == null || this.localCancelled) {
            this.source = null;
            return null;
        }
        Spliterator<T> spliteratorTrySplit = this.source.trySplit();
        if (spliteratorTrySplit == null) {
            return null;
        }
        synchronized (this.lock) {
            orderedCancellableSpliteratorDoClone = doClone();
            orderedCancellableSpliteratorDoClone.source = spliteratorTrySplit;
            this.prefix = orderedCancellableSpliteratorDoClone;
            orderedCancellableSpliteratorDoClone.suffix = this;
            OrderedCancellableSpliterator<T, A> orderedCancellableSpliterator = orderedCancellableSpliteratorDoClone.prefix;
            if (orderedCancellableSpliterator != null) {
                orderedCancellableSpliterator.suffix = orderedCancellableSpliteratorDoClone;
            }
        }
        return orderedCancellableSpliteratorDoClone;
    }

    @Override // java.util.Spliterator
    public long estimateSize() {
        Spliterator<T> spliterator = this.source;
        if (spliterator == null) {
            return 0L;
        }
        return spliterator.estimateSize();
    }

    @Override // java.util.Spliterator
    public int characteristics() {
        return this.source == null ? 64 : 16;
    }
}
