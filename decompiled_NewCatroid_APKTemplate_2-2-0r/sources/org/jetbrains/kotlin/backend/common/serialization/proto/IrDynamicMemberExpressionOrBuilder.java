package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrDynamicMemberExpressionOrBuilder extends MessageLiteOrBuilder {
    int getMemberName();

    IrExpression getReceiver();

    boolean hasMemberName();

    boolean hasReceiver();
}
