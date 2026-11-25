package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrSetValueOrBuilder extends MessageLiteOrBuilder {
    int getOriginName();

    long getSymbol();

    IrExpression getValue();

    boolean hasOriginName();

    boolean hasSymbol();

    boolean hasValue();
}
