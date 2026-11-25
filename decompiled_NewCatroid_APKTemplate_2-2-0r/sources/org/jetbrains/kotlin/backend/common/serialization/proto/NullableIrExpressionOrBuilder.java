package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface NullableIrExpressionOrBuilder extends MessageLiteOrBuilder {
    IrExpression getExpression();

    boolean hasExpression();
}
