package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface FieldAccessCommonOrBuilder extends MessageLiteOrBuilder {
    IrExpression getReceiver();

    long getSuper();

    long getSymbol();

    boolean hasReceiver();

    boolean hasSuper();

    boolean hasSymbol();
}
