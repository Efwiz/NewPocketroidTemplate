package com.hivemq.client.internal.rx.operators;

import com.hivemq.client.rx.FlowableWithSingle;

/* JADX INFO: loaded from: classes10.dex */
abstract class FlowableWithSingleOperator<FU, SU, F, S> extends FlowableWithSingle<F, S> {
    final FlowableWithSingle<FU, SU> source;

    FlowableWithSingleOperator(FlowableWithSingle<FU, SU> flowableWithSingle) {
        this.source = flowableWithSingle;
    }
}
