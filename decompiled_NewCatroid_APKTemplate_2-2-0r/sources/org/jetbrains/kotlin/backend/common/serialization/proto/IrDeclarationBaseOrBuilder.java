package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrDeclarationBaseOrBuilder extends MessageLiteOrBuilder {
    IrConstructorCall getAnnotation(int i);

    int getAnnotationCount();

    List<IrConstructorCall> getAnnotationList();

    long getCoordinates();

    long getFlags();

    int getOriginName();

    long getSymbol();

    boolean hasCoordinates();

    boolean hasFlags();

    boolean hasOriginName();

    boolean hasSymbol();
}
