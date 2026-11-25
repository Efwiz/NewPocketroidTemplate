package com.hivemq.client.internal.rx.operators;

import com.hivemq.client.internal.rx.WithSingleConditionalSubscriber;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.rx.FlowableWithSingle;
import com.hivemq.client.rx.FlowableWithSingleSubscriber;
import com.hivemq.client.rx.reactivestreams.WithSingleSubscriber;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
public class FlowableWithSingleMapError<F, S> extends FlowableWithSingleOperator<F, S, F, S> {
    private final Function<? super Throwable, ? extends Throwable> errorMapper;

    public FlowableWithSingleMapError(FlowableWithSingle<F, S> flowableWithSingle, Function<? super Throwable, ? extends Throwable> function) {
        super(flowableWithSingle);
        this.errorMapper = function;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super F> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new MapErrorSubscriber.Conditional((ConditionalSubscriber) subscriber, this.errorMapper));
        } else {
            this.source.subscribe((FlowableSubscriber) new MapErrorSubscriber(subscriber, this.errorMapper));
        }
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // com.hivemq.client.rx.FlowableWithSingle
    protected void subscribeBothActual(WithSingleSubscriber<? super F, ? super S> withSingleSubscriber) {
        if (withSingleSubscriber instanceof WithSingleConditionalSubscriber) {
            this.source.subscribeBoth((FlowableWithSingleSubscriber<? super FU, ? super SU>) new WithSingleMapErrorSubscriber.Conditional((WithSingleConditionalSubscriber) withSingleSubscriber, this.errorMapper));
        } else {
            this.source.subscribeBoth((FlowableWithSingleSubscriber<? super FU, ? super SU>) new WithSingleMapErrorSubscriber(withSingleSubscriber, this.errorMapper));
        }
    }

    private static class MapErrorSubscriber<F, T extends Subscriber<? super F>> implements FlowableSubscriber<F>, Subscription {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Function<? super Throwable, ? extends Throwable> errorMapper;
        final T subscriber;
        private Subscription subscription;

        MapErrorSubscriber(T t, Function<? super Throwable, ? extends Throwable> function) {
            this.subscriber = t;
            this.errorMapper = function;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.subscriber.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(F f) {
            this.subscriber.onNext(f);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Throwable compositeException;
            try {
                compositeException = (Throwable) Checks.notNull(this.errorMapper.apply(th), "Mapped exception");
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                compositeException = new CompositeException(th, th2);
            }
            this.subscriber.onError(compositeException);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.subscriber.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.subscription.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.subscription.cancel();
        }

        private static class Conditional<F, T extends ConditionalSubscriber<? super F>> extends MapErrorSubscriber<F, T> implements ConditionalSubscriber<F> {
            Conditional(T t, Function<? super Throwable, ? extends Throwable> function) {
                super(t, function);
            }

            @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
            public boolean tryOnNext(F f) {
                return ((ConditionalSubscriber) this.subscriber).tryOnNext(f);
            }
        }
    }

    private static class WithSingleMapErrorSubscriber<F, S> extends MapErrorSubscriber<F, WithSingleSubscriber<? super F, ? super S>> implements FlowableWithSingleSubscriber<F, S> {
        WithSingleMapErrorSubscriber(WithSingleSubscriber<? super F, ? super S> withSingleSubscriber, Function<? super Throwable, ? extends Throwable> function) {
            super(withSingleSubscriber, function);
        }

        @Override // com.hivemq.client.rx.reactivestreams.WithSingleSubscriber
        public void onSingle(S s) {
            ((WithSingleSubscriber) this.subscriber).onSingle(s);
        }

        private static class Conditional<F, S> extends MapErrorSubscriber.Conditional<F, WithSingleConditionalSubscriber<? super F, ? super S>> implements WithSingleConditionalSubscriber<F, S> {
            Conditional(WithSingleConditionalSubscriber<? super F, ? super S> withSingleConditionalSubscriber, Function<? super Throwable, ? extends Throwable> function) {
                super(withSingleConditionalSubscriber, function);
            }

            @Override // com.hivemq.client.rx.reactivestreams.WithSingleSubscriber
            public void onSingle(S s) {
                ((WithSingleConditionalSubscriber) this.subscriber).onSingle(s);
            }
        }
    }
}
