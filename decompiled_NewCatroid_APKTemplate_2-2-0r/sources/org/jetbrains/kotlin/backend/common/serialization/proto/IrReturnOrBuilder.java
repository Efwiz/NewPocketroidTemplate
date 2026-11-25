package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrReturnOrBuilder extends MessageLiteOrBuilder {
    long getReturnTarget();

    IrExpression getValue();

    boolean hasReturnTarget();

    boolean hasValue();
}
