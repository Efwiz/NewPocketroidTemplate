package com.hivemq.client.rx.reactivestreams;

import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes10.dex */
public interface PublisherWithSingle<T, S> extends Publisher<T> {
    void subscribeBoth(WithSingleSubscriber<? super T, ? super S> withSingleSubscriber);
}
