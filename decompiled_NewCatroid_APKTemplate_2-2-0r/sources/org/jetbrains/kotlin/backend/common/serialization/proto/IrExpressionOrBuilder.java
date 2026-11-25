package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrExpressionOrBuilder extends MessageLiteOrBuilder {
    long getCoordinates();

    IrOperation getOperation();

    int getType();

    boolean hasCoordinates();

    boolean hasOperation();

    boolean hasType();
}
