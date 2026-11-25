package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrTypeOrBuilder extends MessageLiteOrBuilder {
    IrDefinitelyNotNullType getDnn();

    IrDynamicType getDynamic();

    IrErrorType getError();

    IrSimpleTypeLegacy getLegacySimple();

    IrSimpleType getSimple();

    boolean hasDnn();

    boolean hasDynamic();

    boolean hasError();

    boolean hasLegacySimple();

    boolean hasSimple();
}
