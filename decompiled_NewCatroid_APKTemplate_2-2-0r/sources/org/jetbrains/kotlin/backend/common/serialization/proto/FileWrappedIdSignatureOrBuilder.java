package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface FileWrappedIdSignatureOrBuilder extends MessageLiteOrBuilder {
    IdSignature getDelegate();

    int getFile();

    boolean hasDelegate();

    boolean hasFile();
}
