package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrFunctionBaseOrBuilder extends MessageLiteOrBuilder {
    IrDeclarationBase getBase();

    int getBody();

    int getContextReceiverParametersCount();

    IrValueParameter getDispatchReceiver();

    IrValueParameter getExtensionReceiver();

    long getNameType();

    IrTypeParameter getTypeParameter(int i);

    int getTypeParameterCount();

    List<IrTypeParameter> getTypeParameterList();

    IrValueParameter getValueParameter(int i);

    int getValueParameterCount();

    List<IrValueParameter> getValueParameterList();

    boolean hasBase();

    boolean hasBody();

    boolean hasContextReceiverParametersCount();

    boolean hasDispatchReceiver();

    boolean hasExtensionReceiver();

    boolean hasNameType();
}
