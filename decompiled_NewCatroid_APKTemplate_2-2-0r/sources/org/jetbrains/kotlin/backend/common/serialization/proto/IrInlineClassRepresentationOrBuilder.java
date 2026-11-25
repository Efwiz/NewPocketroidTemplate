package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrInlineClassRepresentationOrBuilder extends MessageLiteOrBuilder {
    int getUnderlyingPropertyName();

    int getUnderlyingPropertyType();

    boolean hasUnderlyingPropertyName();

    boolean hasUnderlyingPropertyType();
}
