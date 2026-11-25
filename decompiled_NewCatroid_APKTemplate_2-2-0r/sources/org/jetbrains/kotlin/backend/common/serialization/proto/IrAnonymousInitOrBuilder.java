package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrAnonymousInitOrBuilder extends MessageLiteOrBuilder {
    IrDeclarationBase getBase();

    int getBody();

    boolean hasBase();

    boolean hasBody();
}
