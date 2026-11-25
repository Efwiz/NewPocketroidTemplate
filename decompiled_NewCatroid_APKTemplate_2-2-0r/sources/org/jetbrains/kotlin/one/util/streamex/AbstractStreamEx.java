package org.jetbrains.kotlin.one.util.streamex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractStreamEx<T, S extends AbstractStreamEx<T, S>> extends BaseStreamEx<T, Stream<T>, Spliterator<T>, S> implements Iterable<T>, Stream<T> {
    abstract S supply(Stream<T> stream);

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public /* bridge */ /* synthetic */ boolean isParallel() {
        return super.isParallel();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.Iterable
    public /* bridge */ /* synthetic */ Spliterator spliterator() {
        return super.spliterator();
    }

    AbstractStreamEx(Stream<? extends T> stream, StreamContext streamContext) {
        super(stream, streamContext);
    }

    AbstractStreamEx(Spliterator<? extends T> spliterator, StreamContext streamContext) {
        super(spliterator, streamContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx
    public final Stream<T> createStream() {
        return StreamSupport.stream(this.spliterator, this.context.parallel);
    }

    <R, A> R rawCollect(Collector<? super T, A, R> collector) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final Stream<T> stream = stream();
            Objects.requireNonNull(stream);
            return (R) streamContext.terminate(collector, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda14
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return stream.collect((Collector) obj);
                }
            });
        }
        return (R) stream().collect(collector);
    }

    @Override // java.util.stream.BaseStream, java.lang.Iterable
    public Iterator<T> iterator() {
        return Spliterators.iterator(spliterator());
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public S sequential() {
        return (S) super.sequential();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public S parallel() {
        return (S) super.parallel();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public S unordered() {
        return (S) super.unordered();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public S onClose(Runnable runnable) {
        return (S) super.onClose(runnable);
    }

    @Override // java.util.stream.Stream
    public S filter(Predicate<? super T> predicate) {
        return (S) supply(stream().filter(predicate));
    }

    @Override // java.util.stream.Stream
    public <R> StreamEx<R> flatMap(Function<? super T, ? extends Stream<? extends R>> function) {
        return new StreamEx<>(stream().flatMap(function), this.context);
    }

    @Override // java.util.stream.Stream
    public <R> StreamEx<R> map(Function<? super T, ? extends R> function) {
        return new StreamEx<>(stream().map(function), this.context);
    }

    @Override // java.util.stream.Stream
    public IntStreamEx mapToInt(ToIntFunction<? super T> toIntFunction) {
        return new IntStreamEx(stream().mapToInt(toIntFunction), this.context);
    }

    @Override // java.util.stream.Stream
    public LongStreamEx mapToLong(ToLongFunction<? super T> toLongFunction) {
        return new LongStreamEx(stream().mapToLong(toLongFunction), this.context);
    }

    @Override // java.util.stream.Stream
    public DoubleStreamEx mapToDouble(ToDoubleFunction<? super T> toDoubleFunction) {
        return new DoubleStreamEx(stream().mapToDouble(toDoubleFunction), this.context);
    }

    @Override // java.util.stream.Stream
    public IntStreamEx flatMapToInt(Function<? super T, ? extends IntStream> function) {
        return new IntStreamEx(stream().flatMapToInt(function), this.context);
    }

    @Override // java.util.stream.Stream
    public LongStreamEx flatMapToLong(Function<? super T, ? extends LongStream> function) {
        return new LongStreamEx(stream().flatMapToLong(function), this.context);
    }

    @Override // java.util.stream.Stream
    public DoubleStreamEx flatMapToDouble(Function<? super T, ? extends DoubleStream> function) {
        return new DoubleStreamEx(stream().flatMapToDouble(function), this.context);
    }

    @Override // java.util.stream.Stream
    public S distinct() {
        return (S) supply(stream().distinct());
    }

    @Override // java.util.stream.Stream
    public S sorted() {
        return (S) supply(stream().sorted());
    }

    @Override // java.util.stream.Stream
    public S sorted(Comparator<? super T> comparator) {
        return (S) supply(stream().sorted(comparator));
    }

    @Override // java.util.stream.Stream
    public S peek(Consumer<? super T> consumer) {
        return (S) supply(stream().peek(consumer));
    }

    @Override // java.util.stream.Stream
    public S limit(long j) {
        return (S) supply(stream().limit(j));
    }

    @Override // java.util.stream.Stream
    public S skip(long j) {
        return (S) supply(stream().skip(j));
    }

    @Override // java.lang.Iterable, java.util.stream.Stream
    public void forEach(final Consumer<? super T> consumer) {
        if (this.spliterator != 0 && !isParallel()) {
            spliterator().forEachRemaining(consumer);
        } else if (this.context.fjp != null) {
            this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7345xfa901e70(consumer);
                }
            });
        } else {
            stream().forEach(consumer);
        }
    }

    /* JADX INFO: renamed from: lambda$forEach$4$org-jetbrains-kotlin-one-util-streamex-AbstractStreamEx, reason: not valid java name */
    /* synthetic */ Object m7345xfa901e70(Consumer consumer) {
        stream().forEach(consumer);
        return null;
    }

    @Override // java.util.stream.Stream
    public void forEachOrdered(final Consumer<? super T> consumer) {
        if (this.spliterator != 0 && !isParallel()) {
            spliterator().forEachRemaining(consumer);
        } else if (this.context.fjp != null) {
            this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7346x91b7bc44(consumer);
                }
            });
        } else {
            stream().forEachOrdered(consumer);
        }
    }

    /* JADX INFO: renamed from: lambda$forEachOrdered$5$org-jetbrains-kotlin-one-util-streamex-AbstractStreamEx, reason: not valid java name */
    /* synthetic */ Object m7346x91b7bc44(Consumer consumer) {
        stream().forEachOrdered(consumer);
        return null;
    }

    static /* synthetic */ Object[] lambda$toArray$6(int i) {
        return new Object[i];
    }

    @Override // java.util.stream.Stream
    public Object[] toArray() {
        return toArray(new IntFunction() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda11
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return AbstractStreamEx.lambda$toArray$6(i);
            }
        });
    }

    @Override // java.util.stream.Stream
    public <A> A[] toArray(IntFunction<A[]> intFunction) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final Stream<T> stream = stream();
            Objects.requireNonNull(stream);
            return (A[]) ((Object[]) streamContext.terminate(intFunction, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return stream.toArray((IntFunction) obj);
                }
            }));
        }
        return (A[]) stream().toArray(intFunction);
    }

    @Override // java.util.stream.Stream
    public T reduce(final T t, final BinaryOperator<T> binaryOperator) {
        if (this.context.fjp != null) {
            return (T) this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7347xf67c3849(t, binaryOperator);
                }
            });
        }
        return stream().reduce(t, binaryOperator);
    }

    /* JADX INFO: renamed from: lambda$reduce$7$org-jetbrains-kotlin-one-util-streamex-AbstractStreamEx, reason: not valid java name */
    /* synthetic */ Object m7347xf67c3849(Object obj, BinaryOperator binaryOperator) {
        return stream().reduce(obj, binaryOperator);
    }

    @Override // java.util.stream.Stream
    public Optional<T> reduce(BinaryOperator<T> binaryOperator) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final Stream<T> stream = stream();
            Objects.requireNonNull(stream);
            return (Optional) streamContext.terminate(binaryOperator, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda12
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return stream.reduce((BinaryOperator) obj);
                }
            });
        }
        return stream().reduce(binaryOperator);
    }

    @Override // java.util.stream.Stream
    public <U> U reduce(final U u, final BiFunction<U, ? super T, U> biFunction, final BinaryOperator<U> binaryOperator) {
        if (this.context.fjp != null) {
            return (U) this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7348x1c10414a(u, biFunction, binaryOperator);
                }
            });
        }
        return (U) stream().reduce(u, biFunction, binaryOperator);
    }

    /* JADX INFO: renamed from: lambda$reduce$8$org-jetbrains-kotlin-one-util-streamex-AbstractStreamEx, reason: not valid java name */
    /* synthetic */ Object m7348x1c10414a(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        return stream().reduce(obj, biFunction, binaryOperator);
    }

    @Override // java.util.stream.Stream
    public <R> R collect(final Supplier<R> supplier, final BiConsumer<R, ? super T> biConsumer, final BiConsumer<R, R> biConsumer2) {
        if (this.context.fjp != null) {
            return (R) this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda9
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7344x5fb7e635(supplier, biConsumer, biConsumer2);
                }
            });
        }
        return (R) stream().collect(supplier, biConsumer, biConsumer2);
    }

    /* JADX INFO: renamed from: lambda$collect$9$org-jetbrains-kotlin-one-util-streamex-AbstractStreamEx, reason: not valid java name */
    /* synthetic */ Object m7344x5fb7e635(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        return stream().collect(supplier, biConsumer, biConsumer2);
    }

    @Override // java.util.stream.Stream
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        Spliterator unorderedCancellableSpliterator;
        final Predicate predicateFinished = Internals.finished(collector);
        if (predicateFinished != null) {
            final BiConsumer<A, ? super T> biConsumerAccumulator = collector.accumulator();
            BinaryOperator<A> binaryOperatorCombiner = collector.combiner();
            Spliterator spliterator = spliterator();
            if (!isParallel()) {
                final A a = collector.supplier().get();
                if (!predicateFinished.test(a)) {
                    try {
                        spliterator.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AbstractStreamEx.lambda$collect$10(biConsumerAccumulator, a, predicateFinished, obj);
                            }
                        });
                    } catch (Internals.CancelException unused) {
                    }
                }
                return collector.finisher().apply(a);
            }
            if (!spliterator.hasCharacteristics(16) || collector.characteristics().contains(Collector.Characteristics.UNORDERED)) {
                unorderedCancellableSpliterator = new UnorderedCancellableSpliterator(spliterator, collector.supplier(), biConsumerAccumulator, binaryOperatorCombiner, predicateFinished);
            } else {
                unorderedCancellableSpliterator = new OrderedCancellableSpliterator(spliterator, collector.supplier(), biConsumerAccumulator, binaryOperatorCombiner, predicateFinished);
            }
            return (R) collector.finisher().apply(new StreamEx(StreamSupport.stream(unorderedCancellableSpliterator, true), this.context).findFirst().get());
        }
        return (R) rawCollect(collector);
    }

    static /* synthetic */ void lambda$collect$10(BiConsumer biConsumer, Object obj, Predicate predicate, Object obj2) {
        biConsumer.accept(obj, obj2);
        if (predicate.test(obj)) {
            throw new Internals.CancelException();
        }
    }

    @Override // java.util.stream.Stream
    public Optional<T> min(Comparator<? super T> comparator) {
        return reduce(BinaryOperator.minBy(comparator));
    }

    @Override // java.util.stream.Stream
    public Optional<T> max(Comparator<? super T> comparator) {
        return reduce(BinaryOperator.maxBy(comparator));
    }

    @Override // java.util.stream.Stream
    public long count() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final Stream<T> stream = stream();
            Objects.requireNonNull(stream);
            return ((Long) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Long.valueOf(stream.count());
                }
            })).longValue();
        }
        return stream().count();
    }

    @Override // java.util.stream.Stream
    public boolean anyMatch(Predicate<? super T> predicate) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final Stream<T> stream = stream();
            Objects.requireNonNull(stream);
            return ((Boolean) streamContext.terminate(predicate, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda16
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(stream.anyMatch((Predicate) obj));
                }
            })).booleanValue();
        }
        return stream().anyMatch(predicate);
    }

    @Override // java.util.stream.Stream
    public boolean allMatch(Predicate<? super T> predicate) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final Stream<T> stream = stream();
            Objects.requireNonNull(stream);
            return ((Boolean) streamContext.terminate(predicate, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(stream.allMatch((Predicate) obj));
                }
            })).booleanValue();
        }
        return stream().allMatch(predicate);
    }

    @Override // java.util.stream.Stream
    public boolean noneMatch(Predicate<? super T> predicate) {
        return !anyMatch(predicate);
    }

    @Override // java.util.stream.Stream
    public Optional<T> findFirst() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final Stream<T> stream = stream();
            Objects.requireNonNull(stream);
            return (Optional) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final Object get() {
                    return stream.findFirst();
                }
            });
        }
        return stream().findFirst();
    }

    @Override // java.util.stream.Stream
    public Optional<T> findAny() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final Stream<T> stream = stream();
            Objects.requireNonNull(stream);
            return (Optional) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda13
                @Override // java.util.function.Supplier
                public final Object get() {
                    return stream.findAny();
                }
            });
        }
        return stream().findAny();
    }

    public <R> StreamEx<R> flatArray(final Function<? super T, ? extends R[]> function) {
        return flatMap((Function) new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractStreamEx.lambda$flatArray$17(function, obj);
            }
        });
    }

    static /* synthetic */ Stream lambda$flatArray$17(Function function, Object obj) {
        Object[] objArr = (Object[]) function.apply(obj);
        if (objArr == null) {
            return null;
        }
        return StreamEx.of(Arrays.spliterator(objArr));
    }

    static /* synthetic */ Object[] lambda$toList$35(int i) {
        return new Object[i];
    }

    @Override // java.util.stream.Stream
    public List<T> toList() {
        return new ArrayList(new Internals.ArrayCollection(toArray(new IntFunction() { // from class: org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx$$ExternalSyntheticLambda7
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return AbstractStreamEx.lambda$toList$35(i);
            }
        })));
    }
}
