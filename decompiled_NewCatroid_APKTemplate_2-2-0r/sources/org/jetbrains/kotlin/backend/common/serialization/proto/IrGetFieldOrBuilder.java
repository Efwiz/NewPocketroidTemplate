package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrGetFieldOrBuilder extends MessageLiteOrBuilder {
    FieldAccessCommon getFieldAccess();

    int getOriginName();

    boolean hasFieldAccess();

    boolean hasOriginName();
}
