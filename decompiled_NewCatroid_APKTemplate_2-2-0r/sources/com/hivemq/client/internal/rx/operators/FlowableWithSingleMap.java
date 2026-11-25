package com.hivemq.client.internal.rx.operators;

import com.hivemq.client.internal.rx.WithSingleConditionalSubscriber;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.rx.FlowableWithSingle;
import com.hivemq.client.rx.FlowableWithSingleSubscriber;
import com.hivemq.client.rx.reactivestreams.WithSingleSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
public class FlowableWithSingleMap<F, S, FM, SM> extends FlowableWithSingleOperator<F, S, FM, SM> {
    private final Function<? super F, ? extends FM> flowableMapper;
    private final Function<? super S, ? extends SM> singleMapper;

    public static <F, S, FM, SM> FlowableWithSingleMap<F, S, FM, SM> mapBoth(FlowableWithSingle<F, S> flowableWithSingle, Function<? super F, ? extends FM> function, Function<? super S, ? extends SM> function2) {
        return new FlowableWithSingleMap<>(flowableWithSingle, function, function2);
    }

    public static <F, S, SM> FlowableWithSingleMap<F, S, F, SM> mapSingle(FlowableWithSingle<F, S> flowableWithSingle, Function<? super S, ? extends SM> function) {
        return new FlowableWithSingleMap<>(flowableWithSingle, null, function);
    }

    private FlowableWithSingleMap(FlowableWithSingle<F, S> flowableWithSingle, Function<? super F, ? extends FM> function, Function<? super S, ? extends SM> function2) {
        super(flowableWithSingle);
        this.flowableMapper = function;
        this.singleMapper = function2;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super FM> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribeBoth((FlowableWithSingleSubscriber<? super FU, ? super SU>) new MapSubscriber.Conditional((ConditionalSubscriber) subscriber, this.flowableMapper, this.singleMapper));
        } else {
            this.source.subscribeBoth((FlowableWithSingleSubscriber<? super FU, ? super SU>) new MapSubscriber(subscriber, this.flowableMapper, this.singleMapper));
        }
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // com.hivemq.client.rx.FlowableWithSingle
    protected void subscribeBothActual(WithSingleSubscriber<? super FM, ? super SM> withSingleSubscriber) {
        if (withSingleSubscriber instanceof WithSingleConditionalSubscriber) {
            this.source.subscribeBoth((FlowableWithSingleSubscriber<? super FU, ? super SU>) new WithSingleMapSubscriber.Conditional((WithSingleConditionalSubscriber) withSingleSubscriber, this.flowableMapper, this.singleMapper));
        } else {
            this.source.subscribeBoth((FlowableWithSingleSubscriber<? super FU, ? super SU>) new WithSingleMapSubscriber(withSingleSubscriber, this.flowableMapper, this.singleMapper));
        }
    }

    private static class MapSubscriber<F, S, FM, SM, T extends Subscriber<? super FM>> implements FlowableWithSingleSubscriber<F, S>, Subscription {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final Function<? super F, ? extends FM> flowableMapper;
        private final Function<? super S, ? extends SM> singleMapper;
        final T subscriber;
        private Subscription subscription;

        void onSingleMapped(SM sm) {
        }

        MapSubscriber(T t, Function<? super F, ? extends FM> function, Function<? super S, ? extends SM> function2) {
            this.subscriber = t;
            this.flowableMapper = function;
            this.singleMapper = function2;
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.subscriber.onSubscribe(this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.hivemq.client.rx.reactivestreams.WithSingleSubscriber
        public void onSingle(S s) {
            try {
                onSingleMapped(Checks.notNull(this.singleMapper.apply(s), "Mapped single value"));
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(F f) {
            Function<? super F, ? extends FM> function = this.flowableMapper;
            if (function == null) {
                this.subscriber.onNext(f);
                return;
            }
            try {
                this.subscriber.onNext(Checks.notNull(function.apply(f), "Mapped value"));
            } catch (Throwable th) {
                fail(th);
            }
        }

        void fail(Throwable th) {
            Exceptions.throwIfFatal(th);
            this.subscription.cancel();
            onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.subscriber.onError(th);
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

        private static class Conditional<F, S, FM, SM, T extends ConditionalSubscriber<? super FM>> extends MapSubscriber<F, S, FM, SM, T> implements WithSingleConditionalSubscriber<F, S> {
            Conditional(T t, Function<? super F, ? extends FM> function, Function<? super S, ? extends SM> function2) {
                super(t, function, function2);
            }

            @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
            public boolean tryOnNext(F f) {
                if (this.flowableMapper == null) {
                    return ((ConditionalSubscriber) this.subscriber).tryOnNext(f);
                }
                try {
                    return ((ConditionalSubscriber) this.subscriber).tryOnNext(Checks.notNull(this.flowableMapper.apply(f), "Mapped value"));
                } catch (Throwable th) {
                    fail(th);
                    return false;
                }
            }
        }
    }

    private static class WithSingleMapSubscriber<F, S, FM, SM> extends MapSubscriber<F, S, FM, SM, WithSingleSubscriber<? super FM, ? super SM>> {
        WithSingleMapSubscriber(WithSingleSubscriber<? super FM, ? super SM> withSingleSubscriber, Function<? super F, ? extends FM> function, Function<? super S, ? extends SM> function2) {
            super(withSingleSubscriber, function, function2);
        }

        @Override // com.hivemq.client.internal.rx.operators.FlowableWithSingleMap.MapSubscriber
        void onSingleMapped(SM sm) {
            ((WithSingleSubscriber) this.subscriber).onSingle(sm);
        }

        private static class Conditional<F, S, FM, SM> extends MapSubscriber.Conditional<F, S, FM, SM, WithSingleConditionalSubscriber<? super FM, ? super SM>> {
            Conditional(WithSingleConditionalSubscriber<? super FM, ? super SM> withSingleConditionalSubscriber, Function<? super F, ? extends FM> function, Function<? super S, ? extends SM> function2) {
                super(withSingleConditionalSubscriber, function, function2);
            }

            @Override // com.hivemq.client.internal.rx.operators.FlowableWithSingleMap.MapSubscriber
            void onSingleMapped(SM sm) {
                ((WithSingleConditionalSubscriber) this.subscriber).onSingle(sm);
            }
        }
    }
}
