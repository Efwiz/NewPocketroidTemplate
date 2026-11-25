package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface LoopOrBuilder extends MessageLiteOrBuilder {
    IrExpression getBody();

    IrExpression getCondition();

    int getLabel();

    int getLoopId();

    int getOriginName();

    boolean hasBody();

    boolean hasCondition();

    boolean hasLabel();

    boolean hasLoopId();

    boolean hasOriginName();
}
