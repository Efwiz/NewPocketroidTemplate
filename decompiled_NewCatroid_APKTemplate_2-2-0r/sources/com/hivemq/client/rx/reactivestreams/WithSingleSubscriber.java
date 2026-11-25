package com.hivemq.client.rx.reactivestreams;

import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes10.dex */
public interface WithSingleSubscriber<T, S> extends Subscriber<T> {
    void onSingle(S s);
}
