package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrVarargElementOrBuilder extends MessageLiteOrBuilder {
    IrExpression getExpression();

    IrSpreadElement getSpreadElement();

    boolean hasExpression();

    boolean hasSpreadElement();
}
