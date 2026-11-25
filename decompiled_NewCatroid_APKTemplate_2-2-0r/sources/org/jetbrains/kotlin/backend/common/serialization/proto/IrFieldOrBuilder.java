package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrFieldOrBuilder extends MessageLiteOrBuilder {
    IrDeclarationBase getBase();

    int getInitializer();

    long getNameType();

    boolean hasBase();

    boolean hasInitializer();

    boolean hasNameType();
}
