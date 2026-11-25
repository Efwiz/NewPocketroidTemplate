package com.hivemq.client.internal.rx;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import com.hivemq.client.rx.FlowableWithSingleSubscriber;
import com.hivemq.client.rx.reactivestreams.WithSingleSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
public class WithSingleStrictSubscriber<F, S> implements FlowableWithSingleSubscriber<F, S>, Subscription {
    private Throwable error;
    private final WithSingleSubscriber<F, S> subscriber;
    private final AtomicReference<Subscription> subscription = new AtomicReference<>();
    private final AtomicLong requested = new AtomicLong();
    private final AtomicInteger wip = new AtomicInteger();

    public WithSingleStrictSubscriber(WithSingleSubscriber<F, S> withSingleSubscriber) {
        this.subscriber = withSingleSubscriber;
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.subscription, null, this)) {
            this.subscriber.onSubscribe(this);
            if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.subscription, this, subscription)) {
                long andSet = this.requested.getAndSet(0L);
                if (andSet != 0) {
                    subscription.request(andSet);
                    return;
                }
                return;
            }
            subscription.cancel();
            return;
        }
        subscription.cancel();
        cancel();
        onError(new IllegalStateException("§2.12 violated: onSubscribe must be called at most once"));
    }

    @Override // com.hivemq.client.rx.reactivestreams.WithSingleSubscriber
    public void onSingle(S s) {
        this.subscriber.onSingle(s);
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(F f) {
        if (this.wip.compareAndSet(0, 1)) {
            this.subscriber.onNext(f);
            if (this.wip.decrementAndGet() != 0) {
                Throwable th = this.error;
                if (th == null) {
                    this.subscriber.onComplete();
                } else {
                    this.subscriber.onError(th);
                    this.error = null;
                }
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.error = th;
        if (this.wip.getAndIncrement() == 0) {
            this.subscriber.onError(th);
            this.error = null;
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.wip.getAndIncrement() == 0) {
            this.subscriber.onComplete();
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        if (j <= 0) {
            cancel();
            onError(new IllegalArgumentException("§3.9 violated: positive request amount required but it was " + j));
            return;
        }
        Subscription subscription = this.subscription.get();
        if (subscription != null && subscription != this) {
            subscription.request(j);
            return;
        }
        BackpressureHelper.add(this.requested, j);
        Subscription subscription2 = this.subscription.get();
        if (subscription2 == null || subscription2 == this) {
            return;
        }
        long andSet = this.requested.getAndSet(0L);
        if (andSet != 0) {
            subscription2.request(andSet);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        Subscription andSet = this.subscription.getAndSet(SubscriptionHelper.CANCELLED);
        if (andSet == null || andSet == this || andSet == SubscriptionHelper.CANCELLED) {
            return;
        }
        andSet.cancel();
    }
}
