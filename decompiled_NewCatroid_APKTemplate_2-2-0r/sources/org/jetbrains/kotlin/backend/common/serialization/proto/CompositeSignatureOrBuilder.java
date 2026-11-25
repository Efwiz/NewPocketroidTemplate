package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface CompositeSignatureOrBuilder extends MessageLiteOrBuilder {
    int getContainerSig();

    int getInnerSig();

    boolean hasContainerSig();

    boolean hasInnerSig();
}
