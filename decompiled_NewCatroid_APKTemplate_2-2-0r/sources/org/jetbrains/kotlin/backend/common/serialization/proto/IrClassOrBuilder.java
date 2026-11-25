package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrClassOrBuilder extends MessageLiteOrBuilder {
    IrDeclarationBase getBase();

    IrDeclaration getDeclaration(int i);

    int getDeclarationCount();

    List<IrDeclaration> getDeclarationList();

    IrInlineClassRepresentation getInlineClassRepresentation();

    IrMultiFieldValueClassRepresentation getMultiFieldValueClassRepresentation();

    int getName();

    long getSealedSubclass(int i);

    int getSealedSubclassCount();

    List<Long> getSealedSubclassList();

    int getSuperType(int i);

    int getSuperTypeCount();

    List<Integer> getSuperTypeList();

    IrValueParameter getThisReceiver();

    IrTypeParameter getTypeParameter(int i);

    int getTypeParameterCount();

    List<IrTypeParameter> getTypeParameterList();

    boolean hasBase();

    boolean hasInlineClassRepresentation();

    boolean hasMultiFieldValueClassRepresentation();

    boolean hasName();

    boolean hasThisReceiver();
}
