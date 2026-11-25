package org.jetbrains.kotlin.one.util.streamex;

import java.util.DoubleSummaryStatistics;
import java.util.Iterator;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

/* JADX INFO: loaded from: classes2.dex */
public class DoubleStreamEx extends BaseStreamEx<Double, DoubleStream, Spliterator.OfDouble, DoubleStreamEx> implements DoubleStream {
    /* JADX INFO: renamed from: $r8$lambda$-PWp-zbAWExIMM1a2JPyZBDlFLk, reason: not valid java name */
    public static /* synthetic */ DoubleSummaryStatistics m7349$r8$lambda$PWpzbAWExIMM1a2JPyZBDlFLk() {
        return new DoubleSummaryStatistics();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // java.util.stream.DoubleStream
    public /* bridge */ /* synthetic */ DoubleStream flatMap(DoubleFunction doubleFunction) {
        return flatMap((DoubleFunction<? extends DoubleStream>) doubleFunction);
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public /* bridge */ /* synthetic */ boolean isParallel() {
        return super.isParallel();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.Iterable
    public /* bridge */ /* synthetic */ Spliterator.OfDouble spliterator() {
        return (Spliterator.OfDouble) super.spliterator();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream, java.lang.Iterable
    public /* bridge */ /* synthetic */ Spliterator spliterator() {
        return super.spliterator();
    }

    DoubleStreamEx(DoubleStream doubleStream, StreamContext streamContext) {
        super(doubleStream, streamContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx
    public DoubleStream createStream() {
        return StreamSupport.doubleStream((Spliterator.OfDouble) this.spliterator, isParallel());
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public DoubleStreamEx unordered() {
        return (DoubleStreamEx) super.unordered();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public DoubleStreamEx onClose(Runnable runnable) {
        return (DoubleStreamEx) super.onClose(runnable);
    }

    @Override // java.util.stream.DoubleStream
    public DoubleStreamEx filter(DoublePredicate doublePredicate) {
        return new DoubleStreamEx(stream().filter(doublePredicate), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public DoubleStreamEx map(DoubleUnaryOperator doubleUnaryOperator) {
        return new DoubleStreamEx(stream().map(doubleUnaryOperator), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public <U> StreamEx<U> mapToObj(DoubleFunction<? extends U> doubleFunction) {
        return new StreamEx<>(stream().mapToObj(doubleFunction), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public IntStreamEx mapToInt(DoubleToIntFunction doubleToIntFunction) {
        return new IntStreamEx(stream().mapToInt(doubleToIntFunction), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public LongStreamEx mapToLong(DoubleToLongFunction doubleToLongFunction) {
        return new LongStreamEx(stream().mapToLong(doubleToLongFunction), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public DoubleStreamEx flatMap(DoubleFunction<? extends DoubleStream> doubleFunction) {
        return new DoubleStreamEx(stream().flatMap(doubleFunction), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public DoubleStreamEx distinct() {
        return new DoubleStreamEx(stream().distinct(), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public DoubleStreamEx sorted() {
        return new DoubleStreamEx(stream().sorted(), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public DoubleStreamEx peek(DoubleConsumer doubleConsumer) {
        return new DoubleStreamEx(stream().peek(doubleConsumer), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public DoubleStreamEx limit(long j) {
        return new DoubleStreamEx(stream().limit(j), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public DoubleStreamEx skip(long j) {
        return new DoubleStreamEx(stream().skip(j), this.context);
    }

    @Override // java.util.stream.DoubleStream
    public void forEach(final DoubleConsumer doubleConsumer) {
        if (this.spliterator != 0 && !isParallel()) {
            ((Spliterator.OfDouble) spliterator()).forEachRemaining(doubleConsumer);
        } else if (this.context.fjp != null) {
            this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7352x6458175a(doubleConsumer);
                }
            });
        } else {
            stream().forEach(doubleConsumer);
        }
    }

    /* JADX INFO: renamed from: lambda$forEach$12$org-jetbrains-kotlin-one-util-streamex-DoubleStreamEx, reason: not valid java name */
    /* synthetic */ Object m7352x6458175a(DoubleConsumer doubleConsumer) {
        stream().forEach(doubleConsumer);
        return null;
    }

    @Override // java.util.stream.DoubleStream
    public void forEachOrdered(final DoubleConsumer doubleConsumer) {
        if (this.spliterator != 0 && !isParallel()) {
            ((Spliterator.OfDouble) spliterator()).forEachRemaining(doubleConsumer);
        } else if (this.context.fjp != null) {
            this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7353x1db97168(doubleConsumer);
                }
            });
        } else {
            stream().forEachOrdered(doubleConsumer);
        }
    }

    /* JADX INFO: renamed from: lambda$forEachOrdered$13$org-jetbrains-kotlin-one-util-streamex-DoubleStreamEx, reason: not valid java name */
    /* synthetic */ Object m7353x1db97168(DoubleConsumer doubleConsumer) {
        stream().forEachOrdered(doubleConsumer);
        return null;
    }

    @Override // java.util.stream.DoubleStream
    public double[] toArray() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final DoubleStream doubleStreamStream = stream();
            Objects.requireNonNull(doubleStreamStream);
            return (double[]) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    return doubleStreamStream.toArray();
                }
            });
        }
        return stream().toArray();
    }

    @Override // java.util.stream.DoubleStream
    public double reduce(final double d, final DoubleBinaryOperator doubleBinaryOperator) {
        if (this.context.fjp != null) {
            return ((Double) this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda16
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7354xed0a9046(d, doubleBinaryOperator);
                }
            })).doubleValue();
        }
        return stream().reduce(d, doubleBinaryOperator);
    }

    /* JADX INFO: renamed from: lambda$reduce$14$org-jetbrains-kotlin-one-util-streamex-DoubleStreamEx, reason: not valid java name */
    /* synthetic */ Double m7354xed0a9046(double d, DoubleBinaryOperator doubleBinaryOperator) {
        return Double.valueOf(stream().reduce(d, doubleBinaryOperator));
    }

    @Override // java.util.stream.DoubleStream
    public OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final DoubleStream doubleStreamStream = stream();
            Objects.requireNonNull(doubleStreamStream);
            return (OptionalDouble) streamContext.terminate(doubleBinaryOperator, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return doubleStreamStream.reduce((DoubleBinaryOperator) obj);
                }
            });
        }
        return stream().reduce(doubleBinaryOperator);
    }

    @Override // java.util.stream.DoubleStream
    public <R> R collect(final Supplier<R> supplier, final ObjDoubleConsumer<R> objDoubleConsumer, final BiConsumer<R, R> biConsumer) {
        if (this.context.fjp != null) {
            return (R) this.context.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7351x82d02420(supplier, objDoubleConsumer, biConsumer);
                }
            });
        }
        return (R) stream().collect(supplier, objDoubleConsumer, biConsumer);
    }

    /* JADX INFO: renamed from: lambda$collect$18$org-jetbrains-kotlin-one-util-streamex-DoubleStreamEx, reason: not valid java name */
    /* synthetic */ Object m7351x82d02420(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer) {
        return stream().collect(supplier, objDoubleConsumer, biConsumer);
    }

    @Override // java.util.stream.DoubleStream
    public double sum() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final DoubleStream doubleStreamStream = stream();
            Objects.requireNonNull(doubleStreamStream);
            return ((Double) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda17
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Double.valueOf(doubleStreamStream.sum());
                }
            })).doubleValue();
        }
        return stream().sum();
    }

    @Override // java.util.stream.DoubleStream
    public OptionalDouble min() {
        return reduce(new DoubleBinaryOperator() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda3
            @Override // java.util.function.DoubleBinaryOperator
            public final double applyAsDouble(double d, double d2) {
                return Math.min(d, d2);
            }
        });
    }

    @Override // java.util.stream.DoubleStream
    public OptionalDouble max() {
        return reduce(new DoubleBinaryOperator() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda12
            @Override // java.util.function.DoubleBinaryOperator
            public final double applyAsDouble(double d, double d2) {
                return Math.max(d, d2);
            }
        });
    }

