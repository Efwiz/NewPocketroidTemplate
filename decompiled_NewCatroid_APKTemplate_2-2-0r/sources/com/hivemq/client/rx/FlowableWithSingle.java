package com.hivemq.client.rx;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import com.hivemq.client.internal.rx.WithSingleStrictSubscriber;
import com.hivemq.client.internal.rx.operators.FlowableWithSingleMap;
import com.hivemq.client.internal.rx.operators.FlowableWithSingleMapError;
import com.hivemq.client.internal.rx.operators.FlowableWithSingleObserveOn;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.rx.reactivestreams.PublisherWithSingle;
import com.hivemq.client.rx.reactivestreams.WithSingleSubscriber;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
public abstract class FlowableWithSingle<F, S> extends Flowable<F> implements PublisherWithSingle<F, S> {
    protected abstract void subscribeBothActual(WithSingleSubscriber<? super F, ? super S> withSingleSubscriber);

    @SchedulerSupport(SchedulerSupport.CUSTOM)
    @BackpressureSupport(BackpressureKind.FULL)
    public final FlowableWithSingle<F, S> observeOnBoth(Scheduler scheduler) {
        return observeOnBoth(scheduler, false, bufferSize());
    }

    @SchedulerSupport(SchedulerSupport.CUSTOM)
    @BackpressureSupport(BackpressureKind.FULL)
    public final FlowableWithSingle<F, S> observeOnBoth(Scheduler scheduler, boolean z) {
        return observeOnBoth(scheduler, z, bufferSize());
    }

