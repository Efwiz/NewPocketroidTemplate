package org.jetbrains.kotlin.one.util.streamex;

import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
class UnorderedCancellableSpliterator<T, A> extends Internals.CloneableSpliterator<A, UnorderedCancellableSpliterator<T, A>> {
    private final BiConsumer<A, ? super T> accumulator;
    private final Predicate<A> cancelPredicate;
    private final BinaryOperator<A> combiner;
    private boolean flag;
    private volatile Spliterator<T> source;
    private final Supplier<A> supplier;
    private final ConcurrentLinkedQueue<A> partialResults = new ConcurrentLinkedQueue<>();
    private final AtomicBoolean cancelled = new AtomicBoolean(false);
    private final AtomicInteger nPeers = new AtomicInteger(1);

    UnorderedCancellableSpliterator(Spliterator<T> spliterator, Supplier<A> supplier, BiConsumer<A, ? super T> biConsumer, BinaryOperator<A> binaryOperator, Predicate<A> predicate) {
        this.source = spliterator;
        this.supplier = supplier;
        this.accumulator = biConsumer;
        this.combiner = binaryOperator;
        this.cancelPredicate = predicate;
    }

    private boolean checkCancel(A a) {
        if (this.cancelPredicate.test(a) && this.cancelled.compareAndSet(false, true)) {
            this.flag = true;
            return true;
        }
        if (!this.cancelled.get()) {
            return false;
        }
        this.flag = false;
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean handleCancel(Consumer<? super A> consumer, A a) {
        this.source = null;
        if (!this.flag) {
            return false;
        }
        consumer.accept(a);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Spliterator
    public boolean tryAdvance(Consumer<? super A> consumer) {
        Spliterator<T> spliterator = this.source;
        if (spliterator == null || this.cancelled.get()) {
            this.source = null;
            return false;
        }
        final Object objApply = this.supplier.get();
        if (checkCancel(objApply)) {
            return handleCancel(consumer, objApply);
        }
        try {
            spliterator.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.one.util.streamex.UnorderedCancellableSpliterator$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m7369x989fb737(objApply, obj);
                }
            });
            do {
                A aPoll = this.partialResults.poll();
                if (aPoll != null) {
                    objApply = this.combiner.apply((A) objApply, aPoll);
                } else {
                    this.partialResults.offer((A) objApply);
                    this.source = null;
                    if (this.nPeers.decrementAndGet() != 0) {
                        return false;
                    }
                    A aPoll2 = this.partialResults.poll();
                    do {
                        A aPoll3 = this.partialResults.poll();
                        if (aPoll3 == null) {
                            break;
                        }
                        aPoll2 = (Object) this.combiner.apply(aPoll2, aPoll3);
                    } while (!this.cancelPredicate.test(aPoll2));
                    this.source = null;
                    consumer.accept(aPoll2);
                    return true;
                }
            } while (!checkCancel(objApply));
            return handleCancel(consumer, objApply);
        } catch (Internals.CancelException unused) {
            return handleCancel(consumer, objApply);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$tryAdvance$0$org-jetbrains-kotlin-one-util-streamex-UnorderedCancellableSpliterator, reason: not valid java name */
    /* synthetic */ void m7369x989fb737(Object obj, Object obj2) {
        this.accumulator.accept(obj, obj2);
        if (checkCancel(obj)) {
            throw new Internals.CancelException();
        }
    }

    @Override // java.util.Spliterator
    public void forEachRemaining(Consumer<? super A> consumer) {
        tryAdvance(consumer);
    }

    @Override // java.util.Spliterator
    public Spliterator<A> trySplit() {
        if (this.source == null || this.cancelled.get()) {
            this.source = null;
            return null;
        }
        Spliterator<T> spliteratorTrySplit = this.source.trySplit();
        if (spliteratorTrySplit == null) {
            return null;
        }
        UnorderedCancellableSpliterator<T, A> unorderedCancellableSpliteratorDoClone = doClone();
        unorderedCancellableSpliteratorDoClone.source = spliteratorTrySplit;
        this.nPeers.incrementAndGet();
        return unorderedCancellableSpliteratorDoClone;
    }

    @Override // java.util.Spliterator
    public long estimateSize() {
        if (this.source == null) {
            return 0L;
        }
        return this.source.estimateSize();
    }

    @Override // java.util.Spliterator
    public int characteristics() {
        return this.source == null ? 64 : 0;
    }
}
