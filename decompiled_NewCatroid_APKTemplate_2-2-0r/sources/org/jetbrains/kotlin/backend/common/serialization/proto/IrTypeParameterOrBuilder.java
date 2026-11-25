package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrTypeParameterOrBuilder extends MessageLiteOrBuilder {
    IrDeclarationBase getBase();

    int getName();

    int getSuperType(int i);

    int getSuperTypeCount();

    List<Integer> getSuperTypeList();

    boolean hasBase();

    boolean hasName();
}
