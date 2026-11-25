package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrCatchOrBuilder extends MessageLiteOrBuilder {
    IrVariable getCatchParameter();

    IrExpression getResult();

    boolean hasCatchParameter();

    boolean hasResult();
}
