package org.jetbrains.kotlin.one.util.streamex;

import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/* JADX INFO: loaded from: classes2.dex */
public class IntStreamEx extends BaseStreamEx<Integer, IntStream, Spliterator.OfInt, IntStreamEx> implements IntStream {
    public static /* synthetic */ IntSummaryStatistics $r8$lambda$2tYk9Y1gCJd8JAPeXVOiqZDA9BA() {
        return new IntSummaryStatistics();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // java.util.stream.IntStream
    public /* bridge */ /* synthetic */ IntStream flatMap(IntFunction intFunction) {
        return flatMap((IntFunction<? extends IntStream>) intFunction);
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public /* bridge */ /* synthetic */ boolean isParallel() {
        return super.isParallel();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.Iterable
    public /* bridge */ /* synthetic */ Spliterator.OfInt spliterator() {
        return (Spliterator.OfInt) super.spliterator();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.Iterable
    public /* bridge */ /* synthetic */ Spliterator spliterator() {
        return super.spliterator();
    }

    IntStreamEx(IntStream intStream, StreamContext streamContext) {
        super(intStream, streamContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx
    public IntStream createStream() {
        return StreamSupport.intStream((Spliterator.OfInt) this.spliterator, this.context.parallel);
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public IntStreamEx unordered() {
        return (IntStreamEx) super.unordered();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public IntStreamEx onClose(Runnable runnable) {
        return (IntStreamEx) super.onClose(runnable);
    }

    @Override // java.util.stream.IntStream
    public IntStreamEx filter(IntPredicate intPredicate) {
        return new IntStreamEx(stream().filter(intPredicate), this.context);
    }

    @Override // java.util.stream.IntStream
    public IntStreamEx map(IntUnaryOperator intUnaryOperator) {
        return new IntStreamEx(stream().map(intUnaryOperator), this.context);
    }

    @Override // java.util.stream.IntStream
    public <U> StreamEx<U> mapToObj(IntFunction<? extends U> intFunction) {
        return new StreamEx<>(stream().mapToObj(intFunction), this.context);
    }

    @Override // java.util.stream.IntStream
    public LongStreamEx mapToLong(IntToLongFunction intToLongFunction) {
        return new LongStreamEx(stream().mapToLong(intToLongFunction), this.context);
    }

    @Override // java.util.stream.IntStream
    public DoubleStreamEx mapToDouble(IntToDoubleFunction intToDoubleFunction) {
        return new DoubleStreamEx(stream().mapToDouble(intToDoubleFunction), this.context);
    }

    @Override // java.util.stream.IntStream
    public IntStreamEx flatMap(IntFunction<? extends IntStream> intFunction) {
        return new IntStreamEx(stream().flatMap(intFunction), this.context);
    }

    @Override // java.util.stream.IntStream
    public IntStreamEx distinct() {
        return new IntStreamEx(stream().distinct(), this.context);
    }

    @Override // java.util.stream.IntStream
    public IntStreamEx sorted() {
        return new IntStreamEx(stream().sorted(), this.context);
    }

    @Override // java.util.stream.IntStream
    public IntStreamEx peek(IntConsumer intConsumer) {
        return new IntStreamEx(stream().peek(intConsumer), this.context);
    }

    @Override // java.util.stream.IntStream
    public IntStreamEx limit(long j) {
        return new IntStreamEx(stream().limit(j), this.context);
    }

    @Override // java.util.stream.IntStream
    public IntStreamEx skip(long j) {
        return new IntStreamEx(stream().skip(j), this.context);
    }

    @Override // java.util.stream.IntStream
    public void forEach(final IntConsumer intConsumer) {
        if (this.spliterator != 0 && !isParallel()) {
            ((Spliterator.OfInt) spliterator()).forEachRemaining(intConsumer);
        } else if (this.context.fjp != null) {
            this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7359x30da11ca(intConsumer);
                }
            });
        } else {
            stream().forEach(intConsumer);
        }
    }

    /* JADX INFO: renamed from: lambda$forEach$14$org-jetbrains-kotlin-one-util-streamex-IntStreamEx, reason: not valid java name */
    /* synthetic */ Object m7359x30da11ca(IntConsumer intConsumer) {
        stream().forEach(intConsumer);
        return null;
    }

    @Override // java.util.stream.IntStream
    public void forEachOrdered(final IntConsumer intConsumer) {
        if (this.spliterator != 0 && !isParallel()) {
            ((Spliterator.OfInt) spliterator()).forEachRemaining(intConsumer);
        } else if (this.context.fjp != null) {
            this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7360x571fa27c(intConsumer);
                }
            });
        } else {
            stream().forEachOrdered(intConsumer);
        }
    }

    /* JADX INFO: renamed from: lambda$forEachOrdered$15$org-jetbrains-kotlin-one-util-streamex-IntStreamEx, reason: not valid java name */
    /* synthetic */ Object m7360x571fa27c(IntConsumer intConsumer) {
        stream().forEachOrdered(intConsumer);
        return null;
    }

    @Override // java.util.stream.IntStream
    public int[] toArray() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final IntStream intStreamStream = stream();
            Objects.requireNonNull(intStreamStream);
            return (int[]) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    return intStreamStream.toArray();
                }
            });
        }
        return stream().toArray();
    }

    @Override // java.util.stream.IntStream
    public int reduce(final int i, final IntBinaryOperator intBinaryOperator) {
        if (this.context.fjp != null) {
            return ((Integer) this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7361x3808205e(i, intBinaryOperator);
                }
            })).intValue();
        }
        return stream().reduce(i, intBinaryOperator);
    }

    /* JADX INFO: renamed from: lambda$reduce$16$org-jetbrains-kotlin-one-util-streamex-IntStreamEx, reason: not valid java name */
    /* synthetic */ Integer m7361x3808205e(int i, IntBinaryOperator intBinaryOperator) {
        return Integer.valueOf(stream().reduce(i, intBinaryOperator));
    }

    @Override // java.util.stream.IntStream
    public OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final IntStream intStreamStream = stream();
            Objects.requireNonNull(intStreamStream);
            return (OptionalInt) streamContext.terminate(intBinaryOperator, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return intStreamStream.reduce((IntBinaryOperator) obj);
                }
            });
        }
        return stream().reduce(intBinaryOperator);
    }

    @Override // java.util.stream.IntStream
    public <R> R collect(final Supplier<R> supplier, final ObjIntConsumer<R> objIntConsumer, final BiConsumer<R, R> biConsumer) {
        if (this.context.fjp != null) {
            return (R) this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda9
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7358x7171548f(supplier, objIntConsumer, biConsumer);
                }
            });
        }
        return (R) stream().collect(supplier, objIntConsumer, biConsumer);
    }

    /* JADX INFO: renamed from: lambda$collect$20$org-jetbrains-kotlin-one-util-streamex-IntStreamEx, reason: not valid java name */
    /* synthetic */ Object m7358x7171548f(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer) {
        return stream().collect(supplier, objIntConsumer, biConsumer);
    }

    @Override // java.util.stream.IntStream
    public int sum() {
        return reduce(0, new IntBinaryOperator() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda0
            @Override // java.util.function.IntBinaryOperator
            public final int applyAsInt(int i, int i2) {
                return Integer.sum(i, i2);
            }
        });
    }

    @Override // java.util.stream.IntStream
    public OptionalInt min() {
        return reduce(new IntBinaryOperator() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda13
            @Override // java.util.function.IntBinaryOperator
            public final int applyAsInt(int i, int i2) {
                return Integer.min(i, i2);
            }
        });
    }

    @Override // java.util.stream.IntStream
    public OptionalInt max() {
        return reduce(new IntBinaryOperator() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda7
            @Override // java.util.function.IntBinaryOperator
            public final int applyAsInt(int i, int i2) {
                return Integer.max(i, i2);
            }
        });
    }

    @Override // java.util.stream.IntStream
    public long count() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final IntStream intStreamStream = stream();
            Objects.requireNonNull(intStreamStream);
            return ((Long) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Long.valueOf(intStreamStream.count());
                }
            })).longValue();
        }
        return stream().count();
    }

    @Override // java.util.stream.IntStream
    public OptionalDouble average() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final IntStream intStreamStream = stream();
            Objects.requireNonNull(intStreamStream);
            return (OptionalDouble) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return intStreamStream.average();
                }
            });
        }
        return stream().average();
    }

    @Override // java.util.stream.IntStream
    public IntSummaryStatistics summaryStatistics() {
        return (IntSummaryStatistics) collect(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda16
            @Override // java.util.function.Supplier
            public final Object get() {
                return IntStreamEx.$r8$lambda$2tYk9Y1gCJd8JAPeXVOiqZDA9BA();
            }
        }, new ObjIntConsumer() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda17
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                ((IntSummaryStatistics) obj).accept(i);
            }
        }, new BiConsumer() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((IntSummaryStatistics) obj).combine((IntSummaryStatistics) obj2);
            }
        });
    }

    @Override // java.util.stream.IntStream
    public boolean anyMatch(IntPredicate intPredicate) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final IntStream intStreamStream = stream();
            Objects.requireNonNull(intStreamStream);
            return ((Boolean) streamContext.terminate(intPredicate, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda14
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(intStreamStream.anyMatch((IntPredicate) obj));
                }
            })).booleanValue();
        }
        return stream().anyMatch(intPredicate);
    }

    @Override // java.util.stream.IntStream
    public boolean allMatch(IntPredicate intPredicate) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final IntStream intStreamStream = stream();
            Objects.requireNonNull(intStreamStream);
            return ((Boolean) streamContext.terminate(intPredicate, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(intStreamStream.allMatch((IntPredicate) obj));
                }
            })).booleanValue();
        }
        return stream().allMatch(intPredicate);
    }

    @Override // java.util.stream.IntStream
    public boolean noneMatch(IntPredicate intPredicate) {
        return !anyMatch(intPredicate);
    }

    @Override // java.util.stream.IntStream
    public OptionalInt findFirst() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final IntStream intStreamStream = stream();
            Objects.requireNonNull(intStreamStream);
            return (OptionalInt) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda11
                @Override // java.util.function.Supplier
                public final Object get() {
                    return intStreamStream.findFirst();
                }
            });
        }
        return stream().findFirst();
    }

    @Override // java.util.stream.IntStream
    public OptionalInt findAny() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final IntStream intStreamStream = stream();
            Objects.requireNonNull(intStreamStream);
            return (OptionalInt) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.IntStreamEx$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final Object get() {
                    return intStreamStream.findAny();
                }
            });
        }
        return stream().findAny();
    }

    @Override // java.util.stream.IntStream
    public LongStreamEx asLongStream() {
        return new LongStreamEx(stream().asLongStream(), this.context);
    }

    @Override // java.util.stream.IntStream
    public DoubleStreamEx asDoubleStream() {
        return new DoubleStreamEx(stream().asDoubleStream(), this.context);
    }

    @Override // java.util.stream.IntStream
    public StreamEx<Integer> boxed() {
        return new StreamEx<>(stream().boxed(), this.context);
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public IntStreamEx sequential() {
        return (IntStreamEx) super.sequential();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public IntStreamEx parallel() {
        return (IntStreamEx) super.parallel();
    }

    @Override // java.util.stream.BaseStream, java.util.stream.IntStream
    public Iterator<Integer> iterator() {
        return Spliterators.iterator((Spliterator.OfInt) spliterator());
    }
}
