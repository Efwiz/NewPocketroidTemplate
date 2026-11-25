package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrTypeOpOrBuilder extends MessageLiteOrBuilder {
    IrExpression getArgument();

    int getOperand();

    IrTypeOperator getOperator();

    boolean hasArgument();

    boolean hasOperand();

    boolean hasOperator();
}
