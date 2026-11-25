package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrDeclarationOrBuilder extends MessageLiteOrBuilder {
    IrAnonymousInit getIrAnonymousInit();

    IrClass getIrClass();

    IrConstructor getIrConstructor();

    IrEnumEntry getIrEnumEntry();

    IrErrorDeclaration getIrErrorDeclaration();

    IrField getIrField();

    IrFunction getIrFunction();

    IrLocalDelegatedProperty getIrLocalDelegatedProperty();

    IrProperty getIrProperty();

    IrTypeAlias getIrTypeAlias();

    IrTypeParameter getIrTypeParameter();

    IrValueParameter getIrValueParameter();

    IrVariable getIrVariable();

    boolean hasIrAnonymousInit();

    boolean hasIrClass();

    boolean hasIrConstructor();

    boolean hasIrEnumEntry();

    boolean hasIrErrorDeclaration();

    boolean hasIrField();

    boolean hasIrFunction();

    boolean hasIrLocalDelegatedProperty();

    boolean hasIrProperty();

    boolean hasIrTypeAlias();

    boolean hasIrTypeParameter();

    boolean hasIrValueParameter();

    boolean hasIrVariable();
}
