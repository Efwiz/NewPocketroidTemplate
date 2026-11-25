package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrEnumEntryOrBuilder extends MessageLiteOrBuilder {
    IrDeclarationBase getBase();

    IrClass getCorrespondingClass();

    int getInitializer();

    int getName();

    boolean hasBase();

    boolean hasCorrespondingClass();

    boolean hasInitializer();

    boolean hasName();
}
