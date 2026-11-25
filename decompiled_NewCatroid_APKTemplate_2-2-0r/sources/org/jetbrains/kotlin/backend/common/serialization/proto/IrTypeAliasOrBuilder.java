package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrTypeAliasOrBuilder extends MessageLiteOrBuilder {
    IrDeclarationBase getBase();

    long getNameType();

    IrTypeParameter getTypeParameter(int i);

    int getTypeParameterCount();

    List<IrTypeParameter> getTypeParameterList();

    boolean hasBase();

    boolean hasNameType();
}
