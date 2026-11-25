package com.hivemq.client.internal.rx;

import com.hivemq.client.rx.FlowableWithSingleSubscriber;
import io.reactivex.internal.fuseable.ConditionalSubscriber;

/* JADX INFO: loaded from: classes10.dex */
public interface WithSingleConditionalSubscriber<F, S> extends FlowableWithSingleSubscriber<F, S>, ConditionalSubscriber<F> {
}
