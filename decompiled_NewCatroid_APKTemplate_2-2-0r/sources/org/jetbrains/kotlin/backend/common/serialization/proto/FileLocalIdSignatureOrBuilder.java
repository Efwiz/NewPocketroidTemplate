package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface FileLocalIdSignatureOrBuilder extends MessageLiteOrBuilder {
    int getContainer();

    int getDebugInfo();

    long getLocalId();

    boolean hasContainer();

    boolean hasDebugInfo();

    boolean hasLocalId();
}
