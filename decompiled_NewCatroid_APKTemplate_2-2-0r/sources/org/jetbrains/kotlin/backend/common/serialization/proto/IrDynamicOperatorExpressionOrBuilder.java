package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrDynamicOperatorExpressionOrBuilder extends MessageLiteOrBuilder {
    IrExpression getArgument(int i);

    int getArgumentCount();

    List<IrExpression> getArgumentList();

    IrDynamicOperatorExpression.IrDynamicOperator getOperator();

    IrExpression getReceiver();

    boolean hasOperator();

    boolean hasReceiver();
}
