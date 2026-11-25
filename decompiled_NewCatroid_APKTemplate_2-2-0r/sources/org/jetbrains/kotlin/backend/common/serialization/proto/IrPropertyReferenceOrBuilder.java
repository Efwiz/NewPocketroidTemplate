package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrPropertyReferenceOrBuilder extends MessageLiteOrBuilder {
    long getField();

    long getGetter();

    MemberAccessCommon getMemberAccess();

    int getOriginName();

    long getSetter();

    long getSymbol();

    boolean hasField();

    boolean hasGetter();

    boolean hasMemberAccess();

    boolean hasOriginName();

    boolean hasSetter();

    boolean hasSymbol();
}
