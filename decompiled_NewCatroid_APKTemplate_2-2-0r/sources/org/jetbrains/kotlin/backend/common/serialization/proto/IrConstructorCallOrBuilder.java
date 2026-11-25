package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrConstructorCallOrBuilder extends MessageLiteOrBuilder {
    int getConstructorTypeArgumentsCount();

    MemberAccessCommon getMemberAccess();

    int getOriginName();

    long getSymbol();

    boolean hasConstructorTypeArgumentsCount();

    boolean hasMemberAccess();

    boolean hasOriginName();

    boolean hasSymbol();
}