    @Override // java.util.stream.DoubleStream
    public long count() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final DoubleStream doubleStreamStream = stream();
            Objects.requireNonNull(doubleStreamStream);
            return ((Long) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda13
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Long.valueOf(doubleStreamStream.count());
                }
            })).longValue();
        }
        return stream().count();
    }

    @Override // java.util.stream.DoubleStream
    public OptionalDouble average() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final DoubleStream doubleStreamStream = stream();
            Objects.requireNonNull(doubleStreamStream);
            return (OptionalDouble) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda11
                @Override // java.util.function.Supplier
                public final Object get() {
                    return doubleStreamStream.average();
                }
            });
        }
        return stream().average();
    }

    @Override // java.util.stream.DoubleStream
    public DoubleSummaryStatistics summaryStatistics() {
        return (DoubleSummaryStatistics) collect(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return DoubleStreamEx.m7349$r8$lambda$PWpzbAWExIMM1a2JPyZBDlFLk();
            }
        }, new ObjDoubleConsumer() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda9
            @Override // java.util.function.ObjDoubleConsumer
            public final void accept(Object obj, double d) {
                ((DoubleSummaryStatistics) obj).accept(d);
            }
        }, new BiConsumer() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((DoubleSummaryStatistics) obj).combine((DoubleSummaryStatistics) obj2);
            }
        });
    }

    @Override // java.util.stream.DoubleStream
    public boolean anyMatch(DoublePredicate doublePredicate) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final DoubleStream doubleStreamStream = stream();
            Objects.requireNonNull(doubleStreamStream);
            return ((Boolean) streamContext.terminate(doublePredicate, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(doubleStreamStream.anyMatch((DoublePredicate) obj));
                }
            })).booleanValue();
        }
        return stream().anyMatch(doublePredicate);
    }

    @Override // java.util.stream.DoubleStream
    public boolean allMatch(DoublePredicate doublePredicate) {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final DoubleStream doubleStreamStream = stream();
            Objects.requireNonNull(doubleStreamStream);
            return ((Boolean) streamContext.terminate(doublePredicate, new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(doubleStreamStream.allMatch((DoublePredicate) obj));
                }
            })).booleanValue();
        }
        return stream().allMatch(doublePredicate);
    }

    @Override // java.util.stream.DoubleStream
    public boolean noneMatch(DoublePredicate doublePredicate) {
        return !anyMatch(doublePredicate);
    }

    @Override // java.util.stream.DoubleStream
    public OptionalDouble findFirst() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final DoubleStream doubleStreamStream = stream();
            Objects.requireNonNull(doubleStreamStream);
            return (OptionalDouble) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return doubleStreamStream.findFirst();
                }
            });
        }
        return stream().findFirst();
    }

    @Override // java.util.stream.DoubleStream
    public OptionalDouble findAny() {
        if (this.context.fjp != null) {
            StreamContext streamContext = this.context;
            final DoubleStream doubleStreamStream = stream();
            Objects.requireNonNull(doubleStreamStream);
            return (OptionalDouble) streamContext.terminate(new Supplier() { // from class: org.jetbrains.kotlin.one.util.streamex.DoubleStreamEx$$ExternalSyntheticLambda14
                @Override // java.util.function.Supplier
                public final Object get() {
                    return doubleStreamStream.findAny();
                }
            });
        }
        return stream().findAny();
    }

    @Override // java.util.stream.DoubleStream
    public StreamEx<Double> boxed() {
        return new StreamEx<>(stream().boxed(), this.context);
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public DoubleStreamEx sequential() {
        return (DoubleStreamEx) super.sequential();
    }

    @Override // org.jetbrains.kotlin.one.util.streamex.BaseStreamEx, java.util.stream.BaseStream
    public DoubleStreamEx parallel() {
        return (DoubleStreamEx) super.parallel();
    }

    @Override // java.util.stream.BaseStream, java.util.stream.DoubleStream
    public Iterator<Double> iterator() {
        return Spliterators.iterator((Spliterator.OfDouble) spliterator());
    }
}
