package com.hivemq.client.internal.rx.operators;

import com.hivemq.client.internal.rx.WithSingleConditionalSubscriber;
import com.hivemq.client.rx.FlowableWithSingle;
import com.hivemq.client.rx.FlowableWithSingleSubscriber;
import com.hivemq.client.rx.reactivestreams.WithSingleSubscriber;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
class FlowableWithSingleCombine<F, S> extends Flowable<Object> {
    private final FlowableWithSingle<F, S> source;

    FlowableWithSingleCombine(FlowableWithSingle<F, S> flowableWithSingle) {
        this.source = flowableWithSingle;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Object> subscriber) {
        this.source.subscribeBoth((FlowableWithSingleSubscriber) new CombineSubscriber(subscriber));
    }

    private static class CombineSubscriber<F, S> implements FlowableWithSingleSubscriber<F, S>, Subscription {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final Object COMPLETE = new Object();
        private Object done;
        private Object queued;
        private final AtomicLong requested = new AtomicLong();
        private final Subscriber<? super Object> subscriber;
        private Subscription subscription;

        CombineSubscriber(Subscriber<? super Object> subscriber) {
            this.subscriber = subscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.subscriber.onSubscribe(this);
        }

        @Override // com.hivemq.client.rx.reactivestreams.WithSingleSubscriber
        public void onSingle(S s) {
            next(new SingleElement(s));
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(F f) {
            next(f);
        }

        private void next(Object obj) {
            if (this.requested.get() == 0) {
                synchronized (this) {
                    if (this.requested.get() == 0) {
                        this.queued = obj;
                        return;
                    }
                }
            }
            BackpressureHelper.produced(this.requested, 1L);
            this.subscriber.onNext(obj);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            synchronized (this) {
                if (this.queued != null) {
                    this.done = COMPLETE;
                } else {
                    this.subscriber.onComplete();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            synchronized (this) {
                if (this.queued != null) {
                    this.done = th;
                } else {
                    this.subscriber.onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (j > 0) {
                if (BackpressureHelper.add(this.requested, j) == 0) {
                    synchronized (this) {
                        Object obj = this.queued;
                        if (obj != null) {
                            this.queued = null;
                            BackpressureHelper.produced(this.requested, 1L);
                            this.subscriber.onNext(obj);
                            j--;
                            Object obj2 = this.done;
                            if (obj2 != null) {
                                this.done = null;
                                if (obj2 instanceof Throwable) {
                                    this.subscriber.onError((Throwable) obj2);
                                } else {
                                    this.subscriber.onComplete();
                                }
                                return;
                            }
                        }
                        if (j > 0) {
                            this.subscription.request(j);
                        }
                        return;
                    }
                }
                this.subscription.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.subscription.cancel();
        }
    }

    static <F, S> void split(Flowable<Object> flowable, WithSingleSubscriber<? super F, ? super S> withSingleSubscriber) {
        if (withSingleSubscriber instanceof WithSingleConditionalSubscriber) {
            flowable.subscribe((FlowableSubscriber<? super Object>) new SplitSubscriber.Conditional((WithSingleConditionalSubscriber) withSingleSubscriber));
        } else {
            flowable.subscribe((FlowableSubscriber<? super Object>) new SplitSubscriber.Default(withSingleSubscriber));
        }
    }

    private static abstract class SplitSubscriber<F, S, T extends WithSingleSubscriber<? super F, ? super S>> implements ConditionalSubscriber<Object>, Subscription {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final T subscriber;
        private Subscription subscription;

        abstract boolean tryOnNextActual(F f);

        SplitSubscriber(T t) {
            this.subscriber = t;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.subscriber.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (tryOnNext(obj)) {
                return;
            }
            this.subscription.request(1L);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(Object obj) {
            if (obj instanceof SingleElement) {
                this.subscriber.onSingle(((SingleElement) obj).element);
                return false;
            }
            return tryOnNextActual(obj);
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

        private static class Default<F, S> extends SplitSubscriber<F, S, WithSingleSubscriber<? super F, ? super S>> {
            Default(WithSingleSubscriber<? super F, ? super S> withSingleSubscriber) {
                super(withSingleSubscriber);
            }

            @Override // com.hivemq.client.internal.rx.operators.FlowableWithSingleCombine.SplitSubscriber
            boolean tryOnNextActual(F f) {
                this.subscriber.onNext(f);
                return true;
            }
        }

        private static class Conditional<F, S> extends SplitSubscriber<F, S, WithSingleConditionalSubscriber<? super F, ? super S>> {
            Conditional(WithSingleConditionalSubscriber<? super F, ? super S> withSingleConditionalSubscriber) {
                super(withSingleConditionalSubscriber);
            }

            @Override // com.hivemq.client.internal.rx.operators.FlowableWithSingleCombine.SplitSubscriber
            boolean tryOnNextActual(F f) {
                return ((WithSingleConditionalSubscriber) this.subscriber).tryOnNext(f);
            }
        }
    }

    private static class SingleElement {
        final Object element;

        SingleElement(Object obj) {
            this.element = obj;
        }
    }
}
