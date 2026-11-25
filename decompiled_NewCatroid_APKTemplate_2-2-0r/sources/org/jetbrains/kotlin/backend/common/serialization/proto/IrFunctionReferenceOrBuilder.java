package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrFunctionReferenceOrBuilder extends MessageLiteOrBuilder {
    MemberAccessCommon getMemberAccess();

    int getOriginName();

    long getReflectionTargetSymbol();

    long getSymbol();

    boolean hasMemberAccess();

    boolean hasOriginName();

    boolean hasReflectionTargetSymbol();

    boolean hasSymbol();
}
