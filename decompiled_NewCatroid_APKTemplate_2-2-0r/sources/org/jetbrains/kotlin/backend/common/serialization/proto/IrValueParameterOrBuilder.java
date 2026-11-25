package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrValueParameterOrBuilder extends MessageLiteOrBuilder {
    IrDeclarationBase getBase();

    int getDefaultValue();

    long getNameType();

    int getVarargElementType();

    boolean hasBase();

    boolean hasDefaultValue();

    boolean hasNameType();

    boolean hasVarargElementType();
}
