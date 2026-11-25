package org.jetbrains.kotlin.one.util.streamex;

import java.util.Iterator;
import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

/* JADX INFO: loaded from: classes2.dex */
public class LongStreamEx extends BaseStreamEx<Long, LongStream, Spliterator.OfLong, LongStreamEx> implements LongStream {
    public static /* synthetic */ LongSummaryStatistics $r8$lambda$n4gPvXQHuYhit_3monEa3ZsuSWw() {
        return new LongSummaryStatistics();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // java.util.stream.LongStream
    public /* bridge */ /* synthetic */ LongStream flatMap(LongFunction longFunction) {
        return flatMap((LongFunction<? extends LongStream>) longFunction);
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public /* bridge */ /* synthetic */ boolean isParallel() {
        return super.isParallel();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.Iterable
    public /* bridge */ /* synthetic */ Spliterator.OfLong spliterator() {
        return (Spliterator.OfLong) super.spliterator();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.Iterable
    public /* bridge */ /* synthetic */ Spliterator spliterator() {
        return super.spliterator();
    }

    LongStreamEx(LongStream longStream, StreamContext streamContext) {
        super(longStream, streamContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx
    public LongStream createStream() {
        return StreamSupport.longStream((Spliterator.OfLong) this.spliterator, isParallel());
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public LongStreamEx unordered() {
        return (LongStreamEx) super.unordered();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public LongStreamEx onClose(Runnable runnable) {
        return (LongStreamEx) super.onClose(runnable);
    }

    @Override // java.util.stream.LongStream
    public LongStreamEx filter(LongPredicate longPredicate) {
        return new LongStreamEx(stream().filter(longPredicate), this.context);
    }

    @Override // java.util.stream.LongStream
    public LongStreamEx map(LongUnaryOperator longUnaryOperator) {
        return new LongStreamEx(stream().map(longUnaryOperator), this.context);
    }

    @Override // java.util.stream.LongStream
    public <U> StreamEx<U> mapToObj(LongFunction<? extends U> longFunction) {
        return new StreamEx<>(stream().mapToObj(longFunction), this.context);
    }

    @Override // java.util.stream.LongStream
    public IntStreamEx mapToInt(LongToIntFunction longToIntFunction) {
        return new IntStreamEx(stream().mapToInt(longToIntFunction), this.context);
    }

    @Override // java.util.stream.LongStream
    public DoubleStreamEx mapToDouble(LongToDoubleFunction longToDoubleFunction) {
        return new DoubleStreamEx(stream().mapToDouble(longToDoubleFunction), this.context);
    }

    @Override // java.util.stream.LongStream
    public LongStreamEx flatMap(LongFunction<? extends LongStream> longFunction) {
        return new LongStreamEx(stream().flatMap(longFunction), this.context);
    }

    @Override // java.util.stream.LongStream
    public LongStreamEx distinct() {
        return new LongStreamEx(stream().distinct(), this.context);
    }

    @Override // java.util.stream.LongStream
    public LongStreamEx sorted() {
        return new LongStreamEx(stream().sorted(), this.context);
    }

    @Override // java.util.stream.LongStream
    public LongStreamEx peek(LongConsumer longConsumer) {
        return new LongStreamEx(stream().peek(longConsumer), this.context);
    }

    @Override // java.util.stream.LongStream
    public LongStreamEx limit(long j) {
        return new LongStreamEx(stream().limit(j), this.context);
    }

    @Override // java.util.stream.LongStream
    public LongStreamEx skip(long j) {
        return new LongStreamEx(stream().skip(j), this.context);
    }

    @Override // java.util.stream.LongStream
    public void forEach(final LongConsumer longConsumer) {
        if (this.spliterator != 0 && !isParallel()) {
            ((Spliterator.OfLong) spliterator()).forEachRemaining(longConsumer);
        } else if (this.context.fjp != null) {
            this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7365x8e29f787(longConsumer);
                }
            });
        } else {
            stream().forEach(longConsumer);
        }
    }

    /* JADX INFO: renamed from: lambda$forEach$14$org-jetbrains-kotlin-one-util-streamex-LongStreamEx, reason: not valid java name */
    /* synthetic */ Object m7365x8e29f787(LongConsumer longConsumer) {
        stream().forEach(longConsumer);
        return null;
    }

    @Override // java.util.stream.LongStream
    public void forEachOrdered(final LongConsumer longConsumer) {
        if (this.spliterator != 0 && !isParallel()) {
            ((Spliterator.OfLong) spliterator()).forEachRemaining(longConsumer);
        } else if (this.context.fjp != null) {
            this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda14
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7366x30967d15(longConsumer);
                }
            });
        } else {
            stream().forEachOrdered(longConsumer);
        }
    }

    /* JADX INFO: renamed from: lambda$forEachOrdered$15$org-jetbrains-kotlin-one-util-streamex-LongStreamEx, reason: not valid java name */
    /* synthetic */ Object m7366x30967d15(LongConsumer longConsumer) {
        stream().forEachOrdered(longConsumer);
        return null;
    }

    @Override // java.util.stream.LongStream
    public long[] toArray() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final LongStream longStreamStream = stream();
            Objects.requireNonNull(longStreamStream);
            return (long[]) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda10
                @Override // java.util.function.Supplier
                public final Object get() {
                    return longStreamStream.toArray();
                }
            });
        }
        return stream().toArray();
    }

    @Override // java.util.stream.LongStream
    public long reduce(final long j, final LongBinaryOperator longBinaryOperator) {
        if (this.context.fjp != null) {
            return ((Long) this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7367x6cbdbb73(j, longBinaryOperator);
                }
            })).longValue();
        }
        return stream().reduce(j, longBinaryOperator);
    }

    /* JADX INFO: renamed from: lambda$reduce$16$org-jetbrains-kotlin-one-util-streamex-LongStreamEx, reason: not valid java name */
    /* synthetic */ Long m7367x6cbdbb73(long j, LongBinaryOperator longBinaryOperator) {
        return Long.valueOf(stream().reduce(j, longBinaryOperator));
    }

    @Override // java.util.stream.LongStream
    public OptionalLong reduce(LongBinaryOperator longBinaryOperator) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final LongStream longStreamStream = stream();
            Objects.requireNonNull(longStreamStream);
            return (OptionalLong) streamContext.terminate(longBinaryOperator, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda11
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return longStreamStream.reduce((LongBinaryOperator) obj);
                }
            });
        }
        return stream().reduce(longBinaryOperator);
    }

    @Override // java.util.stream.LongStream
    public <R> R collect(final Supplier<R> supplier, final ObjLongConsumer<R> objLongConsumer, final BiConsumer<R, R> biConsumer) {
        if (this.context.fjp != null) {
            return (R) this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda13
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7364x607b0d62(supplier, objLongConsumer, biConsumer);
                }
            });
        }
        return (R) stream().collect(supplier, objLongConsumer, biConsumer);
    }

    /* JADX INFO: renamed from: lambda$collect$20$org-jetbrains-kotlin-one-util-streamex-LongStreamEx, reason: not valid java name */
    /* synthetic */ Object m7364x607b0d62(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer) {
        return stream().collect(supplier, objLongConsumer, biConsumer);
    }

    @Override // java.util.stream.LongStream
    public long sum() {
        return reduce(0L, new LongBinaryOperator() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda1
            @Override // java.util.function.LongBinaryOperator
            public final long applyAsLong(long j, long j2) {
                return Long.sum(j, j2);
            }
        });
    }

    @Override // java.util.stream.LongStream
    public OptionalLong min() {
        return reduce(new LongBinaryOperator() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda4
            @Override // java.util.function.LongBinaryOperator
            public final long applyAsLong(long j, long j2) {
                return Long.min(j, j2);
            }
        });
    }

    @Override // java.util.stream.LongStream
    public OptionalLong max() {
        return reduce(new LongBinaryOperator() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda2
            @Override // java.util.function.LongBinaryOperator
            public final long applyAsLong(long j, long j2) {
                return Long.max(j, j2);
            }
        });
    }

    @Override // java.util.stream.LongStream
    public long count() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final LongStream longStreamStream = stream();
            Objects.requireNonNull(longStreamStream);
            return ((Long) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Long.valueOf(longStreamStream.count());
                }
            })).longValue();
        }
        return stream().count();
    }

    @Override // java.util.stream.LongStream
    public OptionalDouble average() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final LongStream longStreamStream = stream();
            Objects.requireNonNull(longStreamStream);
            return (OptionalDouble) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    return longStreamStream.average();
                }
            });
        }
        return stream().average();
    }

    @Override // java.util.stream.LongStream
    public LongSummaryStatistics summaryStatistics() {
        return (LongSummaryStatistics) collect(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda15
            @Override // java.util.function.Supplier
            public final Object get() {
                return LongStreamEx.$r8$lambda$n4gPvXQHuYhit_3monEa3ZsuSWw();
            }
        }, new ObjLongConsumer() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda16
            @Override // java.util.function.ObjLongConsumer
            public final void accept(Object obj, long j) {
                ((LongSummaryStatistics) obj).accept(j);
            }
        }, new BiConsumer() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda17
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((LongSummaryStatistics) obj).combine((LongSummaryStatistics) obj2);
            }
        });
    }

    @Override // java.util.stream.LongStream
    public boolean anyMatch(LongPredicate longPredicate) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final LongStream longStreamStream = stream();
            Objects.requireNonNull(longStreamStream);
            return ((Boolean) streamContext.terminate(longPredicate, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(longStreamStream.anyMatch((LongPredicate) obj));
                }
            })).booleanValue();
        }
        return stream().anyMatch(longPredicate);
    }

    @Override // java.util.stream.LongStream
    public boolean allMatch(LongPredicate longPredicate) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final LongStream longStreamStream = stream();
            Objects.requireNonNull(longStreamStream);
            return ((Boolean) streamContext.terminate(longPredicate, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(longStreamStream.allMatch((LongPredicate) obj));
                }
            })).booleanValue();
        }
        return stream().allMatch(longPredicate);
    }

    @Override // java.util.stream.LongStream
    public boolean noneMatch(LongPredicate longPredicate) {
        return !anyMatch(longPredicate);
    }

    @Override // java.util.stream.LongStream
    public OptionalLong findFirst() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final LongStream longStreamStream = stream();
            Objects.requireNonNull(longStreamStream);
            return (OptionalLong) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    return longStreamStream.findFirst();
                }
            });
        }
        return stream().findFirst();
    }

    @Override // java.util.stream.LongStream
    public OptionalLong findAny() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final LongStream longStreamStream = stream();
            Objects.requireNonNull(longStreamStream);
            return (OptionalLong) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.LongStreamEx$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final Object get() {
                    return longStreamStream.findAny();
                }
            });
        }
        return stream().findAny();
    }

    @Override // java.util.stream.LongStream
    public DoubleStreamEx asDoubleStream() {
        return new DoubleStreamEx(stream().asDoubleStream(), this.context);
    }

    @Override // java.util.stream.LongStream
    public StreamEx<Long> boxed() {
        return new StreamEx<>(stream().boxed(), this.context);
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public LongStreamEx sequential() {
        return (LongStreamEx) super.sequential();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public LongStreamEx parallel() {
        return (LongStreamEx) super.parallel();
    }

    @Override // java.util.stream.BaseStream, java.util.stream.LongStream
    public Iterator<Long> iterator() {
        return Spliterators.iterator((Spliterator.OfLong) spliterator());
    }
}
