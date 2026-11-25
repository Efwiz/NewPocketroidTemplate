package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrSetFieldOrBuilder extends MessageLiteOrBuilder {
    FieldAccessCommon getFieldAccess();

    int getOriginName();

    IrExpression getValue();

    boolean hasFieldAccess();

    boolean hasOriginName();

    boolean hasValue();
}
