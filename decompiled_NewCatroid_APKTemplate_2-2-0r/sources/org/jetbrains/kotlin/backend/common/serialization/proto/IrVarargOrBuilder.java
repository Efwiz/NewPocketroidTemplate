package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrVarargOrBuilder extends MessageLiteOrBuilder {
    IrVarargElement getElement(int i);

    int getElementCount();

    List<IrVarargElement> getElementList();

    int getElementType();

    boolean hasElementType();
}
