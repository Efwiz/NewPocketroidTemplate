package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrBranchOrBuilder extends MessageLiteOrBuilder {
    IrExpression getCondition();

    IrExpression getResult();

    boolean hasCondition();

    boolean hasResult();
}
