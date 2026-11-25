package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrEnumConstructorCallOrBuilder extends MessageLiteOrBuilder {
    MemberAccessCommon getMemberAccess();

    long getSymbol();

    boolean hasMemberAccess();

    boolean hasSymbol();
}
