package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrPropertyOrBuilder extends MessageLiteOrBuilder {
    IrField getBackingField();

    IrDeclarationBase getBase();

    IrFunction getGetter();

    int getName();

    IrFunction getSetter();

    boolean hasBackingField();

    boolean hasBase();

    boolean hasGetter();

    boolean hasName();

    boolean hasSetter();
}
