package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrCallOrBuilder extends MessageLiteOrBuilder {
    MemberAccessCommon getMemberAccess();

    int getOriginName();

    long getSuper();

    long getSymbol();

    boolean hasMemberAccess();

    boolean hasOriginName();

    boolean hasSuper();

    boolean hasSymbol();
}
