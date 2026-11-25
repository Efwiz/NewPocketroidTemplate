package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrLocalDelegatedPropertyOrBuilder extends MessageLiteOrBuilder {
    IrDeclarationBase getBase();

    IrVariable getDelegate();

    IrFunction getGetter();

    long getNameType();

    IrFunction getSetter();

    boolean hasBase();

    boolean hasDelegate();

    boolean hasGetter();

    boolean hasNameType();

    boolean hasSetter();
}
