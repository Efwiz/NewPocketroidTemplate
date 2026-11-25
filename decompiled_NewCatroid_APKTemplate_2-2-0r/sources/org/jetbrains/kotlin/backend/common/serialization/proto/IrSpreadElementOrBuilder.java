package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrSpreadElementOrBuilder extends MessageLiteOrBuilder {
    long getCoordinates();

    IrExpression getExpression();

    boolean hasCoordinates();

    boolean hasExpression();
}