    @SchedulerSupport(SchedulerSupport.CUSTOM)
    @BackpressureSupport(BackpressureKind.FULL)
    public final FlowableWithSingle<F, S> observeOnBoth(Scheduler scheduler, boolean z, int i) {
        Checks.notNull(scheduler, "Scheduler");
        return new FlowableWithSingleObserveOn(this, scheduler, z, i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    public final <SM> FlowableWithSingle<F, SM> mapSingle(Function<? super S, ? extends SM> function) {
        Checks.notNull(function, "Single mapper");
        return FlowableWithSingleMap.mapSingle(this, function);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    public final <FM, SM> FlowableWithSingle<FM, SM> mapBoth(Function<? super F, ? extends FM> function, Function<? super S, ? extends SM> function2) {
        Checks.notNull(function, "Flowable mapper");
        Checks.notNull(function2, "Single mapper");
        return FlowableWithSingleMap.mapBoth(this, function, function2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    public final FlowableWithSingle<F, S> mapError(Function<? super Throwable, ? extends Throwable> function) {
        Checks.notNull(function, "Mapper");
        return new FlowableWithSingleMapError(this, function);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    public final FlowableWithSingle<F, S> doOnSingle(final Consumer<? super S> consumer) {
        Checks.notNull(consumer, "Single consumer");
        return FlowableWithSingleMap.mapSingle(this, new Function() { // from class: com.hivemq.client.rx.FlowableWithSingle$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return FlowableWithSingle.lambda$doOnSingle$0(consumer, obj);
            }
        });
    }

    static /* synthetic */ Object lambda$doOnSingle$0(Consumer consumer, Object obj) throws Exception {
        consumer.accept(obj);
        return obj;
    }

    @Override // com.hivemq.client.rx.reactivestreams.PublisherWithSingle
    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    public final void subscribeBoth(WithSingleSubscriber<? super F, ? super S> withSingleSubscriber) {
        if (withSingleSubscriber instanceof FlowableWithSingleSubscriber) {
            subscribeBoth((FlowableWithSingleSubscriber) withSingleSubscriber);
        } else {
            Checks.notNull(withSingleSubscriber, "Subscriber");
            subscribeBothActual(new WithSingleStrictSubscriber(withSingleSubscriber));
        }
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    public final void subscribeBoth(FlowableWithSingleSubscriber<? super F, ? super S> flowableWithSingleSubscriber) {
        Checks.notNull(flowableWithSingleSubscriber, "Subscriber");
        subscribeBothActual(flowableWithSingleSubscriber);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final CompletableFuture<S> subscribeSingleFuture() {
        SingleFutureSubscriber singleFutureSubscriber = new SingleFutureSubscriber(this);
        CompletableFuture<S> futureBeforeSubscribe = singleFutureSubscriber.getFutureBeforeSubscribe();
        singleFutureSubscriber.subscribe();
        return futureBeforeSubscribe;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final CompletableFuture<S> subscribeSingleFuture(Consumer<? super F> consumer) {
        SingleFutureSubscriber singleFutureSubscriber = new SingleFutureSubscriber(this);
        CompletableFuture<S> futureBeforeSubscribe = singleFutureSubscriber.getFutureBeforeSubscribe();
        singleFutureSubscriber.subscribe(consumer);
        return futureBeforeSubscribe;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final CompletableFuture<S> subscribeSingleFuture(Consumer<? super F> consumer, Consumer<? super Throwable> consumer2) {
        SingleFutureSubscriber singleFutureSubscriber = new SingleFutureSubscriber(this);
        CompletableFuture<S> futureBeforeSubscribe = singleFutureSubscriber.getFutureBeforeSubscribe();
        singleFutureSubscriber.subscribe(consumer, consumer2);
        return futureBeforeSubscribe;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final CompletableFuture<S> subscribeSingleFuture(Consumer<? super F> consumer, Consumer<? super Throwable> consumer2, Action action) {
        SingleFutureSubscriber singleFutureSubscriber = new SingleFutureSubscriber(this);
        CompletableFuture<S> futureBeforeSubscribe = singleFutureSubscriber.getFutureBeforeSubscribe();
        singleFutureSubscriber.subscribe(consumer, consumer2, action);
        return futureBeforeSubscribe;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final CompletableFuture<S> subscribeSingleFuture(Subscriber<? super F> subscriber) {
        SingleFutureSubscriber singleFutureSubscriber = new SingleFutureSubscriber(this);
        CompletableFuture<S> futureBeforeSubscribe = singleFutureSubscriber.getFutureBeforeSubscribe();
        singleFutureSubscriber.subscribe(subscriber);
        return futureBeforeSubscribe;
    }

    private static class SingleFutureSubscriber<F, S> extends Flowable<F> implements FlowableWithSingleSubscriber<F, S>, Subscription {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final FlowableWithSingle<F, S> source;
        private Subscriber<? super F> subscriber;
        private final AtomicReference<Subscription> subscription = new AtomicReference<>();
        private final AtomicReference<CompletableFuture<S>> future = new AtomicReference<>(new CompletableFuture<S>() { // from class: com.hivemq.client.rx.FlowableWithSingle.SingleFutureSubscriber.1
            @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
            public boolean cancel(boolean z) {
                SingleFutureSubscriber.this.future.set(null);
                SingleFutureSubscriber.this.cancel();
                return super.cancel(z);
            }
        });

        SingleFutureSubscriber(FlowableWithSingle<F, S> flowableWithSingle) {
            this.source = flowableWithSingle;
        }

        CompletableFuture<S> getFutureBeforeSubscribe() {
            return this.future.get();
        }

        @Override // io.reactivex.Flowable
        protected void subscribeActual(Subscriber<? super F> subscriber) {
            this.subscriber = subscriber;
            this.source.subscribeBoth((FlowableWithSingleSubscriber) this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.subscription, null, subscription)) {
                cancel(subscription);
            }
            this.subscriber.onSubscribe(this);
        }

        @Override // com.hivemq.client.rx.reactivestreams.WithSingleSubscriber
        public void onSingle(S s) {
            CompletableFuture<S> andSet = this.future.getAndSet(null);
            if (andSet != null) {
                andSet.complete(s);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(F f) {
            this.subscriber.onNext(f);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            CompletableFuture<S> andSet = this.future.getAndSet(null);
            if (andSet != null) {
                andSet.completeExceptionally(new NoSuchElementException());
            }
            this.subscriber.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            CompletableFuture<S> andSet = this.future.getAndSet(null);
            if (andSet != null) {
                andSet.completeExceptionally(th);
            }
            this.subscriber.onError(th);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Subscription subscription = this.subscription.get();
            if (subscription != this) {
                subscription.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Subscription andSet = this.subscription.getAndSet(this);
            if (andSet == null || andSet == this) {
                return;
            }
            cancel(andSet);
        }

        private void cancel(Subscription subscription) {
            subscription.cancel();
            CompletableFuture<S> andSet = this.future.getAndSet(null);
            if (andSet != null) {
                andSet.cancel(false);
            }
        }
    }
}
