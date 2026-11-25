package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrErrorCallExpressionOrBuilder extends MessageLiteOrBuilder {
    int getDescription();

    IrExpression getReceiver();

    IrExpression getValueArgument(int i);

    int getValueArgumentCount();

    List<IrExpression> getValueArgumentList();

    boolean hasDescription();

    boolean hasReceiver();
}
